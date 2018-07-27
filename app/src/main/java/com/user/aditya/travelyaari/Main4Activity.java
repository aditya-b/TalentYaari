package com.user.aditya.travelyaari;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar tb=(Toolbar)findViewById(R.id.toolbar);
        tb.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        (findViewById(R.id.lin)).getBackground().setAlpha(180);
        (findViewById(R.id.lin2)).getBackground().setAlpha(180);
        (findViewById(R.id.lin3)).getBackground().setAlpha(180);
        (findViewById(R.id.lin4)).getBackground().setAlpha(180);
        (findViewById(R.id.lin5)).getBackground().setAlpha(180);
        (findViewById(R.id.lin6)).getBackground().setAlpha(180);
        (findViewById(R.id.lin7)).getBackground().setAlpha(180);
    }
}
