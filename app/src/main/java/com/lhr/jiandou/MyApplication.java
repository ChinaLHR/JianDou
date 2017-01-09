package com.lhr.jiandou;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.lhr.jiandou.model.db.GreenDaoUtils;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class MyApplication extends Application {
    private static Context context;
    private static GreenDaoUtils mDaoUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mDaoUtils = new GreenDaoUtils(context);
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static GreenDaoUtils getDbUtils() {
        return mDaoUtils;
    }
}
