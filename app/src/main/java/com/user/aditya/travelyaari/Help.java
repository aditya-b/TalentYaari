package com.user.aditya.travelyaari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar tb=(Toolbar)findViewById(R.id.toolbar);
        tb.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ((LinearLayout)findViewById(R.id.lin1)).getBackground().setAlpha(180);
        ((LinearLayout)findViewById(R.id.lin2)).getBackground().setAlpha(180);
        ((LinearLayout)findViewById(R.id.lin3)).getBackground().setAlpha(180);
        (findViewById(R.id.button4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText)findViewById(R.id.editText)).setText("");
                Toast.makeText(Help.this,"Thank you! Response Recorded!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
