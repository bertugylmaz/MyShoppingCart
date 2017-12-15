package com.example.onurhuseyincantay.myshoppingcart.Model;

/**
 * Created by Bertug on 15/12/2017.
 */

public class Item {
    private String ItemId;
    private String Name;
    private String Weight;

    public String getItemId() {
        return ItemId;
    }

    public String getName() {
        return Name;
    }

    public String getWeight() {
        return Weight;
    }


    public Item(String itemId, String name, String weight) {
        ItemId = itemId;
        Name = name;
        Weight = weight;
    }
}
