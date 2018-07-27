package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {
    String n,e,p,URL="https://talentyaari.000webhostapp.com/Android_Retrievals/updateProf.php",uname,rc;
    ProgressDialog pd;
    EditText name,email,pswrd;
    TextView rcode;
    JSONParser jsonParser=new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.namelayout).getBackground().setAlpha(200);
        findViewById(R.id.passwordlayout).getBackground().setAlpha(200);
        findViewById(R.id.ratinglayout).getBackground().setAlpha(200);
        findViewById(R.id.scroll_ed).getBackground().setAlpha(200);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        pswrd=(EditText)findViewById(R.id.password);
        final ImageButton edname=(ImageButton)findViewById(R.id.edname);
        final ImageButton edemail=(ImageButton)findViewById(R.id.edemail);
        final ImageButton edpswrd=(ImageButton)findViewById(R.id.edpswrd);
        final RatingBar urating=(RatingBar)findViewById(R.id.urating);
        final RatingBar arating=(RatingBar)findViewById(R.id.arating);
        rcode=(TextView)findViewById(R.id.RCODE);
        ((ImageButton)findViewById(R.id.help)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert=new AlertDialog.Builder(EditProfile.this);
                alert.setTitle("Information");
                alert.setMessage("This code is used to reset your password in case you forgot it. Please remember this or make note of it carefully.");
                alert.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
        Bundle b=getIntent().getExtras();
        n=b.getString("name");
        e=b.getString("email");
        p=b.getString("password");
        rc=b.getString("rcode");
        name.setText(n);
        email.setText(e);
        pswrd.setText(p);
        rcode.setText(rc);
        uname=b.getString("uname");
        edname.getBackground().setAlpha(0);
        edemail.getBackground().setAlpha(0);
        edpswrd.getBackground().setAlpha(0);
        urating.setRating(Float.parseFloat(b.getString("urating")));
        arating.setRating(Float.parseFloat(b.getString("arating")));
        edname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    callAlert("Name");
            }
        });
        edemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    callAlert("Email");
            }
        });
        edpswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    callAlert("Password");
            }
        });
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fabinprof);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("FAB CLICK");
                pd=new ProgressDialog(EditProfile.this);
                pd.setTitle("Please wait...");
                pd.setMessage("Saving your changes...");
                pd.setCancelable(false);
                pd.show();
                updateProfile pu=new updateProfile();
                try {
                    pu.execute();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
    }
    void callAlert(String field)
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(EditProfile.this,R.style.AlertDialogTheme);
        alert.setTitle("Enter "+field);
        final EditText ed=new EditText(EditProfile.this);
        ed.setFocusable(true);
        ed.setFocusableInTouchMode(true);
        ed.requestFocus();
        final char ch=field.charAt(0);
        switch(ch)
        {
            case 'N':ed.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 'E':ed.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case 'P':ed.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
        }
        ed.setBackgroundResource(R.drawable.noborder);
        ed.setPadding(20,20,20,20);
        alert.setCancelable(false);
        alert.setView(ed);

        alert.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               ed.requestFocus();
                switch(ch)
               {
                   case 'N':n=(ed.getText().toString().isEmpty()?n:ed.getText().toString());
                       name.setText(n);
                       break;
                   case 'E':e=(ed.getText().toString().isEmpty()?e:ed.getText().toString());
                       email.setText(e);
                       break;
                   case 'P':p=(ed.getText().toString().isEmpty()?p:ed.getText().toString());
                       pswrd.setText(p);
                       break;
               }
                Toast.makeText(EditProfile.this,n+p+e,Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

    }

    class updateProfile extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username",uname ));
            params.add(new BasicNameValuePair("password", p));
            params.add(new BasicNameValuePair("name", n));
            params.add(new BasicNameValuePair("email", e));

            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
            System.out.println("JSON OBJECT CREATED SUCCESSFULLY");

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            pd.dismiss();
            try {
                System.out.println("TRY BLOCK CALLED");
                System.out.println(result.toString());
                if (result != null) {
                    String message="";
                    name.setText(n);
                    pswrd.setText(p);
                    email.setText(e);
                    if(result.getString("result").equalsIgnoreCase("false"))
                        message="Changes Successful!";
                    else
                        message="Error Updating Profile!";
                    Toast.makeText(EditProfile.this,message, Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(EditProfile.this, "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
