package com.example.dorm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Objects;

public class Permission extends AppCompatActivity implements
        View.OnClickListener {

    Button btnStartDatePicker, btnEndDatePicker, permission;
    EditText txtStartDate, txtEndDate, addresses, contactNames, relations, phoneNumbers;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String studentId;
    private int mYear, mMonth, mDay, endYear, endMonth, endDay;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        studentId = firebaseAuth.getCurrentUser().getUid();



        btnStartDatePicker = findViewById(R.id.button6);
        btnEndDatePicker = findViewById(R.id.button7);
        txtStartDate = findViewById(R.id.StartDate);
        txtEndDate = findViewById(R.id.EndDate);
        permission = findViewById(R.id.button3);
        addresses = findViewById(R.id.relation);
        contactNames = findViewById(R.id.phone);
        relations = findViewById(R.id.relatives);
        phoneNumbers = findViewById(R.id.phoneNumber);

        btnStartDatePicker.setOnClickListener(this);
        btnEndDatePicker.setOnClickListener(this);
        permission.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == btnStartDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnEndDatePicker) {

            // Get Current Date
            final Calendar calandars = Calendar.getInstance();
            endYear = calandars.get(Calendar.YEAR);
            endMonth = calandars.get(Calendar.MONTH);
            endDay = calandars.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, endYear, endMonth, endDay);

            System.out.println(endDay);
            datePickerDialog.show();
        }

        if (v == permission) {

            String startDate = txtStartDate.getText().toString().trim();
            String endDate = txtEndDate.getText().toString().trim();
            String address = addresses.getText().toString().trim();
            String contactName = contactNames.getText().toString().trim();
            String relation = relations.getText().toString().trim();
            String phoneNumber = phoneNumbers.getText().toString().trim();



            if(TextUtils.isEmpty(startDate)){
              txtStartDate.setError("Start date is Required.");
                return;
            }

            if(TextUtils.isEmpty(endDate)){
                txtEndDate.setError("End date is Required.");
                return;
            }

            if(TextUtils.isEmpty(address)){
                addresses.setError("Address is Required.");
                return;
            }

            if(TextUtils.isEmpty(contactName)){
                contactNames.setError("phone is Required.");
                return;
            }

            if(TextUtils.isEmpty(relation)){
                relations.setError("Emergency contact name is Required.");
                return;
            }

            if(TextUtils.isEmpty(phoneNumber)){
                phoneNumbers.setError("Phone number is Required.");
                return;
            }

            Permissions permissions = new Permissions(startDate, endDate, address, contactName, relation, phoneNumber, studentId);

            firebaseFirestore.collection("Permission").add(permissions).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(Permission.this, "Permission Added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), PermissionList.class));


                }
            });

        }
    }
}