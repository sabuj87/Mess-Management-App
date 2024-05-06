package com.sabuj.messmanagement.model;

public class Mess {
    private String id;
    private String user_name;
    private String password;
    private String address;
    private String created_by;
    private String created_at;
    private String created_date;
    private String month;
    private String totalDeposit;
    private String totalMealCost;
    private String totalMeal;
    private String mealRate;
    private String totalMember;
    private String manager;
    private String updated_at;
    private String balance;
    private String cost_Count;

    public Mess() {
    }

    public Mess(String id, String user_name, String password, String address, String created_by, String created_at, String created_date, String month, String totalDeposit, String totalMealCost, String totalMeal, String mealRate, String totalMember, String manager, String updated_at, String balance, String cost_Count) {
        this.id = id;
        this.user_name = user_name;
        this.password = password;
        this.address = address;
        this.created_by = created_by;
        this.created_at = created_at;
        this.created_date = created_date;
        this.month = month;
        this.totalDeposit = totalDeposit;
        this.totalMealCost = totalMealCost;
        this.totalMeal = totalMeal;
        this.mealRate = mealRate;
        this.totalMember = totalMember;
        this.manager = manager;
        this.updated_at = updated_at;
        this.balance = balance;
        this.cost_Count = cost_Count;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(String totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public String getTotalMealCost() {
        return totalMealCost;
    }

    public void setTotalMealCost(String totalMealCost) {
        this.totalMealCost = totalMealCost;
    }

    public String getTotalMeal() {
        return totalMeal;
    }

    public void setTotalMeal(String totalMeal) {
        this.totalMeal = totalMeal;
    }

    public String getMealRate() {
        return mealRate;
    }

    public void setMealRate(String mealRate) {
        this.mealRate = mealRate;
    }

    public String getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(String totalMember) {
        this.totalMember = totalMember;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCost_Count() {
        return cost_Count;
    }

    public void setCost_Count(String cost_Count) {
        this.cost_Count = cost_Count;
    }
}
