package com.hyunjongkim.justtwo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.hyunjongkim.justtwo.a_adapter.MainListAdapter;

import com.hyunjongkim.justtwo.a_custom.EndlessRecyclerViewScrollListener;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;

import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;

import org.parceler.Parcels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainList extends Fragment implements View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();
    public static final String USER_INFO_ITEM = "USER_INFO_ITEM";

    // LIST
    MainListAdapter infoListAdapter;
    EndlessRecyclerViewScrollListener scrollListener;
    RecyclerView mainList;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    int listTypeValue = 2;
    String orderType;

    //SPINNER
    Spinner spnOrder;
    Spinner spnFilterGen;
    ArrayAdapter<String> spnAdpOd;
    ArrayAdapter<String> spnAdpGen;
    String[] spnOd;
    String[] arrFilterGen;
    Spinner spinner;

    //
    Context context;
    TextView noDataText;
    String userEmail;


    public static MainList newInstance(UserInfoItem userInfoItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER_INFO_ITEM, Parcels.wrap(userInfoItem));

        MainList mainList = new MainList();
        mainList.setArguments(bundle);

        return mainList;
    }

    public static MainList newInstance() {
        MainList mainList = new MainList();
        return mainList;
    }

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = this.getActivity();
        userEmail = ((MyApp) this.getActivity().getApplication()).getUserEmail();

        View layout = inflater.inflate(R.layout.main_list, container, false);

        return layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // LIST
        mainList = view.findViewById(R.id.rv_main_list);
        noDataText = view.findViewById(R.id.no_data);

        setRecyclerView();
        setSpinner();

        //
        orderType = Constant.ORDER_TYPE_METER;
        listInfo(orderType, 0);

    }

    //
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
    public void onClick(View v) {

    }

////////////// BELOW IS FUNCTION

    private void setLayoutManager(int row) {
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(row, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mainList.setLayoutManager(staggeredGridLayoutManager);
    }

    private void setRecyclerView() {

        setLayoutManager(listTypeValue);

        infoListAdapter = new MainListAdapter(context, R.layout.row_main_list, new ArrayList<RoomInfoItem>());
        mainList.setAdapter(infoListAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                listInfo(orderType, page);
            }
        };

        mainList.addOnScrollListener(scrollListener);

    }



    // SPINNER
    private void setSpinner() {
       /* final String[] arrSelect = {
                "Select Qualification", "10th / Below", "12th", "Diploma", "UG","PG", "Phd"};
        ArrayList<SpnFilterItem> filterList = new ArrayList<>();
        for (int i = 0; i < arrSelect.length; i++) {
            SpnFilterItem stateVO = new SpnFilterItem();
            stateVO.setTitle(arrSelect[i]);
            stateVO.setSelected(false);
            filterList.add(stateVO);
        }
        SpnFilterAdapter myAdapter = new SpnFilterAdapter(getActivity(), 0,
                filterList);
        spinner.setAdapter(myAdapter);*/

        // ORDER
        spnOrder = getActivity().findViewById(R.id.spn_main_list_od);
        spnOd = getResources().getStringArray(R.array.main_list_order);
        spnAdpOd = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, spnOd);
        spnOrder.setAdapter(spnAdpOd);

        //GENDER
        spnFilterGen = getActivity().findViewById(R.id.spn_main_list_filter_sex);
        arrFilterGen = getResources().getStringArray(R.array.filter_gender);
        spnAdpGen = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, arrFilterGen);
        spnFilterGen.setAdapter(spnAdpGen);
    }

    // SERVER
    private void listInfo(String orderType, final int currentPage) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        /* 0513
        Call<ArrayList<RoomInfoItem>> call = remoteService.listMain(orderType, currentPage);

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
        });
        */
        // DUMMY DATA
        ArrayList<RoomInfoItem> list = new ArrayList<>();

        RoomInfoItem roomInfoItem = new RoomInfoItem();
        roomInfoItem.category = "Lang";
        roomInfoItem.location = "Tokyo";
        roomInfoItem.dateTime = "2017/05/17";
        roomInfoItem.desc = "i m hot";
        list.add(roomInfoItem);


        MyLog.d(TAG, list.toString());
        infoListAdapter.addItemList(list);

        if (infoListAdapter.getItemCount() == 0) {
            noDataText.setVisibility(View.VISIBLE);
        } else {
            noDataText.setVisibility(View.GONE);
        }
    }

}
