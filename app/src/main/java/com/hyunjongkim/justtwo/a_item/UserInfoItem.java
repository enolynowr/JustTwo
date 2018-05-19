package com.hyunjongkim.justtwo.a_item;


import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class UserInfoItem {

    @SerializedName("user_id")
    public int userId;
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

    @Override
    public String toString() {
        return "UserInfoItem{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", userStatus='" + userStatus + '\'' +
                ", device_no='" + deviceNo + '\'' +
                ", reg_date='" + regDate + '\'' +
                '}';
    }
}
