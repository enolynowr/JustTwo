package com.hyunjongkim.justtwo.a_item.res;

import com.google.gson.annotations.SerializedName;

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
