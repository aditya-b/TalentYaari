package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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

public class ListofPeople extends AppCompatActivity {
    LinearLayout outer[];
    TextView tv[];
    String unames[];
    de.hdodenhof.circleimageview.CircleImageView imgs[];
    String username,talent,URL="https://talentyaari.000webhostapp.com/Android_Retrievals/getPeople.php",NAME;
    JSONParser jsonParser=new JSONParser();
    int number;
    LinearLayout parent;
    LayoutInflater inflater;
    LinearLayout.LayoutParams params2,imgparams;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        talent=getIntent().getStringExtra("talent");
        toolbar.setTitle(talent);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        NAME=getIntent().getStringExtra("name");
        username=getIntent().getStringExtra("uname");
        parent=(LinearLayout)findViewById(R.id.linear);
        //parent.getBackground().setAlpha(160);
        inflater=(LayoutInflater)getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        params2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        //LinearLayout.LayoutParams params1=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imgparams=new LinearLayout.LayoutParams(100,100);
        getPeople gp=new getPeople();
        gp.execute();
        pd=new ProgressDialog(ListofPeople.this);
        pd.setMessage("Searching all "+talent+"s");
        pd.setCancelable(false);
        pd.show();
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
            params.add(new BasicNameValuePair("talent", talent));

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
                            AlertDialog.Builder alert=new AlertDialog.Builder(ListofPeople.this);
                            alert.setMessage("We're expanding! No records for now!");
                            alert.setIcon(getResources().getDrawable(R.mipmap.ic_mood_bad_black_18dp));
                            alert.show();
                        }
                        unames=new String[number];
                        tv=new TextView[number];
                        imgs=new de.hdodenhof.circleimageview.CircleImageView[number];
                        outer=new LinearLayout[number];
                        for(int i=0;i<number;i++)
                        {
                            JSONObject jo=ja.getJSONObject(i);
                            Drawable d=getResources().getDrawable(R.drawable.sample_profile);
                            createView(jo.getString("name"),d,i);
                            unames[i]=jo.getString("uname");
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
    void createView(String name, Drawable image,int i){
        outer[i]=new LinearLayout(this);
        outer[i].setLayoutParams(params2);
        outer[i].setOrientation(LinearLayout.HORIZONTAL);
        tv[i]=new TextView(this);
        tv[i].setLayoutParams(params2);
        tv[i].setPadding(15,15,15,15);
        tv[i].setGravity(Gravity.CENTER_VERTICAL);
        tv[i].setTextColor(Color.BLACK);
        tv[i].setTextSize(20);
        tv[i].setText(name);
        imgs[i]=new de.hdodenhof.circleimageview.CircleImageView(this);
        imgs[i].setLayoutParams(imgparams);
        imgs[i].setImageDrawable(image);
        outer[i].addView(imgs[i]);
        outer[i].addView(tv[i]);
        outer[i].setGravity(Gravity.CENTER_VERTICAL);
        outer[i].setPadding(20,20,20,20);
        outer[i].setBackgroundResource(R.drawable.roudedtextview);
        outer[i].getBackground().setAlpha(180);
        parent.addView(outer[i]);
        inflater.inflate(R.layout.divider,parent);
        final String x=name;
        final int z=i;
        outer[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ListofPeople.this,Details.class);
                    intent.putExtra("name",x);
                    intent.putExtra("parentname",NAME);
                    intent.putExtra("uname",unames[z]);
                    intent.putExtra("parentuname",username);
                    startActivity(intent);
                }
        });
    }
}
