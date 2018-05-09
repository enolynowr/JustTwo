package com.hyunjongkim.justtwo.a_remote;

import com.hyunjongkim.justtwo.a_item.BangInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 서버에 호출할 메소드를 선언하는 인터페이스
 */
public interface RemoteService {
    //Aws
    //String BASE_URL = "http://54.92.71.204:3000";
    //Test 10.4.78.27 192.168.11.4   192.168.43.160
    String BASE_URL = "http://192.168.11.4:3000";


// GET
    // 방 정보
    @GET("/bang/info/{info_seq}")
    Call<BangInfoItem> selectBangInfo(@Path("info_seq") int bangInfoSeq,
                                      @Query("user_seq") int userSeq);
    @GET("/manage/list/bang")
    Call<ArrayList<BangInfoItem>> listManageInfoRoom(@Query("order_type") String orderType,
                                                     @Query("current_page") int currentPage);

    @GET("/bang/list/main")
    Call<ArrayList<BangInfoItem>> listMain(@Query("order_type") String orderType,
                                           @Query("current_page") int currentPage);



// POST
    // Register User
    @POST("/users/info")
    Call<String> insertUserInfo(@Body UserInfoItem userInfoItem);


    @POST("/bang/info")
    Call<String> insertBangInfo(@Body BangInfoItem infoItem);



}