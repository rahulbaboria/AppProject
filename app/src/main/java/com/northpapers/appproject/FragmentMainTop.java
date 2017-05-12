package com.northpapers.appproject;

/**
 * Created by Rahul Baboria on 4/20/2017.
 */

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;


/**

 */
public class FragmentMainTop extends Fragment {

    Animation slidedown;


    ViewFlipper viewFlipperMain;
    ImageView viewFlipperImage1;
    ImageView viewFlipperImage2;
    ImageView viewFlipperImage3;
    ImageView viewFlipperImage4;
    ImageView viewFlipperImage5;
    ImageView viewFlipperImage6;


    ImageView dot1;
    ImageView dot2;
    ImageView dot3;
    ImageView dot4;
    ImageView dot5;
    ImageView dot6;

    static int count=0;

    private FirebaseAuth auth;
    private StorageReference storage;


    View view;


    public FragmentMainTop() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    // TODO: Rename and change types and number of parameters


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view=inflater.inflate(R.layout.fragment_main_top, container,false);





        viewFlipperImage1=(ImageView)view.findViewById(R.id.viewFlipperImage1);
        viewFlipperImage2=(ImageView)view.findViewById(R.id.viewFlipperImage2);
        viewFlipperImage3=(ImageView)view.findViewById(R.id.viewFlipperImage3);
        viewFlipperImage4=(ImageView)view.findViewById(R.id.viewFlipperImage4);
        viewFlipperImage5=(ImageView)view.findViewById(R.id.viewFlipperImage5);
        viewFlipperImage6=(ImageView)view.findViewById(R.id.viewFlipperImage6);



        dot1=(ImageView)view.findViewById(R.id.dot1);
        dot2=(ImageView)view.findViewById(R.id.dot2);
        dot3=(ImageView)view.findViewById(R.id.dot3);
        dot4=(ImageView)view.findViewById(R.id.dot4);
        dot5=(ImageView)view.findViewById(R.id.dot5);
        dot6=(ImageView)view.findViewById(R.id.dot6);


        auth = FirebaseAuth.getInstance();





        viewFlipperMain = (ViewFlipper)  view.findViewById(R.id.pager);

        setColorNull();

        dot1.setImageResource(R.drawable.tab_indicator_selected);


        storage = FirebaseStorage.getInstance().getReference().child("images/FRAGMENTHOME/image1st.jpg");




        Glide.with(getContext()/* context */)
                .using(new FirebaseImageLoader())
                .load(storage)
                .into(viewFlipperImage1);

        storage = FirebaseStorage.getInstance().getReference().child("images/FRAGMENTHOME/image2nd.jpg");

        Glide.with(getContext()/* context */)
                .using(new FirebaseImageLoader())
                .load(storage)
                .into(viewFlipperImage2);

        storage = FirebaseStorage.getInstance().getReference().child("images/FRAGMENTHOME/image3rd.jpg");

        Glide.with(getContext()/* context */)
                .using(new FirebaseImageLoader())
                .load(storage)
                .into(viewFlipperImage3);

        storage = FirebaseStorage.getInstance().getReference().child("images/FRAGMENTHOME/image4th.jpg");

        Glide.with(getContext()/* context */)
                .using(new FirebaseImageLoader())
                .load(storage)
                .into(viewFlipperImage4);

        storage = FirebaseStorage.getInstance().getReference().child("images/FRAGMENTHOME/image5th.jpg");

        Glide.with(getContext()/* context */)
                .using(new FirebaseImageLoader())
                .load(storage)
                .into(viewFlipperImage5);

        storage = FirebaseStorage.getInstance().getReference().child("images/FRAGMENTHOME/image6th.jpg");

        Glide.with(getContext()/* context */)
                .using(new FirebaseImageLoader())
                .load(storage)
                .into(viewFlipperImage6);












        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {

                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                                //  Toast.makeText(getContext(),"Right to Left",Toast.LENGTH_LONG).show();



                                viewFlipperMain.showNext();
                                int id=viewFlipperMain.getDisplayedChild();
                                setColorNull();
                                setColorSelected();




                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                //    Toast.makeText(getContext(),"Left to Right",Toast.LENGTH_LONG).show();


                                viewFlipperMain.showPrevious();
                                setColorNull();
                                setColorSelected();


                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });









        return view;

    }

    void setColorSelected(){
        switch (viewFlipperMain.getDisplayedChild()){
            case 0:
                dot1.setImageResource(R.drawable.tab_indicator_selected);
                break;


            case 1:

                dot2.setImageResource(R.drawable.tab_indicator_selected);
                break;
            case 2:
                dot3.setImageResource(R.drawable.tab_indicator_selected);
                break;

            case 3:
                dot4.setImageResource(R.drawable.tab_indicator_selected);
                break;
            case 4:
                dot5.setImageResource(R.drawable.tab_indicator_selected);
                break;
            case 5:
                dot6.setImageResource(R.drawable.tab_indicator_selected);
                break;
            default:
                setColorNull();

        }
    }

    void setColorNull(){
        dot1.setImageResource(R.drawable.tab_indicator_default);
        dot2.setImageResource(R.drawable.tab_indicator_default);
        dot3.setImageResource(R.drawable.tab_indicator_default);
        dot4.setImageResource(R.drawable.tab_indicator_default);
        dot5.setImageResource(R.drawable.tab_indicator_default);
        dot6.setImageResource(R.drawable.tab_indicator_default);

    }


    private class InternetDataLoading extends AsyncTask<Void, Void, Void> {



        @Override
        protected Void doInBackground(Void... params) {



            return null;
        }




    }



    // TODO: Rename method, update argument and hook method into UI event







}

