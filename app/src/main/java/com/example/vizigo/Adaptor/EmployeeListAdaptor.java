package com.example.vizigo.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vizigo.Models.EmployeeDatabase;
import com.example.vizigo.R;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeListAdaptor extends RecyclerView.Adapter<EmployeeListAdaptor.MyViewHolder>{

    private List<EmployeeDatabase> employeeDatabaseList;
    private OnBookingClick mlistener;
    private Context context;

    public EmployeeListAdaptor(Context context,List<EmployeeDatabase> employeeDatabaseList,OnBookingClick mlistener) {
        this.employeeDatabaseList = employeeDatabaseList;
        this.mlistener=mlistener;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_employee_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final EmployeeDatabase current= employeeDatabaseList.get(position);
        holder.name.setText(current.getName());
        holder.mail.setText(current.getEmail());
        holder.designation.setText(current.getDesignation());

        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onBooking(current);
            }
        });

        Picasso.get().load(current.getProfile_picture_link())
                .placeholder(R.drawable.profile_pic)
                .into(holder.profile_pic);

    }

    @Override
    public int getItemCount() {
        return employeeDatabaseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name,designation,mail;
        private ImageView profile_pic;
        private TextView book;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.tv_name);
            designation=(TextView)itemView.findViewById(R.id.tv_designation);
            mail=(TextView)itemView.findViewById(R.id.tv_email);
            profile_pic=(ImageView) itemView.findViewById(R.id.iv_profile_pic);
            book=(TextView) itemView.findViewById(R.id.btn_book);
        }
    }

    public interface OnBookingClick{
        void onBooking(EmployeeDatabase employeeDatabase);
    }
}
