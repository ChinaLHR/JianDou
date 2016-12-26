package com.lhr.jiandou.model.bean;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/26.
 * Email:13435500980@163.com
 */

public class BookDetailsBean {

    /**
     * rating : {"max":10,"numRaters":67063,"average":"7.6","min":0}
     * subtitle :
     * author : ["[日] 青山七惠"]
     * pubdate : 2007-9
     * tags : [{"count":14998,"name":"一个人的好天气","title":"一个人的好天气"},{"count":13148,"name":"日本文学","title":"日本文学"},{"count":12622,"name":"青山七惠","title":"青山七惠"},{"count":8275,"name":"日本","title":"日本"},{"count":6719,"name":"小说","title":"小说"},{"count":5323,"name":"青春","title":"青春"},{"count":5226,"name":"成长","title":"成长"},{"count":3410,"name":"芥川龙之介奖","title":"芥川龙之介奖"}]
     * origin_title : ひとり日和
     * image : https://img3.doubanio.com/mpic/s2755472.jpg
     * binding : 平装
     * translator : ["竺家荣"]
     * catalog : 春天
     夏天
     秋天
     冬天
     迎接春天
     * pages : 141
     * images : {"small":"https://img3.doubanio.com/spic/s2755472.jpg","large":"https://img3.doubanio.com/lpic/s2755472.jpg","medium":"https://img3.doubanio.com/mpic/s2755472.jpg"}
     * alt : https://book.douban.com/subject/2250587/
     * id : 2250587
     * publisher : 上海译文出版社
     * isbn10 : 7532743519
     * isbn13 : 9787532743513
     * title : 一个人的好天气
     * url : https://api.douban.com/v2/book/2250587
     * alt_title : ひとり日和
     * author_intro : 青山七惠是日本“80后”新锐女作家，1983年出生于埼玉县熊谷市，毕业于筑波大学图书馆信息专业，目前在东京新宿一家旅游公司工作，写作只能算是她的副职。2005年9月，青山凭借小说处女作《窗灯》一举摘得有“芥川奖摇篮”之称的第42届日本文艺奖，在日本文学界崭露头角；继而又在2007年1月，以第二部作品《一个人的好天气》摘得“芥川奖”，成为该奖历史上第三位年轻的女性得主。
     * summary : 《一个人的好天气》描述了一个打零工的女孩如何与年长亲人相处，同时追寻自我、独立的故事，走向自立的一名女孩在工作、生活和恋爱中的种种际遇和心情令人揪心，小说写尽了做一名自由职业者（“飞特族”）的辛酸。内容折射出当前日本的一个社会问题，即许多年轻人不愿投入全职工作而四处打工，宁愿做自由职业者，他们不想长大，不愿担负责任，无法独立，害怕走出去看看这个世界，但是又不知道这种恐惧从何而来。据日本官方统计，15至34岁的短期雇工在1996年到2004年之间翻了一番，达21.4万人。调查也显示，打零工的人收入不稳，结婚生子的机率大减，这对少子化严重的日本来说是一大警讯。作者青山七惠在接受记者采访时说：“我想告诉他们，只要你肯迈出第一步，自然会有出路。”她希望自己的作品能帮助他们“迈出第一步”。
     * series : {"id":"20830","title":"青山七惠作品"}
     * price : 15.00元
     */

    private RatingBean rating;
    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private SeriesBean series;
    private String price;
    private List<String> author;
    private List<TagsBean> tags;
    private List<String> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public SeriesBean getSeries() {
        return series;
    }

    public void setSeries(SeriesBean series) {
        this.series = series;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    public static class RatingBean {
        /**
         * max : 10
         * numRaters : 67063
         * average : 7.6
         * min : 0
         */

        private int max;
        private int numRaters;
        private String average;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : https://img3.doubanio.com/spic/s2755472.jpg
         * large : https://img3.doubanio.com/lpic/s2755472.jpg
         * medium : https://img3.doubanio.com/mpic/s2755472.jpg
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class SeriesBean {
        /**
         * id : 20830
         * title : 青山七惠作品
         */

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TagsBean {
        /**
         * count : 14998
         * name : 一个人的好天气
         * title : 一个人的好天气
         */

        private int count;
        private String name;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
