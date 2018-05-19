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

    TextInputLayout tiEmail;
    TextInputLayout tiPw;

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
     * 右上のメニューを設定（転送）
     *
     * @param menu 메뉴 객체
     * @return 메뉴를 보여준다면 true, 보여주지 않는다면 false
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    /**
     * 왼쪽 화살표 메뉴(android.R.id.home)를 클릭했을 때와
     * 오른쪽 상단 닫기 메뉴를 클릭했을 때의 동작을 지정한다.
     * 여기서는 모든 버튼이 액티비티를 종료한다.
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
        int[] arrAgeInt = getResources().getIntArray(R.array.age_int_array);
        int[] arrDB = new int[1];
        final String[] ageTypes = new String[arrAge.length];

        for (int i = 0; i < arrAge.length; i++) {
            ageTypes[i] = arrAge[i];
        }
        // ageTypes[0] = getResources().getStringArray(R.array.age_array);

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

        newUserInfoItem.email = emailEdt.getText().toString();
        newUserInfoItem.pw = pwEdt.getText().toString();

        switch (selectedUserSex) {
            case 0:
                newUserInfoItem.gender = "E";
                break;
            case 1:
                newUserInfoItem.gender = "M";
                break;
            case 2:
                newUserInfoItem.gender = "W";
                break;
            default:
                newUserInfoItem.gender = "E";
                break;
        }

        switch (selectedUserAge) {
            case 0:
                newUserInfoItem.age = 0;
                break;
            case 1:
                newUserInfoItem.age = 10;
                break;
            case 2:
                newUserInfoItem.age = 20;
                break;
            case 3:
                newUserInfoItem.age = 30;
                break;
            case 4:
                newUserInfoItem.age = 40;
                break;
            case 5:
                newUserInfoItem.age = 50;
                break;
            case 6:
                newUserInfoItem.age = 60;
                break;
            case 7:
                newUserInfoItem.age = 70;
                break;

            default:
                newUserInfoItem.age = 0;
                break;
        }

        newUserInfoItem.userStatus = 0;

        return newUserInfoItem;
    }

    // Check Input Value
    private boolean checkEditTextInput(EditText _email, EditText _pw, EditText _sex, EditText _age) {

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.getText().toString()).matches() || _email.getText().toString() == "") {
            _email.requestFocus();
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$", _pw.getText().toString())) {
            _pw.requestFocus();
            Toast.makeText(SignUpActivity.this, "パスワードを確認してください。", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_sex.getText().toString() == "") {
            Toast.makeText(SignUpActivity.this, "性別を選択してください。",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (_age.getText().toString() == "") {
            Toast.makeText(SignUpActivity.this, "歳を選択してください。",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // DIALOG
    private void showDialog() {

        final EditText edittext = new EditText(this);

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.dialog_title));
        builder.setView(edittext);

        // POSITIVE
        builder.setPositiveButton(getResources().getString(R.string.dialog_yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), edittext.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        //NEGATIVE
        builder.setNegativeButton(getResources().getString(R.string.dialog_no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


    // ユーザが入力した情報を保存する。
    private void save() {

        final UserInfoItem newItem = getUserInfoItem();
        MyLog.d(TAG, "insertItem " + newItem.toString());

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<String> call = remoteService.insertUserInfo(newItem);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String email = response.body();

                try {
                    currentItem.email = email;

                    if (currentItem.email.equals("")) {
                        MyToast.s(context, "currentItem.roomInx == 0");
                        return;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                currentItem.email = newItem.email;
                currentItem.pw = newItem.pw;
                currentItem.gender = newItem.gender;
                currentItem.age = newItem.age;

                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.sign_up_insert_fail_msg),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}