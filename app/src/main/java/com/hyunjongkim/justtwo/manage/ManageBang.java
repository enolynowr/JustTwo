package com.hyunjongkim.justtwo.manage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyunjongkim.justtwo.Constant;

import com.hyunjongkim.justtwo.MyApp;

import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_adapter.ManagementInfoListAdapter;
import com.hyunjongkim.justtwo.a_custom.EndlessRecyclerViewScrollListener;
import com.hyunjongkim.justtwo.a_item.BangInfoItem;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageBang extends Fragment implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();

    ManagementInfoListAdapter infoListAdapter;
    EndlessRecyclerViewScrollListener scrollListener;
    Context context;
    RecyclerView managementRoomList;
    StaggeredGridLayoutManager layoutManager;
    LinearLayoutManager linearLayoutManager;
    Button btnRoomStatus;

    TextView mngRoomCategory;
    TextView mngRoomDate;
    TextView noDataText;
    int userSeq;
    int listTypeValue = 2;

    String bangCategory;
    String hostDate;
    String hostPlace;
    String hostContent;
    String orderType;
    String bangStatus;


    public static ManageBang newInstance() {
        // Required empty public constructor
        ManageBang mr = new ManageBang();

        return mr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = this.getActivity();

        userSeq = ((MyApp)this.getActivity().getApplication()).getUserSeq();

        View layout = inflater.inflate(R.layout.manage_room, container, false);

        return layout;
    }


    @Override
    public void onResume() {
        super.onResume();

        MyApp myApp = ((MyApp) getActivity().getApplication());
        BangInfoItem currentBangInfoItem = myApp.getBangInfoItem();

        if (infoListAdapter != null && currentBangInfoItem != null) {
            infoListAdapter.setItem(currentBangInfoItem);
            myApp.setBangInfoItem(null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.management);

        orderType = Constant.ORDER_TYPE_METER;
        managementRoomList = view.findViewById(R.id.rc_manage_room);
        noDataText = view.findViewById(R.id.no_data);
        setRecyclerView();

        listInfo( orderType, 0);

    }

    private void setLayoutManager() {
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        //layoutManager = new StaggeredGridLayoutManager(row, LinearLayoutManager.VERTICAL);
        //layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        managementRoomList.setLayoutManager(linearLayoutManager);
    }

    private void setRecyclerView() {

        setLayoutManager();

        infoListAdapter = new ManagementInfoListAdapter(context,
                R.layout.row_manage_room,
                new ArrayList<BangInfoItem>());

        managementRoomList.setAdapter(infoListAdapter);


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                listInfo(orderType, page);
            }
        };

        managementRoomList.addOnScrollListener(scrollListener);

    }

    /*
     *
     *
     * */

    private void listInfo(String orderType, final int currentPage) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ArrayList<BangInfoItem>> call = remoteService.listManageInfoRoom( orderType, currentPage);

        call.enqueue(new Callback<ArrayList<BangInfoItem>>() {
            @Override
            public void onResponse(Call<ArrayList<BangInfoItem>> call, Response<ArrayList<BangInfoItem>> response) {

                ArrayList<BangInfoItem> list = response.body();
                      MyLog.d(TAG, list.toString());

                if (response.isSuccessful() && list != null) {
                    infoListAdapter.addItemList(list);

                    if (infoListAdapter.getItemCount() == 0) {
                        noDataText.setVisibility(View.VISIBLE);
                    } else {
                        noDataText.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BangInfoItem>> call, Throwable t) {
                MyLog.d(TAG, "No internet!!");
            }
        });
    }


    @Override
    public void onClick(View v) {

        setRecyclerView();
        listInfo(orderType, 0);

    }
}
