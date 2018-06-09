package com.hyunjongkim.justtwo.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_item.res.ResUserInfo;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_lib.MyToast;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;
import com.hyunjongkim.justtwo.databinding.LoginBinding;
import com.hyunjongkim.justtwo.user.social_login.LineLogin;

import com.hyunjongkim.justtwo.user.social_login.impl.OnResponseListener;
import com.hyunjongkim.justtwo.user.social_login.impl.ResultType;
import com.hyunjongkim.justtwo.user.social_login.impl.SocialType;
import com.hyunjongkim.justtwo.user.social_login.impl.UserInfoType;

import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();

    private LoginBinding loginBinding;
    private EditText edtEmail;
    private EditText edtPw ;
    private BootstrapButton btnLogin;
    private ImageButton btnLineLogin;
    private CheckBox autoLogin;

    //SOCIAL LOGIN
    private LineLogin lineModule;

    // AUTO LOGIN
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private Context context;
    private String valEdtEmail;
    private String valEdtPw;
    boolean loginChecked;
    private UserInfoItem userInfoItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        loginBinding = DataBindingUtil.setContentView(this, R.layout.login);
        setToolbar();
        setView();
        setAutoLogin();
        lineLogin();
        //twitterLogin();
    }

    // ON CLICK
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                valEdtEmail = edtEmail.getText().toString();
                valEdtPw = edtPw.getText().toString();

                if (sharedPreferences.getBoolean("Auto_Login_enabled", false)) {
                    String EMAIL = edtEmail.getText().toString();
                    String PW = edtPw.getText().toString();
                    editor.putString("USER_EMAIL", EMAIL);
                    editor.putString("USER_PW", PW);
                    //editor.putInt("USER_ID", )

                    editor.commit();
                }

                checkLoginInput();
                callProcessUserInfo();
                setAutoLogin();
                break;
            case R.id.auto_login:
                break;
            case R.id.btn_lg_line:
                lineModule.onLogin();
                break;
        }
    }

    /**
     * 右上のメニューを設定（転送）
     *
     * @return SHOW MENU true, DON'T SHOW MENU false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manu_sign_up, menu);
        return true;
    }

    /**
     * CLICKING ARROW MENU ON LEFT(android.R.id.home) AND SIGN UP MENU ON RIGHT
     *
     * @return PROCESS true, DON'T false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_up:
                GoLib.getInstance().goSignUpActivity(context);
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lineModule.onActivityResult(requestCode, resultCode, data);
    }

////////////////////////////// FUNCTION

    // SETTING TOOL BAR
    private void setToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(R.string.nav_drawer_login);
        }
    }

    // SETTING VIEW
    private void setView() {
        edtEmail = loginBinding.edtLgEmail;
        edtPw = loginBinding.edtLgPassword;
        btnLogin = loginBinding.btnLogin;
        btnLineLogin = loginBinding.btnLgLine;
        autoLogin = loginBinding.autoLogin;
        btnLineLogin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private boolean checkLoginInput() {

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(edtEmail.getText().toString()).matches()) {

            if (edtEmail.getText().toString().equals("")) {
                MyToast.s(context, "Email入力してください。");
                edtEmail.requestFocus();
                return false;
            }

            MyToast.s(context, "Emailの形式が正しくありません。");
            edtEmail.requestFocus();
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$", edtPw.getText().toString())) {
            edtPw.requestFocus();
            return false;
        }

        return true;
    }


    private void callProcessUserInfo() {
        UserInfoItem sendToDbUserItem = getUserInfoItem();
        final RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<UserInfoItem> call = remoteService.selectUserInfo(sendToDbUserItem);
        call.enqueue(new Callback<UserInfoItem>() {
            @Override
            public void onResponse(Call<UserInfoItem> call, Response<UserInfoItem> response) {
                userInfoItem = response.body();
                ResUserInfo resUserInfo1 = userInfoItem.getResResults();

                if (response.isSuccessful() && userInfoItem.getResCd().equals("0000")) {
                    MyLog.d(TAG, "success " + response.body().toString());

                    int userId = resUserInfo1.getUserId();
                    editor.putInt("USER_ID", userId);
                    editor.commit();
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

    private void setAutoLogin() {
        sharedPreferences = getSharedPreferences("setting", 0);
        editor = sharedPreferences.edit();

        autoLogin.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    editor.putBoolean("Auto_Login_enabled", isChecked);
                    editor.commit();
                }
        );
    }

    private boolean loginValidation(String id, String password) {
        if (sharedPreferences.getString("ID", "").equals(id) && sharedPreferences.getString("PW", "").equals(password)) {
            // login success
            return true;
        } else if (sharedPreferences.getString("ID", "").equals(null)) {
            // sign in first
            Toast.makeText(Login.this, "Please Sign in first", Toast.LENGTH_LONG).show();
            return false;
        } else {
            // login failed
            return false;
        }
    }

    private void lineLogin() {
        lineModule = new LineLogin(this, (socialType, resultType, map) -> {

        });
    }

       //
    private UserInfoItem getUserInfoItem() {
        UserInfoItem userInfoItem = new UserInfoItem();
        userInfoItem.email = edtEmail.getText().toString();
        userInfoItem.pw = edtPw.getText().toString();
        return userInfoItem;
    }


}
