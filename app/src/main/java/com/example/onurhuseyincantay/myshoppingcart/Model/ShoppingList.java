package com.example.onurhuseyincantay.myshoppingcart.Model;

import java.util.List;

/**
 * Created by Bertug on 15/12/2017.
 */

public class ShoppingList {
    private String ListId;
    private String name;
    private List<String> Items;

    public ShoppingList(String listId, List<String> items, String name) {
        ListId = listId;
        this.name = name;
        Items = items;
    }

    public String getListId() {
        return ListId;
    }

    public List<String> getItems() {
        return Items;
    }

    public String getName() {
        return name;
    }
}
