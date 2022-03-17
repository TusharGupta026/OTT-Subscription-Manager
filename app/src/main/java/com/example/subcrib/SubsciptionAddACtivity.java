package com.example.subcrib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;


import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.Toast;

import com.example.subcrib.util.DbManager;



public class SubsciptionAddACtivity extends AppCompatActivity {
    ImageView goBack;
    EditText subscriptionAmount,description,paymentMethod,email,billingDate;
    AppCompatButton saveSubscription;
    Spinner subscriptionSpinner,billingPeriodSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsciption_add);

        getSupportActionBar().hide();

        goBack=(ImageView) findViewById(R.id.goBack);
        subscriptionSpinner=(Spinner) findViewById(R.id.subscriptionSpinner);
        billingPeriodSpinner=(Spinner) findViewById(R.id.billingPeriodSpinner);

        subscriptionAmount=(EditText) findViewById(R.id.subscriptionAmount);
        description=(EditText) findViewById(R.id.description);

        paymentMethod=(EditText) findViewById(R.id.paymentMethod);
        email=(EditText) findViewById(R.id.email);

        billingDate=(EditText) findViewById(R.id.billingDate);
        saveSubscription=(AppCompatButton) findViewById(R.id.saveSubscription);

        String[] items = new String[]{"Netflix", "Prime Video", "Disney+Hotstar","Youtube Premium","SonyLiv","Voot","MxPlayer","Zee5"};
        String[] items1 = new String[]{"Monthly","Yearly"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        subscriptionSpinner.setAdapter(adapter);
        billingPeriodSpinner.setAdapter(adapter1);

        goBack.setOnClickListener(view -> {
            Intent intent=new Intent(SubsciptionAddACtivity.this,MainActivity.class);
            startActivity(intent);
        });

        saveSubscription.setOnClickListener(view -> {
            if (subscriptionAmount.getText().toString().equals("")||description
                    .getText().toString().equals("")||paymentMethod.getText().toString().equals("")
                    ||email.getText().toString().equals("")||billingDate.getText().toString().equals("")){

                Toast.makeText(getApplicationContext(), "Please Fill All the Fields",Toast.LENGTH_SHORT).show();
            }

            else {

                String res=new DbManager(this).addRecord(subscriptionAmount.getText().toString()
                        ,subscriptionSpinner.getSelectedItem().toString(),
                        description.getText().toString(),paymentMethod.getText().toString(),
                        email.getText().toString(),billingDate.getText().toString(),
                        billingPeriodSpinner.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SubsciptionAddACtivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}