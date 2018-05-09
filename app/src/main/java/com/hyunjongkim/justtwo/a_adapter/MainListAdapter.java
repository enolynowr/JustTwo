package com.hyunjongkim.justtwo.a_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_item.BangInfoItem;
import com.hyunjongkim.justtwo.a_item.UserInfoItem;
import com.hyunjongkim.justtwo.a_lib.GoLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    private Context context;
    private ArrayList<BangInfoItem> itemList;
    private UserInfoItem userInfoItem;

    private int resource;

    //Constructor
    public MainListAdapter(Context context, int resource, ArrayList<BangInfoItem> itemList) {
        this.context = context;
        this.resource = resource;
        this.itemList = itemList;

        userInfoItem = ((MyApp) context.getApplicationContext()).getUserInfoItem();
    }

    //
    public void setItem(BangInfoItem newItem) {
        for (int i = 0; i < itemList.size(); i++) {
            BangInfoItem item = itemList.get(i);

            if (item.seq == newItem.seq) {
                itemList.set(i, newItem);

                notifyItemRemoved(i);

                break;
            }
        }
    }

    //
    public void addItemList(ArrayList<BangInfoItem> itemList) {
        this.itemList.addAll(itemList);

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
        int bangStatusNum = 0;
        final BangInfoItem item = itemList.get(position);

        MyLog.d(TAG, "getView" + item);

        holder.rowCategory.setText(item.category);
        holder.rowDate.setText(item.hostDate);
        holder.rowPlace.setText(item.hostPlace);

        //  申請: 3, 満員: 4
        switch (item.bangStatus) {

            case 3:
                holder.btnRowRoomStatus.setText("申請");
                break;

            case 4:
                holder.btnRowRoomStatus.setText("満員");
                break;
        }

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
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rowCategory;
        TextView rowDate;
        TextView rowPlace;
        Button btnRowRoomStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            rowCategory = itemView.findViewById(R.id.tv_row_main_category);
            rowDate = itemView.findViewById(R.id.tv_row_main_date);
            rowPlace = itemView.findViewById(R.id.tv_row_main_place);
            btnRowRoomStatus = itemView.findViewById(R.id.btn_row_main_status);
        }
    }
}
