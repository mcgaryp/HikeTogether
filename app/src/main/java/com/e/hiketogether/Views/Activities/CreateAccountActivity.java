package com.e.hiketogether.Views.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.R;
import com.e.hiketogether.Presenters.AccountManager;

/**
 * PURPOSE OF THE CLASS
 *      Intent is to present the user with a user friendly display and allow them to create a
 *      personal account that will allow them to do special things with the account.
 */
public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    // Takes input and creates account!
    public void onCreateAccount(View view) {
        // TEMP VARIABLES
        String password;
        String username;
        String email;
        EditText editText;
        String secondPassword;

        // Need some managing of accounts happening
        AccountManager accountManager = new AccountManager();

        // Get the passwords to check them by
        editText = view.findViewById(R.id.createPassword);

        // Check to see if empty
        // TODO listener click? maybe is what we need
        while (!accountManager.checkInput(editText, "Password"))
            // TODO Display error message and keep prompting
            ;

        // Set password to not empty string
        password = editText.toString();

        editText = view.findViewById(R.id.createVerifyPassword);
        // Check to see if empty
        while (!accountManager.checkInput(editText, "Verify Password"))
            // TODO Display error message and keep prompting
            ;

        // Set password to not empty string
        secondPassword = editText.toString();

        // Check to make sure the two passwords match
        if (!accountManager.crossCheckPasswords(password, secondPassword)) {
            while (!accountManager.crossCheckPasswords(password, secondPassword)) {
                // TODO make user reenter password

                editText = view.findViewById(R.id.createPassword);
                password = editText.toString();
                editText = view.findViewById(R.id.createVerifyPassword);
                secondPassword = editText.toString();
            }
        }

        // Okay it's alright to create the account now.
        // Get the rest of the info
        editText = view.findViewById(R.id.createUsername);
        username = editText.toString();
        editText = view.findViewById(R.id.createEmail);
        email = editText.toString();

        // Create the account
        accountManager.createAccount(username, password, email);
    }
}
