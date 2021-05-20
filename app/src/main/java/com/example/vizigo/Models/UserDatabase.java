package com.example.vizigo.Models;

public class UserDatabase {
    String id;
    String contact_no;
    String name;
    String email;
    String password;
    String profile_picture_link;
    String roll_id;

    public UserDatabase(String id, String contact_no, String name, String email, String password, String profile_picture_link, String roll_id) {
        this.id = id;
        this.contact_no = contact_no;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile_picture_link = profile_picture_link;
        this.roll_id = roll_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getProfile_picture_link() {
        return profile_picture_link;
    }

    public void setProfile_picture_link(String profile_picture_link) {
        this.profile_picture_link = profile_picture_link;
    }

    public String getRoll_id() {
        return roll_id;
    }

    public void setRoll_id(String roll_id) {
        this.roll_id = roll_id;
    }
}
