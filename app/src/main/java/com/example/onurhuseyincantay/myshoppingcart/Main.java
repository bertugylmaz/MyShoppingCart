package com.example.onurhuseyincantay.myshoppingcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    private ListView listView;
    //private String[] itemData = {"Item 1","Item 2"};
    ShoppingCartAdapter shoppingCartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (ListView) findViewById(R.id.list_view);
        shoppingCartAdapter = new ShoppingCartAdapter(this, GenericShoppingCart.shoppingCarts);
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
                Intent addShoppingCartIntent = new Intent(Main.this, AddList.class);
                startActivity(addShoppingCartIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
