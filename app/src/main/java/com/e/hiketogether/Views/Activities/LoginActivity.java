package com.e.hiketogether.Views.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.e.hiketogether.R;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


}
