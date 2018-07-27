package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class
FP extends AppCompatActivity {
    ProgressDialog pd;
    String rcode,pass1,pass2,uname;
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        LinearLayout lin1=(LinearLayout)findViewById(R.id.lin1);
        LinearLayout lin2=(LinearLayout)findViewById(R.id.lin2);
        ScrollView sv=(ScrollView)findViewById(R.id.scroll_FP);
        sv.getBackground().setAlpha(200);
        lin1.getBackground().setAlpha(200);
        lin2.getBackground().setAlpha(200);
        final EditText t1=(EditText)findViewById(R.id.t1);
        final EditText t2=(EditText)findViewById(R.id.t2);
        final EditText t3=(EditText)findViewById(R.id.t3);
        final EditText t4=(EditText)findViewById(R.id.t4);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(FP.this);
                uname=t1.getText().toString();
                rcode=t2.getText().toString();
                pass1=t3.getText().toString();
                pass2=t4.getText().toString();
                if(uname.isEmpty()||rcode.isEmpty()||pass1.isEmpty()||pass2.isEmpty())
                {
                    alert.setMessage("All fields are mandatory");
                    alert.show();
                }
                else if(!(pass1.equals(pass2)))
                {
                    alert.setMessage("Passwords donot match");
                    alert.show();
                }
                else if(t2.length()!=6)
                {
                    alert.setMessage("Reset code length must be 6");
                    alert.show();
                }
                else
                {
                    resetFP rfp=new resetFP();
                    rfp.execute();
                    pd=new ProgressDialog(FP.this);
                    pd.setCancelable(false);
                    pd.setTitle("Please wait...");
                    pd.setMessage("Changing your password...");
                    pd.show();
                }
            }

        });
    }
    class resetFP extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", uname));
            params.add(new BasicNameValuePair("password", pass1));
            params.add(new BasicNameValuePair("rcode", rcode));

            JSONObject json = jsonParser.makeHttpRequest("https://talentyaari.000webhostapp.com/Android_Retrievals/resetPass.php", "POST", params);
            System.out.println("JSON OBJECT CREATED SUCCESSFULLY");

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            try {
                System.out.println("TRY BLOCK CALLED");
                pd.dismiss();
                if (result != null) {
                    System.out.println(result.toString());
                    String title,message;
                    AlertDialog.Builder alert=new AlertDialog.Builder(FP.this);
                    if(result.getString("error").equalsIgnoreCase("false")) {
                        title="Success!";
                        message="Password reset successful!";
                    }
                    else
                    {
                        title="Failed!";
                        message="Invalid Reset Code-Username combination!";
                    }
                    alert.setTitle(title);
                    alert.setMessage(message);
                    alert.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.setNegativeButton("BACK TO LOGIN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();;
                        }
                    });
                    alert.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
