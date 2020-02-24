package com.zysoft.tjawshapingapp.view.search;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * miao2012crazy@163.com
 * qq:1773345343
 * tel:15585513651
 * Created by 苗春良 on 2019/10/21.
 */
@Entity
public class SearchBean{
    @Id(autoincrement = true)
    private Long id;
    private String serarchValue;


    @Generated(hash = 229469274)
    public SearchBean(Long id, String serarchValue) {
        this.id = id;
        this.serarchValue = serarchValue;
    }

    @Generated(hash = 562045751)
    public SearchBean() {
    }

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerarchValue() {
        return serarchValue;
    }

    public void setSerarchValue(String serarchValue) {
        this.serarchValue = serarchValue;
    }
}
