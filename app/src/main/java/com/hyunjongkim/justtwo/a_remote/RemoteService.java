package com.hyunjongkim.justtwo.a_remote;

import com.hyunjongkim.justtwo.a_item.req.ReqGetRoomDetailInfoItem;
import com.hyunjongkim.justtwo.a_item.res.ResGetRoomInfoDetailItem;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_item.res.ResResultCodeAndMsg;

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
    String BASE_URL = awsUrl;

    /*
    *
    * */
    //USER
    // Select Info of User
    @POST("/users/getUserInfo")
    Call<UserInfoItem> selectUserInfo(@Body UserInfoItem userInfoItem);

    // Register User
    @POST("/users/regUserInfo")
    Call<UserInfoItem> insertUserInfo(@Body UserInfoItem userInfoItem);

    /*
    *  ROOM
    */
    // List for main
    @GET("/rooms/getRoomListInfo")
    Call<RoomInfoItem> listMain( @Query("user_id") int userId,
                                 @Query("status") int roomStatus,
                                 @Query("current_page") int currentPage );

    // Insert info of bang
    @POST("/rooms/regRoomInfo")
    Call<RoomInfoItem> regiRoomInfo( @Body RoomInfoItem infoItem );

    //Room info detail
    @POST("/rooms/getRoomDetailInfo")
    Call<ResGetRoomInfoDetailItem> getRoomInfoDetail( @Body ReqGetRoomDetailInfoItem reqGetRoomDetailInfoItem );

    //APPLY
    // Request joining to the room
    @POST("/applys/regApplyInfo")
    Call<ResResultCodeAndMsg> insertApplingRoomInfo( @Body ReqGetRoomDetailInfoItem reqGetRoomDetailInfoItem );

}