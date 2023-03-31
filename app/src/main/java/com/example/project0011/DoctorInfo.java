package com.example.project0011;

public class DoctorInfo {
 String iD,docName,docTime,docSpec,docDeg,docPhoto,timeStamp,uid;

    public DoctorInfo() {
    }


    public DoctorInfo(String iD, String docName, String docTime, String docSpec, String docDeg, String docPhoto, String timeStamp, String uid) {
        this.iD = iD;
        this.docName = docName;
        this.docTime = docTime;
        this.docSpec = docSpec;
        this.docDeg = docDeg;
        this.docPhoto = docPhoto;
        this.timeStamp = timeStamp;
        this.uid = uid;
    }

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocTime() {
        return docTime;
    }

    public void setDocTime(String docTime) {
        this.docTime = docTime;
    }

    public String getDocSpec() {
        return docSpec;
    }

    public void setDocSpec(String docSpec) {
        this.docSpec = docSpec;
    }

    public String getDocDeg() {
        return docDeg;
    }

    public void setDocDeg(String docDeg) {
        this.docDeg = docDeg;
    }

    public String getDocPhoto() {
        return docPhoto;
    }

    public void setDocPhoto(String docPhoto) {
        this.docPhoto = docPhoto;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }


    public String getUId() {
        return uid;
    }

    public void setUId(String uid) {
        this.uid = uid;
    }
}
