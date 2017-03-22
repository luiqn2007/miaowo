package org.miaowo.miaowo.bean.data.web;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 具体用户页面
 * Created by luqin on 17-3-18.
 */

public class User implements Parcelable {

    /**
     * username : 么么么喵
     * userslug : 么么么喵
     * email : 1105188240@qq.com
     * joindate : 1479650239103
     * lastonline : 1489775541488
     * picture : /uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg
     * fullname : 么么么喵
     * location :
     * birthday :
     * website :
     * signature :
     * uploadedpicture : /uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg
     * profileviews : 42
     * postcount : 116
     * topiccount : 10
     * lastposttime : 1489774776511
     * banned : false
     * status : online
     * uid : 7
     * passwordExpiry : 0
     * cover:position : 50.0262% 46.4866%
     * cover:url : /uploads/files/1482860372268profilecover
     * githubid : 18262245
     * followingCount : 3
     * groupTitle :
     * banned:expire : 0
     * followerCount : 4
     * icon:text : 么
     * icon:bgColor : #1b5e20
     * joindateISO : 2016-11-20T13:57:19.103Z
     * lastonlineISO : 2017-03-17T18:32:21.488Z
     * age : 0
     * emailClass :
     * ips : ["117.136.67.26","122.192.20.50","153.36.217.132","122.192.20.52","153.36.215.172"]
     * yourid : 7
     * theirid : 7
     * isTargetAdmin : true
     * isAdmin : true
     * isGlobalModerator : false
     * isModerator : false
     * isAdminOrGlobalModerator : true
     * isAdminOrGlobalModeratorOrModerator : true
     * isSelfOrAdminOrGlobalModerator : true
     * canEdit : true
     * canBan : true
     * canChangePassword : true
     * isSelf : true
     * isFollowing : false
     * showHidden : true
     * groups : []
     * disableSignatures : false
     * reputation:disabled : true
     * downvote:disabled : false
     * email:confirmed : false
     * profile_links : []
     * sso : []
     * websiteLink : http://
     * websiteName :
     * moderationNote :
     * username:disableEdit : false
     * email:disableEdit : false
     * posts : [{"pid":1168,"uid":7,"tid":197,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/1\">@Systemd<\/a><br />\n说实话，我查了查awesome，个人不喜欢这样的。不可重叠，硬伤，alt+tab效率也不差。<\/p>\n<p>暂时不喜换系统，webstorm我现在就装着，不过基本没用过 /(ㄒoㄒ)/~~<\/p>\n<p><a href=\"http://Blog.miaowo.org\" rel=\"nofollow\">Blog.miaowo.org<\/a> 现在还是空着的啊，不过既然计划要把公告去掉的话，我就不在APP里面预留了。<\/p>\n<p>rhel。。。也行，不过都差不多吧。说这些有点远，得先搞完喵窝APP再说。不然一不小心硬盘再挂掉哭都没地方哭去。<\/p>\n<p>装软件很烦吗？我感觉 webapp 依托服务器，万一服务器挂了就完了，不太安全持久，而且外面套一层浏览器的壳也挺难看的，显示效果也不好保证。而且webapp比一般APP多一步打开浏览器的步骤，比较麻烦。想用旧版本也不太可能，离线功能也是个问题。<\/p>\n<p>下一步到底是更进一步继续看 nativeApp 呢，还是看 webApp，有点迷茫了。nativeAPP 再深入就得研究 Linux，想看 root 后的功能，或者图形-&gt;游戏。webAPP的话，低平台依赖是个好处，而且如果解决了离线问题的话还是很不错的，但是好像基于web框架做的APP运行性能不如本地。不管C++还是JS我都是只看过一遍语法，也不存在什么难度不同。愁。不过幸好这个APP够我再写一个月的了，我还有比较充裕的时间考虑。<\/p>\n","timestamp":1489774776511,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":197,"uid":7,"cid":"6","mainPid":1128,"title":"搭好了leanote了","slug":"197/搭好了leanote了","postcount":16,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T18:19:36.511Z"},{"pid":1167,"uid":7,"tid":199,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/1\">@Systemd<\/a> ...好吧<\/p>\n","timestamp":1489755370366,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":199,"uid":1,"cid":"5","mainPid":1145,"title":"给你一台服务器的话你会选什么系统？","slug":"199/给你一台服务器的话你会选什么系统","postcount":10,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T12:56:10.366Z"},{"pid":1165,"uid":7,"tid":199,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/1\">@Systemd<\/a> 为什么？<\/p>\n","timestamp":1489748868748,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":199,"uid":1,"cid":"5","mainPid":1145,"title":"给你一台服务器的话你会选什么系统？","slug":"199/给你一台服务器的话你会选什么系统","postcount":10,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T11:07:48.748Z"},{"pid":1161,"uid":7,"tid":198,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/1\">@Systemd<\/a> 还有管理员？<br />\n神奇的学校<\/p>\n","timestamp":1489730917412,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":198,"uid":11,"cid":"5","mainPid":1141,"title":"你是一个礼貌的孩子吗？","slug":"198/你是一个礼貌的孩子吗","postcount":10,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T06:08:37.412Z"},{"pid":1160,"uid":7,"tid":199,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/1\">@Systemd<\/a> 你最后选的什么系统？<\/p>\n","timestamp":1489730879971,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":199,"uid":1,"cid":"5","mainPid":1145,"title":"给你一台服务器的话你会选什么系统？","slug":"199/给你一台服务器的话你会选什么系统","postcount":10,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T06:07:59.971Z"},{"pid":1157,"uid":7,"tid":199,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/1\">@Systemd<\/a> 在 <a href=\"/post/1151\">给你一台服务器的话你会选什么系统？<\/a> 中说：<\/p>\n<blockquote>\n<p>CloudAtCost<\/p>\n<\/blockquote>\n<p><img src=\"/uploads/files/1489730557665-upload-99d5770d-ad08-4ebd-a09b-83d1513999fb-resized.png\" alt=\"0_1489730559864_upload-99d5770d-ad08-4ebd-a09b-83d1513999fb\" class=\"img-responsive img-markdown\" /><br />\n是这个么。。。现在还有，他是不是一直活动啊。。。<\/p>\n","timestamp":1489730585921,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":199,"uid":1,"cid":"5","mainPid":1145,"title":"给你一台服务器的话你会选什么系统？","slug":"199/给你一台服务器的话你会选什么系统","postcount":10,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T06:03:05.921Z"},{"pid":1156,"uid":7,"tid":198,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/1\">@Systemd<\/a> 你们交作业还要写老师名字啊，可怕<\/p>\n","timestamp":1489730252933,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":198,"uid":11,"cid":"5","mainPid":1141,"title":"你是一个礼貌的孩子吗？","slug":"198/你是一个礼貌的孩子吗","postcount":10,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T05:57:32.933Z"},{"pid":1150,"uid":7,"tid":198,"content":"<p>我是怎么都记不住亲戚的称呼，看着脸认不出人更别谈辈分了啊啊啊啊啊<\/p>\n","timestamp":1489726167264,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":198,"uid":11,"cid":"5","mainPid":1141,"title":"你是一个礼貌的孩子吗？","slug":"198/你是一个礼貌的孩子吗","postcount":10,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T04:49:27.264Z"},{"pid":1149,"uid":7,"tid":197,"content":"<p><a class=\"plugin-mentions-a\" href=\"https://miaowo.org/uid/1\">@Systemd<\/a><br />\n重修网课，学校用的好像是超星的。<\/p>\n<p>至于网站，不管是不太可能了，我直接把gson所有数据格式化成WebBean类，然后创建APP用的AppBean，加上转换接口，用多少取多少吧，就是麻烦点。问题是gson库我也是第一次用，类直接继承过来我怕解析错误。不过和界面结合又得花费不少时间，把现有的界面挖去一部分以后再加上不比这个简单。<\/p>\n<p>系统什么的，不太想换了，最多以后装个centos学学服务器配置，debian的testing版本的激进程度应该也不小（虽然我没用）。但是感觉软件版本对我影响可能并不是太大了，安装系统以来我还从来没有手动更新过呢，据说debian9都出来好一阵了。而且有好多应用都不是用软件源安装的。稳定点就好，别需要的时候不能用了掉链子（就像as。。。更新一次坑一次，我也算服了Google了）。<\/p>\n<p>awesome wm，可以试试。我现在在用gnome3<\/p>\n","timestamp":1489726066388,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":197,"uid":7,"cid":"6","mainPid":1128,"title":"搭好了leanote了","slug":"197/搭好了leanote了","postcount":16,"deleted":false},"category":{"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T04:47:46.388Z"},{"pid":1147,"uid":7,"tid":199,"content":"<p>链接Windows server。。。用Windows的远程桌面就行吧，Linux可能玄乎。<br />\n我会选centos7或者debian8，毕竟前者服务器方面资料比较多，后者我用的时间最长可能更熟悉<br />\n话说，系统菌用的是哪一家的？<\/p>\n","timestamp":1489725192190,"deleted":false,"upvotes":0,"downvotes":0,"user":{"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"},"topic":{"tid":199,"uid":1,"cid":"5","mainPid":1145,"title":"给你一台服务器的话你会选什么系统？","slug":"199/给你一台服务器的话你会选什么系统","postcount":10,"deleted":false},"category":{"cid":5,"name":"提问","icon":"fa-question","bgColor":"#e95c5a","color":"#fff","slug":"5/提问","parentCid":0},"isMainPost":false,"votes":0,"timestampISO":"2017-03-17T04:33:12.190Z"}]
     * hasPrivateChat : 0
     * nextStart : 10
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"么么么喵"}]
     * title : 么么么喵
     * pagination : {"rel":[{"rel":"next","href":"?page=2"}],"pages":[{"page":1,"active":true,"qs":"page=1"},{"page":2,"active":false,"qs":"page=2"},{"page":3,"active":false,"qs":"page=3"},{"page":4,"active":false,"qs":"page=4"},{"page":5,"active":false,"qs":"page=5"},{"separator":true},{"page":11,"active":false,"qs":"page=11"},{"page":12,"active":false,"qs":"page=12"}],"currentPage":1,"pageCount":12,"prev":{"page":1,"active":false,"qs":"page=1"},"next":{"page":2,"active":true,"qs":"page=2"}}
     * loggedIn : true
     * relative_path :
     * template : {"name":"account/profile","account/profile":true}
     * url : /user/%E4%B9%88%E4%B9%88%E4%B9%88%E5%96%B5
     * bodyClass : page-user page-user-么么么喵
     */

