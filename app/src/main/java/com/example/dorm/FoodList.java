package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Calendar;

public class FoodList extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Button button, dateButton, foodButton;
    EditText date;
    Spinner spinner;
    FirebaseFirestore firebaseFirestore;
    String course;
    String dateChosen;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button = findViewById(R.id.button4);
        foodButton = findViewById(R.id.button3);
        dateButton = findViewById(R.id.button6);
        date = findViewById(R.id.StartDate);
        spinner = findViewById(R.id.spinner);
        firebaseFirestore = FirebaseFirestore.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

       dateButton.setOnClickListener( this);
       button.setOnClickListener(this);
       foodButton.setOnClickListener(this);


    }

    public void onClick(View v) {

        if (v == dateButton) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, year, month, day);
            datePickerDialog.show();
        }

        if(v == button){

            startActivity(new Intent(v.getContext(),FoodProgram.class));
        }

        if(v == foodButton){

            Intent i = new Intent(v.getContext(), DisplayFoodProgram.class);
            i.putExtra("course", course);
            i.putExtra("date", date.getText().toString());
            startActivity(i);

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        course = adapterView.getItemAtPosition(i).toString();
        // Toast.makeText(adapterView.getContext(), course, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}