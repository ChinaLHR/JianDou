package com.lhr.jiandou.model.bean;

import java.io.Serializable;

/**
 * Created by ChinaLHR on 2016/12/24.
 * Email:13435500980@163.com
 */

public class BooksBean implements Serializable {

    private RatingBean rating;
    private String image;

    private String pages;
    private ImagesBean images;

    private String id;

    private String title;


    public RatingBean getRating() {
        return rating;
    }


    public ImagesBean getImages() {
        return images;
    }


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


    public static class RatingBean implements Serializable {
        /**
         * max : 10
         * numRaters : 177888
         * average : 8.0
         * min : 0
         */

        private String average;


        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }


    }

    public static class ImagesBean implements Serializable {
        /**
         * small : https://img3.doubanio.com/spic/s1228930.jpg
         * large : https://img3.doubanio.com/lpic/s1228930.jpg
         * medium : https://img3.doubanio.com/mpic/s1228930.jpg
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


}
