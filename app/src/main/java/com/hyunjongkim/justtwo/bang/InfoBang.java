package com.hyunjongkim.justtwo.bang;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_custom.WorkaroundMapFragment;
import com.hyunjongkim.justtwo.a_item.BangInfoItem;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_lib.StringLib;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 맛집 정보를 보는 액티비티이다.
 */
public class InfoBang extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();
    public static final String INFO_SEQ = "INFO_SEQ";

    Context context;
    BangInfoItem item;
    GoogleMap map;
    View loadingText;
    ScrollView scrollView;

    int userSeq;
    int bangInfoSeq;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bang_info);

        context = this;

        loadingText = findViewById(R.id.a_loading_layout);

        userSeq = ((MyApp) getApplication()).getUserSeq();
        bangInfoSeq = getIntent().getIntExtra(INFO_SEQ, 1);
        selectBangInfo(bangInfoSeq, userSeq);

        setToolbar();
    }

    // Setting Tool bar
    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
    }

    /**
     * 오른쪽 상단 메뉴를 구성한다.
     * 닫기 메뉴만이 설정되어 있는 menu_close.xml를 지정한다.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_close, menu);
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
            case R.id.action_close:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Check the info of room :: @param memberSeq 사용자 시퀀스
    private void selectBangInfo(int bangInfoSeq, int userSeq) {
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<BangInfoItem> call = remoteService.selectBangInfo(bangInfoSeq, userSeq);

        call.enqueue(new Callback<BangInfoItem>() {
            @Override
            public void onResponse(Call<BangInfoItem> call, Response<BangInfoItem> response) {
                BangInfoItem infoItem = response.body();

                if (response.isSuccessful() && infoItem != null && infoItem.seq > 0) {
                    item = infoItem;
                    setView();
                    loadingText.setVisibility(View.GONE);
                } else {
                    loadingText.setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.loading_text)).setText(R.string.loading_not);
                }
            }

            @Override
            public void onFailure(Call<BangInfoItem> call, Throwable t) {
                MyLog.d(TAG, "no internet connectivity");
                MyLog.d(TAG, t.toString());
            }
        });
    }

    //서버에서 조회한 맛집 정보를 화면에 설정한다.
    private void setView() {
        getSupportActionBar().setTitle(item.category);

        TextView categoryText = findViewById(R.id.bang_info_category);
        if (!StringLib.getInstance().isBlank(item.category)) {
            categoryText.setText(item.category);
        }

        TextView hostDate = findViewById(R.id.bang_info_date);
        if (!StringLib.getInstance().isBlank(item.hostDate)) {
            hostDate.setText(item.hostDate);
        } else {
            hostDate.setVisibility(View.GONE);
        }

        TextView hostTime = findViewById(R.id.bang_info_time);
        if (!StringLib.getInstance().isBlank(item.hostTime)) {
            hostTime.setText(item.hostTime);
        } else {
            hostTime.setVisibility(View.GONE);
        }

        TextView hostPlace = findViewById(R.id.bang_info_place);
        if (!StringLib.getInstance().isBlank(item.hostPlace)) {
            hostPlace.setText(item.hostPlace);
        } else {
            hostPlace.setVisibility(View.GONE);
        }

        TextView description = findViewById(R.id.bang_info_contents);
        if (!StringLib.getInstance().isBlank(item.bangContents)) {
            description.setText(item.bangContents);
        } else {
            description.setText(R.string.no_text);
        }
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
        ((MyApp) getApplication()).setBangInfoItem(item);
    }
}
