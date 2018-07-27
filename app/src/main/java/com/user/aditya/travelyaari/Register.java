package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

public class Register extends AppCompatActivity {
    final String url="https://talentyaari.000webhostapp.com/Android_Retrievals/Register.php";
    JSONParser jsonParser=new JSONParser();
    ProgressDialog pd;
    int regstatus=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.getBackground().setAlpha(200);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ScrollView sv=(ScrollView)findViewById(R.id.paretn);
        sv.getBackground().setAlpha(200);
        LinearLayout ly=(LinearLayout)findViewById(R.id.child);
        ly.getBackground().setAlpha(160);
        final EditText name=(EditText)findViewById(R.id.name);
        final EditText email=(EditText)findViewById(R.id.email);
        final EditText pass1=(EditText)findViewById(R.id.pass1);
        final EditText pass2=(EditText)findViewById(R.id.pass2);
        final EditText uname=(EditText)findViewById(R.id.uname);
        final RadioGroup gender=(RadioGroup)findViewById(R.id.gender);
        final CheckBox cb=(CheckBox)findViewById(R.id.tc);
        final TextView tc=(TextView)findViewById(R.id.tct);
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    validateContainer(name,String.valueOf(isName(name.getText().toString())));
                else
                    validateContainer(name,"");
            }
        });
        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    validateContainer(email,String.valueOf(isEmailValid(email.getText().toString())));
                else
                    validateContainer(email,"");
            }
        });
        pass1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    validateContainer(pass1,String.valueOf(isPass(pass1.getText().toString())));
                else
                    validateContainer(pass1,"");
            }
        });
        pass2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    validateContainer(pass2,String.valueOf(pass1.getText().toString().equals(pass2.getText().toString())&&!pass1.getText().toString().isEmpty()));
                else
                    validateContainer(pass2,"");
            }
        });
        uname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    validateContainer(uname,String.valueOf(isPass(uname.getText().toString())));
                else
                    validateContainer(uname,"");
            }
        });
        tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this,"TERMS AND CONDITIONS",Toast.LENGTH_SHORT).show();
            }
        });
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                validateGroup(gender);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status=1;
                validateContainer(pass2,String.valueOf(pass1.getText().toString().equals(pass2.getText().toString())&&!pass1.getText().toString().isEmpty()));
                validateContainer(name,String.valueOf(isName(name.getText().toString())));
                validateContainer(email,String.valueOf(isEmailValid(email.getText().toString())));
                validateContainer(pass1,String.valueOf(isPass(pass1.getText().toString())));
                validateContainer(uname,String.valueOf(isPass(uname.getText().toString())));
                validateGroup(gender);
                if(name.getText().toString().isEmpty())
                    status=0;
                if(name.getBackground().equals(getResources().getDrawable(R.drawable.failed)))
                    status=0;
                if(email.getText().toString().isEmpty())
                    status=0;
                if(email.getBackground().equals(getResources().getDrawable(R.drawable.failed)))
                    status=0;
                if(pass1.getText().toString().isEmpty())
                    status=0;
                if(pass1.getBackground().equals(getResources().getDrawable(R.drawable.failed)))
                    status=0;
                if(pass2.getText().toString().isEmpty())
                    status=0;
                if(pass2.getBackground().equals(getResources().getDrawable(R.drawable.failed)))
                    status=0;
                if(uname.getText().toString().isEmpty())
                    status=0;
                if(uname.getBackground().equals(getResources().getDrawable(R.drawable.failed)))
                    status=0;
                if(gender.getCheckedRadioButtonId()==-1)
                    status=0;
                if(status==0)
                    Snackbar.make(view,"Please Check the Details!",Snackbar.LENGTH_LONG).setAction("OKAY",null).show();
                else if(!cb.isChecked())
                    Snackbar.make(view,"Accept Terms & Conditions!",Snackbar.LENGTH_LONG).setAction("OKAY",null).show();
                else
                {
                    String a=name.getText().toString();
                    String b=email.getText().toString();
                    String c=pass1.getText().toString();
                    String d=uname.getText().toString();
                    String e=((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString();
                    register r=new register();
                    r.execute(a,b,c,d,e);
                    pd=new ProgressDialog(Register.this);
                    pd.setTitle("Please wait...");
                    pd.setMessage("Creating your account...");
                    pd.setCancelable(false);
                    pd.show();
                }
            }
        });
    }
    private boolean isName(String name)
    {
        if(name.isEmpty())
            return false;
        for(int i=0;i<name.length();i++)
        {
            int a=(int)name.charAt(i);
            if(!((a>=65&&a<=90)||(a>=97&&a<=122)))
                return false;
        }
        return true;
    }
    private boolean isPass(String pass)
    {
        if(pass.isEmpty()||pass.contains(" "))
            return false;
        return true;
    }
    void validateGroup(RadioGroup gender)
    {
        int id=gender.getCheckedRadioButtonId();
        CharSequence gend;
        if(id!=-1)
            gend=((RadioButton)findViewById(id)).getText();
        else
            gend="Invalid";
        if(gend.equals("Invalid"))
            gender.setBackgroundColor(Color.parseColor("#ffcdd2"));
        else
            gender.setBackgroundColor(Color.TRANSPARENT);
    }
    void validateContainer(EditText t,String s)
    {
        if(s.equals("true"))
        {
            t.setBackgroundResource(R.drawable.success);
            t.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.mipmap.done,0);
        }
        else if(s.equals("false"))
        {
            t.setBackgroundResource(R.drawable.failed);
            t.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.mipmap.clear,0);
        }
        else
        {
            t.setBackgroundResource(R.drawable.rounded_et);
            t.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_account_circle_black_24dp,0,0,0);
        }
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    class register extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {

            String password = args[2];
            String name= args[0];
            String email= args[1];
            String uname= args[3];
            char gender= args[4].charAt(0);

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", uname));
            params.add(new BasicNameValuePair("password", password));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("gender",String.valueOf(gender)));
            params.add(new BasicNameValuePair("email", email));

            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
            System.out.println("JSON OBJECT CREATED SUCCESSFULLY");

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            try {
                System.out.println("TRY BLOCK CALLED");
                if (result != null) {
                    if(result.getString("error").equalsIgnoreCase("false")) {
                        regstatus = Integer.parseInt(result.getString("response"));
                        System.out.println(result.getString("email"));
                        System.out.println(result.getString("name"));
                        System.out.println(result.getString("uname"));
                        System.out.println(result.getString("pass"));
                        System.out.println(result.getString("gender"));
                        System.out.println(result.getString("count"));
                    }
                    if(regstatus==1)
                    {
                        pd.setTitle("Account Created Successfully!");
                        pd.setMessage("Please wait while we redirect to login page...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i=new Intent(Register.this,Login.class);
                                finish();
                                startActivity(i);
                            }
                        },2000);
                    }
                    else if(regstatus==2)
                    {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Username already taken!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Server Error! Please try later!",Toast.LENGTH_LONG).show();
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
