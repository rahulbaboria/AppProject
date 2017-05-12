package com.northpapers.appproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {


    private Button submit;
    private EditText emailId;
    private Button loginhere;
    private FirebaseAuth auth;
    private ProgressDialog progressBar;

    Class fragmentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        progressBar = new ProgressDialog(ForgotPasswordActivity.this,R.style.MyProgressTheme);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        submit=(Button)findViewById(R.id.submit);
        loginhere=(Button)findViewById(R.id.loginhere);




        emailId=(EditText)findViewById(R.id.emailId);


        loginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this,Login.class));
                finish();
            }
        });


        auth = FirebaseAuth.getInstance();



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(emailId.getText())) {

                    emailId.setError("Please Enter Registered Email ");
                }

                else {
                    String emailAddress = emailId.getText().toString().trim();


                    progressBar.show();

                    new InternetRequest(emailAddress).execute((Void)null);


                }



            }
        });



    }


    private class InternetRequest extends AsyncTask<Void, Void, Void> {

        String emailAddress;


        public InternetRequest(String email) {
            this.emailAddress = email;

        }


        @Override
        protected Void doInBackground(Void... params) {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                progressBar.dismiss();


                                LayoutInflater inflater = ForgotPasswordActivity.this.getLayoutInflater();


                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this);
                                alertDialog.setView(R.layout.activity_custom_alert_dialog);
                                alertDialog.setView(inflater.inflate(R.layout.activity_custom_alert_dialog, null));
                                AlertDialog alertDialog2 = alertDialog.create();
                                alertDialog2.show();

                                Toast.makeText(ForgotPasswordActivity.this, "Link Sent Successfully", Toast.LENGTH_LONG).show();

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }

                            if (!task.isSuccessful()) {
                                emailId.setError("Email is not Registered");
                            }
                        }
                    });



            return null;
        }



    }





}
