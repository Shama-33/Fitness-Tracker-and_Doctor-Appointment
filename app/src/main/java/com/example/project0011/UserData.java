package com.example.project0011;

public class UserData {
    String userid;
    String name;
    String phone;
    String email;
    String AccountType;
    String HospitalName;
    String HospitalCity;
    String HospitalArea;

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

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String accountType) {
        AccountType = accountType;
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
}
