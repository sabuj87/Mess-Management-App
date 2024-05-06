package com.sabuj.messmanagement.model;

public class Member {
    private String member_id;
    private String member_userName;
    private String member_address;
    private String member_email;
    private String member_mess_userName;
    private String member_mess_id;
    private String joined_date;
    private String updated_at;
    private String member_nid;
    private String member_meal;
    private String member_mealRate;
    private String member_deposit;
    private String member_cost;
    private String member_due;
    private String member_balance;
    private String admin_id;
    private String is_manager;
    private String month;

    public Member() {
    }

    public Member(String member_id, String member_userName, String member_address, String member_email, String member_mess_userName, String member_mess_id, String joined_date, String updated_at, String member_nid, String member_meal, String member_mealRate, String member_deposit, String member_cost, String member_due, String member_balance, String admin_id, String is_manager, String month) {
        this.member_id = member_id;
        this.member_userName = member_userName;
        this.member_address = member_address;
        this.member_email = member_email;
        this.member_mess_userName = member_mess_userName;
        this.member_mess_id = member_mess_id;
        this.joined_date = joined_date;
        this.updated_at = updated_at;
        this.member_nid = member_nid;
        this.member_meal = member_meal;
        this.member_mealRate = member_mealRate;
        this.member_deposit = member_deposit;
        this.member_cost = member_cost;
        this.member_due = member_due;
        this.member_balance = member_balance;
        this.admin_id = admin_id;
        this.is_manager = is_manager;
        this.month = month;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_userName() {
        return member_userName;
    }

    public void setMember_userName(String member_userName) {
        this.member_userName = member_userName;
    }

    public String getMember_address() {
        return member_address;
    }

    public void setMember_address(String member_address) {
        this.member_address = member_address;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_mess_userName() {
        return member_mess_userName;
    }

    public void setMember_mess_userName(String member_mess_userName) {
        this.member_mess_userName = member_mess_userName;
    }

    public String getMember_mess_id() {
        return member_mess_id;
    }

    public void setMember_mess_id(String member_mess_id) {
        this.member_mess_id = member_mess_id;
    }

    public String getJoined_date() {
        return joined_date;
    }

    public void setJoined_date(String joined_date) {
        this.joined_date = joined_date;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getMember_nid() {
        return member_nid;
    }

    public void setMember_nid(String member_nid) {
        this.member_nid = member_nid;
    }

    public String getMember_meal() {
        return member_meal;
    }

    public void setMember_meal(String member_meal) {
        this.member_meal = member_meal;
    }

    public String getMember_mealRate() {
        return member_mealRate;
    }

    public void setMember_mealRate(String member_mealRate) {
        this.member_mealRate = member_mealRate;
    }

    public String getMember_deposit() {
        return member_deposit;
    }

    public void setMember_deposit(String member_deposit) {
        this.member_deposit = member_deposit;
    }

    public String getMember_cost() {
        return member_cost;
    }

    public void setMember_cost(String member_cost) {
        this.member_cost = member_cost;
    }

    public String getMember_due() {
        return member_due;
    }

    public void setMember_due(String member_due) {
        this.member_due = member_due;
    }

    public String getMember_balance() {
        return member_balance;
    }

    public void setMember_balance(String member_balance) {
        this.member_balance = member_balance;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(String is_manager) {
        this.is_manager = is_manager;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}



