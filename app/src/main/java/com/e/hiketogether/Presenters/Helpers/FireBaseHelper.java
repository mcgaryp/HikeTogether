package com.e.hiketogether.Presenters.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.e.hiketogether.Models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * FireBase uses a different thread on it's own!
 */
public class FireBaseHelper {
    private Gson gson;
    private static final String TAG = "FIRE_BASE_HELPER";
    private FirebaseFirestore dataBase;
    private String username;

    // Constructor for the firebase helper. We need to know the username to find the account
    public FireBaseHelper(String username) {
        this.username = username;
        gson = new Gson();
        dataBase = FirebaseFirestore.getInstance();
    }

    // Save the account to the location that we have in the firebase
    // .set(gsonObject or string) creates the document if there isn't one or updates.
    // .update() updates a specific part ie .update("username", "newUsername").
    // .add(gsonObject or string) creates document id for you.
    // TODO update listener and decide what it will do
    public void saveAccount(Account account) {
        // Convert Account to mapAccount
        Map<String, Object> user  = new HashMap<>();
        user.put("username", account.getUsername());
        user.put("password", account.getPassword());
        user.put("email", account.getEmail());
        // TODO Covert the list and settings to something storable
//        user.put("trails", account.getTrailList());
//        user.put("settings", account.getSettings());
        Log.d(TAG, "Created HashMap.");

        // TODO actually save something... no errors but seems like it isn't saving anything
        // Upload to the cloud storage FIRESTORE
        dataBase.collection("accounts").document(username)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
//                        new FirebaseListener().onSaveSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
//                        listener.onSaveFail();
                    }
                });
    }

    // Idea is to pull the date from the account and return it in account form
    // TODO update listener and decide what it will do
    public Account loadAccount() {
        // Need an account to save the info to
        Account account;
        Log.d(TAG, "Attempting to load account.");
        // Start the search in the dataBase
        DocumentReference documentReference = dataBase.collection("accounts").document(username);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data:\n" + document.getData());
                        // notify the login activity that we have logged in to the logged in activity
//                        listener.onLoadSuccess();
                    } else {
                        Log.d(TAG, "No such document");
                        // notify the login activity that we have not logged in.
//                        listener.onLoadFail();
                    }
                } else {
                    Log.d(TAG, "'Get' failed with ", task.getException());
                    return;
                }
            }
        });
        // if we have gotten this far we should add the document to a string
        String gsonString = documentReference.get().toString();

        // What did we just save??
        Log.d(TAG, gsonString);
        // Set the gson to the account
        account = gson.fromJson(gsonString, Account.class);
        // Return the account we found
        Log.d(TAG, "Successful Load from FireBase.");
        return account;
    }

    // Idea is to adjust and make changes to the account as necessary
    // TODO update listener and decide what it will do
    public void updateAccount(String fieldToUpdate, String update) {
        try {
            dataBase.collection("accounts").document(username).update(fieldToUpdate, update);
        } catch (Exception e) {
            Log.d(TAG, "Failed to update account.");
//            listener.onUpdateFail();
        }
        Log.d(TAG, "Successful update to FireBase.");
//        listener.onUpdateSuccess();
    }

    // Delete user account
    // TODO update listener and decide what it will do
    public void deleteAccount() {
        dataBase.collection("account").document(username)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                        listener.onDeleteSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
//                        listener.onDeleteFail();
                    }
                });
    }

    //TODO Check to see if the username is taken
    // not sure if this is going to work. it might not compare the string correctly
    public void exists() throws Exception {
        if (dataBase.collection("accounts").document(username).equals(username)) {
            Log.d(TAG, "Username is already taken.");
            throw new Exception("Username already exists.");
        }
    }
}