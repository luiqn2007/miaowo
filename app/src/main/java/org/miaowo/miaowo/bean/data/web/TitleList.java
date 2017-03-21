package org.miaowo.miaowo.bean.data.web;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 问题列表页面
 * Created by luqin on 17-3-15.
 */

public class TitleList {

    /**
     * cid : 1
     * name : 公告
     * description :
     * descriptionParsed :
     * icon : fa-bullhorn
     * bgColor : #fda34b
     * color : #fff
     * slug : 1/announcements
     * parentCid : 0
     * topic_count : 2
     * post_count : 5
     * disabled : false
     * order : 2
     * link :
     * numRecentReplies : 1
     * class : col-md-3 col-xs-6
     * imageClass : cover
     * undefined : on
     * totalPostCount : 5
     * totalTopicCount : 2
     * unread-class :
     * children : []
     * tagWhitelist : []
     * titles : [{"tid":187,"uid":7,"cid":"1","mainPid":"1041","title":"APP制作进度","slug":"187/app制作进度","timestamp":1488525534569,"lastposttime":1488896525661,"postcount":4,"viewcount":65,"locked":false,"deleted":false,"pinned":false,"teaserPid":"1101","thumb":"","titleRaw":"APP制作进度","timestampISO":"2017-03-03T07:18:54.569Z","lastposttimeISO":"2017-03-07T14:22:05.661Z","category":{"cid":1,"name":"公告","icon":"fa-bullhorn","bgColor":"#fda34b","color":"#fff","slug":"1/announcements","disabled":false,"image":null},"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","fullname":"么么么喵","signature":"","reputation":1,"postcount":113,"banned":0,"status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"teaser":{"pid":1101,"uid":91,"tid":187,"content":"<p>加油↖(^ω^)↗<\/p>\n","timestamp":1488896525661,"user":{"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","uid":91,"icon:text":"吃","icon:bgColor":"#2196f3"},"timestampISO":"2017-03-07T14:22:05.661Z","index":4},"tags":[],"isOwner":true,"ignored":false,"unread":false,"bookmark":null,"unreplied":false,"icons":[],"index":0},{"tid":167,"uid":1,"cid":"1","mainPid":904,"title":"今天中午北京时间 12:00 左右需要维护一下服务器，可能会暂时无法使用～","slug":"167/今天中午北京时间-12-00-左右需要维护一下服务器-可能会暂时无法使用","timestamp":1487288476092,"lastposttime":1487288476092,"postcount":1,"viewcount":9,"locked":false,"deleted":false,"pinned":false,"titleRaw":"今天中午北京时间 12:00 左右需要维护一下服务器，可能会暂时无法使用～","timestampISO":"2017-02-16T23:41:16.092Z","lastposttimeISO":"2017-02-16T23:41:16.092Z","category":{"cid":1,"name":"公告","icon":"fa-bullhorn","bgColor":"#fda34b","color":"#fff","slug":"1/announcements","disabled":false,"image":null},"user":{"username":"Systemd","userslug":"systemd","picture":"","fullname":"","signature":"","reputation":-1,"postcount":177,"banned":0,"status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"tags":[],"isOwner":false,"ignored":false,"unread":false,"bookmark":null,"unreplied":true,"icons":[],"index":1}]
     * nextStart : 20
     * isIgnored : false
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"公告","url":"/category/1/announcements"}]
     * privileges : {"titles:create":true,"titles:read":true,"read":true,"cid":"1","uid":7,"editable":true,"view_deleted":true,"isAdminOrMod":true}
     * showSelect : true
     * feeds:disableRSS : true
     * rssFeedUrl : /category/1.rss
     * title : 公告
     * pagination : {"prev":{"page":1,"active":false},"next":{"page":1,"active":false},"rel":[],"pages":[],"currentPage":1,"pageCount":1}
     * loggedIn : true
     * relative_path :
     * template : {"name":"category","category":true}
     * url : /category/1/announcements
     * bodyClass : page-category page-category-1 page-category-announcements
     */

    private int cid;
    private String name;
    private String description;
    private String descriptionParsed;
    private String icon;
    private String bgColor;
    private String color;
    private String slug;
    private int parentCid;
    private int topic_count;
    private int post_count;
    private boolean disabled;
    private int order;
    private String link;
    private int numRecentReplies;
    @SerializedName("class")
    private String classX;
    private String imageClass;
    private String undefined;
    private int totalPostCount;
    private int totalTopicCount;
    @SerializedName("unread-class")
    private String unreadclass;
    private int nextStart;
    private boolean isIgnored;
    private Privilege privileges;
    private boolean showSelect;
    @SerializedName("feeds:disableRSS")
    private boolean feedsDisableRSS;
    private String rssFeedUrl;
    private String title;
    private Pagination pagination;
    private boolean loggedIn;
    private String relative_path;
    private String url;
    private String bodyClass;
    private List<?> children;
    private List<?> tagWhitelist;
    private List<Title> topics;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionParsed() {
        return descriptionParsed;
    }

    public void setDescriptionParsed(String descriptionParsed) {
        this.descriptionParsed = descriptionParsed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getParentCid() {
        return parentCid;
    }

    public void setParentCid(int parentCid) {
        this.parentCid = parentCid;
    }

    public int getTopic_count() {
        return topic_count;
    }

    public void setTopic_count(int topic_count) {
        this.topic_count = topic_count;
    }

    public int getPost_count() {
        return post_count;
    }

    public void setPost_count(int post_count) {
        this.post_count = post_count;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getNumRecentReplies() {
        return numRecentReplies;
    }

    public void setNumRecentReplies(int numRecentReplies) {
        this.numRecentReplies = numRecentReplies;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getImageClass() {
        return imageClass;
    }

    public void setImageClass(String imageClass) {
        this.imageClass = imageClass;
    }

    public String getUndefined() {
        return undefined;
    }

    public void setUndefined(String undefined) {
        this.undefined = undefined;
    }

    public int getTotalPostCount() {
        return totalPostCount;
    }

    public void setTotalPostCount(int totalPostCount) {
        this.totalPostCount = totalPostCount;
    }

    public int getTotalTopicCount() {
        return totalTopicCount;
    }

    public void setTotalTopicCount(int totalTopicCount) {
        this.totalTopicCount = totalTopicCount;
    }

    public String getUnreadclass() {
        return unreadclass;
    }

    public void setUnreadclass(String unreadclass) {
        this.unreadclass = unreadclass;
    }

    public int getNextStart() {
        return nextStart;
    }

    public void setNextStart(int nextStart) {
        this.nextStart = nextStart;
    }

    public boolean isIsIgnored() {
        return isIgnored;
    }

    public void setIsIgnored(boolean isIgnored) {
        this.isIgnored = isIgnored;
    }

    public Privilege getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Privilege privileges) {
        this.privileges = privileges;
    }

    public boolean isShowSelect() {
        return showSelect;
    }

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    public boolean isFeedsDisableRSS() {
        return feedsDisableRSS;
    }

    public void setFeedsDisableRSS(boolean feedsDisableRSS) {
        this.feedsDisableRSS = feedsDisableRSS;
    }

    public String getRssFeedUrl() {
        return rssFeedUrl;
    }

    public void setRssFeedUrl(String rssFeedUrl) {
        this.rssFeedUrl = rssFeedUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
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

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public List<?> getTagWhitelist() {
        return tagWhitelist;
    }

    public void setTagWhitelist(List<?> tagWhitelist) {
        this.tagWhitelist = tagWhitelist;
    }

    public List<Title> getTitles() {
        return topics;
    }

    public void setTitles(List<Title> topics) {
        this.topics = topics;
    }
}
