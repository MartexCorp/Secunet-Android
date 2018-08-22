package com.app.martex.healthapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private Button update_info;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mDatabase;
    private TextView id_num,phone_num,bloodgrp,dob,e_num1,e_num2,profile_name,profile_weight,profile_height,profile_age;
    private ImageView profileImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Account Info");

        id_num = findViewById(R.id.id_num);
        phone_num = findViewById(R.id.phone_num);
        bloodgrp = findViewById(R.id.blood_grp);
        dob = findViewById(R.id.dob);
        e_num1 = findViewById(R.id.e_num1);
        e_num2 = findViewById(R.id.e_num2);
        profile_name = findViewById(R.id.profile_name);
        profileImg = findViewById(R.id.profile_img);
        profile_weight = findViewById(R.id.profile_weight);
        profile_height = findViewById(R.id.profile_height);
        profile_age = findViewById(R.id.profile_age);

        update_info = findViewById(R.id.update_save_btn);

        update_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PasswordInit.class);
                startActivity(i);
            }
        });

        if (user != null) {
            String telephone = user.getPhoneNumber();

            mDatabase = FirebaseDatabase.getInstance().getReference(telephone);
            mDatabase.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String id_nump = dataSnapshot.child("nic").getValue().toString();
                                                    String phone_nump = dataSnapshot.getKey();
                                                    String bloodgrpp = dataSnapshot.child("blood").getValue().toString();
                                                    String dobp = dataSnapshot.child("dob").getValue().toString();
                                                    String emergencyp = dataSnapshot.child("emergencyNumbers").getValue().toString();
                                                    String namep = dataSnapshot.child("name").getValue().toString();
                                                    String url = dataSnapshot.child("photoUrl").getValue().toString();
                                                    String wieghtp = dataSnapshot.child("weight").getValue().toString();
                                                    String heightp = dataSnapshot.child("height").getValue().toString();
                                                    String [] e_num = emergencyp.split(";");
                                                    String e_num1p = e_num[0].trim();
                                                    String e_num2p = e_num[1].trim();
                                                    profile_height.setText(heightp+"cm");
                                                    profile_weight.setText(wieghtp+"kg");
                                                    id_num.setText("ID Card Number: "+id_nump);
                                                    phone_num.setText("Phone Number: "+phone_nump);
                                                    bloodgrp.setText("Bloodgroup: "+bloodgrpp);
                                                    dob.setText("Date Of Birth: "+dobp);
                                                    e_num1.setText("Emergency Number 1: "+e_num1p);
                                                    e_num2.setText("Emergency Number 2: "+e_num2p);
                                                    profile_name.setText(namep);
                                                    Picasso.get().load(url).into(profileImg);

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {



                                                }
                                            }
            )
            ;
        }

    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
