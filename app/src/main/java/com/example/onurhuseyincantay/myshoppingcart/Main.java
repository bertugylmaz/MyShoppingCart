package com.example.onurhuseyincantay.myshoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.onurhuseyincantay.myshoppingcart.Model.ShoppingList;
import com.example.onurhuseyincantay.myshoppingcart.Network.DataService;

import java.util.LinkedHashMap;

public class Main extends AppCompatActivity {

    private EditText getNameCartEditText;
    private ListView listView;
    ShoppingCartAdapter shoppingCartAdapter;
    ShoppingList shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (ListView) findViewById(R.id.list_view);
        shoppingCartAdapter = new ShoppingCartAdapter(this, GenericShoppingCart.ItemLists);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.add_shopping_cart:

                //Intent addShoppingCartIntent = new Intent(Main.this, AddList.class);
                //startActivity(addShoppingCartIntent);

                showAlertView();

                return true;
            case R.id.logout:
                DataService.ds.mAuth.signOut();
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showAlertView(){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View alertView = inflater.inflate(R.layout.custom_alertview, null);
        alert.setView(alertView);
        final EditText getNameCartEditText = (EditText) alertView.findViewById(R.id.getCartNameEditText);
        alert.setTitle("Sepet Oluştur!");
        alert.setMessage("Sepetinizin Adını Giriniz");
        alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = getNameCartEditText.getText().toString();
                 String id =  DataService.ds.shoppingListsRef.push().getKey();
                Log.d("liste adı", "Onur : "+name);
                Log.d("liste id", id);
               shoppingList = new ShoppingList(id,name);
                Intent addListIntent = new Intent( Main.this, AddList.class );
                addListIntent.putExtra( "shoppingList",shoppingList);
                startActivity(addListIntent);
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Cancel button action
            }
        });

        AlertDialog alrt = alert.create();
        alrt.show();
    }
}
