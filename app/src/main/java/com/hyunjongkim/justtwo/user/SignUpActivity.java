package com.hyunjongkim.justtwo.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_lib.MyToast;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;


import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 프로필을 설정할 수 있는 액티비티
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();

    Context context;
    UserInfoItem currentItem;

    int selectedUserSex;
    int selectedUserAge;

    EditText emailEdt;
    EditText pwEdt;
    EditText sexEdt;
    EditText ageEdt;

    // 액티비티를 생성하고 화면을 구성한다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        context = this;

        currentItem = ((MyApp) getApplication()).getUserInfoItem();

        setToolbar();
        setView();
    }


    /**
     * SETTING MENU
     *
     * @param menu INSTANCE OF MENU
     * @return SHOWING MENU true, NOT false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    /**
     * SETTING PROCESSING FOR CLICKING SHAPE ARROW (android.R.id.home) AND SUMIT BUTTON OF MENU
     *
     * @param item 메뉴 아이템 객체
     * @return 메뉴를 처리했다면 true, 그렇지 않다면 false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                GoLib.getInstance().goLoginActivity(context);
                break;
            case R.id.action_submit:
                //showDialog();
                boolean result = checkEditTextInput(emailEdt, pwEdt, sexEdt, ageEdt);

                if (result) {
                    save();
                } else {
                    emailEdt.requestFocus();
                }

                break;
        }

        return true;
    }

    // 後ろボータンを押したとき、
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onClick(View v) {
    }

/////////////////////// FUNCTION

    // 액티비티 툴바를 설정한다.
    private void setToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.nav_drawer_signup);
        }
    }

    // 액티비티 화면을 설정한다.
    private void setView() {
        emailEdt = findViewById(R.id.edt_email);
        pwEdt = findViewById(R.id.edt_password);

        sexEdt = findViewById(R.id.edt_sex);
        sexEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSexTypeDialog();
            }
        });

        ageEdt = findViewById(R.id.edt_age);
        ageEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAgeTypeDialog();
            }
        });
    }

    // 性別 Dialog
    private void setSexTypeDialog() {
        final String[] sexTypes = new String[3];
        sexTypes[0] = getResources().getString(R.string.sex_none);
        sexTypes[1] = getResources().getString(R.string.sex_man);
        sexTypes[2] = getResources().getString(R.string.sex_woman);

        new AlertDialog.Builder(this)
                .setItems(sexTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which >= 0) {
                            selectedUserSex = which;
                            sexEdt.setText(sexTypes[which]);
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    // 歳 Dialog
    private void setAgeTypeDialog() {
        String[] arrAge = getResources().getStringArray(R.array.age_array);
        final String[] ageTypes = new String[arrAge.length];

        for (int i = 0; i < arrAge.length; i++) {
            ageTypes[i] = arrAge[i];
        }

        new AlertDialog.Builder(this)
                .setItems(ageTypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which >= 0) {
                            selectedUserAge = which;
                            ageEdt.setText(ageTypes[which]);
                        }
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * 사용자가 입력한 정보를 MemberInfoItem 객체에 저장해서 반환한다.
     *
     * @return 사용자 정보 객체
     */
    private UserInfoItem getUserInfoItem() {
        UserInfoItem newUserInfoItem = new UserInfoItem();
        newUserInfoItem.setEmail(emailEdt.getText().toString());
        newUserInfoItem.setPw(pwEdt.getText().toString());

        switch (selectedUserSex) {
            case 0:
                newUserInfoItem.setGender("E");
                break;
            case 1:
                newUserInfoItem.setGender("M");
                break;
            case 2:
                newUserInfoItem.setGender("W");
                break;
            default:
                newUserInfoItem.setGender("E");
                break;
        }

        switch (selectedUserAge) {
            case 0:
                newUserInfoItem.setAge(0);
                break;
            case 1:
                newUserInfoItem.setAge(10);
                break;
            case 2:
                newUserInfoItem.setAge(20);
                break;
            case 3:
                newUserInfoItem.setAge(30);
                break;
            case 4:
                newUserInfoItem.setAge(40);
                break;
            case 5:
                newUserInfoItem.setAge(50);
                break;
            case 6:
                newUserInfoItem.setAge(60);
                break;
            case 7:
                newUserInfoItem.setAge(70);
                break;
            default:
                newUserInfoItem.setAge(0);
                break;
        }

        newUserInfoItem.setUserStatus(0);

        return newUserInfoItem;
    }

    // Check Input Value
    private boolean checkEditTextInput(EditText _email, EditText _pw, EditText _sex, EditText _age) {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.getText().toString()).matches()) {

            if ( _email.getText().toString().equals("")){
                MyToast.l(SignUpActivity.this, "emailを入力してください。");
                _email.requestFocus();
                return false;
            }

            MyToast.l(SignUpActivity.this, "emailの形式正しくありません。");
            _email.requestFocus();
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$", _pw.getText().toString())) {
            _pw.requestFocus();
            MyToast.l(SignUpActivity.this, "パスワードを確認してください。");
            return false;
        }

        if (_sex.getText().toString().equals("")) {
            MyToast.l(SignUpActivity.this, "性別を選択してください。");
            return false;
        }

        if (_age.getText().toString().equals("")) {
            MyToast.l(SignUpActivity.this, "歳を選択してください。");
            return false;
        }

        return true;
    }

    // ユーザが入力した情報を保存する。
    private void save() {
        UserInfoItem userInfoItem = getUserInfoItem();
        MyLog.d(TAG, "insertItem " + userInfoItem.toString());
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<UserInfoItem> call = remoteService.insertUserInfo(userInfoItem);
        call.enqueue(new Callback<UserInfoItem>() {
            @Override
            public void onResponse(Call<UserInfoItem> call, Response<UserInfoItem> response) {
                UserInfoItem userInfoItem = response.body();

                try {
                    if (userInfoItem.getResCd().equals("0000")) {
                        GoLib.getInstance().goLoginActivity(context);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                finish();
            }

            @Override
            public void onFailure(Call<UserInfoItem> call, Throwable t) {
                t.getStackTrace().toString();
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.sign_up_insert_fail_msg),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}