    private String username;
    private String userslug;
    private String email;
    private long joindate;
    private long lastonline;
    private String picture;
    private String fullname;
    private String location;
    private String birthday;
    private String website;
    private String signature;
    private String uploadedpicture;
    private int profileviews;
    private int postcount;
    private int topiccount;
    private long lastposttime;
    private int reputation;
    private String status;
    private int uid;
    private int passwordExpiry;
    @SerializedName("cover:position")
    private String coverPosition;
    @SerializedName("cover:url")
    private String coverUrl;
    private String githubid;
    private int followingCount;
    private String groupTitle;
    @SerializedName("banned:expire")
    private int bannedExpire;
    private int followerCount;
    @SerializedName("icon:text")
    private String iconText;
    @SerializedName("icon:bgColor")
    private String iconBgColor;
    private String joindateISO;
    private String lastonlineISO;
    private int age;
    private String emailClass;
    private int yourid;
    private int theirid;
    private boolean isTargetAdmin;
    private boolean isAdmin;
    @SerializedName("isGlobalMod")
    private boolean isGlobalModerator;
    @SerializedName("isMod")
    private boolean isModerator;
    private boolean isAdminOrGlobalModerator;
    private boolean isAdminOrGlobalModeratorOrModerator;
    private boolean isSelfOrAdminOrGlobalModerator;
    private boolean canEdit;
    private boolean canBan;
    private boolean canChangePassword;
    private boolean isSelf;
    private boolean isFollowing;
    private boolean showHidden;
    private boolean disableSignatures;
    @SerializedName("reputation:disabled")
    private boolean reputationDisabled;
    @SerializedName("downvote:disabled")
    private boolean downvoteDisabled;
    @SerializedName("email:confirmed")
    private boolean emailConfirmed;
    private String websiteLink;
    private String websiteName;
    private String moderationNote;
    @SerializedName("username:disableEdit")
    private boolean usernameDisableEdit;
    @SerializedName("email:disableEdit")
    private boolean emailDisableEdit;
    private int hasPrivateChat;
    private int nextStart;
    private String title;
    private String password;
    private Pagination pagination;
    private boolean loggedIn;
    private String relative_path;
    private String url;
    private String bodyClass;
    private List<String> ips;
    private List<?> groups;
    private List<?> profile_links;
    private List<?> sso;
    private List<?> custom_profile_info;
    private List<Post> posts;
    private boolean isEmailConfirmSent;
    private String aboutme;
    private int banned_until;
    private String banned_until_readable;

