package com.northpapers.appproject;




        import android.app.ProgressDialog;
        import android.content.ActivityNotFoundException;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.Environment;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.Toast;

        import com.google.firebase.database.DatabaseReference;

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



public class MainActivity1 extends AppCompatActivity implements IFileAdapter {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private static final int MEGABYTE = 4 * 1024 * 1024;
    ProgressDialog mProgressDialog;
    String pdfUrl;
    String pdfName;
    FileAdapter fileAdapter;
    Boolean downloaded = false;
    DatabaseReference databaseReference;
    String data;
    ArrayList<FileModel> files_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setMessage("Downloading, Please Wait!");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                startMain();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        Intent intent=getIntent();
        data=intent.getStringExtra("id");




      //  TimeTableFragment timeTableFragment=new TimeTableFragment();
      //  timeTableFragment.setArguments(bundle);




        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SyllabusFragment(), "Syllabus");
        adapter.addFragment(new DatesheetsFragment(), "DateSheets");
        adapter.addFragment(new TimeTableFragment(), "TimeTable");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onItemClick(FileModel fileModel) {

        mProgressDialog.show();
        new DownloadFile().execute(fileModel.getFileReference(),fileModel.getDescription());



    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }





    }



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

    public void view(String name) {


        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/SuryaWorld/" + name);
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity1.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

   public String myData(){


        return data;
    }


    public void startMain(){

        startActivity(new Intent(MainActivity1.this,MainActivity.class));
    }
}




















