package com.example.project0011;

public class HospitalModel {

    String userid;
    String name;
    String phone;
    String email;
    String AccountType;
    String HospitalName;
    String HospitalCity;
    String HospitalArea;


    public HospitalModel() {
    }

    public HospitalModel(String userid, String name, String phone, String email, String accountType, String hospitalName, String hospitalCity, String hospitalArea) {
        this.userid = userid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        AccountType = accountType;
        HospitalName = hospitalName;
        HospitalCity = hospitalCity;
        HospitalArea = hospitalArea;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public String getHospitalCity() {
        return HospitalCity;
    }

    public void setHospitalCity(String hospitalCity) {
        HospitalCity = hospitalCity;
    }

    public String getHospitalArea() {
        return HospitalArea;
    }

    public void setHospitalArea(String hospitalArea) {
        HospitalArea = hospitalArea;
    }
}
