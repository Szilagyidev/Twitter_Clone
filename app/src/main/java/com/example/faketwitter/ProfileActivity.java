package com.example.faketwitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity {

    Button buttonBack,buttonLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        buttonBack = findViewById(R.id.buttonProfileBack);
        buttonLogout = findViewById(R.id.buttonLogout);

        buttonBack.setOnClickListener(view -> {
            goBackToMain();
        });

        buttonLogout.setOnClickListener(view -> {
            logout();
        });
    }

    private void goBackToMain(){
        startActivity(new Intent(ProfileActivity.this, FeedScrollingActivity.class));
    }
    private void logout(){
        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
    }
}