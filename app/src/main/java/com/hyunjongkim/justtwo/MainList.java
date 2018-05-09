package com.hyunjongkim.justtwo;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.hyunjongkim.justtwo.a_adapter.MainListAdapter;
import com.hyunjongkim.justtwo.a_custom.EndlessRecyclerViewScrollListener;
import com.hyunjongkim.justtwo.a_item.BangInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainList extends Fragment implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();

    MainListAdapter infoListAdapter;
    EndlessRecyclerViewScrollListener scrollListener;
    Context context;
    RecyclerView mainList;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    Button btnRoomStatus;


    Spinner spnOrder;
    ArrayAdapter<String> spnAdapter;
    String[] spnOrderData;


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


    public static MainList newInstance() {
        // Required empty public constructor
        MainList mr = new MainList();

        return mr;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = this.getActivity();
// Spinner
        spnOrderData = getResources().getStringArray(R.array.main_list_order);
        spnAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, spnOrderData);
        userSeq = ((MyApp)this.getActivity().getApplication()).getUserSeq();

        View layout = inflater.inflate(R.layout.main_bang_list, container, false);

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

        spnOrder = getActivity().findViewById(R.id.spn_main_list);
        spnOrder.setAdapter(spnAdapter);

        orderType = Constant.ORDER_TYPE_METER;
        mainList = view.findViewById(R.id.rv_main_list);
        noDataText = view.findViewById(R.id.no_data);
        setRecyclerView();

        listInfo( orderType, 0);

    }

    private void setLayoutManager(int row) {
        //linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(row, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mainList.setLayoutManager(staggeredGridLayoutManager);
    }

    private void setRecyclerView() {

        setLayoutManager(listTypeValue);

        infoListAdapter = new MainListAdapter(context,
                R.layout.row_main_list,
                new ArrayList<BangInfoItem>());

        mainList.setAdapter(infoListAdapter);

        //GoLib.getInstance().goFragment(getFragmentManager(), );



        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                listInfo(orderType, page);
            }
        };

        mainList.addOnScrollListener(scrollListener);

    }

    /*
     *
     *
     * */

    private void listInfo(String orderType, final int currentPage) {

        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);

        Call<ArrayList<BangInfoItem>> call = remoteService.listMain( orderType, currentPage);

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

    }
}
