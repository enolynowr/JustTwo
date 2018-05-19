package com.hyunjongkim.justtwo.user.social_login;

import android.app.Activity;
import android.content.Intent;

import com.hyunjongkim.justtwo.user.social_login.impl.OnResponseListener;
import com.hyunjongkim.justtwo.user.social_login.impl.ResultType;
import com.hyunjongkim.justtwo.user.social_login.impl.SocialType;
import com.hyunjongkim.justtwo.user.social_login.impl.UserInfoType;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.HashMap;
import java.util.Map;


/**
 * SocialLogin
 * Class: TwitterLogin
 * Created by Pyxis on 2017-10-27.
 * <p>
 * Description:
 */

public class TwitterLogin extends SocialLogin {

    private TwitterAuthClient twitterAuthClient = new TwitterAuthClient();

    public TwitterLogin(Activity activity, OnResponseListener onResponseListener) {
        super(activity, onResponseListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLogin() {
        twitterAuthClient.authorize(activity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                Map<UserInfoType, String> userInfoMap = new HashMap<>();

                String id = String.valueOf(result.data.getUserId());
                String name = result.data.getUserName();

                userInfoMap.put(UserInfoType.ID, id);
                userInfoMap.put(UserInfoType.NAME, name);

                responseListener.onResult(SocialType.TWITTER, ResultType.SUCCESS, userInfoMap);
            }

            @Override
            public void failure(TwitterException exception) {
                responseListener.onResult(SocialType.TWITTER, ResultType.FAILURE, null);
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
