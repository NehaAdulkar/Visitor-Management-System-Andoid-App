package com.example.vizigo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.vizigo.Adaptor.EmployeeListAdaptor;
import com.example.vizigo.Adaptor.MeetingAdaptor;
import com.example.vizigo.Models.EmployeeDatabase;
import com.example.vizigo.Models.MeetingDatabase;
import com.example.vizigo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MeetingActivity extends AppCompatActivity {


    private RecyclerView rvMeetings;
    private ImageView ivBack;

    private List<MeetingDatabase> meetingDatabaseList;
    private MeetingAdaptor adapter;

    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    String visitorContactNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Intent intent = getIntent();
        visitorContactNo = intent.getStringExtra("contact_no");

        rvMeetings = (RecyclerView)findViewById(R.id.rv_meetings);
        rvMeetings.setHasFixedSize(true);
        rvMeetings.setLayoutManager(new LinearLayoutManager(this));
        meetingDatabaseList=new ArrayList<>();
        adapter = new MeetingAdaptor(meetingDatabaseList);
        rvMeetings.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("MeetingDatabase");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        //instance object to get and set data
                        MeetingDatabase meetingDatabase = postSnapshot.getValue(MeetingDatabase.class);

                        //adding data list from object
                        if(meetingDatabase.getVisitor_contact_number().equals(visitorContactNo)){
                            meetingDatabaseList.add(meetingDatabase);
                            adapter.notifyDataSetChanged();
                        }else
                        {
                            adapter.notifyDataSetChanged();
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        ivBack = (ImageView)findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}