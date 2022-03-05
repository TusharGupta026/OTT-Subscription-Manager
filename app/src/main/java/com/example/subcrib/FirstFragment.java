package com.example.subcrib;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subcrib.databinding.FragmentFirstBinding;
import com.example.subcrib.model.SubscriptionList;
import com.example.subcrib.util.RecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private RecyclerView subscriptionRecyclerView;
    private RecyclerAdapter recyclerAdapter;
    ArrayList<SubscriptionList> subscriptionArrayList;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        subscriptionRecyclerView=(RecyclerView) binding.getRoot().findViewById(R.id.subscriptionRecyclerView);
        loadData();
        buildRecyclerView();
        return binding.getRoot();
    }

    private void buildRecyclerView() {
        // initializing our adapter class.
        recyclerAdapter = new RecyclerAdapter(subscriptionArrayList,getContext());

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        subscriptionRecyclerView.setHasFixedSize(true);

        // setting layout manager to our recycler view.
        subscriptionRecyclerView.setLayoutManager(manager);

        // setting adapter to our recycler view.
        subscriptionRecyclerView.setAdapter(recyclerAdapter);

        // notifying adapter when new data added.
        recyclerAdapter.notifyItemInserted(subscriptionArrayList.size());
        Log.d("subcrip", String.valueOf(subscriptionArrayList.size()));

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for gson.
        Gson gson = new Gson();

        // shared prefs if not present setting it as null.
        String json = sharedPreferences.getString("subscription", null);
        Log.d("subcrip", String.valueOf(json));
        // below line is to get the type of our array list.
        Type type = new TypeToken<ArrayList<SubscriptionList>>() {}.getType();
        // in below line we are getting data from gson
        // and saving it to our array list
        subscriptionArrayList = gson.fromJson(json, type);
        // checking below if the array list is empty or not
        if (subscriptionArrayList == null) {
            // if the array list is empty
            // creating a new array list.
            subscriptionArrayList = new ArrayList<>();
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}