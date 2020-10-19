package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentSuccess extends AppCompatActivity {

    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_success);

        button = findViewById(R.id.button3);
        textView = findViewById(R.id.textView5);
        Intent intent = getIntent();

        String month = intent.getStringExtra("months");

        textView.setText("Thank You, You have successfully "+ "\n" +"completed " + month + "month + \"\\n\" + dormitory payment!");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            }
        });




    }
}