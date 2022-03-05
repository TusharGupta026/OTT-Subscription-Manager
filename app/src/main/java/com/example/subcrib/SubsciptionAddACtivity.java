package com.example.subcrib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.Toast;

import com.example.subcrib.model.SubscriptionList;
import com.example.subcrib.util.RecyclerAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SubsciptionAddACtivity extends AppCompatActivity {
    ImageView goBack;
    EditText subscriptionAmount,description,paymentMethod,email,billingDate;
    AppCompatButton saveSubscription;
    Spinner subscriptionSpinner,billingPeriodSpinner;
    ArrayList<SubscriptionList> subscriptionArrayList=new ArrayList<>();
    RecyclerAdapter recyclerAdapter;
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
                subscriptionArrayList.add(new SubscriptionList(subscriptionAmount.getText().toString(),subscriptionSpinner.getSelectedItem().toString(),description.getText().toString(),paymentMethod.getText().toString(),email.getText().toString(),billingDate.getText().toString(),billingPeriodSpinner.getSelectedItem().toString()));
                saveData();
            }
        });

    }

    private void saveData() {
        SharedPreferences pref = getSharedPreferences("shared preferences", MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(subscriptionArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("subscription", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();
        Intent intent=new Intent(SubsciptionAddACtivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}