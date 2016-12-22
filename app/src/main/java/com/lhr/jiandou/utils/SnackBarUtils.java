package com.lhr.jiandou.utils;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by ChinaLHR on 2016/12/19.
 * Email:13435500980@163.com
 */

public class SnackBarUtils {
    public static Snackbar mSnackbar;
    public static void showSnackBar(View v, String str) {

        if (v instanceof CoordinatorLayout) {
            mSnackbar = Snackbar.make(v,str,Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSnackbar.dismiss();
                }
            });
            mSnackbar.show();
        }
    }
}
