package com.obnoxious.ecatering.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.obnoxious.ecatering.R;
import com.obnoxious.ecatering.view.EventActivity;

public class NoConnectionActivity extends AppCompatActivity {

    Button btn_retry;
    TextView txt_title1,txt_title2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);

        Typeface face = Typeface.createFromAsset(getAssets(), "font/CaviarDreams.ttf");

        txt_title1 = findViewById(R.id.textView);
        txt_title2 = findViewById(R.id.textView2);
        txt_title1.setTypeface(face);
        txt_title2.setTypeface(face);

        btn_retry = findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NoConnectionActivity.this,EventActivity.class);
                startActivity(i);
            }
        });
    }
}
