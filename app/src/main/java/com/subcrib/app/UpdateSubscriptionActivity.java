package com.subcrib.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.ContextThemeWrapper;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import com.subcrib.app.model.InputFilterMinMax;
import com.subcrib.app.util.DbManager;

public class UpdateSubscriptionActivity extends AppCompatActivity {

    ImageView goBackEdit;
    EditText editSubscriptionAmount,editDescription,editPaymentMethod,editEmail,editBillingDate;

    TextView editSubscription,editBillingPeriodSpinner;
    AppCompatButton updateSubscription;

    private String id;
    private String subscription;
    private String billingPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_subscription);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        getSupportActionBar().hide();

        goBackEdit=(ImageView) findViewById(R.id.goBackEdit);
        editSubscriptionAmount=(EditText) findViewById(R.id.editSubscriptionAmount);
        editDescription=(EditText) findViewById(R.id.editDescription);
        editPaymentMethod=(EditText) findViewById(R.id.editPaymentMethod);
        editEmail=(EditText) findViewById(R.id.editEmail);
        editBillingDate=(EditText) findViewById(R.id.editBillingDate);


        editBillingDate.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "30")});


        editBillingPeriodSpinner=(TextView) findViewById(R.id.editBillingPeriodSpinner);
        editSubscription=(TextView) findViewById(R.id.editSubscription);
        updateSubscription=(AppCompatButton) findViewById(R.id.updateSubscription);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id= String.valueOf(extras.getInt("id"));
            subscription= extras.getString("subscriptionText");
            String amount= extras.getString("amountText");
            String description= extras.getString("descriptionText");
            String payment= extras.getString("paymentText");
            String email= extras.getString("emailText");
            String billingDate= extras.getString("dateText");
            billingPeriod= extras.getString("periodText");
            //The key argument here must match that used in the other activity

            editSubscriptionAmount.setText(amount);
            editDescription.setText(description);
            editPaymentMethod.setText(payment);
            editEmail.setText(email);
            editBillingDate.setText(billingDate);
            editSubscription.setText(subscription);
            editBillingPeriodSpinner.setText("/ "+ billingPeriod);
        }

        updateSubscription.setOnClickListener(view -> {
            if (editSubscriptionAmount.getText().toString().equals("")||editDescription
                    .getText().toString().equals("")||editPaymentMethod.getText().toString().equals("")
                    ||editEmail.getText().toString().equals("")||editBillingDate.getText().toString().equals("")){

                Toast.makeText(getApplicationContext(), "Fields Cannot be empty!",Toast.LENGTH_SHORT).show();
            }

            else {

                String res=new DbManager(this).updateRecord(id,editSubscriptionAmount.getText().toString()
                        ,subscription,editDescription.getText().toString(),editPaymentMethod.getText().toString(),
                        editEmail.getText().toString(),editBillingDate.getText().toString(),
                        billingPeriod);

                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateSubscriptionActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Going from update subscription activity to main activity
        goBackEdit.setOnClickListener(view -> {
            UpdateSubscriptionActivity.this.finish();
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        builder.setMessage("Discard Changes ?")
                .setCancelable(false)
                .setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UpdateSubscriptionActivity.this.finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}