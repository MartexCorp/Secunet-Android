package com.app.martex.healthapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class extra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Extra");
    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
