package com.lhr.jiandou.view;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ChinaLHR on 2016/12/17.
 * Email:13435500980@163.com
 */

public class mProgressdialog {
    private ProgressDialog mProgressDialog;

    public static boolean isFirst = true;

    public void showProgressdialog(Context context) {
        if (isFirst) {
            isFirst = false;
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

        }
    }

    public void cancelProgressdialog() {
        if (mProgressDialog!=null){
        mProgressDialog.cancel();
        }
    }
}
