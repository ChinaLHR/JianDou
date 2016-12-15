package com.lhr.jiandou.model.bean;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/15.
 * Email:13435500980@163.com
 */

public class SubjectsBean {


    /**
     * rating : {"max":10,"average":9.1,"stars":"45","min":0}
     * genres : ["剧情","喜剧","爱情"]
     * title : 三傻大闹宝莱坞
     * casts : [{"alt":"https://movie.douban.com/celebrity/1031931/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/13628.jpg","large":"https://img1.doubanio.com/img/celebrity/large/13628.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/13628.jpg"},"name":"阿米尔·汗","id":"1031931"},{"alt":"https://movie.douban.com/celebrity/1049635/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5568.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5568.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5568.jpg"},"name":"卡琳娜·卡普尔","id":"1049635"},{"alt":"https://movie.douban.com/celebrity/1018290/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/5651.jpg","large":"https://img3.doubanio.com/img/celebrity/large/5651.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/5651.jpg"},"name":"马达范","id":"1018290"}]
     * collect_count : 747976
     * original_title : 3 Idiots
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1286677/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/16549.jpg","large":"https://img1.doubanio.com/img/celebrity/large/16549.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/16549.jpg"},"name":"拉吉库马尔·希拉尼","id":"1286677"}]
     * year : 2009
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p579729551.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p579729551.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p579729551.jpg"}
     * alt : https://movie.douban.com/subject/3793023/
     * id : 3793023
     */

    private RatingBean rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private ImagesBean images;
    private String alt;
    private String id;
    private List<String> genres;
    private List<CastsBean> casts;
    private List<DirectorsBean> directors;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 9.1
         * stars : 45
         * min : 0
         */

        private int max;
        private double average;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
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
         * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p579729551.jpg
         * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p579729551.jpg
         * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p579729551.jpg
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

    public static class CastsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1031931/
         * avatars : {"small":"https://img1.doubanio.com/img/celebrity/small/13628.jpg","large":"https://img1.doubanio.com/img/celebrity/large/13628.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/13628.jpg"}
         * name : 阿米尔·汗
         * id : 1031931
         */

        private String alt;
        private AvatarsBean avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBean {
            /**
             * small : https://img1.doubanio.com/img/celebrity/small/13628.jpg
             * large : https://img1.doubanio.com/img/celebrity/large/13628.jpg
             * medium : https://img1.doubanio.com/img/celebrity/medium/13628.jpg
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

    public static class DirectorsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1286677/
         * avatars : {"small":"https://img1.doubanio.com/img/celebrity/small/16549.jpg","large":"https://img1.doubanio.com/img/celebrity/large/16549.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/16549.jpg"}
         * name : 拉吉库马尔·希拉尼
         * id : 1286677
         */

        private String alt;
        private AvatarsBeanX avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBeanX getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBeanX avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AvatarsBeanX {
            /**
             * small : https://img1.doubanio.com/img/celebrity/small/16549.jpg
             * large : https://img1.doubanio.com/img/celebrity/large/16549.jpg
             * medium : https://img1.doubanio.com/img/celebrity/medium/16549.jpg
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
}

