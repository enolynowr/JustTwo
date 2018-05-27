package com.hyunjongkim.justtwo.bang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// MAKE ROOM ACTIVITY
public class RegisterBangBase extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    public static RoomInfoItem currentItem = null;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bang_register_base);

        context = this;
        String userEmail = ((MyApp) getApplication()).getUserEmail();

        //BestFoodRegisterLocationFragment로 넘길 기본적인 정보를 저장한다.
        RoomInfoItem infoItem = new RoomInfoItem();

        MyLog.d(TAG, "infoItem " + infoItem.toString());

        setToolbar();

        //RegisterBangInput를 화면에 보여준다.
        GoLib.getInstance().goFragment(getSupportFragmentManager(),
                R.id.content_main, RegisterBangInput.newInstance(infoItem));
    }

    // RIGHT UP MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close, menu);
        return true;
    }

    // PROCESSING CLICKING OF RIGHT UP MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_close:
                finish();
                break;
        }

        return true;
    }

    /**
     * 다른 액티비티를 실행한 결과를 처리하는 메소드
     * (실제로는 프래그먼트로 onActivityResult 호출을 전달하기 위한 목적으로 작성)     *
     * @param requestCode 액티비티를 실행하면서 전달한 요청 코드
     * @param resultCode  실행한 액티비티가 설정한 결과 코드
     * @param data        결과 데이터
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    // SETTING TOOL BAR
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.register_bang);
        }
    }



}