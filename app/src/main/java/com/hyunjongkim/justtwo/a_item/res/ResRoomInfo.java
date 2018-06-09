package com.hyunjongkim.justtwo.a_item.res;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class ResRoomInfo {

    @SerializedName("room_id")
    int roomId;
    @SerializedName("date_time")
    String dateTime;
    @SerializedName("apply_count")
    int applyCount;
    @SerializedName("reg_date")
    String regDate;

    String category;
    String location;
    String description;
    int status;


    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
