package com.hyunjongkim.justtwo.item;

public class BangInfoItem {

    public int seq;
    public int userSeq;
    public String category;
    public String hostDate;
    public String hostPlace;
    public String bangContents;
    public double latitude;
    public double longitude;
    public String createBang;

    @Override
    public String toString() {
        return "BangInfoItem{" +
                "seq=" + seq +
                ", userSeq=" + userSeq +
                ", category='" + category + '\'' +
                ", hostDate='" + hostDate + '\'' +
                ", hostPlace='" + hostPlace + '\'' +
                ", bangContents='" + bangContents + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", createBang='" + createBang + '\'' +
                '}';
    }
}
