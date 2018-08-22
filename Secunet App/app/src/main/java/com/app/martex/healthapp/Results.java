package com.app.martex.healthapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Results extends AppCompatActivity {

    private FloatingActionButton fab_diag;
    private FloatingActionButton fab_pres;
    private LinearLayout diag_layout;
    private LinearLayout pres_layout;
    private String diag_g, pres_g;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*diag_g="Trish,Boutique,For,You,Truth";
        pres_g="Always,The,Best,For,You";*/

        Intent intent = getIntent();
        int int_pos = intent.getIntExtra("position", 0);
        ArrayList<String> medical_data = intent.getStringArrayListExtra("data");
        String date = intent.getStringExtra("date");
        setTitle(date);

        String pass_break[] = medical_data.get(int_pos).split(";");
        diag_g = pass_break[0];
        pres_g = pass_break[1];

        diag_layout = findViewById(R.id.diag_layout);
        pres_layout = findViewById(R.id.pres_layout);

        String diag_item[] = diag_g.split(",");
        for (int x=0; x<diag_item.length; x++){
            TextView textView = new TextView(getApplicationContext());
            textView.setText(" - " + diag_item[x]);
            textView.setTextColor(Color.BLACK);
            diag_layout.addView(textView);

        }

        String pres_item[] = pres_g.split(",");
        for (int x=0; x<diag_item.length; x++){
            TextView textView = new TextView(getApplicationContext());
            textView.setText(" √ " + pres_item[x]);
            textView.setTextColor(Color.BLACK);
            pres_layout.addView(textView);

        }

        /*if (user != null) {
            String telephone = user.getPhoneNumber();

            mDatabase = FirebaseDatabase.getInstance().getReference(telephone).child("record");

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        //Record record = dataSnapshot1.getValue(Record.class);

                        diag_g = dataSnapshot1.child("diagnostics").getValue().toString();
                        //diag_g = "Carnet,Securite,SECUNET";
                        pres_g = dataSnapshot1.child("prescriptions").getValue().toString();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            }
            )
            ;
            Toast.makeText(Results.this, "Data: "+medical_data.get(int_pos), Toast.LENGTH_LONG).show();
*/
        }







        /*fab_diag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = new EditText(getApplicationContext());
                editText.setText(" - " + diag_g);
                editText.setTextColor(Color.BLACK);
                editText.setPadding(20, 30, 20, 10);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (v.hasFocus()) {
                            v.isInEditMode();
                        } else {
                            v.setClickable(false);
                        }
                    }
                });
                diag_layout.addView(editText);
            }
        });

        fab_pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = new EditText(getApplicationContext());
                editText.setText(" √ " + pres_g);
                editText.setTextColor(Color.BLACK);
                editText.setPadding(20, 30, 20, 10);
                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (v.hasFocus()) {
                            v.isEnabled();
                        } else {
                            v.setClickable(false);
                        }
                    }
                });
                pres_layout.addView(editText);
            }
        });*//*

    }*/
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private String readJsonDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {
            String jsonDataString = null;
            inputStream = getAssets().open("data.json");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            while ((jsonDataString = bufferedReader.readLine()) != null) {
                builder.append(jsonDataString);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return new String(builder);
    }



}
