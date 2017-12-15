package com.example.onurhuseyincantay.myshoppingcart.Model;

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
}
