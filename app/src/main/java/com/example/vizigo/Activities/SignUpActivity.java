package com.example.vizigo.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vizigo.DataBase.DataBaseHelper;
import com.example.vizigo.Model;
import com.example.vizigo.Models.EmployeeDatabase;
import com.example.vizigo.Models.UserDatabase;
import com.example.vizigo.Models.VisitorDatabase;
import com.example.vizigo.PhotoUpload;
import com.example.vizigo.R;
import com.example.vizigo.SplashScreen;
import com.example.vizigo.Utils.CompressImages;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.Random;

public class SignUpActivity extends AppCompatActivity {
    DataBaseHelper db;
    EditText e1, e2, e3, e4, e5, et_companyId, et_designation;
    ImageView imageView;
    Button b3;
    LinearLayout login;

    //firebaseVariables
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressdialog;

    int cameFrom;
    Context context;

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    public static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 2;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 3;
    public static final int REQUEST_CAMERA = 0;
    File destination;
    Uri imageUri;
    StorageReference storageReference;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private StorageReference rf = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;


        db = new DataBaseHelper(this);
        e1 = (EditText) findViewById(R.id.etName);
        e2 = (EditText) findViewById(R.id.etContactNumber);
        e3 = (EditText) findViewById(R.id.editTextTextEmailAddress);
        e4 = (EditText) findViewById(R.id.etPassword);
        e5 = (EditText) findViewById(R.id.etPassword2);
        et_companyId = (EditText) findViewById(R.id.et_companyId);
        et_designation = (EditText) findViewById(R.id.et_designation);
        imageView = (ImageView)findViewById(R.id.pofrilepic);

        progressdialog= new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");


        b3 = (Button) findViewById(R.id.loginbutton2);
        login = findViewById(R.id.tvlogin);

        Intent intent = getIntent();
        cameFrom = intent.getIntExtra("cameFrom", 0);


        checkForRegistraion(cameFrom);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        //    Toast.makeText(this, "" + cameFrom, Toast.LENGTH_SHORT).show();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameFrom == 1) {
                    validateForEmployee();
                } else {
                    validateForVisitor();
                }

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }

    }
    private void uploadToFirebase(String  cno,Uri uri){
        final StorageReference fileRef = rf.child( cno+ "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Model model = new Model(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);
                        progressdialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                        if (cameFrom == 1) {
                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }
    private void validateForEmployee() {
        String fn = e1.getText().toString().trim();
        String contactNumber = e2.getText().toString().trim();
        String email = e3.getText().toString().trim();
        String pwd = e4.getText().toString().trim();
        String cnf_pwd = e5.getText().toString().trim();
        String cmp_id = et_companyId.getText().toString().trim();
        String designation = et_designation.getText().toString().trim();

        if (fn.isEmpty()) {
            e1.requestFocus();
            e1.setError("Please Enter Full Name");
        } else if (contactNumber.isEmpty()) {
            e2.requestFocus();
            e2.setError("Please Enter  Contact Number");
        } else if (cmp_id.isEmpty()) {
            et_companyId.requestFocus();
            et_companyId.setError("Please Enter Company Id");
        } else if (!cmp_id.equals("VITBE123")) {
            et_companyId.requestFocus();
            et_companyId.setError("Invalid Company Id");
        }
        else if (designation.isEmpty()) {
            et_designation.requestFocus();
            et_designation.setError("Please Enter Designation");
        } else if (email.isEmpty()) {
            e3.requestFocus();
            e3.setError("Please Enter  Email");
        } else if (pwd.isEmpty()) {
            e4.requestFocus();
            e4.setError("Please Enter Password");
        } else if (cnf_pwd.isEmpty()) {
            e5.requestFocus();
            e5.setError("Please Enter Confirm  Password");
        } else if (!pwd.equals(cnf_pwd)) {
            e5.requestFocus();
            e5.setError("Password Not Matched");
        }else if (imageUri==null) {
            Toast.makeText(context, "Please Upload Your Image", Toast.LENGTH_SHORT).show();
        }

        else {
            progressdialog.show();
            //veriables assigned to modal class
            EmployeeDatabase employeeDatabase = new EmployeeDatabase(contactNumber,fn,contactNumber,email,pwd,cmp_id,designation,"abc");
            //adding firebase ref
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("EmployeeDatabase");
            //sending object to firebase
            reference.child(contactNumber).setValue(employeeDatabase);

            UserDatabase userDatabase = new UserDatabase(contactNumber,contactNumber,fn,email,pwd,"abc","1");
            reference = database.getReference("Users");
            reference.child(contactNumber).setValue(userDatabase);

            uploadToFirebase(contactNumber,imageUri);

        }

    }

    private void validateForVisitor() {

        String fn = e1.getText().toString().trim();
        String contactNumber = e2.getText().toString().trim();
        String email = e3.getText().toString().trim();
        String pwd = e4.getText().toString().trim();
        String cnf_pwd = e5.getText().toString().trim();
        if (fn.isEmpty()) {
            e1.requestFocus();
            e1.setError("Please Enter Full Name");
        } else if (contactNumber.isEmpty()) {
            e2.requestFocus();
            e2.setError("Please Enter  Username");
        } else if (email.isEmpty()) {
            e3.requestFocus();
            e3.setError("Please Enter  Email");
        } else if (pwd.isEmpty()) {
            e4.requestFocus();
            e4.setError("Please Enter Password");
        } else if (cnf_pwd.isEmpty()) {
            e5.requestFocus();
            e5.setError("Please Enter Confirm  Password");
        } else if (!pwd.equals(cnf_pwd)) {
            e5.requestFocus();
            e5.setError("Password Not Matched");
        }else if (imageUri==null) {
            Toast.makeText(context, "Please Upload Your Image", Toast.LENGTH_SHORT).show();
        }
        else {
            progressdialog.show();
            //veriables assigned to modal class
            VisitorDatabase visitorDatabase = new VisitorDatabase(contactNumber,contactNumber,fn,email,pwd,"abc");
            //adding firebase ref
            database = FirebaseDatabase.getInstance();
            reference = database.getReference("VisitorDatabase");
            //sending object to firebase
            reference.child(contactNumber).setValue(visitorDatabase);

            UserDatabase userDatabase = new UserDatabase(contactNumber,contactNumber,fn,email,pwd,"abc","0");
            reference = database.getReference("Users");
            reference.child(contactNumber).setValue(userDatabase);

            uploadToFirebase(contactNumber,imageUri);


        }

    }

    private void checkForRegistraion(int cameFrom) {
        if (cameFrom == 1) {
            et_companyId.setVisibility(View.VISIBLE);
            et_designation.setVisibility(View.VISIBLE);
        } else {
            et_companyId.setVisibility(View.GONE);
            et_designation.setVisibility(View.GONE);

        }
    }



}