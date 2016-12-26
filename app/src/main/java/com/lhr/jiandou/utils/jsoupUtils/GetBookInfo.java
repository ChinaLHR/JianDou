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
 * Created by ChinaLHR on 2016/12/26.
 * Email:13435500980@163.com
 */

public class GetBookInfo {
    private String url = "https://book.douban.com/subject/";
    private Document doc;
    private String id;

    public GetBookInfo(String id) {
        try {
            url = url + id + "/";
            doc = Jsoup.connect(url).get();
            this.id = id;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取短评
     *
     * @return
     */
    public List<String> getShortComments() {
        List<String> list = new ArrayList<>();
        Elements select = doc.select("[class=comment-content]");
        for (int i = 0; i < select.size(); i++) {
            list.add(select.get(i).text());
        }
        return list;
    }

    /**
     * 获取章节
     *
     * @return
     */
    public List<String> getBookList() {
        List<String> list = new ArrayList<>();
        String mid = "[id=dir_" + id + "_full]";
        Elements select = doc.select(mid);
        if (!select.text().trim().equals("")) {
            String[] split = select.html().split("<br>");
            for (int i = 0; i < split.length; i++) {
                if (!split[i].trim().equals("") && i < split.length - 1) {
                    list.add(split[i]);
                }
            }

            return list;
        } else {
            return null;
        }

    }

    /**
     * 获取推荐书籍的title
     *
     * @return
     */
    public List<String> getLikeBookTitle() {
        List<String> list = new ArrayList<>();
        Elements s1 = doc.select("[class=block5 subject_show knnlike ]>div");
        Elements s2 = s1.select("dd a[href]");
        if (s2.size() > 2) {
            for (int i = 0; i < s2.size(); i++) {
                list.add(s2.get(i).text());
            }
            return list;
        } else {
            return null;
        }
    }

    /**
     * 获取推荐图书img的链接
     *
     * @return
     */
    public List<String> getLikeBookImage() {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern
                .compile("[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*");
        Elements s1 = doc.select("[class=block5 subject_show knnlike ]");
        Elements s2 = s1.select("img[src$=.jpg]");
        if (s2.size()>2) {
            for (int i = 0; i < s2.size(); i++) {
                Matcher matcher = pattern.matcher(s2.get(i).toString());
                while (matcher.find()) {
                    String s = matcher.group().replaceAll("spic", "lpic");
                    list.add(s);
                }
            }
            return list;
        }else {
            return null;
        }

    }

    /**
     * 获取推荐图书的id
     *
     * @return
     */
    public List<String> getLikeBookId() {
        List<String> list = new ArrayList<>();
        Elements s1 = doc.select("[class=block5 subject_show knnlike ]");
        Elements s2 = s1.select("dt a");
        if (s2.size() > 2) {
            for (int i = 0; i < s2.size(); i++) {
                String url = s2.get(i).attr("href").replaceAll("[^(0-9)]", "");
                list.add(url);

            }
            return list;
        }else {
            return null;
        }
    }
}
