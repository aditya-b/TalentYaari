package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {
    LinearLayout outer[],inner[];
    TextView tv[];
    String unames[];
    de.hdodenhof.circleimageview.CircleImageView imgs[];
    String username,URL="https://talentyaari.000webhostapp.com/Android_Retrievals/getNotifications.php";
    JSONParser jsonParser=new JSONParser();
    FloatingActionButton fab;
    int number;
    LinearLayout parent;
    LayoutInflater inflater;
    LinearLayout.LayoutParams params2,imgparams,params1;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        parent=(LinearLayout)findViewById(R.id.linear);
        inflater=(LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        params2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imgparams=new LinearLayout.LayoutParams(100,100);
        getPeople gp=new getPeople();
        gp.execute();
        username=getIntent().getStringExtra("uname");
        pd=new ProgressDialog(Notifications.this);
        pd.setMessage("Getting your notifications...");
        pd.setCancelable(false);
        pd.show();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<number;i++)
                    outer[i].setBackgroundColor(Color.TRANSPARENT);
                setread sr=new setread();
                sr.execute();
                pd.setMessage("Marking all notifications as read...");
                pd.setCancelable(false);
                pd.show();
            }
        });
    }
    class getPeople extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            number=0;
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", username));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
            //System.out.println("JSON OBJECT CREATED SUCCESSFULLY");
            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            try {
                System.out.println("TRY BLOCK CALLED");
                pd.dismiss();
                if (result != null) {
                    System.out.print(result.toString());
                    JSONArray ja=result.getJSONArray("result");
                    number=ja.length();
                    if(number==0)
                    {
                        AlertDialog.Builder alert=new AlertDialog.Builder(Notifications.this);
                        alert.setMessage("No notifications for now!");
                        alert.show();
                        fab.setEnabled(false);
                    }
                    unames=new String[number];
                    tv=new TextView[number*2];
                    imgs=new de.hdodenhof.circleimageview.CircleImageView[number];
                    outer=new LinearLayout[number];
                    inner=new LinearLayout[number];
                    parent.getBackground().setAlpha(180);
                    for(int i=0,x=0;i<number;i++,x+=2)
                    {
                        JSONObject jo=ja.getJSONObject(i);
                        Drawable d=getResources().getDrawable(R.drawable.sample_profile);
                        createView(jo.getString("sname"),d,i,x,jo.getString("date"),Integer.parseInt(jo.getString("status")));
                        unames[i]=jo.getString("suname");
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
    void createView(String name, Drawable image,int i,int x,String date,int stat){
        outer[i]=new LinearLayout(this);
        outer[i].setLayoutParams(params2);
        outer[i].setOrientation(LinearLayout.HORIZONTAL);
        inner[i]=new LinearLayout(this);
        inner[i].setLayoutParams(params1);
        inner[i].setOrientation(LinearLayout.VERTICAL);
        tv[x]=new TextView(this);
        tv[x+1]=new TextView(this);
        tv[x].setLayoutParams(params2);
        tv[x+1].setLayoutParams(params2);
        tv[x].setPadding(10,10,10,10);
        tv[x+1].setPadding(10,10,10,10);
        tv[x].setTextColor(Color.BLACK);
        tv[x+1].setTextColor(Color.BLACK);
        tv[x].setText(name);
        tv[x+1].setText(date);
        imgs[i]=new de.hdodenhof.circleimageview.CircleImageView(this);
        imgs[i].setLayoutParams(imgparams);
        imgs[i].setImageDrawable(image);
        inner[i].addView(tv[x]);
        inner[i].addView(tv[x+1]);
        inner[i].setPadding(10,10,0,0);
        outer[i].addView(imgs[i]);
        outer[i].addView(inner[i]);
        outer[i].setPadding(10,10,10,10);
        if(stat==0)
            outer[i].setBackgroundColor(Color.WHITE);
        parent.addView(outer[i]);
        inflater.inflate(R.layout.divider,parent);
        final String nn=name;
        final int z=i;
        outer[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notifications.this,Details.class);
                intent.putExtra("name",nn);
                intent.putExtra("uname",unames[z]);
                intent.putExtra("parentuname",username);
                startActivity(intent);
            }
        });
    }
    class setread extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",username));
            //params.add(new BasicNameValuePair("password", password));

            JSONObject json = jsonParser.makeHttpRequest("https://talentyaari.000webhostapp.com/Android_Retrievals/setRead.php", "POST", params);
            System.out.println("JSON OBJECT CREATED SUCCESSFULLY");

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            try {
                System.out.println("TRY BLOCK CALLED");
                if (result != null) {
                    pd.dismiss();
                    System.out.println(result.toString());
                    if(result.getString("error").equalsIgnoreCase("false")) {
                        Toast.makeText(Notifications.this,"Notifications successfully marked read!",Toast.LENGTH_SHORT).show();
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
