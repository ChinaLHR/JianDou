package com.lhr.jiandou.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ChinaLHR on 2016/12/13.
 * Email:13435500980@163.com
 */

public class SpUtils  {
    private static SharedPreferences sp;

    /**
     * 写入boolean变量至sp中
     * @param ctx
     * @param key
     * @param value
     */
    public static void putBoolean(Context ctx, String key, boolean value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 从sp中读取boolean变量
     * @param ctx
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context ctx, String key, boolean defValue) {

        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 写入String变量至sp中
     * @param ctx
     * @param key
     * @param value
     */
    public static void putString(Context ctx, String key, String value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 从sp中读取String变量
     * @param ctx
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context ctx, String key, String defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 写入Int变量
     * @param ctx
     * @param key
     * @param value
     */
    public static void putInt(Context ctx, String key, int value) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 从sp中读取Int变量
     * @param ctx
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context ctx, String key, int defValue) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 从sp中移除指定节点
     * @param ctx
     * @param key
     */
    public static void remove(Context ctx, String key) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }
}
