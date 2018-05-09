package com.hyunjongkim.justtwo.user;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_lib.StringLib;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();

    EditText edtEmail;
    EditText edtPw;
    Button btnLogin;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.login);
        setToolbar();
        setView();
    }

    @Override
    public void onClick(View v) {

    }

    // 액티비티 툴바를 설정한다.
    private void setToolbar() {
        final Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.nav_drawer_login);
        }
    }

    private void setView() {

        edtEmail = findViewById(R.id.edt_lg_email);
        edtPw = findViewById(R.id.edt_lg_password);
        btnLogin = findViewById(R.id.btn_login);

        checkLoginInput();

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
                finish();
                break;

            case R.id.action_submit:

        }

        return true;
    }

    private boolean checkLoginInput(){

        if (!android.util.Patterns.EMAIL_ADDRESS
                .matcher(edtEmail.getText().toString()).matches() || edtEmail.getText().toString() == "") {

            edtEmail.requestFocus();

            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$", edtPw.getText().toString())) {

            edtPw.requestFocus();

            return false;
        }


    }

}
