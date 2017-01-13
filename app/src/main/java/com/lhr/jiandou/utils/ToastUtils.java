package com.lhr.jiandou.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lhr.jiandou.R;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;
    public static Toast toast = null;

    /**
     * 显示Toast
     *
     * @param context
     * @param message
     */
    public static void show(Context context, String message) {
        if (isShow) {
            toast = new Toast(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.mtoast_layout, null);
            TextView tv = (TextView) view.findViewById(R.id.toast_tv);
            if (message != null) {
                tv.setText(message);
            }
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void cancel() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
