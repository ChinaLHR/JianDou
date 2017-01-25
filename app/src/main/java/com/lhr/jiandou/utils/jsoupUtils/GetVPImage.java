package com.lhr.jiandou.utils.jsoupUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ChinaLHR on 2016/12/29.
 * Email:13435500980@163.com
 */

public class GetVPImage {
    private String url = "http://www.mtime.com/";
    private Document doc;

    public GetVPImage() {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getImage() {
        List<String> mList = new ArrayList<>();
        Pattern pattern = Pattern
                .compile("[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*");
        if (doc != null) {
            if (doc != null) {
                Elements s1 = doc.select("[id = indexTopSlide]");
                Elements s2 = s1.select("dl");
                Elements s3 = s2.select("dd");
                Elements s4 = s3.select("div");
                for (int i = 0; i < s4.size(); i++) {
                    Matcher matcher = pattern.matcher(s4.get(i).attr("style"));
                    while (matcher.find()) {
                        mList.add(matcher.group());
                    }
                }
                return mList;
            } else {
                return mList;
            }

        } else {
            return null;
        }
    }
    }

