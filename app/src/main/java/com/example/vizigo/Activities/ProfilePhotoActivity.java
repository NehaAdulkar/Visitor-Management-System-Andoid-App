package com.example.vizigo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.vizigo.R;

public class ProfilePhotoActivity extends AppCompatActivity {

    ImageView btnBack,profilePic;
    EditText name,email,contactNo;
    Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnBack=(ImageView)findViewById(R.id.back);
        profilePic=(ImageView)findViewById(R.id.pofrilepic);
        name=(EditText) findViewById(R.id.etName);
        contactNo=(EditText)findViewById(R.id.etContactNumber);
        email=(EditText)findViewById(R.id.editTextTextEmailAddress);
        profile=(Button) findViewById(R.id.update_profile);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });





    }
}