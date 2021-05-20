package com.example.vizigo.Adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizigo.Models.EmployeeDatabase;
import com.example.vizigo.Models.MeetingDatabase;
import com.example.vizigo.R;

import java.util.List;

public class EmployeeMeetingAdaptor extends RecyclerView.Adapter<EmployeeMeetingAdaptor.MyViewHolder> {

    private List<MeetingDatabase> meetingDatabaseList;
    private OnClick mlistener;

    public EmployeeMeetingAdaptor(List<MeetingDatabase> meetingDatabaseList,OnClick mlistener) {
        this.meetingDatabaseList = meetingDatabaseList;
        this.mlistener = mlistener;
    }


    @NonNull
    @Override
    public EmployeeMeetingAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_meeting_layout,parent,false);
        return new EmployeeMeetingAdaptor.MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeMeetingAdaptor.MyViewHolder holder, int position) {
        final MeetingDatabase current= meetingDatabaseList.get(position);
        holder.name.setText(current.getVisitor_name());
        holder.mail.setText(current.getPurpose_of_meeting());
        holder.designation.setText(current.getDate_time_of_visit());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onAcceptClick(current);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onRejectClick(current);
            }
        });



    }

    @Override
    public int getItemCount() {
        return meetingDatabaseList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        private TextView name,designation,mail;
        private ImageView profile_pic;
        private TextView accept,reject;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.tv_name);
            designation=(TextView)itemView.findViewById(R.id.tv_designation);
            mail=(TextView)itemView.findViewById(R.id.tv_email);
            profile_pic=(ImageView) itemView.findViewById(R.id.iv_profile_pic);
            accept=(TextView) itemView.findViewById(R.id.btn_accecpt);
            reject=(TextView) itemView.findViewById(R.id.btn_reject);

        }
    }

    public interface OnClick{
        void onAcceptClick(MeetingDatabase meetingDatabase);
        void onRejectClick(MeetingDatabase meetingDatabase);
    }
}
