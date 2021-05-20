package com.example.vizigo.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.vizigo.Adaptor.EmployeeListAdaptor;
import com.example.vizigo.Models.EmployeeDatabase;
import com.example.vizigo.Models.MeetingDatabase;
import com.example.vizigo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VisitorHomeActivity extends AppCompatActivity implements EmployeeListAdaptor.OnBookingClick {

    private List<EmployeeDatabase> employeeDatabaseList;
    private RecyclerView rvEmployee;
    private ImageView menu;

    private EmployeeListAdaptor adapter;
    TextView username;
    String visitorname, visitorcontactNumber;


    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        menu = (ImageView) findViewById(R.id.menu);
        rvEmployee = (RecyclerView) findViewById(R.id.rv_employee);
        username = (TextView) findViewById(R.id.username);
        rvEmployee.setHasFixedSize(true);
        rvEmployee.setLayoutManager(new LinearLayoutManager(this));
        employeeDatabaseList = new ArrayList<>();
        adapter = new EmployeeListAdaptor(this,employeeDatabaseList, this);
        rvEmployee.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setCancelable(false);

        Intent intent = getIntent();
        visitorname = intent.getStringExtra("name");
        visitorcontactNumber = intent.getStringExtra("contact_no");

        username.setText("Hello\n" + visitorname);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("EmployeeDatabase");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        //instance object to get and set data
                        EmployeeDatabase employeeDatabase = postSnapshot.getValue(EmployeeDatabase.class);

                        //adding data list from object
                        employeeDatabaseList.add(employeeDatabase);
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenuDialog();

            }
        });


    }

    private void showMenuDialog() {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Menu");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_menu, null);
        builder.setView(customLayout);

        Button meeting_status = customLayout.findViewById(R.id.btn_meetings);
        Button location = customLayout.findViewById(R.id.btn_location);
        Button logout = customLayout.findViewById(R.id.btn_logout);
        Button cancel = customLayout.findViewById(R.id.cancel);

        final AlertDialog dialog = builder.create();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        meeting_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(VisitorHomeActivity.this,MeetingActivity.class);
                intent.putExtra("contact_no",visitorcontactNumber);
                startActivity(intent);

            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+19.021651+","+72.870806+ "&mode=d"));
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
                startActivity(new Intent(VisitorHomeActivity.this,LoginActivity.class));
            }
        });


        dialog.show();
    }

    @Override
    public void onBooking(final EmployeeDatabase employeeDatabase) {
        // Create an alert builder
        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
        builder.setTitle("Book Meeting with " + employeeDatabase.getName() + "?");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_book_appointment, null);
        builder.setView(customLayout);

        Button btnconfirm = customLayout.findViewById(R.id.confirmappointment);
        Button btncancel = customLayout.findViewById(R.id.cancel);
        final EditText purpose_of_meeting = customLayout.findViewById(R.id.purpose_of_meeting);
        final EditText date_time_input = customLayout.findViewById(R.id.date_time_input);
        date_time_input.setInputType(InputType.TYPE_NULL);
        final EditText duration = customLayout.findViewById(R.id.duration);


        final AlertDialog dialog = builder.create();

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        date_time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date_time_input);
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForVisitor();
            }

            private void validateForVisitor() {
                String purp = purpose_of_meeting.getText().toString().trim();
                String dur = duration.getText().toString().trim();
                String datetime = date_time_input.getText().toString().trim();

                if (purp.isEmpty()) {
                    purpose_of_meeting.requestFocus();
                    purpose_of_meeting.setError("Please Enter Purpose of meeting");
                } else if (dur.isEmpty()) {
                    duration.requestFocus();
                    duration.setError("Please Enter Duration");
                } else if (datetime.isEmpty()) {
                    date_time_input.requestFocus();
                    date_time_input.setError("Please Enter Date and Time");
                } else {
                    progressDialog.show();

                    MeetingDatabase meetingDatabase = new MeetingDatabase(visitorcontactNumber, employeeDatabase.getContact_no(), visitorname, employeeDatabase.getName(), purp, datetime, dur, java.text.DateFormat.getDateTimeInstance().format(new Date()), "In Progress","0","none");
                    //adding firebase ref
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("MeetingDatabase");

                    String meetingid = "" + visitorcontactNumber + employeeDatabase.getContact_no();
                    //sending object to firebase
                    reference.child(meetingid).setValue(meetingDatabase);
                    progressDialog.dismiss();
                    dialog.dismiss();
                    Toast.makeText(VisitorHomeActivity.this, "Meeting booked successfully", Toast.LENGTH_LONG).show();


                }

            }
        });


        dialog.show();

    }


    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");

                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(VisitorHomeActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(VisitorHomeActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }
}