package com.example.vizigo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vizigo.DataBase.DataBaseHelper;
import com.example.vizigo.R;
import com.example.vizigo.SignUp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText e1, e2;
    Button b1;
    LinearLayout b2;
    DataBaseHelper db;

    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        db = new DataBaseHelper(this);
        e1 = (EditText) findViewById(R.id.etUsername);
        e2 = (EditText) findViewById(R.id.etPassword);
        b1 = (Button) findViewById(R.id.loginbutton);
        b2 = (LinearLayout) findViewById(R.id.loginbutton2);

        progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegistrationAlertDialog();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userenteredcontact_num = e1.getText().toString().trim();
                final String pwd = e2.getText().toString().trim();

                if (userenteredcontact_num.isEmpty()) {
                    e1.requestFocus();
                    e1.setError("Enter Contact Number");
                } else if (pwd.isEmpty()) {
                    e2.requestFocus();
                    e2.setError("Enter Password");
                } else {
                    progressdialog.show();
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Users");

                    //Like select contact no from user database
                    Query chechUser = reference.orderByChild("contact_no").equalTo(userenteredcontact_num);

                    chechUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // contact number matches then check for it password
                                String passwordFromFirebaseDb = snapshot.child(userenteredcontact_num).child("password").getValue().toString();

                                if (pwd.equals(passwordFromFirebaseDb)) {
                                    progressdialog.dismiss();
                                    String nameFromFirebaseDb = snapshot.child(userenteredcontact_num).child("name").getValue().toString();
                                    String contacNumberFromFirebaseDb = snapshot.child(userenteredcontact_num).child("contact_no").getValue().toString();
                                    String emailFromFirebaseDb = snapshot.child(userenteredcontact_num).child("email").getValue().toString();
                                    String rollidFromFirebaseDb = snapshot.child(userenteredcontact_num).child("roll_id").getValue().toString();

                                    long val = db.addUser(nameFromFirebaseDb, contacNumberFromFirebaseDb, emailFromFirebaseDb, Integer.parseInt(rollidFromFirebaseDb));
                                    if (val > 0) {
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        if(rollidFromFirebaseDb.equals("0")){
                                            finish();
                                            Intent intent =new Intent(LoginActivity.this,VisitorHomeActivity.class);
                                            intent.putExtra("name",nameFromFirebaseDb);
                                            intent.putExtra("contact_no",contacNumberFromFirebaseDb);
                                            startActivity(intent);


                                        }else if(rollidFromFirebaseDb.equals("1")){
                                            finish();
                                            Intent intent =new Intent(LoginActivity.this,EmployeeHomeActivity.class);
                                            intent.putExtra("name",nameFromFirebaseDb);
                                            intent.putExtra("contact_no",contacNumberFromFirebaseDb);
                                            startActivity(intent);                                        }

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }


                                } else {
                                    progressdialog.dismiss();
                                    e2.requestFocus();
                                    e2.setError("Incorrect Password");
                                }
                            } else {
                                progressdialog.dismiss();
                                e1.requestFocus();
                                e1.setError("Invalid Contact Number");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressdialog.dismiss();
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }

    private void showRegistrationAlertDialog() {

        // Create an alert builder
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Register For ?");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_resgistration, null);
        builder.setView(customLayout);

        Button btnVisitor = customLayout.findViewById(R.id.btn_visitor);
        Button btnEmployee = customLayout.findViewById(R.id.btn_employee);
        TextView btnCancel = customLayout.findViewById(R.id.btn_cancel);


        final AlertDialog dialog = builder.create();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.putExtra("cameFrom", 0);
                startActivity(intent);
            }
        });

        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                intent.putExtra("cameFrom", 1);
                startActivity(intent);
            }
        });

        dialog.show();

    }
}


