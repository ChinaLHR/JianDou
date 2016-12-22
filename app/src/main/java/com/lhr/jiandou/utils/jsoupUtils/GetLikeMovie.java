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
 * Created by ChinaLHR on 2016/12/20.
 * Email:13435500980@163.com
 */

public class GetLikeMovie {
    public String url = "https://movie.douban.com/subject/";
    public Document doc;

    public GetLikeMovie(String id) {
        try {
            url = url + id + "/";
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获得相关电源的id
     *
     * @return
     */
    public List<String> getmovieId() {
        List<String> list = new ArrayList<>();
        // 获取id
        Elements select = doc.select("[class=recommendations-bd] dd");
        Elements link = select.select("a");
        for (int i = 0; i < link.size(); i++) {
            String movieId = link.get(i).toString().replaceAll("[^(0-9)]", "");

            list.add(movieId);

        }

        return list;

    }

    /**
     * 获取相关电源的img链接
     *
     * @return
     */
    public List<String> getmovieimg() {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern
                .compile("[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*");
        Elements select = doc.select("[class=recommendations-bd]");
        Elements img = select.select("img[src$=.jpg]");

        for (int i = 0; i < img.size(); i++) {
            Matcher matcher = pattern.matcher(img.get(i).toString());
            while (matcher.find()) {
                list.add(matcher.group());
            }
        }

        return list;
    }

    /**
     * 获取电影的title
     *
     * @return
     */
    public List<String> getMovieTitle() {
        List<String> list = new ArrayList<>();
        Elements select = doc.select("[class=recommendations-bd]");
        Elements title = select.select("dd a[href]");
        for (int i = 0; i < title.size(); i++) {
            list.add(title.get(i).text());
        }

        return list;
    }
}

