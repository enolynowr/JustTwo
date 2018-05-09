package com.hyunjongkim.justtwo.a_lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 네트워크와 서버와 관련된 라이브러리
 */
public class RemoteLib {
    public static final String TAG = RemoteLib.class.getSimpleName();

    private volatile static RemoteLib instance;

    public static RemoteLib getInstance() {
        if (instance == null) {
            synchronized (RemoteLib.class) {
                if (instance == null) {
                    instance = new RemoteLib();
                }
            }
        }
        return instance;
    }

    /**
     * 네트워크 연결 여부를 반환한다.
     * @param context 컨텍스트
     * @return 네트워크 연결여부
     */
    public boolean isConnected(Context context) {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();

            return info != null;
        } catch (Exception e) {
            return false;
        }
    }


}
