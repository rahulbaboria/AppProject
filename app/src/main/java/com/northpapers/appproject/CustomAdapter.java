package com.northpapers.appproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by Rahul Baboria on 4/3/2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements View.OnClickListener {

    List<Notifications> notification_list;
    Context context;
    IFragmentDown iFragmentDown;

    String imageR;
    String heading;
    String details;



    public CustomAdapter(List<Notifications> notification, Context context) {
        this.notification_list = notification;
        iFragmentDown = (IFragmentDown) context;
        this.context = context;


    }

    @Override
    public void onClick(View v) {


    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView notification_heading, notification_half, notification_full, notification_imageR;
        public ImageView notification_image;
        public LinearLayout linearLayout;

        public MyViewHolder(View view) {
            super(view);

            notification_heading = (TextView) view.findViewById(R.id.title_heading);
            notification_half = (TextView) view.findViewById(R.id.details_half);
            notification_full = (TextView) view.findViewById(R.id.details_full);
            notification_image = (ImageView) view.findViewById(R.id.notification_image);
            notification_imageR = (TextView) view.findViewById(R.id.imageR);
            linearLayout=(LinearLayout)view.findViewById(R.id.linear);




        }


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_activity_single_list, parent, false);


        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Notifications notifications = notification_list.get(position);





        holder.notification_heading.setText(notifications.getHeading());
        holder.notification_half.setText(notifications.getDetailHalf());
        holder.notification_full.setText(notifications.getDeatailsFull());
        holder.notification_imageR.setText(notifications.getImage());

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(notifications.getImage());




        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(holder.notification_image);

        imageR=holder.notification_imageR.getText().toString();
        heading=holder.notification_heading.getText().toString();
        details=holder.notification_full.getText().toString();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iFragmentDown.onRecyclerItemClick(notifications);


            }
        });


    }

    @Override
    public int getItemCount() {
        return notification_list.size();
    }


}
