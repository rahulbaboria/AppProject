package com.northpapers.appproject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Rahul Baboria on 4/18/2017.
 */

public class SyllabusFragment extends android.support.v4.app.Fragment {

View view;
    private static final int MEGABYTE = 4 * 1024 * 1024;
    ProgressDialog mProgressDialog;
    String pdfUrl;
    String pdfName;
    FileAdapter fileAdapter;
    Boolean downloaded = false;
    DatabaseReference databaseReference;

    ArrayList<FileModel> files_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public SyllabusFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        MainActivity1 activityFirstStep=(MainActivity1) getActivity();

        String data=activityFirstStep.myData();

        switch (data){

            case "btech":
                files_list.clear();
                databaseReference=null;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Btech").child("Syllabus");
                getDataFromFireBase();
                break;

            case "bpharma":
                files_list.clear();
                databaseReference=null;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Bpharma").child("Syllabus");
                getDataFromFireBase();
                break;
            case "barch":

                files_list.clear();
                databaseReference=null;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Barch").child("Syllabus");
                getDataFromFireBase();
                break;
            case "mca":

                files_list.clear();
                databaseReference=null;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("MCA").child("Syllabus");
                getDataFromFireBase();
                break;
            case "mba":

                files_list.clear();
                databaseReference=null;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("MBA").child("Syllabus");
                getDataFromFireBase();
                break;
            case "bca":

                files_list.clear();
                databaseReference=null;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("BCA").child("Syllabus");
                getDataFromFireBase();
                break;
            case "others":

                files_list.clear();
                databaseReference=null;
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Others").child("Syllabus");
                getDataFromFireBase();
                break;

        }


        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_syllabus, container, false);


        mProgressDialog=new ProgressDialog(getContext());
        mProgressDialog.setMessage("Downloading, Please Wait!");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        // progressDialog=(progressDialog)findViewById()








        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_syllabus);
        fileAdapter = new FileAdapter(files_list, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fileAdapter);
        return view;

    }



    void getDataFromFireBase(){


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String fileHeading = snapshot.child("Heading").getValue().toString();
                    String fileDetails = snapshot.child("Details").getValue().toString();
                    String newImgReference = snapshot.child("NewImgRef").getValue().toString();
                    String fileReference = snapshot.child("FileRef").getValue().toString();


                    files_list.add(new FileModel(fileHeading, fileDetails, newImgReference, fileReference));


                    fileAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getContext(), "Data Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
