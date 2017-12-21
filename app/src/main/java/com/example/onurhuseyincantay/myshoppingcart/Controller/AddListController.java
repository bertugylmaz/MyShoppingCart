package com.example.onurhuseyincantay.myshoppingcart.Controller;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.onurhuseyincantay.myshoppingcart.Model.Item;
import com.example.onurhuseyincantay.myshoppingcart.Network.DataService;
import com.example.onurhuseyincantay.myshoppingcart.R;

public class AddListController extends AppCompatActivity {

    private String[] types={"Kg","Litre","Adet"};
    private Spinner typeSpinner;
    private ArrayAdapter<String> typesDataForSpinner;

    private Toolbar toolbar;
    private Button addProductButton;
    private EditText productNameEditText;
    private EditText productCountEditText;
    private String selectedType;
    private String listId;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_add_list);
            Bundle data = getIntent().getExtras();
            listId = data.getString("shoppingListId");

            toolbar = (Toolbar) findViewById(R.id.my_toolbar);
            addProductButton = (Button)findViewById(R.id.addProductButton);
            typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
            productNameEditText = (EditText)findViewById(R.id.productNameEditText);
            productCountEditText = (EditText)findViewById(R.id.productCountEditText);

            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            typesDataForSpinner =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
            typesDataForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(typesDataForSpinner);

            addProductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(productCountEditText.getText().toString().equals("") || productNameEditText.getText().toString().equals("") ){
                        Toast wrongToast = Toast.makeText(getApplicationContext(), "Boş alanları doldurunuzz", Toast.LENGTH_LONG);
                        wrongToast.show();
                    }else {
                        String id = DataService.ds.itemsRef.push().getKey();
                        String weight = productCountEditText.getText().toString() +" "+ typeSpinner.getSelectedItem().toString();
                        String name = productNameEditText.getText().toString();

                        Log.d("TAG", "onClick: " + listId);
                        Item item = new Item(id,listId,name,weight);

                        addItemAction(item);

                        productNameEditText.setText("");
                        productCountEditText.setText("");

                        Toast completedToast = Toast.makeText(getApplicationContext(), "Ürün Eklendi", Toast.LENGTH_LONG);
                        completedToast.show();
                    }
                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        if (menu != null){
            MenuItem logoutButton = menu.findItem(R.id.logout);
            logoutButton.setVisible(false);

            MenuItem addButton= menu.findItem(R.id.add_shopping_cart);
            addButton.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addItemAction(Item item){
        DataService.ds.itemsRef.child(item.getItemId()).updateChildren(item.toMap());
        DataService.ds.addItemOnContainer(listId,item.exportID());
    }
}
