package com.e.hiketogether.Presenters.Helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.e.hiketogether.Models.Account;
import com.e.hiketogether.Models.Settings;
import com.e.hiketogether.Presenters.Interfaces.DeleteAccountListener;
import com.e.hiketogether.Presenters.Interfaces.LoadAccountListener;
import com.e.hiketogether.Presenters.Interfaces.SaveAccountListener;
import com.e.hiketogether.Presenters.Interfaces.UpdateAccountListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String username;

    // Constructor for the firebasehelper, We need to know the username to find the account
    public FireBaseHelper(String username) {
        dataBase = FirebaseFirestore.getInstance();
        setUsername(username);
    }

    // Save the account to the location that we have in the firebase
    public void saveAccount(final Account account, final SaveAccountListener listener) {
        // Convert Account to mapAccount
        Map<String, Object> user  = new HashMap<>();
        user.put("username", account.getUsername());
        user.put("password", account.getPassword());
        user.put("email", account.getEmail());
        user.put("trails", account.getFavTrails());
        user.put("settings", account.getSettings());

        Log.d(TAG, "Created HashMap: " + user.values());

        // Push to cloud storage FIRESTORE
        dataBase.collection("accounts")
                .document(account.getUsername())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        listener.onSuccess(account);
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
    public void loadAccount(final LoadAccountListener listener) {
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
                        ArrayList<Integer> array = (ArrayList<Integer>) temp.get("trailList");
                        account.setFavTrails(array);
                        Map<String, String> map = (Map<String, String>) temp.get("settings");
                        Settings settings = new Settings(map);
                        account.setSettings(settings);
                        listener.onSuccess(account);
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
    public void updateAccount(String fieldToUpdate, String update, final UpdateAccountListener listener) {
        try {
            dataBase.collection("accounts").document(username)
                    .update(fieldToUpdate, update).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "Successful update to FireBase.");
                    listener.onSuccess();
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Failed to update account.");
            listener.onFail();
        }
    }

    // Update for map with a setting
    public void updateAccount(final String fieldToUpdate, final String secondField, final String update, final UpdateAccountListener listener) {
        try {
            Log.d(TAG, "Attempting to get online data for update.");
            final DocumentReference reference = dataBase.collection("accounts").document(username);
            reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    Log.d(TAG, "onComplete");
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Getting results from online.");
                        DocumentSnapshot document = task.getResult();
                        // Get the trail or settings
                        Log.d(TAG, "Getting Settings saved in a map");
                        Map<String, String> map = (Map<String, String>) document.get(fieldToUpdate);
                        // Set the update in the trail or settings
                        Log.d(TAG,"adding update to Settings");
                        map.put(secondField, update);
                        Log.d(TAG, "Update as: " + update + "\nUpdate complete as: " + map.get(secondField));
                        // try and update the field
                        try {
                            reference.update(fieldToUpdate, map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "Successful update to Firebase.");
                                            listener.onSuccess();
                                        }
                                    });
                        } catch (Exception e) {
                            Log.d(TAG, "Catch exception\nFailed to update account.");
                            listener.onFail();
                        }
                    } else {
                        Log.d(TAG, "Failed to find account.");
                        listener.onFail();
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG ,"Failed to update account.");
            listener.onFail();
        }
    }

    // Update the trail list
    public void updateAccount(final String fieldToUpdate, final ArrayList<Integer> arrayList, final UpdateAccountListener listener) {
        dataBase.collection("accounts").document(username)
                .update(fieldToUpdate, arrayList).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onSuccess();
                        return;
                    }
                });
        listener.onFail();
    }

    // Delete user account at least the info in the document?
    public void deleteAccount(final DeleteAccountListener listener) {
        dataBase.collection("account").document(username)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        Log.d(TAG, "Document with username: " + username + " deleted.");
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
    public void exists(final Account account, final SaveAccountListener listener) {
        // Create a reference to the accounts
        CollectionReference reference = dataBase.collection("accounts");
        // Make a search query to try and find the username
        reference.whereEqualTo("username",username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();
                            List<DocumentSnapshot> list = document.getDocuments();
                            // if the query list is larger than 0 then we found something
                            if (list.size() > 0) {
                                Log.d(TAG, username + " was found, choose new username.");
                                listener.onFail();
                            } else {
                                Log.d(TAG, username + " was not found so continue");
                                saveAccount(account, listener);
                            }
                        } else {
                            Log.d(TAG, "Error reading Firebase.");
                            listener.onFail();
                        }
                    }
                });
    }

    // Setter Functions
    private void setUsername(String username)   { this.username = username; }
}
