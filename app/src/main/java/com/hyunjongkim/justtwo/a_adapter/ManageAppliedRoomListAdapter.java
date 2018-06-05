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
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;

import java.util.ArrayList;

public class ManageAppliedRoomListAdapter extends RecyclerView.Adapter<ManageAppliedRoomListAdapter.ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();

    private Context context;
    private ArrayList<RoomInfoItem> roomItemList;
    private UserInfoItem userInfoItem;

    private int resource;

    //Constructor
    public ManageAppliedRoomListAdapter(Context context, int resource, ArrayList<RoomInfoItem> roomItemList) {
        this.context = context;
        this.resource = resource;

        this.roomItemList = roomItemList;
        userInfoItem = ((MyApp) context.getApplicationContext()).getUserInfoItem();
    }

    //
    public void setItem(RoomInfoItem newItem) {
        for (int i = 0; i < roomItemList.size(); i++) {
            RoomInfoItem item = roomItemList.get(i);

            if (item.roomInx == newItem.roomInx) {
                roomItemList.set(i, newItem);


                notifyItemRemoved(i);

                break;
            }
        }
    }

    public void addItemList(ArrayList<RoomInfoItem> roomItemList) {
        this.roomItemList.addAll(roomItemList);

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RoomInfoItem roomItem = roomItemList.get(position);

        MyLog.d(TAG, "getView" + roomItem );

        //holder.rowCategory.setText(item.category);
        holder.rowDate.setText(roomItem.dateTime);
        holder.rowPlace.setText(roomItem.location);
        holder.rowDesc.setText(roomItem.description);

        // 部屋のStatus文字の変更
       /* 0	承認待ち(申請中)
          1	承認済み(申請完了)
          2	承認拒否 (申請失敗)
          9	満了
          */
        switch (roomItem.roomStatus) {
            case 0:
                holder.rowAppliedRoomStatus.setText(R.string.checking);
                break;
            case 1:
                holder.rowAppliedRoomStatus.setText(R.string.confirmed);
                break;
            case 2:
                holder.rowAppliedRoomStatus.setText(R.string.disapprove);
                break;
            case 9:
                holder.rowAppliedRoomStatus.setText(R.string.expired_room_date);
                break;
        }

        holder.rowAppliedRoomStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GoLib.getInstance().goInfoBang(context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.roomItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rowDate;
        TextView rowPlace;
        TextView rowDesc;
        Button rowAppliedRoomStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            rowDate = itemView.findViewById(R.id.row_manage_date);
            rowPlace = itemView.findViewById(R.id.row_manage_place);
            rowDesc = itemView.findViewById(R.id.row_manage_description);
            rowAppliedRoomStatus = itemView.findViewById(R.id.btn_row_manage_apply_status);
        }
    }
}
