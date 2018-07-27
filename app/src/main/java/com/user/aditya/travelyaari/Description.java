package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class Description extends AppCompatActivity implements Works.OnFragmentInteractionListener,Desc.OnFragmentInteractionListener{
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    Desc a;
    Works b;
    JSONParser jsonParser=new JSONParser();
    String URL,des,wrk,uname;
    int clicked=0;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tl);
        tabLayout.addTab(tabLayout.newTab().setText("Description"));
        tabLayout.addTab(tabLayout.newTab().setText("Works"));
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        a=new Desc();
        b=new Works();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        }
        );
        uname=getIntent().getStringExtra("uname");
        System.out.print("Username::"+uname);
        //Toast.makeText(this, uname, Toast.LENGTH_SHORT).show();
        System.out.println("Description"+des);
        System.out.println("Works"+wrk);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked==1){
                AlertDialog.Builder al=new AlertDialog.Builder(Description.this);
                al.setTitle("Confirm your Action!");
                al.setMessage("Save your changes?");
                al.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Storing data to the database
                        Status B=new Status(1);
                        des=a.sendToActivity();
                        wrk=b.sendToActivity();
                        pd=new ProgressDialog(Description.this);
                        pd.setTitle("Please wait...");
                        pd.setMessage("Updating your Changes...");
                        pd.setCancelable(false);
                        pd.show();
                        B.execute();
                        System.out.println(a.sendToActivity());
                        System.out.println(b.sendToActivity());
                    }
                });
                al.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                al.show();
                }
                else
                {
                    AlertDialog.Builder alert=new AlertDialog.Builder(Description.this);
                    alert.setMessage("No edits found!");
                    alert.show();
                }
            }
        });
        FloatingActionButton ee = (FloatingActionButton) findViewById(R.id.ed);
        ee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder al=new AlertDialog.Builder(Description.this);
                al.setTitle("Confirm your Action!");
                al.setMessage("Do you want to edit your description and works?");
                al.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        a.setEd();
                        b.setEd();
                        clicked=1;

                    }
                });
                al.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                al.show();
            }
        });
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:a= Desc.newInstance();
                    Bundle bundle=new Bundle();
                    bundle.putString("uname",uname);
                    a.setArguments(bundle);
                        return a;
                case 1:b=Works.newInstance();
                    Bundle bund=new Bundle();
                    bund.putString("uname",uname);
                    b.setArguments(bund);
                        return b;
                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
    @Override
    public void hello()
    {
        System.out.print("No use");
    }
    @Override
    public void onBackPressed()
    {
        if(a.canexit()||b.canexit())
        {
            AlertDialog.Builder al=new AlertDialog.Builder(Description.this);
            al.setTitle("Confirm your Action!");
            al.setMessage("Save your changes?");
            al.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Status B=new Status(1);
                    des=a.sendToActivity();
                    wrk=b.sendToActivity();
                    pd=new ProgressDialog(Description.this);
                    pd.setTitle("Please wait...");
                    pd.setMessage("Updating your Changes...");
                    pd.setCancelable(false);
                    pd.show();
                    B.execute();
                }
            });
            al.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            al.show();
        }
        else
            super.onBackPressed();
    }
    class Status extends AsyncTask<String, String, JSONObject> {
        int action;
        Status(int a)
        {
            action=a;
        }
        @Override
        protected void onPreExecute(){
            if(action==0)
                URL="https://talentyaari.000webhostapp.com/Android_Retrievals/getDandW.php";
            else
                URL="https://talentyaari.000webhostapp.com/Android_Retrievals/setDandW.php";
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", uname));
            System.out.println(String.valueOf(params.isEmpty()));
            if(action==1) {
                params.add(new BasicNameValuePair("des", des));
                params.add(new BasicNameValuePair("work",wrk));
            }
            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
            System.out.println("JSON OBJECT CREATED SUCCESSFULLY");
            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            try {
                System.out.println("TRY BLOCK CALLED");
                if (result != null) {
                    System.out.println(result.toString());
                    if(result.getString("error").equalsIgnoreCase("false")) {
                                pd.dismiss();
                                Toast.makeText(Description.this,"Changes Saved Successfully!",Toast.LENGTH_SHORT).show();
                                onBackPressed();

                    }

                }
                else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
