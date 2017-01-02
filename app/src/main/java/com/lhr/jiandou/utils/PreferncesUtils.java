package com.lhr.jiandou.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ChinaLHR on 2017/1/1.
 * Email:13435500980@163.com
 */

public class PreferncesUtils {

    private static SharedPreferences sp;

    private static final String Catch_file = "com.lhr.jiandou_preferences";

    public static String getString(Context ctx, String key, String defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(Catch_file, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static Boolean getBoolean(Context ctx, String key, boolean defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences(Catch_file, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }
}
