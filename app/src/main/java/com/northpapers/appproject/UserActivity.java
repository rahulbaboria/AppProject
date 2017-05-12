package com.northpapers.appproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class UserActivity extends AppCompatActivity {

    Button submit;
    EditText edit_name,edit_email,edit_password,edit_roll_no,edit_course,edit_father_name,edit_branch,edit_sem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ProgressDialog progressBar=new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        progressBar.show();

        Intent intent=getIntent();


        edit_email=(EditText)findViewById(R.id.edit_user_email);
        edit_name=(EditText)findViewById(R.id.edit_user_name);
        edit_password=(EditText)findViewById(R.id.edit_user_password);
        edit_roll_no=(EditText)findViewById(R.id.edit_roll_no);
        edit_course=(EditText)findViewById(R.id.edit_course);
        edit_father_name=(EditText)findViewById(R.id.edit_father_name);
        edit_branch=(EditText)findViewById(R.id.edit_branch);
        edit_sem=(EditText)findViewById(R.id.edit_semester);


        edit_name.setText(intent.getStringExtra("name"));
        edit_email.setText(intent.getStringExtra("email"));

        edit_password.setText(intent.getStringExtra("password"));
        edit_father_name.setText(intent.getStringExtra("fatherName"));
        edit_course.setText(intent.getStringExtra("course"));
        edit_roll_no.setText(intent.getStringExtra("rollno"));
        edit_branch.setText(intent.getStringExtra("branch"));
        edit_sem.setText(intent.getStringExtra("semester"));



        edit_name.setEnabled(false);
        edit_email.setEnabled(false);
        edit_password.setEnabled(false);
        edit_father_name.setEnabled(false);
        edit_course.setEnabled(false);
        edit_roll_no.setEnabled(false);
        edit_sem.setEnabled(false);
        edit_branch.setEnabled(false);




progressBar.dismiss();


    }
}
