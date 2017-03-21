package org.miaowo.miaowo.bean.data.web;

import java.util.List;

public class TopicList {

    /**
     * tags : [{"value":"猜猜我是谁","score":2,"color":"","bgColor":""},{"value":"好奇喵app","score":1,"color":"","bgColor":""},{"value":"这是测试","score":0,"color":"","bgColor":""}]
     * nextStart : 100
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[tags:tags]]"}]
     * title : [[pages:tags]]
     * loggedIn : true
     * relative_path :
     * template : {"name":"tags","tags":true}
     * url : /tags
     * bodyClass : page-tags
     */

    private int nextStart;
    private String title;
    private boolean loggedIn;
    private String relative_path;
    private String url;
    private String bodyClass;
    private List<Topic> tags;

    public int getNextStart() {
        return nextStart;
    }

    public void setNextStart(int nextStart) {
        this.nextStart = nextStart;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getRelative_path() {
        return relative_path;
    }

    public void setRelative_path(String relative_path) {
        this.relative_path = relative_path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBodyClass() {
        return bodyClass;
    }

    public void setBodyClass(String bodyClass) {
        this.bodyClass = bodyClass;
    }

    public List<Topic> getTags() {
        return tags;
    }

    public void setTags(List<Topic> tags) {
        this.tags = tags;
    }
}
