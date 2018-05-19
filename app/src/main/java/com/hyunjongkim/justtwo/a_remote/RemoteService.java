package com.hyunjongkim.justtwo.a_remote;

import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Interface for Calling Server
public interface RemoteService {
    String awsUrl = "http://54.92.71.204:3000";
    String homeUrl = "http://192.168.11.4:3000";
    String starbarUrl = "http://10.4.164.210:3000";
    String BASE_URL = awsUrl;

/// USER

    // Select Info of User
    @POST("/users/{email}/{pw}")
    Call<UserInfoItem> selectUserInfo(@Path("email") String email, @Path("pw") String pw);

    // POST
    // Register User
    @POST("/users/regUserInfo")
    Call<String> insertUserInfo(@Body UserInfoItem userInfoItem);


/// ROOM

    // Info of bang
    @GET("/bang/info/{info_seq}")
    Call<RoomInfoItem> selectBangInfo(@Path("info_seq") int bangInfoSeq,
                                      @Query("user_seq") String _userEmail);
    // List for manage
    @GET("/manage/list/bang")
    Call<ArrayList<RoomInfoItem>> listManageInfoRoom(@Query("order_type") String orderType,
                                                     @Query("current_page") int currentPage);
    // List for main
    @GET("/bang/list/main")
    Call<ArrayList<RoomInfoItem>> listMain(@Query("order_type") String orderType,
                                           @Query("current_page") int currentPage);

    // Insert info of bang
    @POST("/bang/info")
    Call<String> insertBangInfo(@Body RoomInfoItem infoItem);
}