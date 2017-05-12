package com.northpapers.appproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NotificationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);

        TextView noti_heading=(TextView)findViewById(R.id.noti_heading);
        TextView noti_details=(TextView)findViewById(R.id.noti_details);
        ImageView imageView=(ImageView)findViewById(R.id.image_noti);


        Intent intent= getIntent();

        String imageR=intent.getStringExtra("imageRef");
        String heading=intent.getStringExtra("heading");
        String details=intent.getStringExtra("details");

        StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl(imageR);

        noti_heading.setText(heading);
        noti_details.setText(details);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(NotificationView.this,MainActivity.class));



    }
}
