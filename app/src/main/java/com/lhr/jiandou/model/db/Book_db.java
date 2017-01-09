package com.lhr.jiandou.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by ChinaLHR on 2017/1/8.
 * Email:13435500980@163.com
 */
@Entity
public class Book_db {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String book_id;


    @NotNull
    private String title;
    @NotNull
    private String imgurl;

    private float rating;

    private String author;

    private String publisher;

    private String time;

    @Generated(hash = 1026737697)
    public Book_db(Long id, @NotNull String book_id, @NotNull String title,
            @NotNull String imgurl, float rating, String author, String publisher,
            String time) {
        this.id = id;
        this.book_id = book_id;
        this.title = title;
        this.imgurl = imgurl;
        this.rating = rating;
        this.author = author;
        this.publisher = publisher;
        this.time = time;
    }

    @Generated(hash = 995350502)
    public Book_db() {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

}
