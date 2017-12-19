package com.example.onurhuseyincantay.myshoppingcart.Network;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class DataService {
    private DataService() {
    }
    public  static  DataService ds = new DataService();
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    public FirebaseUser user;
    public DatabaseReference containerRef = database.getReference(Parameters.Container.toString());
    public DatabaseReference itemsRef = database.getReference(Parameters.Items.toString());
    public DatabaseReference shoppingListsRef = database.getReference(Parameters.ShoppingLists.toString());
    public DatabaseReference userRef = database.getReference(Parameters.Users.toString());

    public void removeSelectedCart(String id){
        shoppingListsRef.child(id).removeValue();
    }

    public void addItemOnContainer(String listId, Map<String, Object> itemId){
        containerRef.child(mAuth.getCurrentUser().getUid().toString()).child(listId).updateChildren(itemId);
    }
}
