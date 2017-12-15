package com.example.onurhuseyincantay.myshoppingcart.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", Name);
        result.put("Weight", Weight);
        return result;
    }
}
