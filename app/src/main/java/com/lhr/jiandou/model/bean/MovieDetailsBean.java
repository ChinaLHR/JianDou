package com.lhr.jiandou.model.bean;

import java.util.List;

/**
 * Created by ChinaLHR on 2016/12/19.
 * Email:13435500980@163.com
 */

public class MovieDetailsBean {

    /**
     * rating : {"max":10,"average":7.4,"stars":"40","min":0}
     * reviews_count : 295
     * wish_count : 15031
     * douban_site :
     * year : 2009
     * images : {"small":"https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p494268647.jpg","large":"https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p494268647.jpg","medium":"https://img1.doubanio.com/view/movie_poster_cover/spst/public/p494268647.jpg"}
     * alt : https://movie.douban.com/subject/1764796/
     * id : 1764796
     * mobile_url : https://movie.douban.com/subject/1764796/mobile
     * title : 机器人9号
     * do_count : null
     * share_url : https://m.douban.com/movie/subject/1764796
     * seasons_count : null
     * schedule_url :
     * episodes_count : null
     * countries : ["美国"]
     * genres : ["动画","冒险","奇幻"]
     * collect_count : 71549
     * casts : [{"alt":"https://movie.douban.com/celebrity/1054395/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/51597.jpg","large":"https://img1.doubanio.com/img/celebrity/large/51597.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/51597.jpg"},"name":"伊利亚·伍德","id":"1054395"},{"alt":"https://movie.douban.com/celebrity/1016673/","avatars":{"small":"https://img5.doubanio.com/img/celebrity/small/3996.jpg","large":"https://img5.doubanio.com/img/celebrity/large/3996.jpg","medium":"https://img5.doubanio.com/img/celebrity/medium/3996.jpg"},"name":"詹妮弗·康纳利","id":"1016673"},{"alt":"https://movie.douban.com/celebrity/1017907/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/55994.jpg","large":"https://img3.doubanio.com/img/celebrity/large/55994.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/55994.jpg"},"name":"约翰·C·赖利","id":"1017907"},{"alt":"https://movie.douban.com/celebrity/1036321/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/42033.jpg","large":"https://img3.doubanio.com/img/celebrity/large/42033.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/42033.jpg"},"name":"克里斯托弗·普卢默","id":"1036321"}]
     * current_season : null
     * original_title : 9
     * summary : 机器人9号（伊利亚•伍德 Elijah Wood 饰）突然醒来，发现身边的世界充满危机，四处残败，一片末世景象。9号带着一个画有三个奇怪符号的圆形物体逃到街上，幸遇发明家机器人2号（马丁•兰道 Martin Landau 饰）给自己装上了声音，但2号却不幸被机器怪兽抓走。9号找到了老兵1号（克里斯托弗•普卢默 Christopher Plummer 饰）、机械工5号（约翰•雷利 John C. Reilly 饰）、疯癫画家6号（克里斯品•格拉夫 Crispin Glover 饰）和大力士8号（弗雷德•塔塔绍尔 Fred Tatasciore 饰）。9号与5号擅自出行援救2号，危急时被女武士7号（詹妮佛•康纳利 Jennifer Connelly 饰）救下，但无意中9号却令终极机器兽复活。带着自己从哪里来以及生存使命的问题，9号决定想尽办法制服机器兽，拯救全世界……©豆瓣
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1276787/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/1351678808.44.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1351678808.44.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1351678808.44.jpg"},"name":"申·阿克","id":"1276787"}]
     * comments_count : 11567
     * ratings_count : 57321
     * aka : ["9：末世决战","九","Number 9","机器人9号"]
     */

    private RatingBean rating;
    private int reviews_count;
    private int wish_count;
    private String douban_site;
    private String year;
    private ImagesBean images;
    private String alt;
    private String id;
    private String mobile_url;
    private String title;
    private Object do_count;
    private String share_url;
    private Object seasons_count;
    private String schedule_url;
    private Object episodes_count;
    private int collect_count;
    private Object current_season;
    private String original_title;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private List<String> countries;
    private List<String> genres;
    private List<CastsBean> casts;
    private List<DirectorsBean> directors;
    private List<String> aka;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
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

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
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

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 7.4
         * stars : 40
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
         * small : https://img1.doubanio.com/view/movie_poster_cover/ipst/public/p494268647.jpg
         * large : https://img1.doubanio.com/view/movie_poster_cover/lpst/public/p494268647.jpg
         * medium : https://img1.doubanio.com/view/movie_poster_cover/spst/public/p494268647.jpg
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
         * alt : https://movie.douban.com/celebrity/1054395/
         * avatars : {"small":"https://img1.doubanio.com/img/celebrity/small/51597.jpg","large":"https://img1.doubanio.com/img/celebrity/large/51597.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/51597.jpg"}
         * name : 伊利亚·伍德
         * id : 1054395
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
             * small : https://img1.doubanio.com/img/celebrity/small/51597.jpg
             * large : https://img1.doubanio.com/img/celebrity/large/51597.jpg
             * medium : https://img1.doubanio.com/img/celebrity/medium/51597.jpg
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
         * alt : https://movie.douban.com/celebrity/1276787/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/1351678808.44.jpg","large":"https://img3.doubanio.com/img/celebrity/large/1351678808.44.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/1351678808.44.jpg"}
         * name : 申·阿克
         * id : 1276787
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
             * small : https://img3.doubanio.com/img/celebrity/small/1351678808.44.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/1351678808.44.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/1351678808.44.jpg
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
