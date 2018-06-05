package com.hyunjongkim.justtwo.a_item;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@org.parceler.Parcel
public class ResGetRoomInfoDetailItem {

    @SerializedName("resCd")
    String resCd;

    public String getResCd() {
        return resCd;
    }

    public void setResCd(String resCd) {
        this.resCd = resCd;
    }

    @SerializedName("resMsg")
    String resMsg;

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    @SerializedName("result")
    ResGetRoomDetailInfoItemResult resGetRoomDetailInfoItemResult;

    public ResGetRoomDetailInfoItemResult getResGetRoomDetailInfoItemResult() {
        return resGetRoomDetailInfoItemResult;
    }

    public void setResGetRoomDetailInfoItemResult(ResGetRoomDetailInfoItemResult resGetRoomDetailInfoItemResult) {
        this.resGetRoomDetailInfoItemResult = resGetRoomDetailInfoItemResult;
    }
}
