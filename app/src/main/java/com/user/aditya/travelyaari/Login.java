package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    JSONParser jsonParser=new JSONParser();
    ProgressDialog pd;
    int status=0;
    String getURL= "https://talentyaari.000webhostapp.com/Android_Retrievals/getStatus.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final EditText u=(EditText)findViewById(R.id.uname);
        final EditText p=(EditText)findViewById(R.id.pass);
        u.setText("");
        p.setText("");
        status=0;
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(200);
        LinearLayout parent=(LinearLayout)findViewById(R.id.parent);
        parent.getBackground().setAlpha(120);
        LinearLayout child=(LinearLayout)findViewById(R.id.child);
        child.getBackground().setAlpha(160);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FloatingActionButton b=(FloatingActionButton)findViewById(R.id.submitlogin);
        pd=new ProgressDialog(Login.this);
        pd.setTitle("Please wait!");
        pd.setMessage("Logging in...");
        pd.setCancelable(false);
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.rounded_et);
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundResource(R.drawable.rounded_et);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatus g=new getStatus();
                String a=u.getText().toString(),b=p.getText().toString();
                int i=1;
                if(a.isEmpty())
                {
                    Toast.makeText(Login.this,"Username cannot be empty!",Toast.LENGTH_SHORT).show();
                    u.setBackgroundResource(R.drawable.failed);
                    if(b.isEmpty())
                    {
                        Toast.makeText(Login.this,"Password cannot be empty!",Toast.LENGTH_SHORT).show();
                        p.setBackgroundResource(R.drawable.failed);
                        i=0;
                    }
                    i=0;
                }
                //Toast.makeText(Login.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
                if(i!=0)
                {
                    g.execute(a,b);
                    pd.show();
                }
            }
        });
        Button b2=(Button)findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,FP.class);
                startActivity(i);
            }
        });
    }
    class getStatus extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            status=0;
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {

            String password = args[1];
            String name= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));

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
                    pd.dismiss();
                    System.out.println(result.toString());
                    Bundle bundle=new Bundle();
                    if(result.getString("error").equalsIgnoreCase("false")) {
                        status = Integer.parseInt(result.getString("response"));
                        bundle.putString("name",result.getString("name"));
                        bundle.putString("uname",result.getString("uname"));
                        bundle.putString("talent",result.getString("talent"));
                        bundle.putString("password",result.getString("password"));
                        bundle.putString("rcode",result.getString("rcode"));
                        bundle.putString("imgurl",result.getString("propic"));
                        System.out.println(result.getString("name"));
                    }
                    if(status==1)
                    {
                        Intent i=new Intent(Login.this,Dashboard.class).putExtras(bundle);
                        finish();
                        startActivity(i);
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Invalid Credentials!",Toast.LENGTH_LONG).show();
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
