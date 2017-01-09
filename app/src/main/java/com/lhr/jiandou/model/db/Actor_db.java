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
public class Actor_db {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String actor_id;

    public String getActor_id() {
        return actor_id;
    }

    public void setActor_id(String actor_id) {
        this.actor_id = actor_id;
    }

    @NotNull
    private String title;
    @NotNull
    private String imgurl;

    private String gender;

    private String born_place;

    private String time;

    @Generated(hash = 1006026375)
    public Actor_db(Long id, @NotNull String actor_id, @NotNull String title,
            @NotNull String imgurl, String gender, String born_place, String time) {
        this.id = id;
        this.actor_id = actor_id;
        this.title = title;
        this.imgurl = imgurl;
        this.gender = gender;
        this.born_place = born_place;
        this.time = time;
    }

    @Generated(hash = 87567351)
    public Actor_db() {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBorn_place() {
        return born_place;
    }

    public void setBorn_place(String born_place) {
        this.born_place = born_place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
