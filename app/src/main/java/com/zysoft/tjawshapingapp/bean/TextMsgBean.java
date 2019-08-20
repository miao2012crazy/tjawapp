package com.zysoft.tjawshapingapp.bean;

/**
 * Created by mr.miao on 2019/8/17.
 */

public class TextMsgBean {


    /**
     * text : 你好 ，请问你需要特殊服务么

     * extras : {}
     */

    private String text;
    private ExtrasBean extras;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ExtrasBean getExtras() {
        return extras;
    }

    public void setExtras(ExtrasBean extras) {
        this.extras = extras;
    }

    public static class ExtrasBean {
    }
}
