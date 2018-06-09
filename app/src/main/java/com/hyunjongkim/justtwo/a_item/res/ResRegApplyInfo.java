package com.hyunjongkim.justtwo.a_item.res;


import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class ResRegApplyInfo extends ResResultCodeAndMsg{
    @SerializedName("room_id")
    int roomId;
    @SerializedName("user_id")
    int userId;
    @SerializedName("message")
    String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
