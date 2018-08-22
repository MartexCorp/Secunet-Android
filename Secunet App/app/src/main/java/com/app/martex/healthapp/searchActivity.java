package com.app.martex.healthapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth;
    private static final String TAG = "tag";
    private TextView no_data;
    //private String pass_diag,pass_pres;
    private ArrayList<String> pass_medical_data = new ArrayList<String>();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String tel;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRecordRef;

    private List<Object> mRecyclerViewItems = new ArrayList<>();
    public RecyclerView.Adapter adapter;

    public ArrayList<String> getPass_medical_data() {
        return pass_medical_data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Records");

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecordAdapter(this, mRecyclerViewItems);
        recyclerView.setAdapter(adapter);

        loadRecyclerData();
    }

    private void loadRecyclerData() {
        if (user != null) {
            tel = user.getPhoneNumber();
            mRecordRef = mRootRef.child(tel).child("record");

        mRecordRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Record record = dataSnapshot1.getValue(Record.class);
                    String pass_diag = dataSnapshot1.child("diagnostics").getValue().toString();
                    String pass_pres = dataSnapshot1.child("prescriptions").getValue().toString();
                    String pass = pass_diag+";"+pass_pres;
                    pass_medical_data.add(pass);

                    /*SharedPreferences sharedPref = searchActivity.this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("data",pass);
                    editor.commit();*/

                    mRecyclerViewItems.add(record);
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.search:
                //
                return true;
            case R.id.refresh:
                finish();
                startActivity(getIntent());

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }




    }
    @Override
    protected void onStart(){
        super.onStart();


    }

}

        /*try {
            //read json file as object
            String result = readJsonDataFromFile();
            JSONObject j_obj = new JSONObject(result);
            //access first-level node objects (info and data), but data is chosen
            JSONObject j_obj_inside = j_obj.getJSONObject("data");
            //access array at second node (data)
            JSONArray j_array = j_obj_inside.getJSONArray("record");
            */
            /*// loop through the array to get all values
            for (int i = 0; i < j_array.length(); ++i) {

                JSONObject recordItem = j_array.getJSONObject(i);
                // get varios attributs of array item
                String date = recordItem.getString("date");
                String hospital = recordItem.getString("hospital");
                String hash = recordItem.getString("hash");

                // pass to model class of a record and add o recycler view
                Record record = new Record(date, hospital, hash);
                mRecyclerViewItems.add(record);
            }
        } catch (IOException | JSONException exception) {
            Log.e(MainActivity.class.getName(), "Unable to parse JSON file.", exception);
        }*/



    // read and stringify all json
    /*private String readJsonDataFromFile() throws IOException {

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
    }*/






