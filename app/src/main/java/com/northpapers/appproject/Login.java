package com.northpapers.appproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.net.URI;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button btnLogin;
private FrameLayout login_frame;
    private EditText editUser;
    private EditText editPass;
    private TextView forgot;
    ProgressDialog progressDialogLogin;
    String user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login);

        //Progress Bar


        progressDialogLogin = new ProgressDialog(Login.this,R.style.MyProgressTheme);
        progressDialogLogin.setCancelable(false);
        progressDialogLogin.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        //Button Login

        btnLogin = (Button) findViewById(R.id.btnLogin);

        //Log in frame Mapping

        login_frame=(FrameLayout)findViewById(R.id.login_frame);

        //EditText email and password
        editUser = (EditText) findViewById(R.id.email);
        editPass = (EditText) findViewById(R.id.password);


        //TextView Forgot Password

        forgot = (TextView) findViewById(R.id.forgot);


        //forgot password on click listener

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPasswordActivity.class));
                finish();


            }
        });


        //Log in button on Touch listener


        btnLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0xe0ce7575, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }

                }

                return false;

            }
        });


        //Log in button on Click listener


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSignin(); //control shifted to this method



            }
        });



        //Firebase mAuth

        mAuth = FirebaseAuth.getInstance();


        //firebase auth listener

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() != null) {



                    Intent intent=new Intent(Login.this, MainActivity.class);

                    intent.putExtra("username",user);


                    startActivity(intent);
                    finish();

                } else {

                }


            }
        };

    }


    //Default Firebase method on start

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }



    // on back button press action

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }


    // start sign in code

    private void startSignin() {

        String email = editUser.getText().toString();

        String password = editPass.getText().toString();

        if (TextUtils.isEmpty(password) && TextUtils.isEmpty(email)) {
            editUser.setError("Enter Registered Email");
            editPass.setError("Enter Password");
        } else if (TextUtils.isEmpty(email)) {

            editUser.setError("Enter Registered Email");

            progressDialogLogin.dismiss();

        } else if (TextUtils.isEmpty(password)) {

            editPass.setError("Enter Password");

            progressDialogLogin.dismiss();
        } else {


            progressDialogLogin.show();
            login_frame.setBackgroundColor(getResources().getColor(R.color.transparentT));




            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            new InternetRequest(email,password).execute((Void)null);

        }



    }

    private class InternetRequest extends AsyncTask<Void, Void, Void> {

       String email;
        String password;

        public InternetRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }


        @Override
        protected Void doInBackground(Void... params) {

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        Toast.makeText(Login.this, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
                        progressDialogLogin.dismiss();
                        login_frame.setBackgroundColor(getResources().getColor(R.color.white));



                    } else {
                        progressDialogLogin.dismiss();
                        Toast.makeText(Login.this, "Sig in Successful", Toast.LENGTH_LONG).show();
                        user=email;
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        login_frame.setBackgroundColor(getResources().getColor(R.color.white));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                }
            });



            return null;
        }



    }

}


