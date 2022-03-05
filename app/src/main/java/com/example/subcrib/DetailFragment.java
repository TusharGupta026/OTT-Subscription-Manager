package com.example.subcrib;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.subcrib.databinding.FragmentDetailBinding;


public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentDetailBinding binding;

    TextView amountText,subscriptionText,descriptionText,paymentText,emailText,dateText,periodText;
    ImageView goBackToHome;
    AppCompatButton removeSubscription;
    private String amount;
    private String subscription;
    private String description;
    private String payment;
    private String email;
    private String billingDate;
    private String billingPeriod;


    public DetailFragment() {
        // Required empty public constructor
    }
    public DetailFragment(String amount, String subscription, String description, String payment, String email, String billingDate, String billingPeriod){
        this.amount = amount;
        this.subscription = subscription;
        this.description = description;
        this.payment = payment;
        this.email = email;
        this.billingDate = billingDate;
        this.billingPeriod = billingPeriod;
    }


    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
        amountText=(TextView) binding.getRoot().findViewById(R.id.amountText);
        subscriptionText=(TextView) binding.getRoot().findViewById(R.id.subscriptionText);
        descriptionText=(TextView) binding.getRoot().findViewById(R.id.descriptionText);
        paymentText=(TextView) binding.getRoot().findViewById(R.id.paymentText);
        emailText=(TextView) binding.getRoot().findViewById(R.id.emailText);
        dateText=(TextView) binding.getRoot().findViewById(R.id.dateText);
        periodText=(TextView) binding.getRoot().findViewById(R.id.periodText);
        goBackToHome=(ImageView) binding.getRoot().findViewById(R.id.goBackToHome);
        removeSubscription=(AppCompatButton) binding.getRoot().findViewById(R.id.removeSubscription);


        amountText.setText(amount);
        subscriptionText.setText(subscription);
        descriptionText.setText(description);
        paymentText.setText(payment);
        emailText.setText(email);
        dateText.setText(billingDate);
        periodText.setText(billingPeriod);

        goBackToHome.setOnClickListener(view -> {
            AppCompatActivity activity=(AppCompatActivity) view.getContext();
            FirstFragment firstFragment=new FirstFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,firstFragment).addToBackStack(null).commit();
            activity.findViewById(R.id.fab).setVisibility(View.VISIBLE);
        });

        removeSubscription.setOnClickListener(view -> {
            SharedPreferences preferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);
            preferences.edit().remove("subscription").apply();
            AppCompatActivity activity=(AppCompatActivity) view.getContext();
            FirstFragment firstFragment=new FirstFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,firstFragment).addToBackStack(null).commit();
            activity.findViewById(R.id.fab).setVisibility(View.VISIBLE);
        });


        return binding.getRoot();
    }
}