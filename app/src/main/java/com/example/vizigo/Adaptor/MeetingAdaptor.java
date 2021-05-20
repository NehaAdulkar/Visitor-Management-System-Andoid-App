package com.example.vizigo.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vizigo.Models.MeetingDatabase;
import com.example.vizigo.R;

import java.util.List;

public class MeetingAdaptor extends RecyclerView.Adapter<MeetingAdaptor.MyViewholder> {
    private List<MeetingDatabase> meetingDatabaseList;

    public MeetingAdaptor(List<MeetingDatabase> meetingDatabaseList) {
        this.meetingDatabaseList = meetingDatabaseList;
    }

    @NonNull
    @Override
    public MeetingAdaptor.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_employee_layout,parent,false);
        return new MeetingAdaptor.MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdaptor.MyViewholder holder, int position) {
        final MeetingDatabase current= meetingDatabaseList.get(position);
        holder.name.setText(current.getEmployee_name());
        holder.mail.setText(current.getPurpose_of_meeting());
        holder.designation.setText(current.getDate_time_of_visit());
        holder.book.setText(current.getMeeting_status());
    }

    @Override
    public int getItemCount() {
        return meetingDatabaseList.size();
    }

    public class MyViewholder extends  RecyclerView.ViewHolder {
        private TextView name,designation,mail;
        private ImageView profile_pic;
        private TextView book;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.tv_name);
            designation=(TextView)itemView.findViewById(R.id.tv_designation);
            mail=(TextView)itemView.findViewById(R.id.tv_email);
            profile_pic=(ImageView) itemView.findViewById(R.id.iv_profile_pic);
            book=(TextView) itemView.findViewById(R.id.btn_book);
        }
    }
}
