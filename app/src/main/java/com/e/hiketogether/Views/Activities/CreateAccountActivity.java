package com.e.hiketogether.Views.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.R;
import com.e.hiketogether.Presenters.AccountManager;

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
        // TODO make sure the passwords aren't empty strings
        editText = view.findViewById(R.id.createPassword);
        password = editText.toString();
        editText = view.findViewById(R.id.createVerifyPassword);
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

        editText = view.findViewById(R.id.createUsername);
        username = editText.toString();
        editText = view.findViewById(R.id.createEmail);
        email = editText.toString();

        accountManager.createAccount(username, password, email);
    }
}
