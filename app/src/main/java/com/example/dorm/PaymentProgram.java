package com.example.dorm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class PaymentProgram extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener  {

    Spinner spinner, spinner2;
    String payment, month;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_program);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        button = findViewById(R.id.button4);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Payment, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,R.array.Months, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        spinner2.setAdapter(adapters);
        spinner2.setOnItemSelectedListener(this);

        button.setOnClickListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Spinner spin = (Spinner) adapterView;
        Spinner spin2 = (Spinner) adapterView;

        if(spin.getId() == R.id.spinner){
            payment = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), payment, Toast.LENGTH_SHORT).show();
        }
        else if(spin2.getId() == R.id.spinner2){
            month = adapterView.getItemAtPosition(i).toString();
            Toast.makeText(adapterView.getContext(), month, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(view == button){
            if(payment.equals("Credit Card")){
                Intent i = new Intent(view.getContext(), PaymentInformation.class);
                i.putExtra("payment", payment);
                i.putExtra("month", month);
                startActivity(i);
            }
            else {
                startActivity(new Intent(view.getContext(), PayPal.class));
            }

        }
    }
}