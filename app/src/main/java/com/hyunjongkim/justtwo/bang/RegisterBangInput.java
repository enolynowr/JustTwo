package com.hyunjongkim.justtwo.bang;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hyunjongkim.justtwo.MyApp;
import com.hyunjongkim.justtwo.R;
import com.hyunjongkim.justtwo.a_item.RoomInfoItem;
import com.hyunjongkim.justtwo.a_lib.DialogLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;

import org.parceler.Parcels;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * REGISTER ROOM
 */
public class RegisterBangInput extends Fragment implements View.OnClickListener {

    public static final String INFO_ITEM = "INFO_ITEM";
    private final String TAG = this.getClass().getSimpleName();

    Context context;
    RoomInfoItem infoItem;
    Address address;

    // View 관련
    Spinner spnCategory;
    ArrayAdapter<String> adapter;
    String[] spnData;

    EditText descriptionEdit;

    TextView dateTv;
    TextView timeTv;
    TextView adrTv;

    Button btnSaveBangInfo;

    //MAKE INSTANCE RegisterBangInput IN ROOMINFOITEM
    public static RegisterBangInput newInstance(RoomInfoItem infoItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INFO_ITEM, Parcels.wrap(infoItem));
        RegisterBangInput fragment = new RegisterBangInput();
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * CALLING ON MAKING FRAGMENT 프래그먼트가 생성될 때 호출되며 인자에 저장된 FoodInfoItem를
     * BestFoodRegisterActivity에 currentItem를 저장한다.
     * @param savedInstanceState 프래그먼트가 새로 생성되었을 경우, 이전 상태 값을 가지는 객체
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            infoItem = Parcels.unwrap(getArguments().getParcelable(INFO_ITEM));

            if (infoItem.roomInx != 0) {
                RegisterBangBase.currentItem = infoItem;
            }

            MyLog.d(TAG, "infoItem " + infoItem);
        }
    }

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();

        // CATEGORY
        spnData = getResources().getStringArray(R.array.bang_category);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, spnData);
        MyLog.d(TAG, "address" + address);

        return inflater.inflate(R.layout.bang_register, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (spnData == null) {

        }
    }

    //CALLING AFTER onCreateView() METHOD
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Category
        spnCategory = getActivity().findViewById(R.id.spn_bang_category);
        spnCategory.setAdapter(adapter);
        // Date
        dateTv = view.findViewById(R.id.register_bang_date);
        dateTv.setOnClickListener(this);
        // Time
        timeTv = view.findViewById(R.id.register_bang_time);
        timeTv.setOnClickListener(this);
        // Adr
        adrTv = view.findViewById(R.id.display_adr);
        // Description
        descriptionEdit = view.findViewById(R.id.bang_description);

    }

    // PROCESSING CLICKING
    @Override
    public void onClick(View v) {

        // User ID
//        infoItem.userEmail = ((MyApp) context.getApplicationContext()).getUserEmail();
        // Category
        infoItem.category = spnCategory.getSelectedItem().toString();
        // Date
//        infoItem.hostDate = dateTv.getText().toString();
        // Time
        infoItem.location = adrTv.getText().toString();
        // Description
        infoItem.desc = descriptionEdit.getText().toString();

        MyLog.d(TAG, "onClick imageItem " + infoItem);

        if (v.getId() == R.id.register_bang_date) {
            DialogLib dialogLib = new DialogLib();
            dialogLib.setDatePicker(getContext(), dateTv);
        }

        if (v.getId() == R.id.register_bang_time) {
            DialogLib dialogLib = new DialogLib();
            dialogLib.setTimePicker(getContext(), timeTv);
        }


    }
/////////////////////// FUNCTION

    // Userが入力した情報をCheckし、保存する
    private void save() {
        // Category
        /*if (StringLib.getInstance().isBlank(infoItem.category)) {
            // MyToast.s(context, context.getResources().getString(R.string.input_bestfood_name));
            return;
        }*/
        // Date
        /*if (StringLib.getInstance().isBlank(infoItem.hostDate)) {
            // MyToast.s(context, context.getResources().getString(R.string.input_bestfood_name));
            return;
        }
        // Time
        if (StringLib.getInstance().isBlank(infoItem.hostTime)) {
            // MyToast.s(context, context.getResources().getString(R.string.input_bestfood_name));
            return;
        }*/
       /* // Adr
        if (StringLib.getInstance().isBlank(infoItem.location)) {
            // MyToast.s(context, context.getResources().getString(R.string.input_bestfood_name));
            return;
        }*/

        insertFoodInfo();
    }


    // Save the info of room to the Server.
    private void insertFoodInfo() {

        MyLog.d(TAG, infoItem.toString());
        RemoteService remoteService = ServiceGenerator.createService(RemoteService.class);
        Call<String> call = remoteService.insertBangInfo(infoItem);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    int seq = 0;
                    String seqString = response.body();

                    try {
                        seq = Integer.parseInt(seqString);
                    } catch (Exception e) {
                        seq = 0;
                    }

                    if (seq == 0) {
                        //등록 실패
                    } else {
                        infoItem.roomInx = seq;

                    }
                } else { // 등록 실패
                    int statusCode = response.code();
                    ResponseBody errorBody = response.errorBody();
                    MyLog.d(TAG, "fail " + statusCode + errorBody.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MyLog.d(TAG, "no internet connectivity");
            }
        });
    }
}
