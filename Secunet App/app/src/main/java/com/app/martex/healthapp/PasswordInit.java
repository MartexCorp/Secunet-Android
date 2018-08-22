package com.app.martex.healthapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

public class PasswordInit extends AppCompatActivity {

    static final int SELECT_PICTURE = 1;
    static final int RESULT_LOAD_IMG = 2;
    public Uri filepath;
    Button imageUpload;
    ImageView imageView;
    FirebaseUser user;
    FirebaseStorage storage;
    private EditText init_pass;
    private EditText new_pass;
    private EditText weight;
    private EditText height;
    private Button save;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
    private String initial_password;
    private EditText blood;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_init);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Profile");


        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getInstance().getReference().child("photos/profiles");


        imageView = (ImageView) findViewById(R.id.imgView);
        imageUpload = (Button) findViewById(R.id.imgUpload);
        init_pass = findViewById(R.id.init_pass);
        new_pass = findViewById(R.id.new_pass);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        save = findViewById(R.id.save);
        blood = findViewById(R.id.blood);
        progressBar = findViewById(R.id.progressSaveProfile);
        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference(user.getPhoneNumber());
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    initial_password = dataSnapshot.child("password").getValue().toString();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    String telephone = user.getPhoneNumber();
                    if (init_pass.getText().toString().equals(initial_password)) {

                        if (weight.getText().toString().matches("")) {

                        } else {
                            mDatabase.child("weight").setValue(weight.getText().toString());
                        }
                        if (height.getText().toString().matches("")) {

                        } else {
                            mDatabase.child("height").setValue(height.getText().toString());

                        }
                        if (new_pass.getText().toString().matches("")) {

                        } else {
                            mDatabase.child("password").setValue(new_pass.getText().toString());

                        }
                        if (blood.getText().toString().matches("")) {

                        } else {
                            mDatabase.child("blood").setValue(blood.getText().toString());

                        }
                    }else {
                        Toast.makeText(PasswordInit.this, "Wrong Initial Password. Try again", Toast.LENGTH_SHORT).show();

                    }

                    progressBar.setVisibility(View.VISIBLE);

                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                    byte[] data = baos.toByteArray();


                    final StorageReference picRef = mStorageRef.child(telephone + "_profile.jpg");
                    if (filepath != null) {
                        picRef.putFile(filepath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Get a URL to the uploaded content
                                        picRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                mDatabase.child("photoUrl").setValue(uri.toString());
                                            }
                                        });
                                        progressBar.setVisibility(View.INVISIBLE);
                                        finish();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        // ...
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                });
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    finish();

                }


            }
        });


    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                filepath = imageUri;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(PasswordInit.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(PasswordInit.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
