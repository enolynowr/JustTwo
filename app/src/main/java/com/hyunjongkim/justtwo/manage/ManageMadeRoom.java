package com.hyunjongkim.justtwo.manage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyunjongkim.justtwo.Constant;
import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_adapter.ManageAppliedRoomListAdapter;
import com.hyunjongkim.justtwo.a_custom.EndlessRecyclerViewScrollListener;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageMadeRoom extends Fragment implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();

    ManageAppliedRoomListAdapter infoListAdapter;
    EndlessRecyclerViewScrollListener scrollListener;

    RecyclerView rcManageAppliedRoom;
    LinearLayoutManager linearLayoutManager;
    Context context;

    TextView noDataText;
    String userEmail;
    String orderType;

    public static ManageMadeRoom newInstance() {
        ManageMadeRoom mr = new ManageMadeRoom();
        return mr;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = this.getActivity();
        userEmail = ((MyApp) this.getActivity().getApplication()).getUserEmail();

        View layout = inflater.inflate(R.layout.manage_room, container, false);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyApp myApp = ((MyApp) getActivity().getApplication());
        RoomInfoItem currentRoomInfoItem = myApp.getRoomInfoItem();

        if (infoListAdapter != null && currentRoomInfoItem != null) {
            infoListAdapter.setItem(currentRoomInfoItem);
            myApp.setRoomInfoItem(null);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.management);

        orderType = Constant.ORDER_TYPE_METER;
        rcManageAppliedRoom = view.findViewById(R.id.rc_manage_room);
        noDataText = view.findViewById(R.id.no_data);

        setRecyclerView();

        listInfo(orderType, 0);
    }

    @Override
    public void onClick(View v) {
        setRecyclerView();
        listInfo(orderType, 0);
    }

///////////////////// FUNCTION

    private void setLayoutManager() {
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rcManageAppliedRoom.setLayoutManager(linearLayoutManager);
    }

    private void setRecyclerView() {

        setLayoutManager();

        infoListAdapter = new ManageAppliedRoomListAdapter(context, R.layout.row_manage_room, new ArrayList<RoomInfoItem>());
        rcManageAppliedRoom.setAdapter(infoListAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                listInfo(orderType, page);
            }
        };

        rcManageAppliedRoom.addOnScrollListener(scrollListener);
    }

    //
    private void listInfo(String orderType, final int currentPage) {

        /*RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<ArrayList<RoomInfoItem>> call = remoteService.listManageInfoRoom(orderType, currentPage);

        call.enqueue(new Callback<ArrayList<RoomInfoItem>>() {
            @Override
            public void onResponse(Call<ArrayList<RoomInfoItem>> call, Response<ArrayList<RoomInfoItem>> response) {

                ArrayList<RoomInfoItem> list = response.body();
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
            public void onFailure(Call<ArrayList<RoomInfoItem>> call, Throwable t) {
                MyLog.d(TAG, "No internet!!");
            }
        });*/
        ArrayList<RoomInfoItem> listRoomInfoItem = new ArrayList<>();
        RoomInfoItem roomInfoItem = new RoomInfoItem();

        roomInfoItem.category = "Lang";
        roomInfoItem.dateTime = "2018-05-15";

        listRoomInfoItem.add(roomInfoItem);
        infoListAdapter.addItemList(listRoomInfoItem);

    }
}
