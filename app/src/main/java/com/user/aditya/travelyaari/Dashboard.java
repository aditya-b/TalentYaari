package com.user.aditya.travelyaari;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    AlertDialog dialog;
    boolean check = true;
    ProgressDialog pd,pd2,progressDialog;
    Bitmap bp;
    Drawable d,profimg;
    JSONParser jsonParser=new JSONParser();
    int status=0,stat=0;
    String talent;
    String username,tl,na,pa,searchstring;
    String getURL= "https://talentyaari.000webhostapp.com/Android_Retrievals/setTalent.php";
    String ImagePath = "image_path" ;
    String ServerUploadPath ="https://talentyaari.000webhostapp.com/Android_Retrievals/img_upload_to_server.php" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(Dashboard.this,"Logout",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert=new AlertDialog.Builder(Dashboard.this);
                alert.setCancelable(false);
                alert.setTitle("Confirm your action");
                alert.setMessage("Do you really want to logout?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i=new Intent(Dashboard.this,MainActivity.class);
                        finish();
                        startActivity(i);
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        final EditText search=(EditText)findViewById(R.id.search);
        ImageButton srch=(ImageButton)findViewById(R.id.searchbut);
        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchstring=search.getText().toString();
                search.setText("");
                if(searchstring.isEmpty())
                {
                    AlertDialog.Builder alert=new AlertDialog.Builder(Dashboard.this);
                    alert.setMessage("Keywords empty! Please enter a keyword to search!");
                    alert.show();
                }
                else{
                Intent intent=new Intent(Dashboard.this,Main3Activity.class);
                intent.putExtra("search",searchstring);
                startActivity(intent);}
            }
        });
        final Bundle b=getIntent().getExtras();
        int loader = R.drawable.sample_profile;
        username=b.getString("uname");
        na=b.getString("name");
        pa=b.getString("password");
        tl=b.getString("talent");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageButton b1=(ImageButton)findViewById(R.id.imageButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talent="Director";
                callIntent();
            }
        });
        ImageButton b2=(ImageButton)findViewById(R.id.imageButton2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talent="Cinematographer";
                callIntent();
            }
        });
        ImageButton b3=(ImageButton)findViewById(R.id.imageButton3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talent="Singer";
                callIntent();
            }
        });
        ImageButton b4=(ImageButton)findViewById(R.id.imageButton4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talent="Asst. Director";
                callIntent();
            }
        });
        ImageButton b5=(ImageButton)findViewById(R.id.imageButton6);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talent="Editor";
                callIntent();
            }
        });
        ImageButton b6=(ImageButton)findViewById(R.id.imageButton5);
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talent="Music Director";
                callIntent();
            }
        });
        ImageButton b7=(ImageButton)findViewById(R.id.imageButton7);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talent="Catering";
                callIntent();
            }
        });
        ImageButton b8=(ImageButton)findViewById(R.id.imageButton8);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talent="Producer";
                callIntent();
            }
        });
        final View x=navigationView.getHeaderView(0);
        final LinearLayout l=(LinearLayout)x.findViewById(R.id.lin);
        final CircleImageView propic=(CircleImageView)x.findViewById(R.id.propicdash);
        profimg=propic.getDrawable();
        l.getBackground().setAlpha(120);
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        imgLoader.DisplayImage(b.getString("imgurl"), loader, propic);
        /*propic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGallery(propic,1);
            }
        });*/
        final TextView uname=(TextView) x.findViewById(R.id.uname);
        final TextView talent=(TextView)x.findViewById(R.id.proff);
        uname.getBackground().setAlpha(120);
        talent.getBackground().setAlpha(120);
        if(tl.equals("null")) {
            AlertDialog.Builder alert = new AlertDialog.Builder(Dashboard.this);
            alert.setTitle("Who are you?");
            alert.setCancelable(false);
            final CharSequence[] seq = {"Director", "Cinematographer", "Editor", "Music Director", "Producer", "Singer", "Asst. Director", "Catering"};
            alert.setSingleChoiceItems(seq, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setTalent t=new setTalent();
                    tl=String.valueOf(seq[which]);
                    t.execute(username,tl);
                    pd=new ProgressDialog(Dashboard.this);
                    pd.setCancelable(false);
                    pd.setMessage("Updating your details...");
                    pd.setTitle("Setting up your stage!");
                    pd.show();
                    dialog.dismiss();
                }
            });
            dialog = alert.create();
            dialog.show();
        }
        uname.setText(username);
        talent.setText(tl);

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        View v = nv.getHeaderView(0);
        if(data==null)
        {
            Toast.makeText(Dashboard.this,"No Image Selected",Toast.LENGTH_SHORT).show();
                CircleImageView cv=(CircleImageView)v.findViewById(R.id.propicdash);
                cv.setImageDrawable(profimg);
        }
        else
        {
             Bundle extras2 = data.getExtras();

            try{
                bp = extras2.getParcelable("data");
                d = new BitmapDrawable(getResources(), bp);
                d.setAlpha(120);
            }
            catch(Exception e)
            {
                Toast.makeText(Dashboard.this,"Select image only from Gallery!",Toast.LENGTH_SHORT).show();

            }
            if(resultCode==RESULT_OK)
            {
                try{
                        CircleImageView cv=(CircleImageView)v.findViewById(R.id.propicdash);
                        cv.setImageDrawable(d);
                        ImageUploadToServerFunction();
                }
                catch (Exception e)
                {
                    Toast.makeText(Dashboard.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            pd2=new ProgressDialog(Dashboard.this);
            pd2.setCancelable(false);
            pd2.setMessage("Loading your profile...");
            pd2.show();
            getStatus g=new getStatus();
            g.execute(username,pa);
            return false;
        } else if (id == R.id.nav_gallery) {
            Intent i=new Intent(Dashboard.this,Description.class);
            i.putExtra("uname",username);
            startActivity(i);
            return false;
        } else if (id == R.id.nav_slideshow) {
            Intent i=new Intent(Dashboard.this,Notifications.class);
            i.putExtra("uname",username);
            startActivity(i);
            return false;
        } else if (id == R.id.nav_det) {
            Intent i=new Intent(Dashboard.this,Main4Activity.class);
            //i.putExtra("uname",username);
            startActivity(i);
            return false;

        } else if (id == R.id.nav_send) {
            Intent i=new Intent(Dashboard.this,Help.class);
            //i.putExtra("uname",username);
            startActivity(i);
            return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }
    class setTalent extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){

            status=0;
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {

            String talent = args[1];
            String name= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("talent", talent));

            JSONObject json = jsonParser.makeHttpRequest(getURL, "POST", params);
            //System.out.println("JSON OBJECT CREATED SUCCESSFULLY");

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            try {
                System.out.println("TRY BLOCK CALLED");
                if (result != null) {
                    if(result.getString("error").equalsIgnoreCase("false")) {
                        status=1;
                        Toast.makeText(getApplicationContext(),"Profession Successfully Updated!",Toast.LENGTH_LONG).show();
                    }
                    if(status==0)
                    {
                        Toast.makeText(getApplicationContext(),"Couldn't update profession!",Toast.LENGTH_LONG).show();
                        tl=null;
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
                pd.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void startGallery(View l,int d){
        final Intent i=new Intent();
        final int a=d;
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.putExtra("crop", "true");
        i.putExtra("aspectX", l.getWidth());
        i.putExtra("aspectY", l.getHeight());
        i.putExtra("scale",true);
        i.putExtra("return-data", true);
        AlertDialog.Builder alert=new AlertDialog.Builder(Dashboard.this);
        alert.setTitle("Are you sure?");
        alert.setMessage("Image Selection is only available from Gallery! Continue?");
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(Intent.createChooser(i,"Select Background"),a);
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
    void callIntent(){
        Intent i=new Intent(Dashboard.this,ListofPeople.class);
        i.putExtra("name",na);
        i.putExtra("uname",username);
        i.putExtra("talent",talent);
        startActivity(i);
    }
    class getStatus extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            stat=0;
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {

            String password = args[1];
            String name= args[0];

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", name));
            params.add(new BasicNameValuePair("password", password));

            JSONObject json = jsonParser.makeHttpRequest("https://talentyaari.000webhostapp.com/Android_Retrievals/getStatus.php", "POST", params);
            System.out.println("JSON OBJECT CREATED SUCCESSFULLY");

            return json;

        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            System.out.println("EXECUTE METHOD CALLED");
            try {
                System.out.println("TRY BLOCK CALLED");
                if (result != null) {
                    pd2.dismiss();
                    System.out.println(result.toString());
                    Bundle bundle=new Bundle();
                    if(result.getString("error").equalsIgnoreCase("false")) {
                        stat = Integer.parseInt(result.getString("response"));
                        bundle.putString("name",result.getString("name"));
                        bundle.putString("uname",result.getString("uname"));
                        bundle.putString("talent",result.getString("talent"));
                        bundle.putString("password",result.getString("password"));
                        bundle.putString("urating",result.getString("urating"));
                        bundle.putString("arating",result.getString("arating"));
                        bundle.putString("email",result.getString("email"));
                        bundle.putString("rcode",result.getString("rcode"));
                        System.out.println(result.getString("name"));
                    }
                    if(stat==1)
                    {
                        Intent i=new Intent(Dashboard.this,EditProfile.class).putExtras(bundle);
                        startActivity(i);
                    }

                }
                else {
                    pd2.dismiss();
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
    public void ImageUploadToServerFunction(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();

        bp.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(Dashboard.this,"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
                progressDialog.dismiss();

                // Printing uploading success message coming from server on android app.
                Toast.makeText(Dashboard.this,string1,Toast.LENGTH_LONG).show();



            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ImagePath, ConvertImage);
                HashMapParams.put("username", username);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }
}
