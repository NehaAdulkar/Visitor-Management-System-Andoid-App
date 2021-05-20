package com.example.vizigo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vizigo.DataBase.DataBaseHelper;

public class SignUp extends AppCompatActivity {
    DataBaseHelper db;
    EditText e1,e2,e3,e4,e5;
    Button b3;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        db=new DataBaseHelper(this);
        e1=(EditText)findViewById(R.id.etName);
        e2=(EditText)findViewById(R.id.etUsername);
        e3=(EditText)findViewById(R.id.editTextTextEmailAddress);
        e4=(EditText)findViewById(R.id.etPassword);
        e5=(EditText)findViewById(R.id.etPassword2);
        b3=(Button)findViewById(R.id.loginbutton2);
        login=findViewById(R.id.tvlogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( SignUp.this,MainActivity.class));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String fn = e1.getText().toString().trim();
//                String user = e2.getText().toString().trim();
//                String email = e3.getText().toString().trim();
//                String pwd = e4.getText().toString().trim();
//                String cnf_pwd = e5.getText().toString().trim();
//
//                if (pwd.equals(cnf_pwd)){
//                    long val=db.addUser(user,pwd);
//                    if (val>0) {
//                        Toast.makeText(SignUp.this, "Successfully finished Signing Up", Toast.LENGTH_SHORT).show();
//                        Intent moveToLogin = new Intent(SignUp.this, MainActivity.class);
//                        startActivity(moveToLogin);
//                    }
//                    else {
//                        Toast.makeText(SignUp.this, "Sign Up Error!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else {
//                    Toast.makeText(SignUp.this, "Sign Up Error!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}