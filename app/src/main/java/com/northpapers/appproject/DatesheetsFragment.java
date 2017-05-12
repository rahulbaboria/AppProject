package com.northpapers.appproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rahul Baboria on 4/28/2017.
 */

public class DatesheetsFragment extends android.support.v4.app.Fragment {



        View view;

        public DatesheetsFragment(){


        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view= inflater.inflate(R.layout.fragment_syllabus, container, false);




            return view;
        }

}
