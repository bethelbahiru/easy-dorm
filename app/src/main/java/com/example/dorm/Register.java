package com.example.dorm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class Register extends AppCompatActivity {

    EditText name, surname, email, password;
    Button registerButton;
    TextView dorm, register, login;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;
    String studentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        email = findViewById(R.id.email);
        registerButton = findViewById(R.id.button);
        dorm = findViewById(R.id.textView4);
        login = findViewById(R.id.textView2);
        register = findViewById(R.id.register);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar2);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if(firebaseAuth.getCurrentUser() != null ){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emails = email.getText().toString().trim();
                String passwords = password.getText().toString().trim();
                final String names = name.getText().toString().trim();
                final String surnames = surname.getText().toString().trim();

                if(TextUtils.isEmpty(names)){
                    name.setError("Name is Required.");
                    return;
                }

                if(TextUtils.isEmpty(emails)){
                    email.setError("Email is Required.");
                    return;
                }
                if(TextUtils.isEmpty(passwords)){
                    password.setError("Password is Required.");
                    return;
                }

                if(TextUtils.isEmpty(surnames)){
                    surname.setError("Surname is Required.");
                    return;
                }

                if(TextUtils.isEmpty(surnames) && TextUtils.isEmpty(names) && TextUtils.isEmpty(emails) && TextUtils.isEmpty(passwords)){
                    Toast.makeText(Register.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(emails,passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Student Registered", Toast.LENGTH_SHORT).show();
                            studentID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("students").document(studentID);
                            Map<String, Object> student = new HashMap<>();
                            student.put("surname", surnames);
                            student.put("name", names);
                            student.put("email", emails);
                            student.put("placeOfBirth", null);
                            student.put("school", null);
                            student.put("phone", null);
                            documentReference.set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    System.out.println("Success");

                                }
                            });

                            startActivity(new Intent(getApplicationContext(), MainActivity2.class));

                           // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });
    }

    public void showHidePass(View view) {
        if(view.getId()==R.id.imageView8){

            if(password.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_24);

                //Show Password
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_off_24);

                //Hide Password
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
}