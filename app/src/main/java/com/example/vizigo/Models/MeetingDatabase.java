package com.example.vizigo.Models;

public class MeetingDatabase {

    String visitor_contact_number;
    String epmloyee_contact_number;
    String visitor_name;
    String employee_name;
    String purpose_of_meeting;
    String date_time_of_visit;
    String duration_meeting;
    String created_date;
    String meeting_status;
    String rejected_flag;
    String rejeted_reason;

    public MeetingDatabase(){

    }

    public MeetingDatabase(String visitor_contact_number, String epmloyee_contact_number, String visitor_name, String employee_name, String purpose_of_meeting, String date_time_of_visit, String duration_meeting, String created_date, String meeting_status, String rejected_flag, String rejeted_reason) {
        this.visitor_contact_number = visitor_contact_number;
        this.epmloyee_contact_number = epmloyee_contact_number;
        this.visitor_name = visitor_name;
        this.employee_name = employee_name;
        this.purpose_of_meeting = purpose_of_meeting;
        this.date_time_of_visit = date_time_of_visit;
        this.duration_meeting = duration_meeting;
        this.created_date = created_date;
        this.meeting_status = meeting_status;
        this.rejected_flag = rejected_flag;
        this.rejeted_reason = rejeted_reason;
    }

    public String getVisitor_contact_number() {
        return visitor_contact_number;
    }

    public void setVisitor_contact_number(String visitor_contact_number) {
        this.visitor_contact_number = visitor_contact_number;
    }

    public String getEpmloyee_contact_number() {
        return epmloyee_contact_number;
    }

    public void setEpmloyee_contact_number(String epmloyee_contact_number) {
        this.epmloyee_contact_number = epmloyee_contact_number;
    }

    public String getVisitor_name() {
        return visitor_name;
    }

    public void setVisitor_name(String visitor_name) {
        this.visitor_name = visitor_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getPurpose_of_meeting() {
        return purpose_of_meeting;
    }

    public void setPurpose_of_meeting(String purpose_of_meeting) {
        this.purpose_of_meeting = purpose_of_meeting;
    }

    public String getDate_time_of_visit() {
        return date_time_of_visit;
    }

    public void setDate_time_of_visit(String date_time_of_visit) {
        this.date_time_of_visit = date_time_of_visit;
    }

    public String getDuration_meeting() {
        return duration_meeting;
    }

    public void setDuration_meeting(String duration_meeting) {
        this.duration_meeting = duration_meeting;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getMeeting_status() {
        return meeting_status;
    }

    public void setMeeting_status(String meeting_status) {
        this.meeting_status = meeting_status;
    }

    public String getRejected_flag() {
        return rejected_flag;
    }

    public void setRejected_flag(String rejected_flag) {
        this.rejected_flag = rejected_flag;
    }

    public String getRejeted_reason() {
        return rejeted_reason;
    }

    public void setRejeted_reason(String rejeted_reason) {
        this.rejeted_reason = rejeted_reason;
    }


}
