package com.northpapers.appproject;



/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */


public class User {

    public String name;
    public String email;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
