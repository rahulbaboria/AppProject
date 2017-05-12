package com.northpapers.appproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,IFragmentDown {
    ImageView img1;

    FrameLayout f1Content;
    String fabString="loading";
    List<Notifications> notification_list=new ArrayList<>();
    private RecyclerView recyclerView;
    private CustomAdapter mAdapter;
    ProgressDialog progressDialogOnItemClick;
    String userEmail;

    DatabaseReference databaseReference;

View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //firebase database reference

        FirebaseAuth auth=FirebaseAuth.getInstance();
        userEmail=auth.getCurrentUser().getEmail();



        //recyclerview
        recyclerView = (RecyclerView) findViewById(R.id.recycler_list_view);

        //recycler view progressbar
        progressDialogOnItemClick = new ProgressDialog(MainActivity.this,R.style.MyProgressTheme);
        progressDialogOnItemClick.setCancelable(false);
        progressDialogOnItemClick.setProgressStyle(android.R.style.Widget_ProgressBar_Large);




        //making fragment transactions
        FragmentMainTop fragmentMainTop=new FragmentMainTop();
        FragmentMainDown fragmentMainDown=new FragmentMainDown();
        FragmentManager manager=getSupportFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction=manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.add(R.id.flContent, fragmentMainTop, "Frag_Top");
        transaction.add(R.id.f2content,fragmentMainDown,"Frag down");
        transaction.commit();


        //toolbar mapping
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        databaseReference= FirebaseDatabase.getInstance().getReference();
        //get fab button click director's message from firebase database
        DatabaseReference reference=databaseReference.child("directorMessage");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fabString=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







       //floating button on click

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Director's Message : "+ fabString, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);



        TextView txtUser=(TextView)header.findViewById(R.id.txtUserName);
        txtUser.setText(userEmail);


        navigationView.setNavigationItemSelectedListener(this);













    }

    public void prepareNotificationData(ArrayList<Notifications> notificationlist) {


        notification_list.addAll(notificationlist);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure to exit?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                           finish();
                        }
                    });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getUseDataFromFireBase();
        }

        if(id==R.id.action_logOut){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {



        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.btech) {
            Intent intent=new Intent(MainActivity.this,MainActivity1.class);
            intent.putExtra("id","btech");

            startActivity(intent);



            // Handle the camera action
        } else if (id == R.id.notications) {
            Intent intent=new Intent(MainActivity.this,ActivityFirstStep.class);
            intent.putExtra("id","notifications");

            startActivity(intent);




        } else if (id == R.id.home){



        }

        else if (id == R.id.bpharma){
            Intent intent=new Intent(MainActivity.this,MainActivity1.class);
            intent.putExtra("id","bpharma");

            startActivity(intent);
        }

        else if (id == R.id.barch){
            Intent intent=new Intent(MainActivity.this,MainActivity1.class);
            intent.putExtra("id","barch");

            startActivity(intent);

        }

        else if (id == R.id.mba){
            Intent intent=new Intent(MainActivity.this,ActivityFirstStep.class);
            intent.putExtra("id","mba");

            startActivity(intent);

        }

        else if (id == R.id.mca){
            Intent intent=new Intent(MainActivity.this,ActivityFirstStep.class);
            intent.putExtra("id","mca");

            startActivity(intent);

        }

        else if (id == R.id.others){
            Intent intent=new Intent(MainActivity.this,ActivityFirstStep.class);
            intent.putExtra("id","others");

            startActivity(intent);

        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {



    }


    @Override
    public void onRecyclerItemClick(Notifications notifications) {



        progressDialogOnItemClick.show();


       Notifications notifications1=notifications;

        Intent intent=new Intent(MainActivity.this,NotificationView.class);
        intent.putExtra("imageRef",notifications1.getImage());
        intent.putExtra("heading",notifications1.getHeading());
        intent.putExtra("details",notifications1.getDeatailsFull());

        startActivity(intent);




    }

    void getUseDataFromFireBase(){

        databaseReference=null;
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    String email=snapshot.child("Email").getValue().toString();

                    if(userEmail.equals(email)){

                        String name = snapshot.child("Name").getValue().toString();
                        String fatherName= snapshot.child("Father's Name").getValue().toString();
                        String course = snapshot.child("Course").getValue().toString();
                        String branch = snapshot.child("Branch").getValue().toString();
                        String semester=snapshot.child("Semester").getValue().toString();
                        String password=snapshot.child("Password").getValue().toString();
                        String rollno=snapshot.child("Roll No").getValue().toString();

                        Intent intent=new Intent(MainActivity.this,UserActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("course",course);
                        intent.putExtra("fatherName",fatherName);
                        intent.putExtra("branch",branch);
                        intent.putExtra("semester",semester);
                        intent.putExtra("password",password);
                        intent.putExtra("rollno",rollno);
                        intent.putExtra("email",userEmail);

                        startActivity(intent);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getBaseContext(), "Data Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
