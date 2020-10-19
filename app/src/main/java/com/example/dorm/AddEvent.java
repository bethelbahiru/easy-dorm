package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity implements
        View.OnClickListener {

    EditText eventTime, eventDate, address, contactInformation,eventName;
    Button eventButtonTime, eventButtonDate, addEvent;
    FirebaseFirestore firebaseFirestore;
    int mHour, mMinute, mYear, mMonth, mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseFirestore = FirebaseFirestore.getInstance();

        eventTime = findViewById(R.id.EventTime);
        eventDate = findViewById(R.id.EventDate);
        address = findViewById(R.id.relation);
        contactInformation = findViewById(R.id.phone);
        eventName = findViewById(R.id.EventName);
        eventButtonTime = findViewById(R.id.buttonTimeEvent);
        eventButtonDate = findViewById(R.id.buttonDateEvent);
        addEvent = findViewById(R.id.button3);

        eventButtonDate.setOnClickListener(this);
        eventButtonTime.setOnClickListener(this);
        addEvent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == eventButtonTime) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            eventTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (v == eventButtonDate) {

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

                            eventDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == addEvent){
            String date = eventDate.getText().toString().trim();
            String time = eventTime.getText().toString().trim();
            String addresses = address.getText().toString().trim();
            String eventNames = eventName.getText().toString().trim();
            String phoneNumber = contactInformation.getText().toString().trim();



            if(TextUtils.isEmpty(date)){
               eventDate.setError("Event date is Required.");
                return;
            }

            if(TextUtils.isEmpty(time)){
                eventTime.setError("Event time is Required.");
                return;
            }

            if(TextUtils.isEmpty(addresses)){
                address.setError("Address is Required.");
                return;
            }

            if(TextUtils.isEmpty(eventNames)){
                eventName.setError("Event name is Required.");
                return;
            }

            if(TextUtils.isEmpty(phoneNumber)){
                contactInformation.setError("Phone number is Required.");
                return;
            }

            Event events = new Event(date, time, addresses, eventNames,phoneNumber);

            firebaseFirestore.collection("Event").add(events).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(AddEvent.this, "Event is added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), EventList.class));

                }
            });

        }


    }
}