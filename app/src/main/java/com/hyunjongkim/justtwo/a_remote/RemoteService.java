package com.hyunjongkim.justtwo.a_remote;

import com.hyunjongkim.justtwo.a_item.ReqGetRoomDetailInfoItem;
import com.hyunjongkim.justtwo.a_item.ResRegApplyInfo;
import com.hyunjongkim.justtwo.a_item.ResGetRoomInfoDetailItem;
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

    //USER
    // Select Info of User
    @POST("/users/getUserInfo")
    Call<UserInfoItem> selectUserInfo(@Body UserInfoItem userInfoItem);

    // Register User
    @POST("/users/regUserInfo")
    Call<UserInfoItem> insertUserInfo(@Body UserInfoItem userInfoItem);

    // ROOM
    // Info of bang
    @GET("/bang/info/{info_seq}")
    Call<RoomInfoItem> selectBangInfo(@Path("info_seq") int bangInfoSeq);

    // List for manage
    @GET("/manage/list/bang")
    Call<ArrayList<RoomInfoItem>> listManageInfoRoom(@Query("order_type") String orderType,
                                                     @Query("current_page") int currentPage);

    // List for main
    @GET("/rooms/getRoomListInfo")
    Call<RoomInfoItem> listMain( @Query("user_id") int userId,
                                 @Query("status") int roomStatus,
                                 @Query("current_page") int currentPage);

    // Insert info of bang
    @POST("/rooms/regRoomInfo")
    Call<RoomInfoItem> regiRoomInfo(@Body RoomInfoItem infoItem);

    //Room info detail
    @POST("/rooms/getRoomDetailInfo")
    Call<ResGetRoomInfoDetailItem> getRoomInfoDetail(@Body ReqGetRoomDetailInfoItem reqGetRoomDetailInfoItem);

    //APPLY
    // Request joining to the room
    @POST("/applys/regApplyInfo")
    Call<ResRegApplyInfo> insertApplingRoomInfo(@Body ReqGetRoomDetailInfoItem reqGetRoomDetailInfoItem );

}