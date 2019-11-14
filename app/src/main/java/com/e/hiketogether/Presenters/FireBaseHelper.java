package com.e.hiketogether.Presenters;

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

public class FireBaseHelper {
    private Gson gson = null;
    private static final String TAG = "FIRE_BASE_HELPER";
    private FirebaseFirestore dataBase = null;
    private String username;

    public FireBaseHelper(String username) {
        this.username = username;
    }

    // Save the account to the location that we have in the firebase
    // .set(gsonObject or string) creates the document if there isn't one or updates.
    // .update() updates a specific part ie .update("username", "newUsername").
    // .add(gsonObject or string) creates document id for you.
    // TODO make this async task or call in an async task?
    public void saveAccount() {
        // Convert the account to gson string
        gson.toJson(Account.class);
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

    // Idea is to pull the date from the account and return it in account form
    // TODO make async task or thread?
    public Account loadAccount(String username) {
        // Need an account to save the info to
        Account account;
        // Start the search in the dataBase
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
                        return;
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
        return account;
    }

    // Idea is to adjust and make changes to the account as necessary
    public void updateAccount(String fieldToUpdate, String update) {
        try {
            dataBase.collection("accounts").document(username).update(fieldToUpdate, update);
        } catch (Exception e) {
            Log.d(TAG, "Failed to update account.");
        }
    }
}
