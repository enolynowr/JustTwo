package com.hyunjongkim.justtwo.bang;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_lib.StringLib;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LAYOUT FOR INFO OF ROOM
 */
public class InfoBang extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();
    public static final String INFO_SEQ = "INFO_SEQ";
    DrawerLayout drawer;
    Context context;
    RoomInfoItem item;
    String userEmail;
    int bangInfoSeq;

    // VIEW
    View loadingText;
    View headerLayout;
    ScrollView scrollView;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bang_info);

        context = this;
        drawer = findViewById(R.id.drawer_layout);
//        loadingText = findViewById(R.id.a_loading_layout);

        userEmail = ((MyApp) getApplication()).getUserEmail();
        bangInfoSeq = getIntent().getIntExtra(INFO_SEQ, 1);
        //selectBangInfo(bangInfoSeq, userEmail);

        setToolbar();
        //setView();
    }

    /**
     * 오른쪽 상단 메뉴를 구성한다.
     * 닫기 메뉴만이 설정되어 있는 menu_close.xml를 지정한다.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_request, menu);
        return true;
    }

    /**
     * 왼쪽 화살표 메뉴(android.R.id.home)를 클릭했을 때와
     * 오른쪽 상단 닫기 메뉴를 클릭했을 때의 동작을 지정한다.
     * 여기서는 모든 버튼이 액티비티를 종료한다.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_req:
                showDialog();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //
    @Override
    public void onClick(View v) {
        /* if (v.getId() == R.id.location) {
            movePosition(new LatLng(item.latitude, item.longitude),
                    Constant.MAP_ZOOM_LEVEL_DETAIL);
        }*/

    }

    /**
     * 화면이 일시정지 상태로 될 때 호출되며 현재 아이템의 변경 사항을 저장한다.
     * 이는 BestFoodListFragment나 BestFoodKeepFragment에서 변경된 즐겨찾기 상태를 반영하는
     * 용로도 사용된다.
     */
    @Override
    protected void onPause() {
        super.onPause();
        ((MyApp) getApplication()).setRoomInfoItem(item);
    }

///////////////// BELOW FUNCTION

    // Setting Tool bar
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("部屋申込");

        }
        // need to layout inflate
        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        headerLayout = navigationView.getHeaderView(0);*/
    }

    //서버에서 조회한 맛집 정보를 화면에 설정한다.
    private void setView() {
        //getSupportActionBar().setTitle(item.category);

        TextView categoryText = findViewById(R.id.bang_info_category);
        TextView hostDate = findViewById(R.id.bang_info_date);
        TextView hostTime = findViewById(R.id.bang_info_time);
        TextView hostPlace = findViewById(R.id.bang_info_place);
        TextView description = findViewById(R.id.bang_info_contents);
        TextView appliedCnt = findViewById(R.id.show_applied_cnt);

        categoryText.setText(item.category);

/*
        if (!StringLib.getInstance().isBlank(item.hostDate)) {
            hostDate.setText(item.hostDate);
        } else {
            hostDate.setVisibility(View.GONE);
        }

        if (!StringLib.getInstance().isBlank(item.hostTime)) {
            hostTime.setText(item.hostTime);
        } else {
            hostTime.setVisibility(View.GONE);
        }
*/

        if (!StringLib.getInstance().isBlank(item.location)) {
            hostPlace.setText(item.location);
        } else {
            hostPlace.setVisibility(View.GONE);
        }

        if (!StringLib.getInstance().isBlank(item.description)) {
            description.setText(item.description);
        } else {
            description.setText(R.string.no_text);
        }
    }


    // SERVER
    // Check the info of room :: @param memberSeq 사용자 시퀀스
    private void selectBangInfo(int bangInfoSeq, String _userEmail) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<RoomInfoItem> call = remoteService.selectBangInfo(bangInfoSeq, _userEmail);

        call.enqueue(new Callback<RoomInfoItem>() {
            @Override
            public void onResponse(Call<RoomInfoItem> call, Response<RoomInfoItem> response) {
                RoomInfoItem infoItem = response.body();

                if (response.isSuccessful() && infoItem != null && infoItem.roomInx > 0) {
                    item = infoItem;
                    //setView();
                    loadingText.setVisibility(View.GONE);
                } else {
                    // loadingText.setVisibility(View.VISIBLE);
                   // ((TextView) findViewById(R.id.loading_text)).setText(R.string.loading_not);
                }
            }

            @Override
            public void onFailure(Call<RoomInfoItem> call, Throwable t) {
                MyLog.d(TAG, "no internet connectivity");
                MyLog.d(TAG, t.toString());
            }
        });
    }

    private void checkValid(TextView... _textView) {

        for (TextView txtV : _textView) {
            if (!StringLib.getInstance().isBlank(item.location)) {
                txtV.setText(item.location);
            } else {
                txtV.setVisibility(View.GONE);
            }


        }
    }

    private void showDialog() {
        final EditText edittext = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("AlertDialog Content");
        builder.setView(edittext);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), edittext.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            GoLib.getInstance().goHome(this);
        } else if (id == R.id.nav_manage_room) {
            GoLib.getInstance().goManagementActivity(this);
        } else if (id == R.id.nav_manage) {
            GoLib.getInstance().goManagementActivity(this);
        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


}
