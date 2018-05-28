package com.hyunjongkim.justtwo.a_item;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class ManagementInfoItem {


    @SerializedName("apply_inx")
    public int applyInx;
    @SerializedName("room_inx")
    public int roomInx;
    @SerializedName("apply_user_id")
    public int appliedRoomUserId;
    public String message;
    public int status;
    @SerializedName("reg_date")
    public String regDate;

    @Override
    public String toString() {
        return "ManagementInfoItem{" +
                "applyInx=" + applyInx +
                ", roomInx=" + roomInx +
                ", appliedRoomUserId='" + appliedRoomUserId + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", regDate='" + regDate + '\'' +
                '}';
    }
}
