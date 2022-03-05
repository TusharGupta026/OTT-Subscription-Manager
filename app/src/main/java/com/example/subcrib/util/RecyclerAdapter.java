package com.example.subcrib.util;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subcrib.DetailFragment;
import com.example.subcrib.R;
import com.example.subcrib.model.SubscriptionList;
import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SubscriptionList> subscriptionArrayList;
    private final Context context;


    public RecyclerAdapter(ArrayList<SubscriptionList> subscriptionArrayList,Context context) {
        this.context = context;
        this.subscriptionArrayList = subscriptionArrayList;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView subscriptionList;
        private final TextView descriptionList;
        private final TextView amountList;
        private final TextView periodList;
        private final TextView emailList;
        private final TextView dateList;
        private final TextView paymentList;
        private final ImageView subscriptionImage;
        private final CardView subscriptionCard;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            subscriptionList = (TextView) itemView.findViewById(R.id.subscriptionList);
            descriptionList = (TextView) itemView.findViewById(R.id.descriptionList);
            amountList = (TextView) itemView.findViewById(R.id.amountList);
            periodList = (TextView) itemView.findViewById(R.id.periodList);
            emailList = (TextView) itemView.findViewById(R.id.emailList);
            dateList = (TextView) itemView.findViewById(R.id.dateList);
            paymentList = (TextView) itemView.findViewById(R.id.paymentList);
            subscriptionImage = (ImageView) itemView.findViewById(R.id.subscriptionImage);
            subscriptionCard=(CardView) itemView.findViewById(R.id.subscriptionCard);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.subscription_list, viewGroup, false);

        return new ItemViewHolder((layoutView));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {


        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        SubscriptionList subscription = subscriptionArrayList.get(i);

        itemViewHolder.subscriptionList.setText(subscription.getSubscription());
        itemViewHolder.descriptionList.setText(subscription.getDescription());
        itemViewHolder.amountList.setText(subscription.getAmount());
        itemViewHolder.periodList.setText(subscription.getBillingPeriod());
        itemViewHolder.emailList.setText(subscription.getEmail());
        itemViewHolder.dateList.setText(subscription.getBillingDate());
        itemViewHolder.paymentList.setText(subscription.getPayment());


        SharedPreferences pref = context.getSharedPreferences("shared preferences",MODE_PRIVATE); // 0 - for private mode;
        int imageResource;
        String json = pref.getString("subscription", null);
        if (json.contains("Netflix")) {
            imageResource = context.getResources().getIdentifier("@drawable/netflix_logo", null, context.getPackageName());
            itemViewHolder.subscriptionImage.setImageResource(imageResource);
        } else if (json.contains("Prime Video")) {
            imageResource = context.getResources().getIdentifier("@drawable/amazon_prime_video", null, context.getPackageName());
            itemViewHolder.subscriptionImage.setImageResource(imageResource);
        } else if (json.contains("Disney+Hotstar")) {
            imageResource = context.getResources().getIdentifier("@drawable/hotstart_logo", null, context.getPackageName());
            itemViewHolder.subscriptionImage.setImageResource(imageResource);
        } else if (json.contains("Youtube Premium")) {
            imageResource = context.getResources().getIdentifier("@drawable/youtube_logo", null, context.getPackageName());
            itemViewHolder.subscriptionImage.setImageResource(imageResource);
        } else if (json.contains("SonyLiv")) {
            imageResource = context.getResources().getIdentifier("@drawable/sonyliv_logo", null, context.getPackageName());
            itemViewHolder.subscriptionImage.setImageResource(imageResource);
        } else if (json.contains("Voot")) {
            imageResource = context.getResources().getIdentifier("@drawable/voot_logo", null, context.getPackageName());
            itemViewHolder.subscriptionImage.setImageResource(imageResource);
        } else if (json.contains("MxPlayer")) {
            imageResource = context.getResources().getIdentifier("@drawable/mxplayer_logo", null, context.getPackageName());
            itemViewHolder.subscriptionImage.setImageResource(imageResource);
        } else if (json.contains("Zee5")) {
            imageResource = context.getResources().getIdentifier("@drawable/zee5_logo", null, context.getPackageName());
            itemViewHolder.subscriptionImage.setImageResource(imageResource);
        }

        itemViewHolder.subscriptionCard.setOnClickListener(view -> {
            AppCompatActivity activity=(AppCompatActivity) view.getContext();
            DetailFragment detailFragment=new DetailFragment(subscription.getAmount(),subscription.getSubscription(),subscription.getDescription(),subscription.getPayment(),subscription.getEmail(),subscription.getBillingDate(),subscription.getBillingPeriod());
            activity.findViewById(R.id.fab).setVisibility(View.GONE);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,detailFragment).addToBackStack(null).commit();

        });

    }

    @Override
    public int getItemCount() {
        return subscriptionArrayList.size();
    }
}

