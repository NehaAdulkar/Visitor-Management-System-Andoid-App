package com.example.vizigo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vizigo.Activities.LoginActivity;
import com.example.vizigo.Activities.VisitorHomeActivity;

public class Home extends AppCompatActivity  {
    private TextView login1;
    private String visLogin;
    private CardView vistor;
    private CardView login;
    private CardView location;
    private CardView employee;
    private CardView admin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        login1=findViewById(R.id.tvlogin);
        visLogin=login1.getText().toString();

        vistor = (CardView)findViewById(R.id.cardVisitor);
        login = (CardView)findViewById(R.id.cardLogin);
        location = (CardView)findViewById(R.id.cardLocation);
        employee = (CardView)findViewById(R.id.cardEmployee);
        admin = (CardView)findViewById(R.id.cardAdmin);


        vistor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Home.this,VisitorHomeActivity.class);
                startActivity(intent2);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Home.this,LoginActivity.class);
                startActivity(intent2);
            }
        });


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Home.this,Location.class);
                startActivity(intent2);
            }
        });

        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Welcome, Employee.", Toast.LENGTH_SHORT).show();

            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, "Welcome, Admin.", Toast.LENGTH_SHORT).show();

            }
        });







    }

    public void gotoDetails(View view) {
        switch (view.getId()){
            case R.id.tvAdmin:
                Toast.makeText(this, "Welcome, Admin.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvVisitor:

                break;
            case R.id.tvEmployee:
                Toast.makeText(this, "Welcome, Employee.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvlogin:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("visLogin",visLogin);
                startActivity(intent);
                break;
        }
    }
}