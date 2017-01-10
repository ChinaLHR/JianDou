package com.lhr.jiandou.model.bean;

/**
 * Created by ChinaLHR on 2017/1/10.
 * Email:13435500980@163.com
 */

public class SpinnerBean {

    private String name;
    private int id;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpinnerBean(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
