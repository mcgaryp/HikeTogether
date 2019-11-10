package com.e.hiketogether.Models;

public class Account {
    // VARIABLES
    private String password;
    private String username;
    private TrailList trailList;
    private String email;
//    private Settings settings;

    // Constructor
    // Stores password, username, email, and generates regular settings?
    Account(String password, String username, String email) {

    }

    // GET the hashed password
    public String getHashedPassword() {
        return password;
    }

    // ADD a Trail to the accounts trail list
    public void addTrail() {
        // TODO add new trail to the account
    }

}
