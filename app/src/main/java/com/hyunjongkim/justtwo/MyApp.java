package com.hyunjongkim.justtwo;

import android.app.Application;
import android.os.StrictMode;

import com.hyunjongkim.justtwo.a_item.BangInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;


/**
 * 앱 전역에서 사용할 수 있는 클래스
 */
public class MyApp extends Application {
    private UserInfoItem userInfoItem;
    private BangInfoItem bangInfoItem;

    @Override
    public void onCreate() {
        super.onCreate();

        // FileUriExposedException 문제를 해결하기 위한 코드
        // 관련 설명은 책의 [참고] 페이지 참고
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public UserInfoItem getUserInfoItem() {
        if (userInfoItem == null) userInfoItem = new UserInfoItem();

        return userInfoItem;
    }

    public void setUserInfoItem(UserInfoItem item) {

        this.userInfoItem = item;
    }

    public int getUserSeq() {

        return userInfoItem.seq ;
    }

    public String getUserId(){
        return userInfoItem.uId;
    }

    public void setBangInfoItem(BangInfoItem bangInfoItem) {
        this.bangInfoItem = bangInfoItem;
    }

    public BangInfoItem getBangInfoItem() {
        return bangInfoItem;
    }
}
