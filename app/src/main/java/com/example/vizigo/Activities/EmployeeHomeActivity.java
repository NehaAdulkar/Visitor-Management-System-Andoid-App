package com.example.vizigo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.vizigo.Adaptor.EmployeeMeetingAdaptor;
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

public class EmployeeHomeActivity extends AppCompatActivity implements EmployeeMeetingAdaptor.OnClick {

    String employeeName,employeeContactnumber;

    private List<MeetingDatabase> meetingDatabaseList;
    private RecyclerView rvEmpMeetings;
    private ImageView menu;

    private EmployeeMeetingAdaptor adapter;
    TextView username;


    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        employeeName = intent.getStringExtra("name");
        employeeContactnumber = intent.getStringExtra("contact_no");
        username = (TextView)findViewById(R.id.empusername);
        username.setText(employeeName);

        rvEmpMeetings = (RecyclerView)findViewById(R.id.rv_meetings);
        rvEmpMeetings.setHasFixedSize(true);
        rvEmpMeetings.setLayoutManager(new LinearLayoutManager(this));
        meetingDatabaseList=new ArrayList<>();
        adapter = new EmployeeMeetingAdaptor(meetingDatabaseList,this);
        rvEmpMeetings.setAdapter(adapter);

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
                        if(meetingDatabase.getEpmloyee_contact_number().equals(employeeContactnumber)){


                            if(meetingDatabase.getRejected_flag().equals("0")){
                                meetingDatabaseList.add(meetingDatabase);
                                adapter.notifyDataSetChanged();
                            }else {
                                adapter.notifyDataSetChanged();
                            }

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






    }

    @Override
    public void onAcceptClick(MeetingDatabase meetingDatabase) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("MeetingDatabase");
        //sending object to firebase
        reference.child(""+meetingDatabase.getVisitor_contact_number()+meetingDatabase.getEpmloyee_contact_number()).child("meeting_status").setValue("Accepted By "+meetingDatabase.getEmployee_name());
    }

    @Override
    public void onRejectClick(MeetingDatabase meetingDatabase) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("MeetingDatabase");
        //sending object to firebase
        reference.child(""+meetingDatabase.getVisitor_contact_number()+meetingDatabase.getEpmloyee_contact_number()).child("meeting_status").setValue("Rejected By "+meetingDatabase.getEmployee_name());
        reference.child(""+meetingDatabase.getVisitor_contact_number()+meetingDatabase.getEpmloyee_contact_number()).child("rejected_flag").setValue("1");

    }
}