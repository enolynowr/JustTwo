package com.hyunjongkim.justtwo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.hyunjongkim.justtwo.a_item.ResRoomInfo;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;

import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

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

    List<ResRoomInfo> resRoomInfos;

    int userId;
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


    /* public static MainList newInstance(UserInfoItem userInfoItem) {
         Bundle bundle = new Bundle();
         bundle.putParcelable(USER_INFO_ITEM, Parcels.wrap(userInfoItem));

         MainList mainList = new MainList();
         mainList.setArguments(bundle);

         return mainList;
     }
 */
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

        //userId = ((MyApp) this.getActivity().getApplication()).getResUserInfo().getUserId();
        userId = 15;

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
        //todo listinfo에 userid 더함
        listInfo(userId, orderType, 0);

    }

    //
    @Override
    public void onResume() {
        super.onResume();

        MyApp myApp = ((MyApp) getActivity().getApplication());
        RoomInfoItem currentRoomInfoItem = myApp.getRoomInfoItem();

        if (infoListAdapter != null && currentRoomInfoItem != null) {
            //infoListAdapter.setItem(currentRoomInfoItem);
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

        infoListAdapter = new MainListAdapter(context, R.layout.row_main_list, new ArrayList<ResRoomInfo>());
        mainList.setAdapter(infoListAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                listInfo(userId, orderType, page);
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
    private void listInfo(int userId, String orderType, final int currentPage) {

        final RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);


        Call<RoomInfoItem> call = remoteService.listMain(userId, 1, currentPage);

        call.enqueue(new Callback<RoomInfoItem>() {
            @Override
            public void onResponse(Call<RoomInfoItem> call, Response<RoomInfoItem> response) {

                RoomInfoItem list = response.body();

                //List<ResRoomInfo> resRoomInfo1 = new ArrayList<>();

    /*            for (int i = 0; i < list.getResRoomInfos().size(); i++) {
                    resRoomInfo1.add((ResRoomInfo) list.getResRoomInfos());
                }*/


                resRoomInfos = new ArrayList<>();
                for (ResRoomInfo resRoomInfo1: list.getResRoomInfos()) {


                    resRoomInfos.add(resRoomInfo1);

                }


                //MyLog.d(TAG, list.toString());

              /*  for (int i=0; i < list.size(); i++){


                }
                for (RoomInfoItem roomInfo: list){

                    ResRoomInfo resRoomInfos = new ResRoomInfo();

                    resRoomInfos.setRoomId(list.get(0));

                }*/


                if (response.isSuccessful() && list != null) {
                    infoListAdapter.addItemList(resRoomInfos);

                    if (infoListAdapter.getItemCount() == 0) {
                        noDataText.setVisibility(View.VISIBLE);
                    } else {
                        noDataText.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RoomInfoItem> call, Throwable t) {
                MyLog.d(TAG, "No internet!!");

                t.getStackTrace().toString();
            }
        });
    }

   /* private RoomInfoItem getRoomItemForCallServer(){

        RoomInfoItem roomInfoItem = new RoomInfoItem();

        roomInfoItem.setUserId(userId);
        roomInfoItem.setRoomStatus(0);
        roomInfoItem.

        return roomInfoItem;
    }*/

}
