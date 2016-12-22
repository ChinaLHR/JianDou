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
 * Created by ChinaLHR on 2016/12/22.
 * Email:13435500980@163.com
 */

public class GetActor {
    public String url = "https://movie.douban.com/celebrity/";
    public Document doc;

    public GetActor(String id) {
        try {
            url = url + id + "/";
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取影人图片
     *
     * @return
     */
    public List<String> getActorImage() {
        List<String> list = new ArrayList<>();
        String text;
        Pattern pattern = Pattern
                .compile("[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*");
        Elements select = doc.select("[class=pic-col5]");
        Elements elements = select.select("img[src$=.jpg]");
        for (int i = 0; i < elements.size(); i++) {
            text = elements.get(i).toString();
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                list.add(matcher.group());
            }
        }
        return list;
    }

    /**
     * 获得影人简介
     */
    public String getActorSummary() {
        String text;
        Elements select = doc.select("div.bd");
        Elements selecthid = doc.select("[class=all hidden]");
        if (selecthid.text().trim()!=null&&!selecthid.text().trim().equals("")){
            text = doc.select("[class=all hidden]").text();
        }else{
            text = select.get(1).text();
        }
        return text;
    }

}
