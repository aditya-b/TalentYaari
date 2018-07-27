package com.user.aditya.travelyaari;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Works.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Works#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Works extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static EditText ed;
    JSONParser jsonParser=new JSONParser();
    String URL,des,wrk,uname;
    // TODO: Rename and change types of parameters


    private OnFragmentInteractionListener mListener;

    public Works() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Works newInstance() {
        Works fragment = new Works();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_works, container, false);
        ed=(EditText)v.findViewById(R.id.editText2);
        ed.setLongClickable(true);
        ed.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm=(ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip=ClipData.newPlainText("Label",ed.getText().toString());
                cm.setPrimaryClip(clip);
                return false;
            }
        });
        uname=getArguments().getString("uname");
        Status s=new Status();
        s.execute();
        return v;
    }
    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            Works.OnFragmentInteractionListener callback = (Works.OnFragmentInteractionListener) getActivity();
            callback.hello();
        } catch (ClassCastException e) {
            Log.e("ERROR", getActivity()+" must implement ViewInterface");
        }}
   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void hello();
    }

    public static void setEd() {
        ed.setEnabled(true);
    }

    public boolean canexit()
    {
        return ed.isEnabled();
    }
    public String sendToActivity()
    {
        ed.setEnabled(false);
        return ed.getText().toString();
    }
    class Status extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute(){
            URL="https://talentyaari.000webhostapp.com/Android_Retrievals/getDandW.php";
            super.onPreExecute();
        }
        @Override

        protected JSONObject doInBackground(String... args) {
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", uname));
            System.out.println(String.valueOf(params.isEmpty()));
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
                        wrk=result.getString("works");
                        System.out.println("Description"+des);
                        System.out.println("Works"+wrk);
                        if(wrk.isEmpty())
                            wrk="No Works Uploaded Yet!";
                        ed.setText(wrk);
                        ed.setEnabled(false);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
