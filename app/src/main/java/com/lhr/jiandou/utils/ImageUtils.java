package com.lhr.jiandou.utils;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import com.lhr.jiandou.MyApplication;
import com.lhr.jiandou.R;

/**
 * Created by ChinaLHR on 2016/12/19.
 * Email:13435500980@163.com
 */

public class ImageUtils {

    /**
     * 根据bitmap提取颜色
     *
     * @param bitmap
     * @return
     */
    public static int getColor(Bitmap bitmap) {
        if (bitmap != null) {
            Palette p = Palette.from(bitmap).generate();
            Palette.Swatch s_dm = p.getDarkMutedSwatch();
            Palette.Swatch s_dv = p.getDarkVibrantSwatch();
            if (s_dm != null) {
                return s_dm.getRgb();
            } else {
                if (s_dv != null) {
                    return s_dv.getRgb();
                } else {
                    return UIUtils.getColor(MyApplication.getContext(), R.color.colorPrimary);
                }
            }
        } else {
            return UIUtils.getColor(MyApplication.getContext(), R.color.colorPrimary);
        }
    }
}
