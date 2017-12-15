package com.example.onurhuseyincantay.myshoppingcart.Model;

import java.util.List;

/**
 * Created by Bertug on 15/12/2017.
 */

public class ShoppingList {
    private  String LıstId;
    private List<String> Items;

    public String getLıstId() {
        return LıstId;
    }

    public List<String> getItems() {
        return Items;
    }

    public ShoppingList(String lıstId, List<String> items) {
        LıstId = lıstId;
        Items = items;
    }
}
