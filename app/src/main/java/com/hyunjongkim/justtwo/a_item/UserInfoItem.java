package com.hyunjongkim.justtwo.a_item;


import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class UserInfoItem {


    @SerializedName("user_id")
    public int userId;
    @SerializedName("resCd")
    String resCd;
    public String email;
    public String pw;
    public String gender;
    public int age;
    @SerializedName("status")
    public int userStatus;
    @SerializedName("device_no")
    public String deviceNo;
    @SerializedName("reg_date")
    public String regDate;
    @SerializedName("result")
    ResUserInfo resResults;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResCd() {
        return resCd;
    }

    public void setResCd(String resCd) {
        this.resCd = resCd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public ResUserInfo getResResults() {
        return resResults;
    }

    public void setResResults(ResUserInfo resResults) {
        this.resResults = resResults;
    }

    @Override
    public String toString() {
        return "UserInfoItem{" +
                "userId=" + userId +
                ", resCd='" + resCd + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", userStatus=" + userStatus +
                ", deviceNo='" + deviceNo + '\'' +
                ", regDate='" + regDate + '\'' +
                '}';
    }
}
