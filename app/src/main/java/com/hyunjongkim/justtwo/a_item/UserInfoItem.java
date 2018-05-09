package com.hyunjongkim.justtwo.a_item;


import com.google.gson.annotations.SerializedName;

public class UserInfoItem {

    public int seq;
    @SerializedName("uid") public String uId;
    public String email;
    public String pw;
    public String sex;
    public String age;
    @SerializedName("reg_date") public String regDate;
    public int status;

    @Override
    public String toString() {
        return "UserInfoItem{" +
                "seq=" + seq +
                ", uId='" + uId + '\'' +
                ", email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", regDate='" + regDate + '\'' +
                ", status=" + status +
                '}';
    }
}
