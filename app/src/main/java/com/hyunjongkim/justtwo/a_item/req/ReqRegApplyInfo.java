package com.hyunjongkim.justtwo.a_item.req;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class ReqRegApplyInfo extends ReqGetRoomDetailInfoItem {

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int getUserId() {
        return super.getUserId();
    }

    @Override
    public int getRoomId() {
        return super.getRoomId();
    }

    @Override
    public void setRoomId(int roomId) {
        super.setRoomId(roomId);
    }

    @Override
    public void setUserId(int userId) {
        super.setUserId(userId);
    }
}
