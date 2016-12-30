package com.lhr.jiandou.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/18.
 * Email:13435500980@163.com
 * <p>
 * 序列化缓存List<bean>
 */

public class CacheUtils {
    public static final String DataCache_movie = "Cache_File_movie";
    public static final String DataCache_book = "Cache_File_book";

    /**
     * 序列化List
     */
    public static <T> void savebean(Context context, List<T> list, String Cache_type, String name) {
        if (context == null) {
            return;
        }
        File file;
        if (!Cache_type.isEmpty()) {
            File fileDir = new File(context.getFilesDir(), Cache_type);
            if (!fileDir.exists() || !fileDir.isDirectory()) {
                fileDir.mkdir();
            }
            file = new File(fileDir, name);
        } else {
            file = new File(context.getFilesDir(), name);
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(list);
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 反序列化
     *
     * @param context
     * @param name
     * @param <E>
     * @return
     */
    public static <E> List<E> readbean(Context context, String Cache_type, String name) {
        if (name == null) {
            return null;
        } else {
            File file;
            if (!Cache_type.isEmpty()) {
                File fileDir = new File(context.getFilesDir(), Cache_type);
                if (!fileDir.exists() || !fileDir.isDirectory()) {
                    fileDir.mkdir();
                }

                file = new File(fileDir, name);

            } else {
                file = new File(context.getFilesDir(), name);
            }
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                List<E> list = (List<E>) ois.readObject();
                ois.close();
                return list;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


    }
}