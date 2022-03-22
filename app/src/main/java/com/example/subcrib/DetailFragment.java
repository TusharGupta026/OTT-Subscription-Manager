package com.example.subcrib;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.subcrib.databinding.FragmentDetailBinding;
import com.example.subcrib.util.DbManager;
import com.example.subcrib.util.NotificationManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class DetailFragment extends Fragment implements EasyPermissions.PermissionCallbacks{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentDetailBinding binding;

    TextView amountText,subscriptionText,descriptionText,paymentText,emailText,dateText,periodText;
    ImageView goBackToHome, addToGoogleCalender,imageLogo;

    AppCompatButton removeSubscription;
    private String amount;
    private String subscription;
    private String description;
    private String payment;
    private String email;
    private String billingDate;
    private String billingPeriod;
    private int id;
    private int imageResource;
    int icon;
    int beginTime;
    private String bill;
    private boolean paused = true;

    public DetailFragment() {
        // Required empty public constructor
    }
    public DetailFragment(int id,String amount, String subscription, String description, String payment, String email, String billingDate, String billingPeriod,int imageResource,String bill){
        this.amount = amount;
        this.subscription = subscription;
        this.description = description;
        this.payment = payment;
        this.email = email;
        this.billingDate = billingDate;
        this.billingPeriod = billingPeriod;
        this.id=id;
        this.imageResource=imageResource;
        this.bill=bill;
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
        imageLogo=(ImageView) binding.getRoot().findViewById(R.id.imageLogo);


        amountText.setText("Rs "+amount);
        subscriptionText.setText(subscription);
        descriptionText.setText(description);
        paymentText.setText(payment);
        emailText.setText(email);
        dateText.setText(billingDate);
        periodText.setText("/ "+billingPeriod);
        imageLogo.setImageResource(imageResource);

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

        Cursor cursor=new NotificationManager(this.getContext()).readNotificationData(id);

        while(cursor.moveToNext()){
            if((cursor.getInt(0) == id) && (cursor.getString(1).contains("true"))){
                paused=false;
                icon = R.drawable.ic_notifications_active;
            }else{
                paused=true;
                icon=R.drawable.ic_notifications;
            }
            addToGoogleCalender.setImageDrawable(
                    ContextCompat.getDrawable(getContext(), icon));
        }
        Log.d("date",billingDate);

        addToGoogleCalender.setOnClickListener(view -> {

         ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR},1);

        if(EasyPermissions.hasPermissions(getActivity(), Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR)){
            addCalendar();
        }

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



    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private void addCalendar() {

        Calendar cal = Calendar.getInstance();
        final ContentValues event = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");

        if (paused) {

            paused =false;
            icon = R.drawable.ic_notifications_active;
            event.put(CalendarContract.Events.CALENDAR_ID, 1);
            event.put(CalendarContract.Events.TITLE, subscription + " Payment Due");
            event.put(CalendarContract.Events.DESCRIPTION, description);

            if(Integer.parseInt(bill) >= Integer.parseInt(dateFormat.format(cal.getTime()))){

                beginTime= Integer.parseInt(bill) - Integer.parseInt(dateFormat.format(cal.getTime()));
                cal.add(Calendar.DAY_OF_YEAR,30+beginTime);
            }else{

                beginTime= Integer.parseInt(dateFormat.format(cal.getTime()))- Integer.parseInt(bill);
                // now add 30 day in Calendar instance
                cal.add(Calendar.DAY_OF_YEAR, 30-beginTime);
            }

            event.put(CalendarContract.Events.DTSTART,cal.getTimeInMillis()+24*60*60*1000);
            event.put(CalendarContract.Events.DTEND, cal.getTimeInMillis());
            event.put(CalendarContract.Events.ALL_DAY, 1);   // 0 for false, 1 for true
            event.put(CalendarContract.Events.HAS_ALARM, 1); // 0 for false, 1 for true
            event.put(CalendarContract.Events.RRULE,"FREQ=MONTHLY;");
            event.put(CalendarContract.Events.ORIGINAL_ID, id);


            String timeZone = TimeZone.getDefault().getID();
            event.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone);

            Uri baseUri;
            ContentResolver cr = getContext().getContentResolver();
            baseUri = Uri.parse("content://com.android.calendar/events");
            Uri uri= cr.insert(baseUri, event);

            long eventID = Long.parseLong(uri.getLastPathSegment());

            ContentValues reminders = new ContentValues();
            reminders.put(CalendarContract.Reminders.EVENT_ID, eventID);
            reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            reminders.put(CalendarContract.Reminders.MINUTES, 240);
            cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders);


            String res=new NotificationManager(this.getContext()).addNotification(id,"true");
            Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();

        }else {

            paused=true;
            icon = R.drawable.ic_notifications;
            Uri deleteUri = null;
            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);

            int rows = requireContext().getContentResolver().delete(deleteUri, null, null);
            Log.d("debug", String.valueOf(deleteUri));
            String res=new NotificationManager(this.getContext()).deleteNotification(id);
            Toast.makeText(getContext(),res,Toast.LENGTH_SHORT).show();

        }


        addToGoogleCalender.setImageDrawable(
                ContextCompat.getDrawable(getContext(), icon));
    }
}