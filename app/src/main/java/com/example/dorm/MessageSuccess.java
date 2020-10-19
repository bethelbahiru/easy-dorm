package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageSuccess extends AppCompatActivity {

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_success);

        Intent i = getIntent();

        String receiver = i.getStringExtra("receiver");
        String subject = i.getStringExtra("subject");

        textView = findViewById(R.id.textView5);
        button = findViewById(R.id.button3);

        textView.setText("The Message " + "Has Been Successfully" + "\n" + "Sent To " + receiver );

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            }
        });


    }
}