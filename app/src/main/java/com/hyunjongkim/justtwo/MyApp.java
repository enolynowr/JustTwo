package com.hyunjongkim.justtwo;

import android.app.Application;
import android.os.StrictMode;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.hyunjongkim.justtwo.a_item.ManagementInfoItem;
import com.hyunjongkim.justtwo.a_item.ResUserInfo;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.user.social_login.LineConfig;
import com.hyunjongkim.justtwo.user.social_login.SocialLogin;
import com.hyunjongkim.justtwo.user.social_login.TwitterConfig;
import com.hyunjongkim.justtwo.user.social_login.impl.SocialType;


/**
 * 앱 전역에서 사용할 수 있는 클래스
 */
public class MyApp extends Application {
    // User info
    private UserInfoItem userInfoItem;
    private RoomInfoItem roomInfoItem;
    private ResUserInfo resUserInfo;



    private ManagementInfoItem managementInfoItem;

    private String sharedId;
    private String sharedPw;

    @Override
    public void onCreate() {
        super.onCreate();
        SocialLogin.init(getApplicationContext());
        // FileUriExposedException 문제를 해결하기 위한 코드
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        TypefaceProvider.registerDefaultIconSets();
        socialLoginInit();
    }


    public ResUserInfo getResUserInfo() {
        return resUserInfo;
    }

    public void setResUserInfo(ResUserInfo resUserInfo) {
        this.resUserInfo = resUserInfo;
    }

    public UserInfoItem getUserInfoItem() {

        if (userInfoItem == null) {
            userInfoItem = new UserInfoItem();
        }

        return userInfoItem;
    }

    public void setUserInfoItem(UserInfoItem item) {

        this.userInfoItem = item;
    }

    public String getUserEmail() {

        return userInfoItem.email;
    }

    public void setRoomInfoItem(RoomInfoItem roomInfoItem) {
        this.roomInfoItem = roomInfoItem;
    }

    public RoomInfoItem getRoomInfoItem() {
        return roomInfoItem;
    }

    public void socialLoginInit() {
        // LINE
        LineConfig lineConfig = new LineConfig.Builder()
                .setChannelId("1581878477")
                .build();
        SocialLogin.addType(SocialType.LINE, lineConfig);
        // TWITTER
        TwitterConfig twitterConfig = new TwitterConfig.Builder()
                .setConsumerKey("a8UIpD2q3h3HikhRQnohsrbSd")
                .setConsumerSecret("oZvl24EFkLR4fz2aurGQomnwsPTd6455nbKxIOJGMkMHOGwxQ9")
                .build();
        SocialLogin.addType(SocialType.TWITTER, twitterConfig);
    }
}
