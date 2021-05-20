package com.example.vizigo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.vizigo.Activities.LoginActivity;
import com.example.vizigo.Activities.VisitorHomeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public CardView card1, card2, card3, card4, card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home1);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        card1=(CardView)findViewById(R.id.cardVisitor);
        card2=(CardView)findViewById(R.id.cardEmployee);
        card3=(CardView)findViewById(R.id.cardAdmin);
        card4=(CardView)findViewById(R.id.cardLogin);
        card5=(CardView)findViewById(R.id.cardLocation);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.cardVisitor:
                i = new Intent(this, VisitorHomeActivity.class);
                startActivity(i);
                break;

            case R.id.cardEmployee:
                i = new Intent(this,Home.class);
                startActivity(i);
                break;

            case R.id.cardAdmin:
                i = new Intent(this,SignUp.class);
                startActivity(i);
                break;

            case R.id.cardLogin:
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;

            case R.id.cardLocation:
                i = new Intent(this,Location.class);
                startActivity(i);
                break;
        }
    }
}



