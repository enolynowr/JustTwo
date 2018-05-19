package com.hyunjongkim.justtwo.a_item;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class RoomInfoItem {

    @SerializedName("room_id")
    public int roomInx;
    @SerializedName("user_id")
    public String userId;// userEmailから変更
    public String category;
    public String location;
    @SerializedName("datetime")
    public String dateTime;
    public String desc;
    @SerializedName("apply_count")
    public int applyCnt;
    @SerializedName("status")
    public int appliedRoomStatus;
    @SerializedName("reg_date")
    public String regDate;

    @Override
    public String toString() {
        return "RoomInfoItem{" +
                "roomInx=" + roomInx +
                ", userId='" + userId + '\'' +
                ", category='" + category + '\'' +
                ", location='" + location + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", desc='" + desc + '\'' +
                ", applyCnt=" + applyCnt +
                ", appliedRoomStatus=" + appliedRoomStatus +
                ", regDate='" + regDate + '\'' +
                '}';
    }
}

