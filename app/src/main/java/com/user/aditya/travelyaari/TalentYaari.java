package com.user.aditya.travelyaari;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TalentYaari extends AppCompatActivity {
    boolean stat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talent_yaari);
        LinearLayout mainlayout=(LinearLayout)findViewById(R.id.mainlayout);
        LinearLayout childlayout=(LinearLayout)findViewById(R.id.childlayout);
        mainlayout.getBackground().setAlpha(200);
        childlayout.getBackground().setAlpha(160);
        FloatingActionButton login=(FloatingActionButton)findViewById(R.id.log);
        FloatingActionButton register=(FloatingActionButton)findViewById(R.id.reg);
        final ConnectInfo connectInfo=new ConnectInfo(TalentYaari.this);
        stat= connectInfo.isNetworkAvailable();
        if(!stat)
            Toast.makeText(TalentYaari.this,"No Internet Connection!",Toast.LENGTH_SHORT).show();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat=connectInfo.isNetworkAvailable();
                if(!stat)
                    Toast.makeText(TalentYaari.this,"No Internet Connection!",Toast.LENGTH_SHORT).show();
                else
                {
                    Intent n=new Intent(TalentYaari.this,Login.class);
                    startActivity(n);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat=connectInfo.isNetworkAvailable();
                if(!stat)
                    Toast.makeText(TalentYaari.this,"No Internet Connection!",Toast.LENGTH_SHORT).show();
                else
                {
                    Intent n=new Intent(TalentYaari.this,Register.class);
                    startActivity(n);
                }
            }
        });
    }
}
