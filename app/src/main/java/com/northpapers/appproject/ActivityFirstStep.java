package com.northpapers.appproject;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import java.util.List;

public class ActivityFirstStep extends AppCompatActivity {
    private static final int MEGABYTE = 4 * 1024 * 1024;
    ProgressDialog mProgressDialog;
    String pdfUrl;
    String pdfName;
    FileAdapter fileAdapter;
    Boolean downloaded = false;
    DatabaseReference databaseReference;

    ArrayList<FileModel> files_list = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first_step);


        // Set your progress dialog Message
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Downloading, Please Wait!");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

      // progressDialog=(progressDialog)findViewById()


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Btech");


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

                Toast.makeText(getBaseContext(), "Data Error", Toast.LENGTH_SHORT).show();
            }
        });


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_first);
        fileAdapter = new FileAdapter(files_list, ActivityFirstStep.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityFirstStep.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fileAdapter);


    }


    void setFileUrl(String pdfUrl1, String pdfName1) {
        pdfUrl = pdfUrl1;
        this.pdfName = pdfName1 + ".pdf";

    }


    public void view(String name) {


        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/SuryaWorld/" + name);
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ActivityFirstStep.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }


  /*  @Override
    public void onItemClick(FileModel fileModel) {
        ActivityFirstStep activityFirstStep = new ActivityFirstStep();
//        activityFirstStep.setFileUrl(fileModel.getFileReference(), fileModel.getDescription());
        mProgressDialog.show();
        new DownloadFile().execute(fileModel.getFileReference(),fileModel.getDescription());
    }

*/
    public class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {

            String url = params[0];
            String name =params[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "SuryaWorld");

            if (!folder.exists()) {
                folder.mkdir();
            }

            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/SuryaWorld/"+name+ ".pdf");

//            try {
//                if (!pdfFile.exists())
//                {
//                    pdfFile.createNewFile();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }



            try {

                URL urlnew = new URL(url);
                HttpURLConnection urlConnection = (HttpURLConnection) urlnew.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                int totalSize = urlConnection.getContentLength();
                byte data[] = new byte[1024];
                long total = 0;
                int count;


                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;


                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    total+=bufferLength;
                    int progress=((int)(total*100/totalSize));
                    publishProgress(progress);

                    fileOutputStream.write(buffer, 0, bufferLength);
                }


                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            mProgressDialog.setProgress(0);
            mProgressDialog.dismiss();

            return name;
        }

        @Override
        protected void onPostExecute(String params) {
            super.onPostExecute(params);

            view(params+".pdf");


        }


        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // Update the progress dialog
            mProgressDialog.setProgress(progress[0]);
            // Dismiss the progress dialog
            //mProgressDialog.dismiss();
        }
    }




}
