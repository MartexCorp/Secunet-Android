package com.app.martex.healthapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class UpdateProfileActivity extends AppCompatActivity {

    private Button update_to_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Update Profile");

        update_to_db = findViewById(R.id.update_btn);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}