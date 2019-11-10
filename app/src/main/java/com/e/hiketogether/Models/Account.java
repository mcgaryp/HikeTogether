package com.e.hiketogether.Models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * PURPOSE:
 *      This class will handle the data and the model. I think it should save things and be directly
 *      related and connected to the firebase database that we are setting up.
 */
public class Account {
    // VARIABLES
    private String password;
    private String username;
    private TrailList trailList;
    private String email;
//    private Settings settings;

    private FirebaseFirestore dataBase;

    // Constructor
    // Stores password, username, email, and generates regular settings?
    public Account(String password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    // Save the account to the location that we have in the firebase
    // .set(gsonObject or string) creates the document if there isn't one or updates.
    // .update() updates a specific part ie .update("username", "newUsername").
    // .add(gsonObject or string) creates document id for you.
    // TODO make this async task or call in an async task?
    public void saveAccount() {
        // Convert the account to gson string
        Gson gson = null;
        gson.toJson(this);
        String gsonString = gson.toString();

        // Upload to the cloud storage FIRESTORE
        dataBase.collection("accounts").document(username)
                .set(gsonString)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    // Idea is to pull the date from the account
    public void loadAccount() {
        DocumentReference documentReference = dataBase.collection("accounts").document(username);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data:\n" + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    // Idea is to adjust and make changes to the account as necessary
    public void updateAccount(String fieldToUpdate, String update) {
        dataBase.collection("accounts").document(username).update(fieldToUpdate, update);
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
