package com.lhr.jiandou.utils.jsoupUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetNavImage {
    private String url = "http://www.bjp.org.cn/col/col89/index.html";
    private String defulturl = "http://www.bjp.org.cn";
    private Document doc;

    public GetNavImage() {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getImage() {
        List<String> mList = new ArrayList<>();
        if (doc != null) {
            Elements s1 = doc.select("td");
            Elements s2 = s1.select("b");
            Elements s3 = s2.select("a");
            //获取每日一图的描述
            if (!s3.get(0).text().trim().equals("")) {
                mList.add(s3.get(0).text());
            }
            // 获取到每日一图的html文件
            String html = defulturl + s3.get(0).attr("href");
            try {
                doc = Jsoup.connect(html).get();
                if (doc != null) {
                    Elements s4 = doc.select("[id=oneday]");
                    Elements s5 = s4.select("a");
                    String imageUrl = defulturl + s5.attr("href");
                    if (!imageUrl.trim().equals("")) {
                        mList.add(imageUrl);
                    }
                    return mList;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return mList;
            }
        }
        return null;
    }
}