    protected User(Parcel in) {
        username = in.readString();
        userslug = in.readString();
        email = in.readString();
        joindate = in.readLong();
        lastonline = in.readLong();
        picture = in.readString();
        fullname = in.readString();
        location = in.readString();
        birthday = in.readString();
        website = in.readString();
        signature = in.readString();
        uploadedpicture = in.readString();
        profileviews = in.readInt();
        postcount = in.readInt();
        topiccount = in.readInt();
        lastposttime = in.readLong();
        reputation = in.readInt();
        status = in.readString();
        uid = in.readInt();
        passwordExpiry = in.readInt();
        coverPosition = in.readString();
        coverUrl = in.readString();
        githubid = in.readString();
        followingCount = in.readInt();
        groupTitle = in.readString();
        bannedExpire = in.readInt();
        followerCount = in.readInt();
        iconText = in.readString();
        iconBgColor = in.readString();
        joindateISO = in.readString();
        lastonlineISO = in.readString();
        age = in.readInt();
        emailClass = in.readString();
        yourid = in.readInt();
        theirid = in.readInt();
        isTargetAdmin = in.readByte() != 0;
        isAdmin = in.readByte() != 0;
        isGlobalModerator = in.readByte() != 0;
        isModerator = in.readByte() != 0;
        isAdminOrGlobalModerator = in.readByte() != 0;
        isAdminOrGlobalModeratorOrModerator = in.readByte() != 0;
        isSelfOrAdminOrGlobalModerator = in.readByte() != 0;
        canEdit = in.readByte() != 0;
        canBan = in.readByte() != 0;
        canChangePassword = in.readByte() != 0;
        isSelf = in.readByte() != 0;
        isFollowing = in.readByte() != 0;
        showHidden = in.readByte() != 0;
        disableSignatures = in.readByte() != 0;
        reputationDisabled = in.readByte() != 0;
        downvoteDisabled = in.readByte() != 0;
        emailConfirmed = in.readByte() != 0;
        websiteLink = in.readString();
        websiteName = in.readString();
        moderationNote = in.readString();
        usernameDisableEdit = in.readByte() != 0;
        emailDisableEdit = in.readByte() != 0;
        hasPrivateChat = in.readInt();
        nextStart = in.readInt();
        title = in.readString();
        password = in.readString();
        pagination = in.readParcelable(Pagination.class.getClassLoader());
        loggedIn = in.readByte() != 0;
        relative_path = in.readString();
        url = in.readString();
        bodyClass = in.readString();
        ips = in.createStringArrayList();
        posts = in.createTypedArrayList(Post.CREATOR);
        isEmailConfirmSent = in.readByte() != 0;
        aboutme = in.readString();
        banned_until = in.readInt();
        banned_until_readable = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(userslug);
        dest.writeString(email);
        dest.writeLong(joindate);
        dest.writeLong(lastonline);
        dest.writeString(picture);
        dest.writeString(fullname);
        dest.writeString(location);
        dest.writeString(birthday);
        dest.writeString(website);
        dest.writeString(signature);
        dest.writeString(uploadedpicture);
        dest.writeInt(profileviews);
        dest.writeInt(postcount);
        dest.writeInt(topiccount);
        dest.writeLong(lastposttime);
        dest.writeInt(reputation);
        dest.writeString(status);
        dest.writeInt(uid);
        dest.writeInt(passwordExpiry);
        dest.writeString(coverPosition);
        dest.writeString(coverUrl);
        dest.writeString(githubid);
        dest.writeInt(followingCount);
        dest.writeString(groupTitle);
        dest.writeInt(bannedExpire);
        dest.writeInt(followerCount);
        dest.writeString(iconText);
        dest.writeString(iconBgColor);
        dest.writeString(joindateISO);
        dest.writeString(lastonlineISO);
        dest.writeInt(age);
        dest.writeString(emailClass);
        dest.writeInt(yourid);
        dest.writeInt(theirid);
        dest.writeByte((byte) (isTargetAdmin ? 1 : 0));
        dest.writeByte((byte) (isAdmin ? 1 : 0));
        dest.writeByte((byte) (isGlobalModerator ? 1 : 0));
        dest.writeByte((byte) (isModerator ? 1 : 0));
        dest.writeByte((byte) (isAdminOrGlobalModerator ? 1 : 0));
        dest.writeByte((byte) (isAdminOrGlobalModeratorOrModerator ? 1 : 0));
        dest.writeByte((byte) (isSelfOrAdminOrGlobalModerator ? 1 : 0));
        dest.writeByte((byte) (canEdit ? 1 : 0));
        dest.writeByte((byte) (canBan ? 1 : 0));
        dest.writeByte((byte) (canChangePassword ? 1 : 0));
        dest.writeByte((byte) (isSelf ? 1 : 0));
        dest.writeByte((byte) (isFollowing ? 1 : 0));
        dest.writeByte((byte) (showHidden ? 1 : 0));
        dest.writeByte((byte) (disableSignatures ? 1 : 0));
        dest.writeByte((byte) (reputationDisabled ? 1 : 0));
        dest.writeByte((byte) (downvoteDisabled ? 1 : 0));
        dest.writeByte((byte) (emailConfirmed ? 1 : 0));
        dest.writeString(websiteLink);
        dest.writeString(websiteName);
        dest.writeString(moderationNote);
        dest.writeByte((byte) (usernameDisableEdit ? 1 : 0));
        dest.writeByte((byte) (emailDisableEdit ? 1 : 0));
        dest.writeInt(hasPrivateChat);
        dest.writeInt(nextStart);
        dest.writeString(title);
        dest.writeString(password);
        dest.writeParcelable(pagination, flags);
        dest.writeByte((byte) (loggedIn ? 1 : 0));
        dest.writeString(relative_path);
        dest.writeString(url);
        dest.writeString(bodyClass);
        dest.writeStringList(ips);
        dest.writeTypedList(posts);
        dest.writeByte((byte) (isEmailConfirmSent ? 1 : 0));
        dest.writeString(aboutme);
        dest.writeInt(banned_until);
        dest.writeString(banned_until_readable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getReputation() {
        return reputation;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUploadedpicture() {
        return uploadedpicture;
    }

    public void setUploadedpicture(String uploadedpicture) {
        this.uploadedpicture = uploadedpicture;
    }

    public int getProfileviews() {
        return profileviews;
    }

    public void setProfileviews(int profileviews) {
        this.profileviews = profileviews;
    }

    public int getPostcount() {
        return postcount;
    }

    public void setPostcount(int postcount) {
        this.postcount = postcount;
    }

    public int getTopiccount() {
        return topiccount;
    }

    public void setTopiccount(int topiccount) {
        this.topiccount = topiccount;
    }

    public long getLastposttime() {
        return lastposttime;
    }

    public void setLastposttime(long lastposttime) {
        this.lastposttime = lastposttime;
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

    public boolean emailConfirmed() {
        return emailConfirmed;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPasswordExpiry() {
        return passwordExpiry;
    }

    public void setPasswordExpiry(int passwordExpiry) {
        this.passwordExpiry = passwordExpiry;
    }

    public String getCoverPosition() {
        return coverPosition;
    }

    public void setCoverPosition(String coverPosition) {
        this.coverPosition = coverPosition;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getGithubid() {
        return githubid;
    }

    public void setGithubid(String githubid) {
        this.githubid = githubid;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public int getBannedExpire() {
        return bannedExpire;
    }

    public void setBannedExpire(int bannedExpire) {
        this.bannedExpire = bannedExpire;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getPassword() {
        return password;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmailClass() {
        return emailClass;
    }

    public void setEmailClass(String emailClass) {
        this.emailClass = emailClass;
    }

    public int getYourid() {
        return yourid;
    }

    public void setYourid(int yourid) {
        this.yourid = yourid;
    }

    public int getTheirid() {
        return theirid;
    }

    public void setTheirid(int theirid) {
        this.theirid = theirid;
    }

    public boolean isIsTargetAdmin() {
        return isTargetAdmin;
    }

    public void setIsTargetAdmin(boolean isTargetAdmin) {
        this.isTargetAdmin = isTargetAdmin;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean isIsGlobalModerator() {
        return isGlobalModerator;
    }

    public void setIsGlobalModerator(boolean isGlobalModerator) {
        this.isGlobalModerator = isGlobalModerator;
    }

    public boolean isIsModerator() {
        return isModerator;
    }

    public void setIsModerator(boolean isModerator) {
        this.isModerator = isModerator;
    }

    public boolean isIsAdminOrGlobalModerator() {
        return isAdminOrGlobalModerator;
    }

    public void setIsAdminOrGlobalModerator(boolean isAdminOrGlobalModerator) {
        this.isAdminOrGlobalModerator = isAdminOrGlobalModerator;
    }

    public boolean isIsAdminOrGlobalModeratorOrModerator() {
        return isAdminOrGlobalModeratorOrModerator;
    }

    public void setIsAdminOrGlobalModeratorOrModerator(boolean isAdminOrGlobalModeratorOrModerator) {
        this.isAdminOrGlobalModeratorOrModerator = isAdminOrGlobalModeratorOrModerator;
    }

    public boolean isIsSelfOrAdminOrGlobalModerator() {
        return isSelfOrAdminOrGlobalModerator;
    }

    public void setIsSelfOrAdminOrGlobalModerator(boolean isSelfOrAdminOrGlobalModerator) {
        this.isSelfOrAdminOrGlobalModerator = isSelfOrAdminOrGlobalModerator;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public boolean isCanBan() {
        return canBan;
    }

    public void setCanBan(boolean canBan) {
        this.canBan = canBan;
    }

    public boolean isCanChangePassword() {
        return canChangePassword;
    }

    public void setCanChangePassword(boolean canChangePassword) {
        this.canChangePassword = canChangePassword;
    }

    public boolean isIsSelf() {
        return isSelf;
    }

    public void setIsSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }

    public boolean isIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public boolean isShowHidden() {
        return showHidden;
    }

    public void setShowHidden(boolean showHidden) {
        this.showHidden = showHidden;
    }

    public boolean isDisableSignatures() {
        return disableSignatures;
    }

    public void setDisableSignatures(boolean disableSignatures) {
        this.disableSignatures = disableSignatures;
    }

    public boolean isReputationDisabled() {
        return reputationDisabled;
    }

    public void setReputationDisabled(boolean reputationDisabled) {
        this.reputationDisabled = reputationDisabled;
    }

    public boolean isDownvoteDisabled() {
        return downvoteDisabled;
    }

    public void setDownvoteDisabled(boolean downvoteDisabled) {
        this.downvoteDisabled = downvoteDisabled;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public String getModerationNote() {
        return moderationNote;
    }

    public void setModerationNote(String moderationNote) {
        this.moderationNote = moderationNote;
    }

    public boolean isUsernameDisableEdit() {
        return usernameDisableEdit;
    }

    public void setUsernameDisableEdit(boolean usernameDisableEdit) {
        this.usernameDisableEdit = usernameDisableEdit;
    }

    public boolean isEmailDisableEdit() {
        return emailDisableEdit;
    }

    public void setEmailDisableEdit(boolean emailDisableEdit) {
        this.emailDisableEdit = emailDisableEdit;
    }

    public int getHasPrivateChat() {
        return hasPrivateChat;
    }

    public void setHasPrivateChat(int hasPrivateChat) {
        this.hasPrivateChat = hasPrivateChat;
    }

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

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public List<?> getGroups() {
        return groups;
    }

    public void setGroups(List<?> groups) {
        this.groups = groups;
    }

    public List<?> getProfile_links() {
        return profile_links;
    }

    public void setProfile_links(List<?> profile_links) {
        this.profile_links = profile_links;
    }

    public List<?> getSso() {
        return sso;
    }

    public void setSso(List<?> sso) {
        this.sso = sso;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
