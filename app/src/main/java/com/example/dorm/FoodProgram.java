package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FoodProgram extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Button button, buttonFood;
    Spinner spinner;
    EditText appetizer , mainDish ,  dessert , calory , date;
    String course;
    FirebaseFirestore firebaseFirestore;
    private int mYear, mMonth, mDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_program);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        button = findViewById(R.id.button3);
        buttonFood = findViewById(R.id.button6);
        spinner = findViewById(R.id.spinner);
        appetizer = findViewById(R.id.appetizer);
        mainDish = findViewById(R.id.mainDish);
        dessert = findViewById(R.id.Dessert);
        calory = findViewById(R.id.calory);
        date = findViewById(R.id.editTextDate);
        firebaseFirestore = FirebaseFirestore.getInstance();




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Role, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        buttonFood.setOnClickListener(this);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v ==   buttonFood) {

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

                            date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if(v == button){

            final String appetizers = appetizer.getText().toString().trim();
            final String main = mainDish.getText().toString().trim();
            final String desserts = dessert.getText().toString().trim();
            final String calories = calory.getText().toString().trim();


            if(TextUtils.isEmpty(appetizers)){
                appetizer.setError("Appetizer is Required.");
                return;
            }

            if(TextUtils.isEmpty(main)){
                mainDish.setError("Main Dish is Required.");
                return;
            }
            if(TextUtils.isEmpty(desserts)){
                dessert.setError("Dessert is Required.");
                return;
            }

            if(TextUtils.isEmpty(calories)){
                calory.setError("Calory is Required.");
                return;
            }

            Food food = new Food(course,appetizers,main,desserts,calories, date.getText().toString());

            firebaseFirestore.collection("Food").add(food).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(FoodProgram.this, "Food Program Added", Toast.LENGTH_SHORT).show();

                }
            });
            startActivity(new Intent(getApplicationContext(), FoodList.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        course = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), course, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}