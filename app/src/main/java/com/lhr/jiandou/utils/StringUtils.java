package com.lhr.jiandou.utils;

import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/25.
 * Email:13435500980@163.com
 */

public class StringUtils {

    public static void addViewString(List<String> list, TextView view) {
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                view.append(list.get(i));
            } else {
                view.append(list.get(i) + "/");
            }
        }
    }

    @NonNull
    public static String SpliceString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i) + "/");
            }
        }
        return sb.toString();
    }
}
