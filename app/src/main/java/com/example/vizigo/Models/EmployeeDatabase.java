package com.example.vizigo.Models;

public class EmployeeDatabase {
    String id;
    String name;
    String contact_no;
    String email;
    String password;
    String company_id;
    String designation;
    String profile_picture_link;

    public EmployeeDatabase(){

    }

    public EmployeeDatabase(String id, String name, String contact_no, String email, String password, String company_id, String designation, String profile_picture_link) {
        this.id = id;
        this.name = name;
        this.contact_no = contact_no;
        this.email = email;
        this.password = password;
        this.company_id = company_id;
        this.designation = designation;
        this.profile_picture_link = profile_picture_link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getProfile_picture_link() {
        return profile_picture_link;
    }

    public void setProfile_picture_link(String profile_picture_link) {
        this.profile_picture_link = profile_picture_link;
    }
}
