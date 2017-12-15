package com.example.onurhuseyincantay.myshoppingcart.Model;

import com.example.onurhuseyincantay.myshoppingcart.Network.Parameters;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bertug on 15/12/2017.
 */

public class User {
    private String UserId ;
    private String Email;

    public String getUserId() {
        return UserId;
    }

    public String getEmail() {
        return Email;
    }

    public User(String userId, String email) {
        UserId = userId;
        Email = email;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        //result.put("UserId", UserId);
        result.put("Email", Email);
        return result;
    }
}
