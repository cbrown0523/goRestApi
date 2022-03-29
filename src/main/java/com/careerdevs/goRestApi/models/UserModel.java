package com.careerdevs.goRestApi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserModel {
    private int id;
    private String name;
    private String status;
    private String email;
    private String gender;

    public UserModel(){

    }

    public UserModel(String name, String email, String gender, String status) {
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.status = status;
    }
    @JsonProperty("email")

    public String getEmail() {
        return email;
    }
    @JsonProperty("gender")

    public String getGender() {
        return gender;
    }

    @JsonProperty("id")

    public int getId() {
        return id;
    }
    @JsonProperty("name")

    public String getName() {
        return name;
    }
    @JsonProperty("status")

    public String getStatus() {
        return status;
    }
    @Override
    public String toString(){
        return "UserModel {  id " + id + ", name" + name + ", status" + status;
    }
}
