package com.hyunjongkim.justtwo.a_item;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class ReqGetRoomDetailInfoItem {

    @SerializedName("room_id")
    public int roomId;
    @SerializedName("user_id")
    public int userId;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
