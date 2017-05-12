package com.northpapers.appproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by Rahul Baboria on 4/20/2017.
 */

public class FragmentMainDown extends Fragment
{
    ProgressDialog progressDialog;
    View view;
    private RecyclerView recyclerView;
    private CustomAdapter mAdapter;
    ArrayList<Notifications> notification_list=new ArrayList<>();
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_main_down, container, false);

        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Notifications");
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        DatabaseReference notificationReference=databaseReference.child("Notifications");
        notificationReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    String heading=snapshot.child("Heading").getValue().toString();
                    String detailsHalf=snapshot.child("DetailsHalf").getValue().toString();
                    String detailsFull=snapshot.child("DetailsFull").getValue().toString();
                    String imsgeUrl=snapshot.child("ImageUrlAddress").getValue().toString();


                    notification_list.add(new Notifications(heading,imsgeUrl,detailsHalf,detailsFull));

                    mAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getContext(),"Data Error",Toast.LENGTH_SHORT).show();
            }
        });


        FragmentMainDown item=new FragmentMainDown();

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_list_view);

        mAdapter = new CustomAdapter(notification_list,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);








        return view;
    }





}
