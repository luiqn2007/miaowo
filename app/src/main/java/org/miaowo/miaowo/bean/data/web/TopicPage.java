package org.miaowo.miaowo.bean.data.web;

import com.google.gson.annotations.SerializedName;

import org.miaowo.miaowo.bean.data.User;

import java.util.List;

/**
 * 问题列表页面
 * Created by luqin on 17-3-15.
 */

public class TopicPage {

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
     * topics : [{"tid":187,"uid":7,"cid":"1","mainPid":"1041","title":"APP制作进度","slug":"187/app制作进度","timestamp":1488525534569,"lastposttime":1488896525661,"postcount":4,"viewcount":65,"locked":false,"deleted":false,"pinned":false,"teaserPid":"1101","thumb":"","titleRaw":"APP制作进度","timestampISO":"2017-03-03T07:18:54.569Z","lastposttimeISO":"2017-03-07T14:22:05.661Z","category":{"cid":1,"name":"公告","icon":"fa-bullhorn","bgColor":"#fda34b","color":"#fff","slug":"1/announcements","disabled":false,"image":null},"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","fullname":"么么么喵","signature":"","reputation":1,"postcount":113,"banned":0,"status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"teaser":{"pid":1101,"uid":91,"tid":187,"content":"<p>加油↖(^ω^)↗<\/p>\n","timestamp":1488896525661,"user":{"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","uid":91,"icon:text":"吃","icon:bgColor":"#2196f3"},"timestampISO":"2017-03-07T14:22:05.661Z","index":4},"tags":[],"isOwner":true,"ignored":false,"unread":false,"bookmark":null,"unreplied":false,"icons":[],"index":0},{"tid":167,"uid":1,"cid":"1","mainPid":904,"title":"今天中午北京时间 12:00 左右需要维护一下服务器，可能会暂时无法使用～","slug":"167/今天中午北京时间-12-00-左右需要维护一下服务器-可能会暂时无法使用","timestamp":1487288476092,"lastposttime":1487288476092,"postcount":1,"viewcount":9,"locked":false,"deleted":false,"pinned":false,"titleRaw":"今天中午北京时间 12:00 左右需要维护一下服务器，可能会暂时无法使用～","timestampISO":"2017-02-16T23:41:16.092Z","lastposttimeISO":"2017-02-16T23:41:16.092Z","category":{"cid":1,"name":"公告","icon":"fa-bullhorn","bgColor":"#fda34b","color":"#fff","slug":"1/announcements","disabled":false,"image":null},"user":{"username":"Systemd","userslug":"systemd","picture":"","fullname":"","signature":"","reputation":-1,"postcount":177,"banned":0,"status":"offline","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"tags":[],"isOwner":false,"ignored":false,"unread":false,"bookmark":null,"unreplied":true,"icons":[],"index":1}]
     * nextStart : 20
     * isIgnored : false
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"公告","url":"/category/1/announcements"}]
     * privileges : {"topics:create":true,"topics:read":true,"read":true,"cid":"1","uid":7,"editable":true,"view_deleted":true,"isAdminOrMod":true}
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
    private PrivilegesBean privileges;
    private boolean showSelect;
    @SerializedName("feeds:disableRSS")
    private boolean _$FeedsDisableRSS89; // FIXME check this code
    private String rssFeedUrl;
    private String title;
    private PaginationBean pagination;
    private boolean loggedIn;
    private String relative_path;
    private TemplateBean template;
    private String url;
    private String bodyClass;
    private List<?> children;
    private List<?> tagWhitelist;
    private List<TopicsBean> topics;
    private List<BreadcrumbsBean> breadcrumbs;

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

    public PrivilegesBean getPrivileges() {
        return privileges;
    }

    public void setPrivileges(PrivilegesBean privileges) {
        this.privileges = privileges;
    }

    public boolean isShowSelect() {
        return showSelect;
    }

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    public boolean is_$FeedsDisableRSS89() {
        return _$FeedsDisableRSS89;
    }

    public void set_$FeedsDisableRSS89(boolean _$FeedsDisableRSS89) {
        this._$FeedsDisableRSS89 = _$FeedsDisableRSS89;
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

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
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

    public List<TopicsBean> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicsBean> topics) {
        this.topics = topics;
    }

    public List<BreadcrumbsBean> getBreadcrumbs() {
        return breadcrumbs;
    }

