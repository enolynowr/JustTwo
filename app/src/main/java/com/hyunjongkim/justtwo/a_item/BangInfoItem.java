package com.hyunjongkim.justtwo.a_item;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class BangInfoItem {

    public int seq;
    @SerializedName("uid") public String uId; // From userSeq to uId
    //@SerializedName("user_email")public String userEmail; // del from
    public String category;
    @SerializedName("host_date")public String hostDate;
    @SerializedName("host_time")public String hostTime;
    @SerializedName("host_place")public String hostPlace;
    @SerializedName("contents")public String bangContents;
    @SerializedName("room_status")public int bangStatus;
    @SerializedName("create_date")public String createBang;

    @Override
    public String toString() {
        return "BangInfoItem{" +
                "seq=" + seq +
                ", uId='" + uId + '\'' +
                ", category='" + category + '\'' +
                ", hostDate='" + hostDate + '\'' +
                ", hostTime='" + hostTime + '\'' +
                ", hostPlace='" + hostPlace + '\'' +
                ", bangContents='" + bangContents + '\'' +
                ", bangStatus=" + bangStatus +
                ", createBang='" + createBang + '\'' +
                '}';
    }
}

