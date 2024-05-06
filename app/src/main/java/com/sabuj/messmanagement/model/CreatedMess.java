package com.sabuj.messmanagement.model;

public class CreatedMess {
    private String id;
    private String user_name;
    private String address;
    private String created_by;
    private String created_at;
    private String type;
    private String duration;
    private String total_member;
    private String month;
    private String manager;

    public CreatedMess() {
    }

    public CreatedMess(String id, String user_name, String address, String created_by, String created_at, String type, String duration, String total_member, String month, String manager) {
        this.id = id;
        this.user_name = user_name;
        this.address = address;
        this.created_by = created_by;
        this.created_at = created_at;
        this.type = type;
        this.duration = duration;
        this.total_member = total_member;
        this.month = month;
        this.manager = manager;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTotal_member() {
        return total_member;
    }

    public void setTotal_member(String total_member) {
        this.total_member = total_member;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}

