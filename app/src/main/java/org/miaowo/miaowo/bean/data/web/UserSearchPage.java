package org.miaowo.miaowo.bean.data.web;

import com.google.gson.annotations.SerializedName;

import org.miaowo.miaowo.bean.data.User;

import java.util.List;

/**
 * 搜索用户
 * Created by luqin on 17-3-18.
 */

public class UserSearchPage {

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
    private PaginationBean pagination;
    private boolean showAsPosts;
    private boolean showAsTopics;
    private String title;
    private boolean expandSearch;
    private boolean loggedIn;
    private String relative_path;
    private TemplateBean template;
    private String url;
    private String bodyClass;
    private List<UsersBean> users;
    private List<CategoriesBean> categories;
    private List<BreadcrumbsBean> breadcrumbs;

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

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
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

    public TemplateBean getTemplate() {
        return template;
    }

    public void setTemplate(TemplateBean template) {
        this.template = template;
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

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public List<BreadcrumbsBean> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<BreadcrumbsBean> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public static class PaginationBean {
        /**
         * prev : {"page":1,"active":false}
         * next : {"page":1,"active":false}
         * rel : []
         * pages : []
         * currentPage : 1
         * pageCount : 1
         */

        private PrevBean prev;
        private NextBean next;
        private int currentPage;
        private int pageCount;
        private List<?> rel;
        private List<?> pages;

        public PrevBean getPrev() {
            return prev;
        }

        public void setPrev(PrevBean prev) {
            this.prev = prev;
        }

        public NextBean getNext() {
            return next;
        }

        public void setNext(NextBean next) {
            this.next = next;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<?> getRel() {
            return rel;
        }

        public void setRel(List<?> rel) {
            this.rel = rel;
        }

        public List<?> getPages() {
            return pages;
        }

        public void setPages(List<?> pages) {
            this.pages = pages;
        }

        public static class PrevBean {
            /**
             * page : 1
             * active : false
             */

            private int page;
            private boolean active;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }
        }

        public static class NextBean {
            /**
             * page : 1
             * active : false
             */

            private int page;
            private boolean active;

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }

            public boolean isActive() {
                return active;
            }

            public void setActive(boolean active) {
                this.active = active;
            }
        }
    }

    public static class TemplateBean {
        /**
         * name : search
         * search : true
         */

        private String name;
        private boolean search;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSearch() {
            return search;
        }

        public void setSearch(boolean search) {
            this.search = search;
        }
    }

    public static class UsersBean implements User {
        /**
         * username : 啊辄不吃鱼
         * userslug : 啊辄不吃鱼
         * joindate : 1485661213692
         * lastonline : 1485661279651
         * picture :
         * reputation : 0
         * postcount : 0
         * banned : false
         * status : offline
         * uid : 103
         * flags : null
         * banned:expire : null
         * email:confirmed : false
         * icon:text : 啊
         * icon:bgColor : #e65100
         * joindateISO : 2017-01-29T03:40:13.692Z
         * lastonlineISO : 2017-01-29T03:41:19.651Z
         * administrator : false
         * banned_until : 0
         * banned_until_readable : Not Banned
         */

        private String username;
        private String userslug;
        private long joindate;
        private long lastonline;
        private String picture;
        private int reputation;
        private int postcount;
        private boolean banned;
        private String status;
        private int uid;
        private Object flags;
        @SerializedName("banned:expire")
        private Object bannedExpire;
        @SerializedName("email:confirmed")
        private boolean emailConfirmed;
        @SerializedName("icon:text")
        private String iconText;
        @SerializedName("icon:bgColor")
        private String iconBgColor;
        private String joindateISO;
        private String lastonlineISO;
        private boolean administrator;
        private int banned_until;
        private String banned_until_readable;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserslug() {
            return userslug;
        }

        @Override
        public String getEmail() {
            return null;
        }

        public void setUserslug(String userslug) {
            this.userslug = userslug;
        }

        public long getJoindate() {
            return joindate;
        }

        public void setJoindate(long joindate) {
            this.joindate = joindate;
        }

        public long getLastonline() {
            return lastonline;
        }

        public void setLastonline(long lastonline) {
            this.lastonline = lastonline;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getReputation() {
            return reputation;
        }

        public void setReputation(int reputation) {
            this.reputation = reputation;
        }

        public int getPostcount() {
            return postcount;
        }

        public void setPostcount(int postcount) {
            this.postcount = postcount;
        }

        public boolean isBanned() {
            return banned;
        }

        public void setBanned(boolean banned) {
            this.banned = banned;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getUid() {
            return uid;
        }

        @Override
        public boolean emailConfirmed() {
            return false;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public Object getFlags() {
            return flags;
        }

        public void setFlags(Object flags) {
            this.flags = flags;
        }

        public Object getBannedExpire() {
            return bannedExpire;
        }

        public void setBannedExpire(Object bannedExpire) {
            this.bannedExpire = bannedExpire;
        }

        public boolean isEmailConfirmed() {
            return emailConfirmed;
        }

        public void setEmailConfirmed(boolean emailConfirmed) {
            this.emailConfirmed = emailConfirmed;
        }

        public String getIconText() {
            return iconText;
        }

        public void setIconText(String iconText) {
            this.iconText = iconText;
        }

        public String getIconBgColor() {
            return iconBgColor;
        }

        @Override
        public boolean isAdmin() {
            return false;
        }

        @Override
        public String getPassword() {
            return null;
        }

        public void setIconBgColor(String iconBgColor) {
            this.iconBgColor = iconBgColor;
        }

        public String getJoindateISO() {
            return joindateISO;
        }

        public void setJoindateISO(String joindateISO) {
            this.joindateISO = joindateISO;
        }

        public String getLastonlineISO() {
            return lastonlineISO;
        }

        public void setLastonlineISO(String lastonlineISO) {
            this.lastonlineISO = lastonlineISO;
        }

        public boolean isAdministrator() {
            return administrator;
        }

        public void setAdministrator(boolean administrator) {
            this.administrator = administrator;
        }

        public int getBanned_until() {
            return banned_until;
        }

        public void setBanned_until(int banned_until) {
            this.banned_until = banned_until;
        }

        public String getBanned_until_readable() {
            return banned_until_readable;
        }

        public void setBanned_until_readable(String banned_until_readable) {
            this.banned_until_readable = banned_until_readable;
        }
    }

    public static class CategoriesBean {
        /**
         * value : all
         * text : [[unread:all_categories]]
         * cid : 7
         * name : 每日问题
         * description :
         * descriptionParsed :
         * icon : fa-calendar-check-o
         * bgColor : #e95c5a
         * color : #fff
         * slug : 7/每日问题
         * parentCid : 0
         * topic_count : 0
         * post_count : 0
         * disabled : false
         * order : 1
         * link :
         * numRecentReplies : 1
         * class : col-md-3 col-xs-6
         * imageClass : cover
         * undefined : on
         * totalPostCount : 0
         * totalTopicCount : 0
         * unread-class :
         * children : []
         * tagWhitelist : []
         */

        private String value;
        private String text;
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
        private List<?> children;
        private List<?> tagWhitelist;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

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
    }

    public static class BreadcrumbsBean {
        /**
         * text : [[global:home]]
         * url : /
         */

        private String text;
        private String url;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
