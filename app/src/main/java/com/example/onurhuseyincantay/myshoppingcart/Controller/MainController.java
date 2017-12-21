package com.example.onurhuseyincantay.myshoppingcart.Controller;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.onurhuseyincantay.myshoppingcart.Model.ShoppingList;
import com.example.onurhuseyincantay.myshoppingcart.Network.DataService;
import com.example.onurhuseyincantay.myshoppingcart.R;
import com.example.onurhuseyincantay.myshoppingcart.Adapter.ShoppingCartAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainController extends AppCompatActivity {

    private EditText getNameCartEditText;
    private ListView listView;
    private String uid = DataService.ds.mAuth.getCurrentUser().getUid().toString();
    ShoppingCartAdapter shoppingCartAdapter;
    List<String> shoppingListId = new ArrayList<String>();
    List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DataService.ds.containerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot listId : dataSnapshot.child(uid).getChildren()){

                    shoppingListId.add(listId.getKey().toString());
                    Log.d("CONTAINERREF:", "onDataChange:"+ listId.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Data değiştirelemdi", "loadPost:onCancelled", databaseError.toException());
            }
        });

        ValueEventListener shoppingListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot shoppingListSnapshot : dataSnapshot.getChildren()){
                    String name = (String) shoppingListSnapshot.child("Name").getValue();
                    String id = (String)  shoppingListSnapshot.getKey().toString();

                    for (String s: shoppingListId ){
                        if (id.equals(s)) {
                            shoppingLists.add(new ShoppingList(id,name));
                        }
                    }
                }
                shoppingCartAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Data değiştirelemdi", "loadPost:onCancelled", databaseError.toException());
            }
        };

        DataService.ds.shoppingListsRef.addValueEventListener(shoppingListener);

        listView = (ListView) findViewById(R.id.list_view);
        shoppingCartAdapter = new ShoppingCartAdapter(this,shoppingLists);
        listView.setAdapter(shoppingCartAdapter);

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Intent cartInfoIntent = new Intent( MainController.this, CartInfoController.class );
                 ShoppingList s = shoppingLists.get(i);
                 cartInfoIntent.putExtra( "shoppingListId",s.getListId().toString());
                 startActivity(cartInfoIntent);
             }
         });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                showAlertView();
               shoppingCartAdapter.notifyDataSetChanged();
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

                ShoppingList shoppingList = new ShoppingList(id,name);

                Intent addListIntent = new Intent( MainController.this, AddListController.class );
                addListIntent.putExtra( "shoppingListId",shoppingList.getListId().toString());

                DataService.ds.shoppingListsRef.child(shoppingList.getListId()).child("Name").setValue(shoppingList.getName());
                shoppingLists.clear();

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
