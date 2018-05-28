package com.hyunjongkim.justtwo.a_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_item.ResRoomInfo;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;

import java.util.ArrayList;
import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    //private ArrayList<RoomInfoItem> itemList;
    private ArrayList<ResRoomInfo> itemList;
    private UserInfoItem userInfoItem;
    private int resource;

    //Constructor
/*
    public MainListAdapter(Context context, int resource, ArrayList<RoomInfoItem> itemList) {
        this.context = context;
        this.resource = resource;
        this.itemList = itemList;

        userInfoItem = ((MyApp) context.getApplicationContext()).getUserInfoItem();
    }
*/

    public MainListAdapter(Context context, int resource, ArrayList<ResRoomInfo> itemList) {
        this.context = context;
        this.resource = resource;
        this.itemList = itemList;

        userInfoItem = ((MyApp) context.getApplicationContext()).getUserInfoItem();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int bangStatusNum = 0;
        //final RoomInfoItem item = itemList.get(position);
        final ResRoomInfo item = itemList.get(position);
        MyLog.d(TAG, "getView" + item);
        holder.rowCategory.setText(item.getCategory());
        holder.rowDate.setText(item.getDateTime());
        holder.rowPlace.setText(item.getLocation());
        holder.rowDesc.setText(item.getDescription());

        holder.btnRowRoomStatus.setText(" vacant");

        /*switch (item.roomStatus) {
            case 0:
                holder.btnRowRoomStatus.setText("空部屋");
                break;
            case 1:
                holder.btnRowRoomStatus.setText("満席");
                holder.btnRowRoomStatus.setEnabled(false);
                break;
        }*/

        holder.btnRowRoomStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoLib.getInstance().goInfoBang(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    //
    public void setItem(ResRoomInfo newItem) {
        for (int i = 0; i < itemList.size(); i++) {
            ResRoomInfo item = itemList.get(i);

            if (item.getRoomId() == newItem.getRoomId()) {
                itemList.set(i, newItem);

                notifyItemRemoved(i);

                break;
            }
        }
    }

    //
/*    public void addItemList(ArrayList<RoomInfoItem> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }*/

    public void addItemList(List<ResRoomInfo> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    //
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rowCategory;
        TextView rowDate;
        TextView rowPlace;
        TextView rowDesc;
        Button btnRowRoomStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            rowCategory = itemView.findViewById(R.id.tv_row_main_category);
            rowDate = itemView.findViewById(R.id.tv_row_main_date);
            rowPlace = itemView.findViewById(R.id.tv_row_main_place);
            rowDesc = itemView.findViewById(R.id.tv_row_main_desc);
            btnRowRoomStatus = itemView.findViewById(R.id.btn_row_main_status);
        }
    }
}
