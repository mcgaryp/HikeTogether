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
    public Account(String password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public void saveAccount() {

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
