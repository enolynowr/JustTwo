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
import com.hyunjongkim.justtwo.a_item.res.ResRoomInfo;
import com.hyunjongkim.justtwo.a_item.res.ResUserInfo;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;

import java.util.ArrayList;
import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;
    private ArrayList<ResRoomInfo> itemList;
    private ResUserInfo resUserInfo;
    private int resource;

    boolean roomStatus;
    ResRoomInfo resRoomitem;

    //Constructor
    public MainListAdapter(Context context, int resource, ArrayList<ResRoomInfo> itemList) {
        this.context = context;
        this.resource = resource;
        this.itemList = itemList;

        resUserInfo = ((MyApp) context.getApplicationContext()).getResUserInfo();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        resRoomitem = itemList.get(position);
        MyLog.d(TAG, "getView" + resRoomitem);
        holder.rowCategory.setText(resRoomitem.getCategory());
        holder.rowDate.setText(resRoomitem.getDateTime());
        holder.rowPlace.setText(resRoomitem.getLocation());
        holder.rowDesc.setText(resRoomitem.getDescription());

        switch (resRoomitem.getStatus()) {
            case 0:
                holder.btnRowRoomStatus.setText(R.string.main_list_room_status_vacant);
                roomStatus = true;
                break;
            case 1:
                holder.btnRowRoomStatus.setText(R.string.main_list_room_status_full);
                roomStatus = false;
                break;
            case 9:
                holder.btnRowRoomStatus.setText(R.string.main_list_room_status_expired);
                break;
        }

        holder.btnRowRoomStatus.setOnClickListener((View v) -> GoLib.getInstance().goInfoBang(context, resRoomitem.getRoomId(), resUserInfo.getUserId(), roomStatus));
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
