package com.subcrib.app;

import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.subcrib.app.databinding.FragmentFirstBinding;
import com.subcrib.app.model.SubscriptionList;
import com.subcrib.app.util.DbManager;
import com.subcrib.app.util.RecyclerAdapter;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    RecyclerView subscriptionRecyclerView;
    RecyclerAdapter recyclerAdapter;
    private final ArrayList<SubscriptionList> subscriptionArrayList = new ArrayList<>();

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        subscriptionRecyclerView=(RecyclerView) binding.getRoot().findViewById(R.id.subscriptionRecyclerView);

        buildRecyclerView();

        container.setFocusableInTouchMode(true);
        container.requestFocus();
        container.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                getActivity().finish();
                System.exit(0);
                return true;
            }
            return false;
        });

        return binding.getRoot();
    }

    private void buildRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        subscriptionRecyclerView.setHasFixedSize(true);

        subscriptionRecyclerView.setLayoutManager(manager);

        Cursor cursor=new DbManager(this.getContext()).readalldata();

        while(cursor.moveToNext()){
            SubscriptionList obj=new SubscriptionList(cursor.getInt(0), cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5),
                    cursor.getString(6), cursor.getString(7));

            subscriptionArrayList.add(obj);
        }

        recyclerAdapter = new RecyclerAdapter(subscriptionArrayList,getContext());

        subscriptionRecyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.notifyDataSetChanged();

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