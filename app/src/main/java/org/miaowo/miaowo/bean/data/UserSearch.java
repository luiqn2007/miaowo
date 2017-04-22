package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 搜索用户
 * Created by luqin on 17-3-18.
 */

public class UserSearch implements Parcelable {

    /**
     * matchCount : 1
     * pageCount : 1
     * timing : 0.95
     * users : [{"username":"啊辄不吃鱼","userslug":"啊辄不吃鱼","joindate":1485661213692,"lastonline":1485661279651,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":103,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"啊","icon:bgColor":"#e65100","joindateISO":"2017-01-29T03:40:13.692Z","lastonlineISO":"2017-01-29T03:41:19.651Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"}]
     * search_query : 啊
     * time : 0.95
     * categories : [{"value":"all","text":"[[unread:all_categories]]"},{"value":"watched","text":"[[category:watched-categories]]"},{"cid":7,"name":"每日问题","description":"","descriptionParsed":"","icon":"fa-calendar-check-o","bgColor":"#e95c5a","color":"#fff","slug":"7/每日问题","parentCid":0,"topic_count":0,"post_count":0,"disabled":false,"order":1,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":0,"totalTopicCount":0,"unread-class":"","children":[],"tagWhitelist":[],"value":7,"text":"每日问题"},{"cid":1,"name":"公告","description":"","descriptionParsed":"","icon":"fa-bullhorn","bgColor":"#fda34b","color":"#fff","slug":"1/announcements","parentCid":0,"topic_count":2,"post_count":5,"disabled":false,"order":2,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":5,"totalTopicCount":2,"unread-class":"","children":[],"tagWhitelist":[],"value":1,"text":"公告"},{"cid":5,"name":"提问","description":"","descriptionParsed":"","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0,"topic_count":69,"post_count":472,"disabled":false,"order":5,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":472,"totalTopicCount":69,"unread-class":"","children":[],"tagWhitelist":[],"value":5,"text":"提问"},{"cid":6,"name":"灌水","description":"","descriptionParsed":"","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0,"topic_count":123,"post_count":682,"disabled":false,"order":6,"link":"","numRecentReplies":1,"class":"col-md-3 col-xs-6","imageClass":"cover","undefined":"on","totalPostCount":682,"totalTopicCount":123,"unread-class":"","children":[],"tagWhitelist":[],"value":6,"text":"灌水"}]
     * categoriesCount : 4
     * pagination : {"prev":{"page":1,"active":false},"next":{"page":1,"active":false},"rel":[],"pages":[],"currentPage":1,"pageCount":1}
     * showAsPosts : true
     * showAsTopics : false
     * title : [[global:header.search]]
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[global:search]]"}]
     * expandSearch : false
     * loggedIn : true
     * relative_path :
     * template : {"name":"search","search":true}
     * url : /search
     * bodyClass : page-search
     */

    private int matchCount;
    private int pageCount;
    private String timing;
    private String search_query;
    private String time;
    private int categoriesCount;
    private Pagination pagination;
    private boolean showAsPosts;
    private boolean showAsTopics;
    private String title;
    private boolean expandSearch;
    private boolean loggedIn;
    private String relative_path;
    private String url;
    private String bodyClass;
    private List<User> users;
    private List<Category> categories;

    protected UserSearch(Parcel in) {
        matchCount = in.readInt();
        pageCount = in.readInt();
        timing = in.readString();
        search_query = in.readString();
        time = in.readString();
        categoriesCount = in.readInt();
        pagination = in.readParcelable(Pagination.class.getClassLoader());
        showAsPosts = in.readByte() != 0;
        showAsTopics = in.readByte() != 0;
        title = in.readString();
        expandSearch = in.readByte() != 0;
        loggedIn = in.readByte() != 0;
        relative_path = in.readString();
        url = in.readString();
        bodyClass = in.readString();
        users = in.createTypedArrayList(User.CREATOR);
        categories = in.createTypedArrayList(Category.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(matchCount);
        dest.writeInt(pageCount);
        dest.writeString(timing);
        dest.writeString(search_query);
        dest.writeString(time);
        dest.writeInt(categoriesCount);
        dest.writeParcelable(pagination, flags);
        dest.writeByte((byte) (showAsPosts ? 1 : 0));
        dest.writeByte((byte) (showAsTopics ? 1 : 0));
        dest.writeString(title);
        dest.writeByte((byte) (expandSearch ? 1 : 0));
        dest.writeByte((byte) (loggedIn ? 1 : 0));
        dest.writeString(relative_path);
        dest.writeString(url);
        dest.writeString(bodyClass);
        dest.writeTypedList(users);
        dest.writeTypedList(categories);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserSearch> CREATOR = new Creator<UserSearch>() {
        @Override
        public UserSearch createFromParcel(Parcel in) {
            return new UserSearch(in);
        }

        @Override
        public UserSearch[] newArray(int size) {
            return new UserSearch[size];
        }
    };

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getSearch_query() {
        return search_query;
    }

    public void setSearch_query(String search_query) {
        this.search_query = search_query;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCategoriesCount() {
        return categoriesCount;
    }

    public void setCategoriesCount(int categoriesCount) {
        this.categoriesCount = categoriesCount;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public boolean isShowAsPosts() {
        return showAsPosts;
    }

    public void setShowAsPosts(boolean showAsPosts) {
        this.showAsPosts = showAsPosts;
    }

    public boolean isShowAsTopics() {
        return showAsTopics;
    }

    public void setShowAsTopics(boolean showAsTopics) {
        this.showAsTopics = showAsTopics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpandSearch() {
        return expandSearch;
    }

    public void setExpandSearch(boolean expandSearch) {
        this.expandSearch = expandSearch;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
