package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity implements Works.OnFragmentInteractionListener,Desc.OnFragmentInteractionListener{
    String name,uname,parentuname,ratingstring,URL,NAME;
    RatingBar arating,urating;
    JSONParser jsonParser=new JSONParser();
    ProgressDialog pd;
    String getURL= "https://talentyaari.000webhostapp.com/Android_Retrievals/getRating.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        LinearLayout lin1=(LinearLayout)findViewById(R.id.lin1);
        LinearLayout lin2=(LinearLayout)findViewById(R.id.lin2);
        ScrollView sv=(ScrollView)findViewById(R.id.sv);
        lin1.getBackground().setAlpha(160);
        lin2.getBackground().setAlpha(160);
        sv.getBackground().setAlpha(210);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tl);
        tabLayout.addTab(tabLayout.newTab().setText("Description"));
        tabLayout.addTab(tabLayout.newTab().setText("Works"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        name=getIntent().getStringExtra("name");
        uname=getIntent().getStringExtra("uname");
        parentuname=getIntent().getStringExtra("parentuname");
        NAME=getIntent().getStringExtra("parentname");
        toolbar.setTitle(name);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final CircleImageView propic=(CircleImageView)findViewById(R.id.propic);
        Drawable img=getResources().getDrawable(R.drawable.sample_profile);
        propic.setImageDrawable(img);
        arating=(RatingBar)findViewById(R.id.arating);
        urating=(RatingBar)findViewById(R.id.urating);
        getStatus g=new getStatus();
        g.execute();
        final ViewPager vp=(ViewPager)findViewById(R.id.vp);
        final PagerAdapter pa=new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        pa.getUsername(uname);
        vp.setAdapter(pa);
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotificationandRating sn=new setNotificationandRating(0);
                sn.execute();
                pd=new ProgressDialog(Details.this);
                pd.setCancelable(false);
                pd.setMessage("Notifying!");
                pd.show();
            }
        });
    }
    @Override
    public void hello()
    {
        System.out.print("No use");
    }
    class getStatus extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", uname));

            JSONObject json = jsonParser.makeHttpRequest(getURL, "POST", params);
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
                        urating.setRating(Float.parseFloat(result.getString("urating")));
                        arating.setRating(Float.parseFloat(result.getString("arating")));
                        System.out.println(result.getString("name"));
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
    class setNotificationandRating extends AsyncTask<String, String, JSONObject> {
        int action;
        setNotificationandRating(int a)
        {
            action=a;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("receiver", uname));
            if(action==0) {
                params.add(new BasicNameValuePair("sendername", NAME));
                URL="https://talentyaari.000webhostapp.com/Android_Retrievals/setNotifications.php";
            }
            else
            {
                params.add(new BasicNameValuePair("rating", ratingstring));
                URL="https://talentyaari.000webhostapp.com/Android_Retrievals/setRating.php";
            }
            params.add(new BasicNameValuePair("senderuname", parentuname));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
            System.out.println("JSON OBJECT CREATED SUCCESSFULLY");

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            try {
                System.out.println("TRY BLOCK CALLED");
                pd.dismiss();
                String message;
                if (result != null) {

                    System.out.println(result.toString());
                    if(result.getString("error").equalsIgnoreCase("false")) {
                        if(action==0)
                            message="Notification sent!";
                        else
                            message="Thank you for your rating!";
                    }
                    else if(action==1)
                        message="Sorry! You have already rated this user!";
                    else
                        message="Sorry! Server Issue!";
                    Toast.makeText(Details.this, message, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    int count=0,clicked=0;
    @Override
    public void onBackPressed()
    {
        if(count==1)
            super.onBackPressed();
        else
        {
            LayoutInflater li=LayoutInflater.from(this);
            LayoutInflater li2=LayoutInflater.from(this);
            final View ratingdialog=li.inflate(R.layout.review_dalog,null);
            final View reasongialog=li2.inflate(R.layout.reasons,null);
            final AlertDialog rating=new AlertDialog.Builder(this).create();
            rating.setView(ratingdialog);
            rating.setCancelable(false);
            final RatingBar rb=(RatingBar)ratingdialog.findViewById(R.id.ratingBar);
            final Button button=(Button)reasongialog.findViewById(R.id.button);
            //final EditText ed=(EditText) reasongialog.findViewById(R.id.re);
            ratingdialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ratingstring=String.valueOf(rb.getRating());
                    setNotificationandRating sr=new setNotificationandRating(1);
                    sr.execute();
                    pd=new ProgressDialog(Details.this);
                    pd.setMessage("Submitting your rating...");
                    pd.setCancelable(false);
                    pd.show();
                    count=1;
                }
            });
            ratingdialog.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rating.dismiss();
                    AlertDialog reasons=new AlertDialog.Builder(Details.this).create();
                    reasons.setView(reasongialog);
                    reasons.setCancelable(false);
                    reasongialog.findViewById(R.id.c1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicked=1;
                            //ed.setEnabled(false);
                            button.setEnabled(true);
                        }
                    });
                    reasongialog.findViewById(R.id.c2).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicked=1;
                            //ed.setEnabled(false);
                            button.setEnabled(true);
                        }
                    });
                    reasongialog.findViewById(R.id.c3).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicked=1;
                            //ed.setEnabled(false);
                            button.setEnabled(true);
                        }
                    });
                    reasongialog.findViewById(R.id.c4).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicked=1;
                            //ed.setEnabled(false);
                            button.setEnabled(true);
                        }
                    });
                    reasongialog.findViewById(R.id.c5).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicked=1;
                            button.setEnabled(true);
                        }
                    });
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            count=1;
                            onBackPressed();
                        }
                    });
                    reasons.show();
                }
            });
            rating.show();
        }

    }
}
