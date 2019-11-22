package com.e.hiketogether.Presenters.Helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Presenters.Interfaces.LoadListener;
import com.e.hiketogether.Presenters.Interfaces.Listener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * PURPOSE
 *      Helper class to help out with the usage of the cloud storage
 *
 * THINGS TO KNOW ABOUT THIS CLASS AS YOU USE IT
 *      FireBase uses a different thread on it's own!
 *      .set(gsonObject or string) creates the document if there isn't one or updates.
 *      .update() updates a specific part ie .update("username", "newUsername").
 *      .add(gsonObject or string) creates document id for you.
 */
public class FireBaseHelper {
    // VARIABLES
    private static final String TAG = "FIRE_BASE_HELPER";
    private FirebaseFirestore dataBase;
    private Listener listener;
    private LoadListener load;
    private String username;
    private Gson gson;

    // Constructor for the firebase helper, We need to know the username to find the account
    public FireBaseHelper(String username,
                          Listener listener) {
        dataBase = FirebaseFirestore.getInstance();
        setUsername(username);
        gson = new Gson();
        // TODO fix bug
        // Determine the type of listener and set accordingly
//        if (listener instanceof LoadListener)
//            setLoad(listener);
//        else
            setListener(listener);
    }

    // Save the account to the location that we have in the firebase
    public void saveAccount(Account account) {
        // Convert Account to mapAccount
        Map<String, Object> user  = new HashMap<>();
        user.put("username", account.getUsername());
        user.put("password", account.getPassword());                        // temp storage of password
//        user.put("password", gson.toJson(account.getSealedPassword()));   // Hopefully the future wat to store a password
        user.put("email", account.getEmail());
//        user.put("key", gson.toJson(account.getKey()));                   // Hopefully the way to store the key OR come up with better implementation
        user.put("trails", account.getFavTrails());
//        user.put("settings", account.getSettings());

        Log.d(TAG, "Created HashMap: " + user.values());

        // Push to cloud storage FIRESTORE
        dataBase.collection("accounts")
                .document(username)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        listener.onFail();
                    }
                });
    }

    // Idea is to pull the date from the account and return it in account form
    public void loadAccount() {
        // Need an account to save the info to
        Log.d(TAG, "Attempting to load account.");
        // Start the search in the dataBase
        DocumentReference documentReference = dataBase.collection("accounts")
                .document(username);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // What did we find?
                        Log.d(TAG, "DocumentSnapshot data:\n" + document.getData());
                        Map<String, Object> temp = document.getData();
                        Account account = new Account();
                        account.setEmail(temp.get("email").toString());
                        account.setPassword(temp.get("password").toString());
                        account.setUsername(temp.get("username").toString());
//                        account.setFavTrails(temp.get("trailsList"));
//                        account.setSettings(temp.get("settings"));
                        if (listener instanceof LoadListener) {
                            load.onLoadSuccess(account);
                        }
                    } else {
                        Log.d(TAG, "No such document");
                        // notify the login activity that we have not logged in.
                        listener.onFail();
                    }
                } else {
                    Log.d(TAG, "'Get' failed with ", task.getException());
                    listener.onFail();
                }
            }
        });
    }

    // Idea is to adjust and make changes to the account as necessary
    public void updateAccount(String fieldToUpdate, String update) {
        try {
            dataBase.collection("accounts").document(username)
                    .update(fieldToUpdate, update);
        } catch (Exception e) {
            Log.d(TAG, "Failed to update account.");
            listener.onSuccess();
        }
        Log.d(TAG, "Successful update to FireBase.");
        listener.onFail();
    }

    // Delete user account
    public void deleteAccount() {
        dataBase.collection("account").document(username)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                        listener.onFail();
                    }
                });
    }

    // Check to see if the username is taken
    // TODO BROKEN
    public void exists(final Account account) {
        // Create a reference to the accounts
        CollectionReference reference = dataBase.collection("accounts");
        // Make a search query to try and find the username
        reference.whereEqualTo("username", username).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //here?
                        if (task.isSuccessful()) {
                            Log.d(TAG, username + " has already been taken");
                            listener.onFail();
                        } else {
                            Log.d(TAG, username = " was not found so continue");
                            saveAccount(account);
                        }
                    }
                });
    }

    // Setter Functions
    private void setListener(Listener listener) { this.listener = listener; }
    private void setUsername(String username)   { this.username = username; }
    private void setLoad(LoadListener load)     { this.load = load;         }
}
