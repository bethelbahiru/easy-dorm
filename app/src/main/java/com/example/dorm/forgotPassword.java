package com.example.dorm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    Button button;
    FirebaseAuth firebaseAuth;
    EditText email;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        button = findViewById(R.id.button);
        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emails = email.getText().toString().trim();

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.sendPasswordResetEmail(emails).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(forgotPassword.this,"Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(), Login.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(forgotPassword.this,"Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        });

    }
}