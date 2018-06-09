package com.hyunjongkim.justtwo.a_item;

import com.google.gson.annotations.SerializedName;
import com.hyunjongkim.justtwo.a_item.res.ResRoomInfo;

import java.util.List;

@org.parceler.Parcel
public class RoomInfoItem {

    @SerializedName("resCd")
    String resCd;

    @SerializedName("room_id")
    public int roomInx;

    @SerializedName("user_id")
    public int userId;
    @SerializedName("email")
    String email;

    public String category;
    public String location;

    @SerializedName("date_time")
    public String dateTime;

    @SerializedName("description")
    public String description;

    @SerializedName("apply_count")
    public int applyCnt;

    @SerializedName("status")
    public int roomStatus;

    @SerializedName("reg_date")
    public String regDate;

    @SerializedName("result")
    public List<ResRoomInfo> resRoomInfos;

    public List<ResRoomInfo> getResRoomInfos() {
        return resRoomInfos;
    }

    public void setResRoomInfos(List<ResRoomInfo> resRoomInfos) {
        this.resRoomInfos = resRoomInfos;
    }

    public String getResCd() {
        return resCd;
    }

    public void setResCd(String resCd) {
        this.resCd = resCd;
    }

    public int getRoomInx() {
        return roomInx;
    }

    public void setRoomInx(int roomInx) {
        this.roomInx = roomInx;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getApplyCnt() {
        return applyCnt;
    }

    public void setApplyCnt(int applyCnt) {
        this.applyCnt = applyCnt;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}

