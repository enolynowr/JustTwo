package com.hyunjongkim.justtwo.bang;

import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.hyunjongkim.justtwo.a_item.BangInfoItem;
import com.hyunjongkim.justtwo.a_lib.DialogLib;
import com.hyunjongkim.justtwo.a_lib.MyLog;
import com.hyunjongkim.justtwo.a_lib.MyToast;
import com.hyunjongkim.justtwo.a_lib.StringLib;
import com.hyunjongkim.justtwo.a_remote.RemoteService;
import com.hyunjongkim.justtwo.a_remote.ServiceGenerator;

import org.parceler.Parcels;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 맛집 정보를 입력하는 액티비티
 */
public class RegisterBangInput extends Fragment implements View.OnClickListener {

    public static final String INFO_ITEM = "INFO_ITEM";
    private final String TAG = this.getClass().getSimpleName();

    Context context;
    BangInfoItem infoItem;
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

    /**
     * FoodInfoItem 객체를 인자로 저장하는
     * BestFoodRegisterInputFragment 인스턴스를 생성해서 반환한다.     *
     *
     * @param infoItem 맛집 정보를 저장하는 객체
     * @return BestFoodRegisterInputFragment 인스턴스
     */
    public static RegisterBangInput newInstance(BangInfoItem infoItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INFO_ITEM, Parcels.wrap(infoItem));

        RegisterBangInput fragment = new RegisterBangInput();
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * 프래그먼트가 생성될 때 호출되며 인자에 저장된 FoodInfoItem를
     * BestFoodRegisterActivity에 currentItem를 저장한다.
     *
     * @param savedInstanceState 프래그먼트가 새로 생성되었을 경우, 이전 상태 값을 가지는 객체
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            infoItem = Parcels.unwrap(getArguments().getParcelable(INFO_ITEM));

            if (infoItem.seq != 0) {
                RegisterBangBase.currentItem = infoItem;
            }
            MyLog.d(TAG, "infoItem " + infoItem);
        }
    }

    /**
     * fragment_bestfood_register_input.xml 기반으로 뷰를 생성한다.
     *
     * @param inflater           XML를 객체로 변환하는 LayoutInflater 객체
     * @param container          null이 아니라면 부모 뷰
     * @param savedInstanceState null이 아니라면 이전에 저장된 상태를 가진 객체
     * @return 생성한 뷰 객체
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();

        // CATEGORY
        spnData = getResources().getStringArray(R.array.bang_category);
        adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line,
                spnData);

        MyLog.d(TAG, "address" + address);

        return inflater.inflate(R.layout.bang_register, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (spnData == null) {

        }
    }

    /**
     * onCreateView() 메소드 뒤에 호출되며 맛집 정보를 입력할 뷰들을 생성한다.
     *
     * @param view               onCreateView() 메소드에 의해 반환된 뷰
     * @param savedInstanceState null이 아니라면 이전에 저장된 상태를 가진 객체
     */
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
        // Btn Save
        btnSaveBangInfo = view.findViewById(R.id.btn_bang_regi_input);
        btnSaveBangInfo.setOnClickListener(this);

    }

    /**
     * 클릭이벤트를 처리한다.
     *
     * @param v 클릭한 뷰에 대한 정보
     */
    @Override
    public void onClick(View v) {

        // User ID
        infoItem.uId = ((MyApp) context.getApplicationContext()).getUserId();

        // Category
        infoItem.category = spnCategory.getSelectedItem().toString();
        // Date
        infoItem.hostDate = dateTv.getText().toString();
        // Time
        infoItem.hostPlace = adrTv.getText().toString();
        // Description
        infoItem.bangContents = descriptionEdit.getText().toString();

        MyLog.d(TAG, "onClick imageItem " + infoItem);

        if (v.getId() == R.id.register_bang_date) {

            DialogLib dialogLib = new DialogLib();
            dialogLib.setDatePicker(getContext(), dateTv);
        }

        if (v.getId() == R.id.register_bang_time) {

            DialogLib dialogLib = new DialogLib();
            dialogLib.setTimePicker(getContext(), timeTv);
        }

        if (v.getId() == R.id.btn_bang_regi_input) {

            save();
        }
    }

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
        if (StringLib.getInstance().isBlank(infoItem.hostPlace)) {
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
                        infoItem.seq = seq;

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
