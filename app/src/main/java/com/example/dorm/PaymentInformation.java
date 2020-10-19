package com.example.dorm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;

public class PaymentInformation extends AppCompatActivity {

    Button button, dateButton;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String studentID, payment , month, names, surnames;
    EditText fullName, cardNumber, date, cvv;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_information);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.button3);
        dateButton = findViewById(R.id.button6);
        fullName = findViewById(R.id.fullName);
        cardNumber = findViewById(R.id.card);
        date = findViewById(R.id.expireDate);
        cvv = findViewById(R.id.cvv);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        Intent intents = getIntent();


        studentID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("students").document(studentID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    surnames = value.getString("surname");
                    names = value.getString("name");
                }
            }
        });

        payment = intents.getStringExtra("payment");
        month = intents.getStringExtra("month");



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fullNames = fullName.getText().toString().trim();
                final String cardNumbers = cardNumber.getText().toString().trim();
                final String cvvNumber = cvv.getText().toString().trim();
                final String expireDate = date.getText().toString().trim();


                if(TextUtils.isEmpty(fullNames)){
                    fullName.setError("Full Name is Required.");
                    return;
                }

                if(TextUtils.isEmpty(cardNumbers)){
                    cardNumber.setError("Card Number is Required.");
                    return;
                }
                if(TextUtils.isEmpty(cvvNumber)){
                    cvv.setError("CVV is Required.");
                    return;
                }

                if(TextUtils.isEmpty(expireDate)){
                    date.setError("Expire Date is Required.");
                    return;
                }

                Payment payments = new Payment(names, surnames,month, payment);

                firebaseFirestore.collection("Payment").add(payments).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(PaymentInformation.this, "Payment is performed", Toast.LENGTH_SHORT).show();

                    }
                });

                Intent i = new Intent(getApplicationContext(), PaymentSuccess.class);
                i.putExtra("months", month);
                startActivity(i);




            }
        });


        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentInformation.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }
}