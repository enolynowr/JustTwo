package com.hyunjongkim.justtwo.a_item.res;

import com.google.gson.annotations.SerializedName;

@org.parceler.Parcel
public class ResResultCodeAndMsg {


    @SerializedName("resCd")
    String resResultCode;
    @SerializedName("resMsg")
    String getResResultMsg;

    public String getResResultCode() {
        return resResultCode;
    }

    public void setResResultCode(String resResultCode) {
        this.resResultCode = resResultCode;
    }

    public String getGetResResultMsg() {
        return getResResultMsg;
    }

    public void setGetResResultMsg(String getResResultMsg) {
        this.getResResultMsg = getResResultMsg;
    }
}
