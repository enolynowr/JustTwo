package com.hyunjongkim.justtwo.a_item;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class ResGetRoomDetailInfoItemResult {
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getApply_count() {
        return apply_count;
    }

    public void setApply_count(int apply_count) {
        this.apply_count = apply_count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isApply() {
        return isApply;
    }

    public void setApply(boolean apply) {
        isApply = apply;
    }

    @SerializedName("room_id")
    public int roomId;

    @SerializedName("apply_count")
    public int apply_count;

    @SerializedName("status")
    public int status;

    @SerializedName("user_id")
    public int userId;

    @SerializedName("age")
    public int age;

    @SerializedName("category")
    public String category;

    @SerializedName("location")
    public String location;

    @SerializedName("date_time")
    public String dateTime;

    @SerializedName("description")
    public String description;

    @SerializedName("reg_date")
    public String reg_date;

    @SerializedName("gender")
    public String gender;

    @SerializedName("is_apply")
    public boolean isApply;
}
