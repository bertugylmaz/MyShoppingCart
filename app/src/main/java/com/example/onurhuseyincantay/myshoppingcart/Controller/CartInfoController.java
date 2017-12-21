package com.example.onurhuseyincantay.myshoppingcart.Controller;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.onurhuseyincantay.myshoppingcart.Adapter.ItemListAdapter;
import com.example.onurhuseyincantay.myshoppingcart.Model.Item;
import com.example.onurhuseyincantay.myshoppingcart.Network.DataService;
import com.example.onurhuseyincantay.myshoppingcart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartInfoController extends AppCompatActivity {

    private ListView listView;
    private Toolbar toolbar;
    private String listID;
    private String uid = DataService.ds.mAuth.getCurrentUser().getUid().toString();
    List<String> itemIdList = new ArrayList<String>();
    ItemListAdapter itemListAdapter;
    List<Item> itemList = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_info);

        toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle data = getIntent().getExtras();
        listID = data.getString("shoppingListId");

        DataService.ds.containerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot listId : dataSnapshot.child(uid).child(listID).getChildren()){
                    itemIdList.add(listId.getKey().toString());
                    Log.d("CONTAINERREF:", "onDataChange:"+ listId.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("CONTAINERREF-LOG:", "loadPost:onCancelled", databaseError.toException());
            }
        });

        DataService.ds.itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    String name = (String) item.child("Name").getValue();
                    String weight = (String) item.child("Weight").getValue();
                    String id = (String)  item.getKey().toString();
                    String cartid = (String)  item.child("CartId").getValue();

                    for (String s : itemIdList){
                        if (id.equals(s)) {
                            itemList.add(new Item(id,cartid,name,weight));
                        }
                    }
                    Log.d("LOG ITEMSREF:", "onDataChange: "+id + " " + name + " " + weight);
                }
                itemListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("ITEMREF-LOG:", "loadPost:onCancelled", databaseError.toException());
            }
        });

        listView = (ListView)findViewById(R.id.ListView);
        itemListAdapter = new ItemListAdapter(this, itemList);
        listView.setAdapter(itemListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        if (menu != null){
            MenuItem logoutButton = menu.findItem(R.id.logout);
            logoutButton.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.add_shopping_cart:
                addItemButtonAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItemButtonAction(){
        Intent addListIntent = new Intent(CartInfoController.this, AddListController.class);
        addListIntent.putExtra("shoppingListId",listID);
        startActivity(addListIntent);
    }
}
