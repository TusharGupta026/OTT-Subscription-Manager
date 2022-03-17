package com.example.subcrib;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.subcrib.databinding.FragmentDetailBinding;

import com.example.subcrib.model.SubscriptionList;
import com.example.subcrib.util.DbManager;
import com.example.subcrib.util.NotificationManager;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;
import java.util.TimeZone;

public class DetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentDetailBinding binding;

    TextView amountText,subscriptionText,descriptionText,paymentText,emailText,dateText,periodText;
    ImageView goBackToHome, addToGoogleCalender;

    AppCompatButton removeSubscription;
    private String amount;
    private String subscription;
    private String description;
    private String payment;
    private String email;
    private String billingDate;
    private String billingPeriod;
    private int id;
    int icon;
    private boolean paused = true;


    public DetailFragment() {
        // Required empty public constructor
    }
    public DetailFragment(int id,String amount, String subscription, String description, String payment, String email, String billingDate, String billingPeriod){
        this.amount = amount;
        this.subscription = subscription;
        this.description = description;
        this.payment = payment;
        this.email = email;
        this.billingDate = billingDate;
        this.billingPeriod = billingPeriod;
        this.id=id;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();

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
        addToGoogleCalender=(ImageView) binding.getRoot().findViewById(R.id.addToGoogleCalender);


        amountText.setText(amount);
        subscriptionText.setText(subscription);
        descriptionText.setText(description);
        paymentText.setText(payment);
        emailText.setText(email);
        dateText.setText(billingDate);
        periodText.setText("/ "+billingPeriod);

        goBackToHome.setOnClickListener(view -> {
            AppCompatActivity activity=(AppCompatActivity) view.getContext();
            FirstFragment fragment=new FirstFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,fragment).addToBackStack(null).commit();
            activity.findViewById(R.id.fab).setVisibility(View.VISIBLE);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).show();
        });

        removeSubscription.setOnClickListener(view -> {
            String res=new DbManager(this.getContext()).deleteItem(id);
            Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();
            AppCompatActivity activity=(AppCompatActivity) view.getContext();
            FirstFragment fragment=new FirstFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,fragment).addToBackStack(null).commit();
            activity.findViewById(R.id.fab).setVisibility(View.VISIBLE);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).show();

        });

        Cursor cursor=new NotificationManager(this.getContext()).readNotificationData();

        while(cursor.moveToNext()){

            if( (cursor.moveToNext()) && (cursor.getInt(0)==id) && (cursor.getString(1).equals("true"))){
                paused = false;
                icon = R.drawable.ic_notifications_active;

//                Log.d("paused", String.valueOf(cursor.moveToNext()));
//                Log.d("paused", String.valueOf(cursor.getInt(0)));
//                Log.d("paused", String.valueOf(cursor.getString(1)));
//                Log.d("paused", String.valueOf(id));
            }else{
                paused =true;
                icon=R.drawable.ic_notifications;
            }

            addToGoogleCalender.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), icon));
        }



        addToGoogleCalender.setOnClickListener(view -> {

            Calendar cal = Calendar.getInstance();
            final ContentValues event = new ContentValues();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");

            if (paused) {

                paused =false;
                icon = R.drawable.ic_notifications_active;

                event.put(CalendarContract.Events.CALENDAR_ID, 1);

                event.put(CalendarContract.Events.TITLE, subscription + " Payment Due");
                event.put(CalendarContract.Events.DESCRIPTION, description);

                Log.d("debug",CalendarContract.Events.CALENDAR_ID);
                Log.d("debug", String.valueOf(id));
                event.put(CalendarContract.Events.DTSTART,billingDate.substring(0,2));
                event.put(CalendarContract.Events.DTEND, cal.getTimeInMillis()+ 60*60*1000);
                event.put(CalendarContract.Events.ALL_DAY, 1);   // 0 for false, 1 for true
                event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true
                event.put(CalendarContract.Events._ID,id);

                String timeZone = TimeZone.getDefault().getID();
                event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

                Uri baseUri;
                baseUri = Uri.parse("content://com.android.calendar/events");
                requireContext().getContentResolver().insert(baseUri, event);

                String res=new NotificationManager(this.getContext()).addNotification(id,"true");

                Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();

            }else {
                paused=true;
                icon = R.drawable.ic_notifications;
                Uri deleteUri = null;
                deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
                Log.d("debug", String.valueOf(id));
                int rows = requireContext().getContentResolver().delete(deleteUri, null, null);
                Log.d("debug", String.valueOf(deleteUri));
                String res=new NotificationManager(this.getContext()).deleteNotification(id);
                Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();

            }


            addToGoogleCalender.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), icon));
        });



        container.setFocusableInTouchMode(true);
        container.requestFocus();
        container.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                AppCompatActivity activity=(AppCompatActivity) getContext();
                assert activity != null;
                FirstFragment fragment=new FirstFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,fragment).commit();
                activity.findViewById(R.id.fab).setVisibility(View.VISIBLE);
                Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).show();
                return true;
            }
            return false;
        });

        return binding.getRoot();
    }



}