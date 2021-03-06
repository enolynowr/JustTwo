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
import com.hyunjongkim.justtwo.a_item.ManagementInfoItem;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.MyLog;

import java.util.ArrayList;

public class ManageMadeRoomListAdapter extends RecyclerView.Adapter<ManageMadeRoomListAdapter.ViewHolder> {

    private final String TAG = this.getClass().getSimpleName();

    private Context context;
    private ArrayList<RoomInfoItem> itemList;
    private ArrayList<ManagementInfoItem> manageItemList;
    private ManagementInfoItem manageItem;
    private UserInfoItem userInfoItem;

    private int resource;

    //Constructor
    public ManageMadeRoomListAdapter(Context context, int resource,
                                     ArrayList<RoomInfoItem> itemList, ArrayList<ManagementInfoItem> manageItemList) {

        this.context = context;
        this.resource = resource;
        this.itemList = itemList;
        this.manageItemList = manageItemList;

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
        final RoomInfoItem item = itemList.get(position);
        final ManagementInfoItem manageItem = manageItemList.get(position);

        MyLog.d(TAG, "getView" + item);

        //holder.rowCategory.setText(item.category);
        holder.rowDate.setText("2018-05-16");
        holder.rowPlace.setText("TOKYO");
        holder.rowDesc.setText("are you ready!!");
        //holder.btnRowApplyStatus.

        // 部屋のStatus文字の変更
  /*      switch (item.status) {
            case 0:
                holder.btnRowRoomStatus.setText(R.string.checking);
                break;
            case 1:
                holder.btnRowRoomStatus.setText(R.string.confirmed);
                break;
            case 2:
                holder.btnRowRoomStatus.setText(R.string.disapprove);
                break;
        }
*/
        holder.btnRowApplyStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

////////////////////// FUNCTION ////////////////////////////////////////////////////////////////

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rowDate;
        TextView rowPlace;
        TextView rowDesc;
        Button btnRowApplyStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            rowDate = itemView.findViewById(R.id.row_manage_date);
            rowPlace = itemView.findViewById(R.id.row_manage_place);
            rowDesc = itemView.findViewById(R.id.row_manage_description);
            btnRowApplyStatus = itemView.findViewById(R.id.btn_row_manage_apply_status);
        }
    }

    //
    public void setItem(RoomInfoItem newItem) {
        for (int i = 0; i < itemList.size(); i++) {
            RoomInfoItem item = itemList.get(i);

            if (item.roomInx == newItem.roomInx) {
                itemList.set(i, newItem);
                notifyItemRemoved(i);

                break;
            }
        }
    }

    public void addItemList(ArrayList<RoomInfoItem> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }
}
