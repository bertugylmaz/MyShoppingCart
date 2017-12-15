package com.example.onurhuseyincantay.myshoppingcart;

import android.annotation.SuppressLint;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.onurhuseyincantay.myshoppingcart.Model.Item;
import com.example.onurhuseyincantay.myshoppingcart.Network.DataService;

import java.util.Map;

public class AddList extends AppCompatActivity {

    private String[] types={"Kg","Litre","Adet"};
    private Spinner typeSpinner;
    private ArrayAdapter<String> typesDataForSpinner;

    private Toolbar toolbar;
    private Button addProductButton;
    private EditText productNameEditText;
    private EditText productCountEditText;
    private String selectedType;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

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
        final ShoppingCartAdapter shoppingCartAdapter = new ShoppingCartAdapter(this, GenericShoppingCart.ItemLists);
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

                    Item item = new Item(id,name,weight);

                    addItemAction(item);

                    shoppingCartAdapter.notifyDataSetChanged();

                    productNameEditText.setText("");
                    productCountEditText.setText("");

                    Toast completedToast = Toast.makeText(getApplicationContext(), "Ürün Eklendi", Toast.LENGTH_LONG);
                    completedToast.show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addItemAction(Item item){
        DataService.ds.itemsRef.child(item.getItemId()).updateChildren(item.toMap());
    }

}
