package com.hyunjongkim.justtwo.user.social_login.impl;

import java.util.Map;

public interface OnResponseListener {
    void onResult(SocialType social, ResultType result, Map<UserInfoType, String> resultMap);
}