package com.northpapers.appproject;

import android.media.Image;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;

/**
 * Created by Rahul Baboria on 4/9/2017.
 */

public class Notifications {
    String heading;
    String image;
    String DetailHalf;
    String DeatailsFull;


    public Notifications(String heading, String image, String detailHalf, String deatailsFull) {
        this.heading = heading;
        this.image = image;
        DetailHalf = detailHalf;
        DeatailsFull = deatailsFull;
    }

    public Notifications(String heading, String detailHalf, String deatailsFull) {
        this.heading = heading;
        DetailHalf = detailHalf;
        DeatailsFull = deatailsFull;
    }

    public Notifications(String heading, String detailHalf) {
        this.heading = heading;
        DetailHalf = detailHalf;
    }
    public Notifications(String heading) {
        this.heading = heading;

    }

    public String getHeading() {
        return heading;
    }

    public String  getImage() {

        return image;
    }

    public String getDetailHalf() {
        return DetailHalf;
    }

    public String getDeatailsFull() {
        return DeatailsFull;
    }
}
