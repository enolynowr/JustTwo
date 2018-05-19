package com.hyunjongkim.justtwo.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_lib.StringLib;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;
import com.hyunjongkim.justtwo.user.social_login.LineLogin;
import com.hyunjongkim.justtwo.user.social_login.TwitterLogin;
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

    //SOCIAL LOGIN
    private LineLogin lineModule;
    private TwitterLogin twitterModule;

    // AUTO LOGIN
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    boolean loginChecked;
    EditText edtEmail, edtPw;
    Button btnLogin;
    ImageButton btnLineLogin;
    ImageButton btnTwitterLogin;
    Context context;
    String valEdtEmail, valEdtPw;
    CheckBox autoLogin;

    String dmEmail = "khj@gmail.com";
    String dmPw = "1234";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.login);
        setToolbar();
        setView();
        setAutoLogin();
        lineLogin();
        twitterLogin();
    }

    // ON CLICK
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:
                valEdtPw = edtPw.getText().toString();
                valEdtEmail = edtEmail.getText().toString();
                selectUserInfo(valEdtEmail, valEdtPw);
                break;

            case R.id.auto_login:
                break;

            case R.id.btn_lg_line:
                lineModule.onLogin();
                break;

            case R.id.btn_lg_tw:
                twitterModule.onLogin();
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
        twitterModule.onActivityResult(requestCode, resultCode, data);
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
        edtEmail = findViewById(R.id.edt_lg_email);
        edtPw = findViewById(R.id.edt_lg_password);
        autoLogin = findViewById(R.id.auto_login);
        // social btn
        btnLogin = findViewById(R.id.btn_login);
        btnLineLogin = findViewById(R.id.btn_lg_line);
        btnTwitterLogin = findViewById(R.id.btn_lg_tw);
        btnTwitterLogin.setOnClickListener(this);
        btnLineLogin.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private boolean checkLoginInput() {

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(edtEmail.getText().toString()).matches() || edtEmail.getText().toString() == "") {
            edtEmail.requestFocus();

            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$", edtPw.getText().toString())) {
            edtPw.requestFocus();

            return false;
        }

        return true;
    }

    private void checkLoginInfo() { //todo
    }

    public void selectUserInfo(String _email, String _pw) {

       /* RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<UserInfoItem> call = remoteService.selectUserInfo(email, pw);
        call.enqueue(new Callback<UserInfoItem>() {
            @Override
            public void onResponse(Call<UserInfoItem> call, Response<UserInfoItem> response) {
                UserInfoItem item = response.body();

                if (response.isSuccessful() && !StringLib.getInstance().isBlank(item.email)) {
                    MyLog.d(TAG, "success " + response.body().toString());

                    if (edtEmail.getText().toString().equals(item.email) && edtPw.getText().toString().equals(item.pw)) {
                        GoLib.getInstance().goMainActivity(context);
                    }

                    setUserInfoItem(item);

                } else {
                    MyLog.d(TAG, "not success");

                }
            }

            @Override
            public void onFailure(Call<UserInfoItem> call, Throwable t) {
                MyLog.d(TAG, "no internet connectivity");
                MyLog.d(TAG, t.toString());
            }
        });*/


        if (_email.equals(dmEmail) && _pw.equals(dmPw)) {
            GoLib.getInstance().goMainActivity(context);
        }
    }

    private void setUserInfoItem(UserInfoItem userInfoItem) {
        ((MyApp) getApplicationContext()).setUserInfoItem(userInfoItem);
    }

    private void setAutoLogin() {
        //AUTO LOGIN
        sharedPreferences = getSharedPreferences("setting", 0);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("Auto_Login_enabled", false)) {
            edtEmail.setText(sharedPreferences.getString("ID", ""));
            edtPw.setText(sharedPreferences.getString("PW", ""));
            autoLogin.setChecked(true);

            GoLib.getInstance().goMainActivity(this);
        }

        autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String ID = edtEmail.getText().toString();
                    String PW = edtPw.getText().toString();
                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("Auto_Login_enabled", true);
                    editor.commit();

                } else {
                    editor.clear();
                    editor.commit();
                }
            }
        });
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
        lineModule = new LineLogin(this, new OnResponseListener() {

            @Override
            public void onResult(SocialType socialType, ResultType resultType, Map<UserInfoType, String> map) {

            }
        });
    }

    private void twitterLogin() {
        twitterModule = new TwitterLogin(this, new OnResponseListener() {

            @Override
            public void onResult(SocialType socialType, ResultType resultType, Map<UserInfoType, String> map) {

            }
        });
    }



}
