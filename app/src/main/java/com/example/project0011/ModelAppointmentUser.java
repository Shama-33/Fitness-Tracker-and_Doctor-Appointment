package com.example.project0011;

public class ModelAppointmentUser {


    String docname,docdeg,docspec,doctime,UId,date,appstatus,RequestedBy;
    String HospitalName;
    String HospitalCity;
    String HospitalArea;
    String AppointmentID;
    //AppointmentID

    public String getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        AppointmentID = appointmentID;
    }

    public ModelAppointmentUser() {
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }

    public String getDocdeg() {
        return docdeg;
    }

    public void setDocdeg(String docdeg) {
        this.docdeg = docdeg;
    }

    public String getDocspec() {
        return docspec;
    }

    public void setDocspec(String docspec) {
        this.docspec = docspec;
    }

    public String getDoctime() {
        return doctime;
    }

    public void setDoctime(String doctime) {
        this.doctime = doctime;
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAppstatus() {
        return appstatus;
    }

    public void setAppstatus(String appstatus) {
        this.appstatus = appstatus;
    }

    public String getRequestedBy() {
        return RequestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        RequestedBy = requestedBy;
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
