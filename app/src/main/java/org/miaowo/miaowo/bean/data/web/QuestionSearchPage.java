package org.miaowo.miaowo.bean.data.web;

import com.google.gson.annotations.SerializedName;

import org.miaowo.miaowo.bean.data.User;

import java.util.List;

/**
 * 搜索帖子和主题
 * Created by luqin on 17-3-18.
 */

public class QuestionSearchPage {

    /**
     * posts : [{"pid":575,"uid":18,"tid":111,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/65\">@不必再说<\/a> 啊~啊~啊~一脸别扭傲娇推开我的小不什么的我也稀饭（抖M脸淫笑）<\/p>\n","timestamp":1485872640802,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"条狗","userslug":"条狗","picture":"/uploads/files/1482846081778jpeg_20161227_205350_-202324715.jpg","uid":18,"icon:text":"条","icon:bgColor":"#3f51b5"},"topic":{"tid":111,"uid":65,"cid":"6","mainPid":527,"title":"报道！","slug":"111/报道","postcount":15,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-01-31T14:24:00.802Z"},{"pid":728,"uid":99,"tid":135,"content":"<p>啊\u2026\u2026好期待啊！！希望做出一个一样温暖的喵窝！！<\/p>\n","timestamp":1486138179739,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"九尾yueye","userslug":"九尾yueye","picture":"/uploads/files/148559721400460856166_p0_master1200.jpg","uid":99,"icon:text":"九","icon:bgColor":"#e65100"},"topic":{"tid":135,"uid":32,"cid":"5","mainPid":721,"title":"会有一个新喵窝app吗？","slug":"135/会有一个新喵窝app吗","postcount":3,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-02-03T16:09:39.739Z"},{"pid":330,"uid":74,"tid":80,"content":"<p>\u2014\u2014呐我<strong>烟花<\/strong>啊(๑\u2022ั็ω\u2022็ั๑)<\/p>\n","timestamp":1485534712150,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"哈那比酱","userslug":"哈那比酱","picture":"","uid":74,"icon:text":"哈","icon:bgColor":"#3f51b5"},"topic":{"tid":80,"uid":74,"cid":"6","mainPid":330,"title":"今天熬夜2333( \u2022̀∀\u2022́ )","slug":"80/今天熬夜2333","postcount":6,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":true,"votes":0,"timestampISO":"2017-01-27T16:31:52.150Z"},{"pid":770,"uid":1,"tid":139,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/7\">@么么么喵<\/a> 我上了 CLOUDFLARE 的 CDN 啊\u2026\u2026接下来打算折腾一下香港的\u2026<\/p>\n","timestamp":1486255662954,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"Systemd","userslug":"systemd","picture":"","uid":1,"icon:text":"S","icon:bgColor":"#673ab7"},"topic":{"tid":139,"uid":7,"cid":"5","mainPid":760,"title":"喵窝的服务器在海外吗？","slug":"139/喵窝的服务器在海外吗","postcount":5,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-02-05T00:47:42.954Z"},{"pid":165,"uid":56,"tid":33,"content":"<p>啊 |･ω･｀)！这里是豆芽，又来猫窝辣~<br />\n唔啊，，有点蠢啊。<br />\n猫窝怎么用啊？<\/p>\n","timestamp":1482943365660,"deleted":false,"upvotes":1,"downvotes":0,"user":{"username":"豆芽昂","userslug":"豆芽昂","picture":"","uid":56,"icon:text":"豆","icon:bgColor":"#607d8b"},"topic":{"tid":33,"uid":56,"cid":"5","mainPid":165,"title":"蠢】豆芽参上","slug":"33/蠢-豆芽参上","postcount":3,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":true,"votes":1,"timestampISO":"2016-12-28T16:42:45.660Z"},{"pid":402,"uid":18,"tid":77,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/94\">@图图图<\/a> 啊~！被图亲亲了~嘿嘿嘿（娇羞笑）\u2044(\u2044 \u2044\u2022\u2044ω\u2044\u2022\u2044 \u2044)\u2044（撒娇蹭蹭图）嗯！我们很多人还在哟！幸好最后一刻图你回来了ww新的一年也请多多指教啦=3=<\/p>\n","timestamp":1485588816309,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"条狗","userslug":"条狗","picture":"/uploads/files/1482846081778jpeg_20161227_205350_-202324715.jpg","uid":18,"icon:text":"条","icon:bgColor":"#3f51b5"},"topic":{"tid":77,"uid":94,"cid":"5","mainPid":312,"title":"这里图，恭祝大家新年快乐，鸡年大吉","slug":"77/这里图-恭祝大家新年快乐-鸡年大吉","postcount":9,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-01-28T07:33:36.309Z"},{"pid":1001,"uid":18,"tid":180,"content":"<p>@小贼不乖不多捏， 就写过五封而己❣❣ꉂ ૡ(・ꈊ・า˒˒)<br />\n都是心情好到飞起嗨到爆表的时候写出来的hhhhhhhhh<br />\n内容挺羞耻play的说ww<br />\n啊！不过经你说起这个话题我在想平时悠哉悠哉的时候也可以写一封耶挺有意思的w(○-艸･)*:ﾟ･☆<\/p>\n","timestamp":1488106660889,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"条狗","userslug":"条狗","picture":"/uploads/files/1482846081778jpeg_20161227_205350_-202324715.jpg","uid":18,"icon:text":"条","icon:bgColor":"#3f51b5"},"topic":{"tid":180,"uid":11,"cid":"6","mainPid":988,"title":"你写过情书吗","slug":"180/你写过情书吗","postcount":15,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-02-26T10:57:40.889Z"},{"pid":874,"uid":11,"tid":157,"content":"<p>哈哈小系统好可爱<br />\n嗯很多都没有其实_(:з」∠)<em>来自早已忘记地理的文科生<br />\n东北觉得只有黑龙江 |<\/em>・)<br />\n西北觉得只有新疆和西藏 |<em>・)<br />\n西南觉得只有四川重庆云南，再加个海南（额海南算西南么 (ー`´ー)）<br />\n东南就福建浙江和广东，啊还有台湾ԅ(¯﹃¯ԅ)<br />\n至于其他就，啊武汉啊西安啊上海啊北京按城市来了<\/em>(:з」∠)_<br />\n武汉是哪个省的？  啊？湖南还湖北？∠( ᐛ 」∠)_<\/p>\n","timestamp":1486874377584,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"小贼不乖","userslug":"小贼不乖","picture":"/uploads/files/1483678110140jpeg_20160930_173917_-1157967832.jpg","uid":11,"icon:text":"小","icon:bgColor":"#33691e"},"topic":{"tid":157,"uid":38,"cid":"6","mainPid":861,"title":"在你的印象里，中国34和省级行政区划单位，最没有存在感的是哪个？我觉得是青海呢，想到青海，就想到格尔木，青海湖，然后就没有了\u2026\u2026","slug":"157/在你的印象里-中国34和省级行政区划单位-最没有存在感的是哪个-我觉得是青海呢-想到青海-就想到格尔木-青海湖-然后就没有了","postcount":5,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-02-12T04:39:37.584Z"}]
     * matchCount : 8
     * pageCount : 1
     * search_query : 啊
     * time : 0.03
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
    private List<PostsBean> posts;
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

    public List<PostsBean> getPosts() {
        return posts;
    }

    public void setPosts(List<PostsBean> posts) {
        this.posts = posts;
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

    public static class PostsBean {
        /**
         * pid : 575
         * uid : 18
         * tid : 111
         * content : <p><a class="plugin-mentions-a" href="https://miaowo.org/uid/65">@不必再说</a> 啊~啊~啊~一脸别扭傲娇推开我的小不什么的我也稀饭（抖M脸淫笑）</p>

         * timestamp : 1485872640802
         * deleted : false
         * upvotes : 0
         * downvotes : 0
         * user : {"username":"条狗","userslug":"条狗","picture":"/uploads/files/1482846081778jpeg_20161227_205350_-202324715.jpg","uid":18,"icon:text":"条","icon:bgColor":"#3f51b5"}
         * topic : {"tid":111,"uid":65,"cid":"6","mainPid":527,"title":"报道！","slug":"111/报道","postcount":15,"deleted":false}
         * category : {"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0}
         * isMainPost : false
         * votes : 0
         * timestampISO : 2017-01-31T14:24:00.802Z
         */