    public void setBreadcrumbs(List<BreadcrumbsBean> breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public static class PrivilegesBean {
        @SerializedName("topics:create")
        private boolean _$TopicsCreate302; // FIXME check this code
        @SerializedName("topics:read")
        private boolean _$TopicsRead63; // FIXME check this code
        private boolean read;
        private String cid;
        private int uid;
        private boolean editable;
        private boolean view_deleted;
        private boolean isAdminOrMod;

        public boolean is_$TopicsCreate302() {
            return _$TopicsCreate302;
        }

        public void set_$TopicsCreate302(boolean _$TopicsCreate302) {
            this._$TopicsCreate302 = _$TopicsCreate302;
        }

        public boolean is_$TopicsRead63() {
            return _$TopicsRead63;
        }

        public void set_$TopicsRead63(boolean _$TopicsRead63) {
            this._$TopicsRead63 = _$TopicsRead63;
        }

        public boolean isRead() {
            return read;
        }

        public void setRead(boolean read) {
            this.read = read;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

        public boolean isView_deleted() {
            return view_deleted;
        }

        public void setView_deleted(boolean view_deleted) {
            this.view_deleted = view_deleted;
        }

        public boolean isIsAdminOrMod() {
            return isAdminOrMod;
        }

        public void setIsAdminOrMod(boolean isAdminOrMod) {
            this.isAdminOrMod = isAdminOrMod;
        }
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
         * name : category
         * category : true
         */

        private String name;
        private boolean category;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isCategory() {
            return category;
        }

        public void setCategory(boolean category) {
            this.category = category;
        }
    }

    public static class TopicsBean {
        /**
         * tid : 187
         * uid : 7
         * cid : 1
         * mainPid : 1041
         * title : APP制作进度
         * slug : 187/app制作进度
         * timestamp : 1488525534569
         * lastposttime : 1488896525661
         * postcount : 4
         * viewcount : 65
         * locked : false
         * deleted : false
         * pinned : false
         * teaserPid : 1101
         * thumb :
         * titleRaw : APP制作进度
         * timestampISO : 2017-03-03T07:18:54.569Z
         * lastposttimeISO : 2017-03-07T14:22:05.661Z
         * category : {"cid":1,"name":"公告","icon":"fa-bullhorn","bgColor":"#fda34b","color":"#fff","slug":"1/announcements","disabled":false,"image":null}
         * user : {"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","fullname":"么么么喵","signature":"","reputation":1,"postcount":113,"banned":0,"status":"online","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"}
         * teaser : {"pid":1101,"uid":91,"tid":187,"content":"<p>加油↖(^ω^)↗<\/p>\n","timestamp":1488896525661,"user":{"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","uid":91,"icon:text":"吃","icon:bgColor":"#2196f3"},"timestampISO":"2017-03-07T14:22:05.661Z","index":4}
         * tags : []
         * isOwner : true
         * ignored : false
         * unread : false
         * bookmark : null
         * unreplied : false
         * icons : []
         * index : 0
         */

        private int tid;
        private int uid;
        private String cid;
        private String mainPid;
        private String title;
        private String slug;
        private long timestamp;
        private long lastposttime;
        private int postcount;
        private int viewcount;
        private boolean locked;
        private boolean deleted;
        private boolean pinned;
        private String teaserPid;
        private String thumb;
        private String titleRaw;
        private String timestampISO;
        private String lastposttimeISO;
        private CategoryBean category;
        private UserBean user;
        private TeaserBean teaser;
        private boolean isOwner;
        private boolean ignored;
        private boolean unread;
        private Object bookmark;
        private boolean unreplied;
        private int index;
        private List<?> tags;
        private List<?> icons;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getMainPid() {
            return mainPid;
        }

        public void setMainPid(String mainPid) {
            this.mainPid = mainPid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public long getLastposttime() {
            return lastposttime;
        }

        public void setLastposttime(long lastposttime) {
            this.lastposttime = lastposttime;
        }

        public int getPostcount() {
            return postcount;
        }

        public void setPostcount(int postcount) {
            this.postcount = postcount;
        }

        public int getViewcount() {
            return viewcount;
        }

        public void setViewcount(int viewcount) {
            this.viewcount = viewcount;
        }

        public boolean isLocked() {
            return locked;
        }

        public void setLocked(boolean locked) {
            this.locked = locked;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public boolean isPinned() {
            return pinned;
        }

        public void setPinned(boolean pinned) {
            this.pinned = pinned;
        }

        public String getTeaserPid() {
            return teaserPid;
        }

        public void setTeaserPid(String teaserPid) {
            this.teaserPid = teaserPid;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getTitleRaw() {
            return titleRaw;
        }

        public void setTitleRaw(String titleRaw) {
            this.titleRaw = titleRaw;
        }

        public String getTimestampISO() {
            return timestampISO;
        }

        public void setTimestampISO(String timestampISO) {
            this.timestampISO = timestampISO;
        }

        public String getLastposttimeISO() {
            return lastposttimeISO;
        }

        public void setLastposttimeISO(String lastposttimeISO) {
            this.lastposttimeISO = lastposttimeISO;
        }

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public TeaserBean getTeaser() {
            return teaser;
        }

        public void setTeaser(TeaserBean teaser) {
            this.teaser = teaser;
        }

        public boolean isIsOwner() {
            return isOwner;
        }

        public void setIsOwner(boolean isOwner) {
            this.isOwner = isOwner;
        }

        public boolean isIgnored() {
            return ignored;
        }

        public void setIgnored(boolean ignored) {
            this.ignored = ignored;
        }

        public boolean isUnread() {
            return unread;
        }

        public void setUnread(boolean unread) {
            this.unread = unread;
        }

        public Object getBookmark() {
            return bookmark;
        }

        public void setBookmark(Object bookmark) {
            this.bookmark = bookmark;
        }

        public boolean isUnreplied() {
            return unreplied;
        }

        public void setUnreplied(boolean unreplied) {
            this.unreplied = unreplied;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public List<?> getTags() {
            return tags;
        }

        public void setTags(List<?> tags) {
            this.tags = tags;
        }

        public List<?> getIcons() {
            return icons;
        }

        public void setIcons(List<?> icons) {
            this.icons = icons;
        }

        public static class CategoryBean {
            /**
             * cid : 1
             * name : 公告
             * icon : fa-bullhorn
             * bgColor : #fda34b
             * color : #fff
             * slug : 1/announcements
             * disabled : false
             * image : null
             */

            private int cid;
            private String name;
            private String icon;
            private String bgColor;
            private String color;
            private String slug;
            private boolean disabled;
            private Object image;

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

            public boolean isDisabled() {
                return disabled;
            }

            public void setDisabled(boolean disabled) {
                this.disabled = disabled;
            }

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }
        }

        public static class UserBean implements User {
            /**
             * username : 么么么喵
             * userslug : 么么么喵
             * picture : /uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg
             * fullname : 么么么喵
             * signature :
             * reputation : 1
             * postcount : 113
             * banned : 0
             * status : online
             * uid : 7
             * icon:text : 么
             * icon:bgColor : #1b5e20
             */

            private String username;
            private String userslug;
            private String picture;
            private String fullname;
            private String signature;
            private int reputation;
            private int postcount;
            private int banned;
            private String status;
            private int uid;
            @SerializedName("icon:text")
            private String iconText;
            @SerializedName("icon:bgColor")
            private String iconBgColor;

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
                return "";
            }

            public void setUserslug(String userslug) {
                this.userslug = userslug;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getFullname() {
                return fullname;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
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

            public int getBanned() {
                return banned;
            }

            public void setBanned(int banned) {
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
                return "";
            }

            public void setIconBgColor(String iconBgColor) {
                this.iconBgColor = iconBgColor;
            }
        }

        public static class TeaserBean {
            /**
             * pid : 1101
             * uid : 91
             * tid : 187
             * content : <p>加油↖(^ω^)↗</p>

             * timestamp : 1488896525661
             * user : {"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","uid":91,"icon:text":"吃","icon:bgColor":"#2196f3"}
             * timestampISO : 2017-03-07T14:22:05.661Z
             * index : 4
             */

            private int pid;
            private int uid;
            private int tid;
            private String content;
            private long timestamp;
            private UserBeanX user;
            private String timestampISO;
            private int index;

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getTid() {
                return tid;
            }

            public void setTid(int tid) {
                this.tid = tid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(long timestamp) {
                this.timestamp = timestamp;
            }

            public UserBeanX getUser() {
                return user;
            }

            public void setUser(UserBeanX user) {
                this.user = user;
            }

            public String getTimestampISO() {
                return timestampISO;
            }

            public void setTimestampISO(String timestampISO) {
                this.timestampISO = timestampISO;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public static class UserBeanX {
                /**
                 * username : 吃草莓的喵喵
                 * userslug : 吃草莓的喵喵
                 * picture : /uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg
                 * uid : 91
                 * icon:text : 吃
                 * icon:bgColor : #2196f3
                 */

                private String username;
                private String userslug;
                private String picture;
                private int uid;
                @SerializedName("icon:text")
                private String _$IconText48; // FIXME check this code
                @SerializedName("icon:bgColor")
                private String _$IconBgColor25; // FIXME check this code

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getUserslug() {
                    return userslug;
                }

                public void setUserslug(String userslug) {
                    this.userslug = userslug;
                }

                public String getPicture() {
                    return picture;
                }

                public void setPicture(String picture) {
                    this.picture = picture;
                }

                public int getUid() {
                    return uid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public String get_$IconText48() {
                    return _$IconText48;
                }

                public void set_$IconText48(String _$IconText48) {
                    this._$IconText48 = _$IconText48;
                }

                public String get_$IconBgColor25() {
                    return _$IconBgColor25;
                }

                public void set_$IconBgColor25(String _$IconBgColor25) {
                    this._$IconBgColor25 = _$IconBgColor25;
                }
            }
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
