package com.example.subcrib.util;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<SubscriptionList> subscriptionArrayList;
    private final Context context;
    private int imageResource;


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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {


        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        SubscriptionList subscription = subscriptionArrayList.get(position);

        itemViewHolder.subscriptionList.setText(subscription.getSubscription());
        itemViewHolder.descriptionList.setText(subscription.getDescription());
        itemViewHolder.amountList.setText(subscription.getAmount());
        itemViewHolder.periodList.setText(subscription.getBillingPeriod());
        itemViewHolder.emailList.setText(subscription.getEmail());

        Calendar currentMonth = Calendar.getInstance();
        SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        String billDate;

        if (Integer.parseInt(subscription.getBillingDate()) < Integer.parseInt(dateFormat.format(currentMonth.getTime()))) {
            // Increment month
            currentMonth.add(Calendar.MONTH, 1);
        }

        billDate="Next Due: "+ subscription.getBillingDate()+" "+ dateFormatMonth.format(currentMonth.getTime()).substring(0,3);


        itemViewHolder.dateList.setText(billDate);
        itemViewHolder.paymentList.setText(subscription.getPayment());



        switch (subscription.getSubscription()) {
            case "Netflix":
                imageResource = context.getResources().getIdentifier("@drawable/netflix_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Prime Video":
                imageResource = context.getResources().getIdentifier("@drawable/amazon_prime_video", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Disney+Hotstar":
                imageResource = context.getResources().getIdentifier("@drawable/hotstart_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Youtube Premium":
                imageResource = context.getResources().getIdentifier("@drawable/youtube_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "SonyLiv":
                imageResource = context.getResources().getIdentifier("@drawable/sonyliv_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Voot":
                imageResource = context.getResources().getIdentifier("@drawable/voot_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "MxPlayer":
                imageResource = context.getResources().getIdentifier("@drawable/mxplayer_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Zee5":
                imageResource = context.getResources().getIdentifier("@drawable/zee5_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "ALTBalaji":
                imageResource = context.getResources().getIdentifier("@drawable/altbalaji_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Viu":
                imageResource = context.getResources().getIdentifier("@drawable/viu_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Hoichoi":
                imageResource = context.getResources().getIdentifier("@drawable/hoichoi_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Spotify":
                imageResource = context.getResources().getIdentifier("@drawable/spotify_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Gaana":
                imageResource = context.getResources().getIdentifier("@drawable/gaana_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "Youtube Music":
                imageResource = context.getResources().getIdentifier("@drawable/youtube_music_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
            case "JioSaavn":
                imageResource = context.getResources().getIdentifier("@drawable/jio_saavn_logo", null, context.getPackageName());
                subscription.setImageResource(imageResource);
                itemViewHolder.subscriptionImage.setImageResource(imageResource);
                break;
        }

        itemViewHolder.subscriptionCard.setOnClickListener(view -> {
            AppCompatActivity activity=(AppCompatActivity) view.getContext();
            String nextBill=subscription.getBillingDate() + " " + dateFormatMonth.format(currentMonth.getTime());
            DetailFragment detailFragment=new DetailFragment(subscription.getId(), subscription.getAmount(),subscription.getSubscription(),subscription.getDescription(),subscription.getPayment(),subscription.getEmail(),nextBill,subscription.getBillingPeriod(), subscription.getImageResource());
            activity.findViewById(R.id.fab).setVisibility(View.GONE);
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_content_main,detailFragment).addToBackStack(null).commit();

        });

    }

    @Override
    public int getItemCount() {
        return subscriptionArrayList.size();
    }
}

