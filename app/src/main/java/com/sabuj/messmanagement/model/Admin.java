package com.sabuj.messmanagement.model;

public class Admin {
    private String id;
    private String name;
    private String email;
    private String created_at;
    private String password;
    private String messCreated;
    private String messUserNme;

    public Admin() {
    }

    public Admin(String id, String name, String email, String created_at, String password, String messCreated, String messUserNme) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.password = password;
        this.messCreated = messCreated;
        this.messUserNme = messUserNme;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessCreated() {
        return messCreated;
    }

    public void setMessCreated(String messCreated) {
        this.messCreated = messCreated;
    }

    public String getMessUserNme() {
        return messUserNme;
    }

    public void setMessUserNme(String messUserNme) {
        this.messUserNme = messUserNme;
    }
}
