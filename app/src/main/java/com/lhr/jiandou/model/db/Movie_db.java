package com.lhr.jiandou.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by ChinaLHR on 2017/1/8.
 * Email:13435500980@163.com
 */
@Entity
public class Movie_db {
    @Id
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String imgurl;

    private float rating;

    private String genres;

    private String year;

    private String time;

    @Generated(hash = 1898076406)
    public Movie_db(Long id, @NotNull String title, @NotNull String imgurl,
            float rating, String genres, String year, String time) {
        this.id = id;
        this.title = title;
        this.imgurl = imgurl;
        this.rating = rating;
        this.genres = genres;
        this.year = year;
        this.time = time;
    }

    @Generated(hash = 1012879875)
    public Movie_db() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
