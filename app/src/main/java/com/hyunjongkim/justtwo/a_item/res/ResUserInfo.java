package com.hyunjongkim.justtwo.a_item.res;


import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class ResUserInfo {

    @SerializedName("user_id")
        int userId;
    @SerializedName("email")
        String email;
    @SerializedName("gender")
        String gender;
    @SerializedName("age")
        String age;
    @SerializedName("status")
        String status;

        public int getUserId() {
            return userId;
        }

        public String getEmail() {
            return email;
        }

        public String getGender() {
            return gender;
        }

        public String getAge() {
            return age;
        }

        public String getStatus() {
            return status;
        }


}
