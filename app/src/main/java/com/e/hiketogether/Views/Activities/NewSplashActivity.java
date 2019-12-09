package com.e.hiketogether.Views.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.e.hiketogether.R;

public class NewSplashActivity extends AppCompatActivity {
    private Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash);

        h.postDelayed(new Runnable() {

            @Override
            public void run() {

                try {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        h.removeCallbacksAndMessages(null);
    }
}

