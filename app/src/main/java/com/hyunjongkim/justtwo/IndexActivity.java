
package com.hyunjongkim.justtwo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hyunjongkim.justtwo.a_item.ResUserInfo;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_lib.RemoteLib;
import com.hyunjongkim.justtwo.a_lib.StringLib;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;
import com.hyunjongkim.justtwo.user.Login;
import com.hyunjongkim.justtwo.user.SignUpActivity;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// DECIDE RUNNING MAIN ACTIVITY OR LOGIN ACTIVITY AFTER CHECK USER INFO
public class IndexActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    Context context;
    UserInfoItem userInfoItem;
    UserInfoItem userInfoItemForshared;

    // IF ISN'T CONNECTING TO INTERNET THEN CALL showNoService()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        context = this;
        if (!RemoteLib.getInstance().isConnected(context)) {
            showNoService();
            return;
        }
    }

    /**
     * AFTER 1,2 SECONDS AND CALLING startTask()
     * CHECK USER INFO FROM SERVER
     */
    @Override
    protected void onStart() {
        super.onStart();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startTask();
            }
        }, 1200);
    }

    // SHOWING MSG, BUTTON OF FINISH DISPLAY  WHEN DON'T CONNECT TO THE INTERNET
    private void showNoService() {
        TextView messageText = findViewById(R.id.message);
        messageText.setVisibility(View.VISIBLE);
        Button closeButton = findViewById(R.id.close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        closeButton.setVisibility(View.VISIBLE);
    }

    public void startTask() {
        SharedPreferences shared = getSharedPreferences("setting", 0);

        // IF NOT AUTO LOGIN, GO TO LOGIN
        if (shared.getBoolean("Auto_Login_enabled", false) == false) {
            GoLib.getInstance().goLoginActivity(this);
            return;
        } else {
            callProcessUserInfo();
            userInfoItemForshared = new UserInfoItem();

            userInfoItemForshared.setEmail(shared.getString("USER_EMAIL", ""));
            userInfoItemForshared.setPw(shared.getString("USER_PW", ""));
            //userInfoItemForshared.setUserId(shared.getInt("USER_ID", 0));

            //
            ((MyApp) getApplication()).setUserInfoItem(userInfoItemForshared);
            GoLib.getInstance().goMainActivity(getApplicationContext());
            return;
        }
    }

    public void callProcessUserInfo() {


        final RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<UserInfoItem> call = remoteService.selectUserInfo(userInfoItemForshared);
        call.enqueue(new Callback<UserInfoItem>() {
            @Override
            public void onResponse(Call<UserInfoItem> call, Response<UserInfoItem> response) {
                userInfoItem = response.body();
                ResUserInfo resUserInfo1 = userInfoItem.getResResults();

                if (response.isSuccessful() && userInfoItem.getResCd().equals("0000")) {
                    MyLog.d(TAG, "success " + response.body().toString());
                    // Save resUserInfo
                    //setAutoLogin();
                    ((MyApp) getApplication()).setResUserInfo(resUserInfo1);
                    GoLib.getInstance().goMainActivity(context);
                } else {
                    MyLog.d(TAG, "not success");
                }
            }

            @Override
            public void onFailure(Call<UserInfoItem> call, Throwable t) {
                MyLog.d(TAG, "no internet connectivity");
                MyLog.d(TAG, t.toString());
            }
        });
    }

}

