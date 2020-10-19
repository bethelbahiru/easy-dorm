package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SendMessage extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;
    Button button;
    EditText sender , receiver , subject , content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseFirestore = FirebaseFirestore.getInstance();
        button = findViewById(R.id.button3);
        sender = findViewById(R.id.relation);
        receiver = findViewById(R.id.phone);
        subject = findViewById(R.id.relatives);
        content = findViewById(R.id.phoneNumber);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String senders = sender.getText().toString().trim();
                final String receivers = receiver.getText().toString().trim();
                final String subjects = subject.getText().toString().trim();
                final String contents = content.getText().toString().trim();


                if(TextUtils.isEmpty(senders)){
                    sender.setError("Sender's Email Address is Required.");
                    return;
                }

                if(TextUtils.isEmpty(receivers)){
                    receiver.setError("Receiver's Email Address is Required.");
                    return;
                }
                if(TextUtils.isEmpty(subjects)){
                    subject.setError("Message subject is Required.");
                    return;
                }

                if(TextUtils.isEmpty(contents)){
                    content.setError("Message Content is Required.");
                    return;
                }

                Message message = new Message(senders, receivers, subjects, contents);

                firebaseFirestore.collection("Message").add(message).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendMessage.this, "Message Sent", Toast.LENGTH_SHORT).show();

                    }
                });

                Intent i = new Intent(view.getContext(), MessageSuccess.class);
                i.putExtra("Receiver", receivers);
                i.putExtra("subject", subjects);
                startActivity(i);

            }
        });


    }
}