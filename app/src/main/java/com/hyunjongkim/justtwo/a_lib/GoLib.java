package com.hyunjongkim.justtwo.a_lib;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.hyunjongkim.justtwo.MainActivity;
import com.hyunjongkim.justtwo.bang.InfoBang;
import com.hyunjongkim.justtwo.bang.RegisterBangBase;
import com.hyunjongkim.justtwo.manage.Management;
import com.hyunjongkim.justtwo.user.Login;
import com.hyunjongkim.justtwo.user.SignUpActivity;

/**
 * 액티비티나 프래그먼트 실행 라이브러리
 */
public class GoLib {
    public final String TAG = GoLib.class.getSimpleName();
    private volatile static GoLib instance;

    public static GoLib getInstance() {
        if (instance == null) {
            synchronized (GoLib.class) {
                if (instance == null) {
                    instance = new GoLib();
                }
            }
        }

        return instance;
    }

    /**
     * 프래그먼트를 보여준다.
     *
     * @param fragmentManager 프래그먼트 매니저
     * @param containerViewId 프래그먼트를 보여줄 컨테이너 뷰 아이디
     * @param fragment        프래그먼트
     */
    public void goFragment(FragmentManager fragmentManager, int containerViewId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .commit();
    }

    /**
     * 뒤로가기를 할 수 있는 프래그먼트를 보여준다.
     *
     * @param fragmentManager 프래그먼트 매니저
     * @param containerViewId 프래그먼트를 보여줄 컨테이너 뷰 아이디
     * @param fragment        프래그먼트
     */
    public void goFragmentBack(FragmentManager fragmentManager, int containerViewId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * 이전 프래그먼트를 보여준다.
     *
     * @param fragmentManager 프래그먼트 매니저
     */
    public void goBackFragment(FragmentManager fragmentManager) {
        fragmentManager.popBackStack();
    }

    public void goSignUpActivity(Context context) {
        MyLog.d(TAG,"<<< goSignUpActivity >>>");
        Intent intent = new Intent(context, SignUpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goBangRegisterActivity(Context context) {
        MyLog.d(TAG,"<<< goBangRegisterActivity >>>");
        Intent intent = new Intent(context, RegisterBangBase.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goManagementActivity(Context context) {
        MyLog.d(TAG,"<<< goManagementActivity >>>");
        Intent intent = new Intent(context, Management.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goInfoBang(Context context, int roomId, int userId, boolean roomStatus) {
        MyLog.d(TAG,"<<< goInfoBang >>>");
        Intent intent = new Intent(context, InfoBang.class);
        intent.putExtra(InfoBang.ROOM_ID, roomId);
        intent.putExtra(InfoBang.USER_ID, userId);
        intent.putExtra(InfoBang.ROOM_STATUS, roomStatus);

        context.startActivity(intent);
    }

    public void goLoginActivity(Context context) {
        MyLog.d(TAG,"<<< goLoginActivity >>>");
        Intent intent = new Intent(context, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goMainActivity(Context context) {
        MyLog.d(TAG,"<<< goMainActivity >>>");
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goHome(Context context) {
        MyLog.d(TAG,"<<< goHome >>>");
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


}
