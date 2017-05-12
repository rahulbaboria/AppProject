package com.northpapers.appproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rahul Baboria on 4/9/2017.
 */

public class MainActivityAdapter extends BaseAdapter {
   private ArrayList<Notifications> notifications=new ArrayList<>();
    private Activity activity;
    private LayoutInflater inflater;



    public MainActivityAdapter(Activity activity,ArrayList<Notifications> notifications) {
        this.activity=activity;
        this.notifications=notifications;
        inflater=(LayoutInflater.from(activity));

    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v1=convertView;






        return v1;
    }
}