        private int pid;
        private int uid;
        private int tid;
        private String content;
        private long timestamp;
        private boolean deleted;
        private int upvotes;
        private int downvotes;
        private UserBean user;
        private TopicBean topic;
        private CategoryBean category;
        private boolean isMainPost;
        private int votes;
        private String timestampISO;

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

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public int getUpvotes() {
            return upvotes;
        }

        public void setUpvotes(int upvotes) {
            this.upvotes = upvotes;
        }

        public int getDownvotes() {
            return downvotes;
        }

        public void setDownvotes(int downvotes) {
            this.downvotes = downvotes;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public TopicBean getTopic() {
            return topic;
        }

        public void setTopic(TopicBean topic) {
            this.topic = topic;
        }

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public boolean isIsMainPost() {
            return isMainPost;
        }

        public void setIsMainPost(boolean isMainPost) {
            this.isMainPost = isMainPost;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }

        public String getTimestampISO() {
            return timestampISO;
        }

        public void setTimestampISO(String timestampISO) {
            this.timestampISO = timestampISO;
        }

        public static class UserBean implements User {
            /**
             * username : 条狗
             * userslug : 条狗
             * picture : /uploads/files/1482846081778jpeg_20161227_205350_-202324715.jpg
             * uid : 18
             * icon:text : 条
             * icon:bgColor : #3f51b5
             */

            private String username;
            private String userslug;
            private String picture;
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
                return null;
            }

            public void setUserslug(String userslug) {
                this.userslug = userslug;
            }

            public String getPicture() {
                return picture;
            }

            @Override
            public int getReputation() {
                return 0;
            }

            @Override
            public String getStatus() {
                return null;
            }

            public void setPicture(String picture) {
                this.picture = picture;
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
                return null;
            }

            @Override
            public int getPostcount() {
                return 0;
            }

            public void setIconBgColor(String iconBgColor) {
                this.iconBgColor = iconBgColor;
            }
        }

        public static class TopicBean {
            /**
             * tid : 111
             * uid : 65
             * cid : 6
             * mainPid : 527
             * title : 报道！
             * slug : 111/报道
             * postcount : 15
             * deleted : false
             */

            private int tid;
            private int uid;
            private String cid;
            private int mainPid;
            private String title;
            private String slug;
            private int postcount;
            private boolean deleted;

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

            public int getMainPid() {
                return mainPid;
            }

            public void setMainPid(int mainPid) {
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

            public int getPostcount() {
                return postcount;
            }

            public void setPostcount(int postcount) {
                this.postcount = postcount;
            }

            public boolean isDeleted() {
                return deleted;
            }

            public void setDeleted(boolean deleted) {
                this.deleted = deleted;
            }
        }

        public static class CategoryBean {
            /**
             * cid : 6
             * name : 灌水
             * icon : fa-comments
             * bgColor : #32c3e3
             * color : #fff
             * slug : 6/灌水
             * parentCid : 0
             */

            private int cid;
            private String name;
            private String icon;
            private String bgColor;
            private String color;
            private String slug;
            private int parentCid;

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

            public int getParentCid() {
                return parentCid;
            }

            public void setParentCid(int parentCid) {
                this.parentCid = parentCid;
            }
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
