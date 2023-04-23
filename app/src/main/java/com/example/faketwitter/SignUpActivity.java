package com.example.faketwitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    EditText regEmail;
    EditText regUsername;
    EditText regPassword;
    Button buttonsignUp;
    Button buttonBack;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regEmail = findViewById(R.id.editSingUpEmail);
        regUsername = findViewById(R.id.editSignUpUsername);
        regPassword = findViewById(R.id.editSignUpPassword);
        buttonsignUp = findViewById(R.id.buttonSingUp);
        buttonBack = findViewById(R.id.buttonSignupBack);

        mAuth = FirebaseAuth.getInstance();

        buttonsignUp.setOnClickListener(view -> {
            createUser();
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        });

        buttonBack.setOnClickListener(view -> {
            goBackToMain();
        });
    }

    private void createUser(){
        String email = regEmail.getText().toString();
        String password = regPassword.getText().toString();
        String username = regUsername.getText().toString();

        if (TextUtils.isEmpty(email)){
            regEmail.setError("Email cannot be empty");
            regEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            regPassword.setError("Password cannot be empty");
            regPassword.requestFocus();
        }else if (TextUtils.isEmpty(username)){
            regUsername.setError("Username cannot be empty");
            regUsername.requestFocus();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String userId = mAuth.getCurrentUser().getUid();
                        UserModel newUser = new UserModel(username, email, password);
                        FirebaseFirestore.getInstance().collection("users").document(userId).set(newUser)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(SignUpActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SignUpActivity.this, "Registration Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, SignUpActivity.class));
                                    }
                                });
                    }else{
                        Toast.makeText(SignUpActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, SignUpActivity.class));
                    }
                }
            });

        }
    }

    private void goBackToMain(){
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
    }
}