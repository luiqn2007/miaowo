package org.miaowo.miaowo.bean.data.web;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList implements Parcelable {

    /**
     * users : [{"username":"楱椿","userslug":"楱椿","joindate":1485589939602,"lastonline":1485590080506,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":97,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"楱","icon:bgColor":"#33691e","joindateISO":"2017-01-28T07:52:19.602Z","lastonlineISO":"2017-01-28T07:54:40.506Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"Sterben","userslug":"sterben","joindate":1485538949365,"lastonline":1488710016283,"picture":"/uploads/files/1485539179164img_0007.jpg","reputation":0,"postcount":23,"banned":false,"status":"offline","uid":96,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"S","icon:bgColor":"#827717","joindateISO":"2017-01-27T17:42:29.365Z","lastonlineISO":"2017-03-05T10:33:36.283Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"叶落","userslug":"叶落","joindate":1485537996928,"lastonline":1485643904572,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":95,"email:confirmed":true,"flags":null,"banned:expire":null,"icon:text":"叶","icon:bgColor":"#2196f3","joindateISO":"2017-01-27T17:26:36.928Z","lastonlineISO":"2017-01-28T22:51:44.572Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"图图图","userslug":"图图图","joindate":1485533437397,"lastonline":1490118034513,"picture":"/uploads/profile/94-profileimg.png","reputation":0,"postcount":36,"banned":false,"status":"offline","uid":94,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"图","icon:bgColor":"#9c27b0","joindateISO":"2017-01-27T16:10:37.397Z","lastonlineISO":"2017-03-21T17:40:34.513Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"今昔何夕","userslug":"今昔何夕","joindate":1485525051762,"lastonline":1485847282128,"picture":"/uploads/files/1485525431610394525697.jpg","reputation":0,"postcount":1,"banned":false,"status":"offline","uid":93,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"今","icon:bgColor":"#795548","joindateISO":"2017-01-27T13:50:51.762Z","lastonlineISO":"2017-01-31T07:21:22.128Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"零一","userslug":"零一","joindate":1485516315017,"lastonline":1486824274736,"picture":"/uploads/files/1485516881758img_20170127_193242.jpg","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":92,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"零","icon:bgColor":"#e65100","joindateISO":"2017-01-27T11:25:15.017Z","lastonlineISO":"2017-02-11T14:44:34.736Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"吃草莓的喵喵","userslug":"吃草莓的喵喵","joindate":1485505823225,"lastonline":1489898655176,"picture":"/uploads/files/1485515194903jpeg_20170127_190634_914569370.jpg","reputation":0,"postcount":8,"banned":false,"status":"offline","uid":91,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"吃","icon:bgColor":"#2196f3","joindateISO":"2017-01-27T08:30:23.225Z","lastonlineISO":"2017-03-19T04:44:15.176Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"落辰","userslug":"落辰","joindate":1485484422245,"lastonline":1486110195369,"picture":"","reputation":0,"postcount":1,"banned":false,"status":"offline","uid":90,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"落","icon:bgColor":"#1b5e20","joindateISO":"2017-01-27T02:33:42.245Z","lastonlineISO":"2017-02-03T08:23:15.369Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"风骚的大总攻","userslug":"风骚的大总攻","joindate":1485477557616,"lastonline":1488384608813,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":89,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"风","icon:bgColor":"#673ab7","joindateISO":"2017-01-27T00:39:17.616Z","lastonlineISO":"2017-03-01T16:10:08.813Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"抠土豆很傲娇","userslug":"抠土豆很傲娇","joindate":1485475222670,"lastonline":1485476404538,"picture":"/uploads/files/1485475443721img_0197.jpg","reputation":0,"postcount":10,"banned":false,"status":"offline","uid":88,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"抠","icon:bgColor":"#9c27b0","joindateISO":"2017-01-27T00:00:22.670Z","lastonlineISO":"2017-01-27T00:20:04.538Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"CatFuFu","userslug":"catfufu","joindate":1485454504103,"lastonline":1485455481556,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":87,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"C","icon:bgColor":"#e65100","joindateISO":"2017-01-26T18:15:04.103Z","lastonlineISO":"2017-01-26T18:31:21.556Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"群喵乱舞","userslug":"群喵乱舞","joindate":1485451218789,"lastonline":1485530696538,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":86,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"群","icon:bgColor":"#795548","joindateISO":"2017-01-26T17:20:18.789Z","lastonlineISO":"2017-01-27T15:24:56.538Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"还是好呲菌","userslug":"还是好呲菌","joindate":1485424687432,"lastonline":1485576788624,"picture":"/uploads/files/1485425186239img_6705.jpg","reputation":0,"postcount":6,"banned":false,"status":"offline","uid":85,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"还","icon:bgColor":"#3f51b5","joindateISO":"2017-01-26T09:58:07.432Z","lastonlineISO":"2017-01-28T04:13:08.624Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"猫呢酱","userslug":"猫呢酱","joindate":1485412249790,"lastonline":1486608004832,"picture":"/uploads/files/148550715679133.jpg","reputation":0,"postcount":3,"banned":false,"status":"offline","uid":84,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"猫","icon:bgColor":"#009688","joindateISO":"2017-01-26T06:30:49.790Z","lastonlineISO":"2017-02-09T02:40:04.832Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"bloom","userslug":"bloom","joindate":1485411673304,"lastonline":1485784735159,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":83,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"B","icon:bgColor":"#2196f3","joindateISO":"2017-01-26T06:21:13.304Z","lastonlineISO":"2017-01-30T13:58:55.159Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"苏烟","userslug":"苏烟","joindate":1485402778757,"lastonline":1485402778757,"picture":"/uploads/files/148540294939932324966.jpg","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":82,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"苏","icon:bgColor":"#795548","joindateISO":"2017-01-26T03:52:58.757Z","lastonlineISO":"2017-01-26T03:52:58.757Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"Daemon.Version","userslug":"daemon-version","joindate":1485319320849,"lastonline":1485665526746,"picture":"/uploads/files/1485319460677b27ebc03738da9774138ff87b251f8198718e372.jpg","reputation":0,"postcount":2,"banned":false,"status":"offline","uid":81,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"D","icon:bgColor":"#795548","joindateISO":"2017-01-25T04:42:00.849Z","lastonlineISO":"2017-01-29T04:52:06.746Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"法杖","userslug":"法杖","joindate":1485285463883,"lastonline":1485285463883,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":80,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"法","icon:bgColor":"#827717","joindateISO":"2017-01-24T19:17:43.883Z","lastonlineISO":"2017-01-24T19:17:43.883Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"何二二","userslug":"何二二","joindate":1485220515953,"lastonline":1489453223336,"picture":"/uploads/files/148539178754115530792.jpg","reputation":0,"postcount":15,"banned":false,"status":"offline","uid":79,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"何","icon:bgColor":"#673ab7","joindateISO":"2017-01-24T01:15:15.953Z","lastonlineISO":"2017-03-14T01:00:23.336Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"Hex_","userslug":"hex_","joindate":1485166685381,"lastonline":1485167749644,"picture":"","reputation":0,"postcount":1,"banned":false,"status":"offline","uid":78,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"H","icon:bgColor":"#e65100","joindateISO":"2017-01-23T10:18:05.381Z","lastonlineISO":"2017-01-23T10:35:49.644Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"憂鬱","userslug":"憂鬱","joindate":1485048685893,"lastonline":1485615907693,"picture":"/uploads/files/1485057912021pixiv59547925.jpg","reputation":0,"postcount":3,"banned":false,"status":"offline","uid":77,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"憂","icon:bgColor":"#ff5722","joindateISO":"2017-01-22T01:31:25.893Z","lastonlineISO":"2017-01-28T15:05:07.693Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"꧁许我流年换汝꧂","userslug":"许我流年换汝","joindate":1485016657175,"lastonline":1485628185547,"picture":"/uploads/files/1485017029429jpeg_20170120_193655_823050414.jpg","reputation":0,"postcount":1,"banned":false,"status":"offline","uid":76,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"꧁","icon:bgColor":"#f44336","joindateISO":"2017-01-21T16:37:37.175Z","lastonlineISO":"2017-01-28T18:29:45.547Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"树叶的香味","userslug":"树叶的香味","joindate":1484668005320,"lastonline":1485160250643,"picture":"","reputation":0,"postcount":0,"banned":false,"status":"offline","uid":75,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"树","icon:bgColor":"#673ab7","joindateISO":"2017-01-17T15:46:45.320Z","lastonlineISO":"2017-01-23T08:30:50.643Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"},{"username":"hanabi","userslug":"hanabi","joindate":1484645409689,"lastonline":1489798691385,"picture":"","reputation":0,"postcount":8,"banned":false,"status":"offline","uid":74,"flags":null,"banned:expire":null,"email:confirmed":false,"icon:text":"H","icon:bgColor":"#827717","joindateISO":"2017-01-17T09:30:09.689Z","lastonlineISO":"2017-03-18T00:58:11.385Z","administrator":false,"banned_until":0,"banned_until_readable":"Not Banned"}]
     * pagination : {"rel":[{"rel":"next","href":"?page=3"},{"rel":"prev","href":"?page=1"}],"pages":[{"page":1,"active":false,"qs":"page=1"},{"page":2,"active":true,"qs":"page=2"},{"page":3,"active":false,"qs":"page=3"},{"page":4,"active":false,"qs":"page=4"},{"page":5,"active":false,"qs":"page=5"}],"currentPage":2,"pageCount":5,"prev":{"page":1,"active":true,"qs":"page=1"},"next":{"page":3,"active":true,"qs":"page=3"}}
     * userCount : 119
     * title : [[pages:users/latest]]
     * breadcrumbs : [{"text":"[[global:home]]","url":"/"},{"text":"[[global:users]]"}]
     * isAdminOrGlobalMod : true
     * section_joindate : true
     * maximumInvites :
     * inviteOnly : false
     * adminInviteOnly : false
     * reputation:disabled : true
     * invites : 0
     * loggedIn : true
     * relative_path :
     * template : {"name":"users","users":true}
     * url : /users
     * bodyClass : page-users
     */

    private Pagination pagination;
    private int userCount;
    private String title;
    private boolean isAdminOrGlobalMod;
    private boolean section_joindate;
    private String maximumInvites;
    private boolean inviteOnly;
    private boolean adminInviteOnly;
    @SerializedName("reputation:disabled")
    private boolean reputationDisabled;
    private int invites;
    private boolean loggedIn;
    private String relative_path;
    private String url;
    private String bodyClass;
    private List<User> users;

    protected UserList(Parcel in) {
        pagination = in.readParcelable(Pagination.class.getClassLoader());
        userCount = in.readInt();
        title = in.readString();
        isAdminOrGlobalMod = in.readByte() != 0;
        section_joindate = in.readByte() != 0;
        maximumInvites = in.readString();
        inviteOnly = in.readByte() != 0;
        adminInviteOnly = in.readByte() != 0;
        reputationDisabled = in.readByte() != 0;
        invites = in.readInt();
        loggedIn = in.readByte() != 0;
        relative_path = in.readString();
        url = in.readString();
        bodyClass = in.readString();
        users = in.createTypedArrayList(User.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(pagination, flags);
        dest.writeInt(userCount);
        dest.writeString(title);
        dest.writeByte((byte) (isAdminOrGlobalMod ? 1 : 0));
        dest.writeByte((byte) (section_joindate ? 1 : 0));
        dest.writeString(maximumInvites);
        dest.writeByte((byte) (inviteOnly ? 1 : 0));
        dest.writeByte((byte) (adminInviteOnly ? 1 : 0));
        dest.writeByte((byte) (reputationDisabled ? 1 : 0));
        dest.writeInt(invites);
        dest.writeByte((byte) (loggedIn ? 1 : 0));
        dest.writeString(relative_path);
        dest.writeString(url);
        dest.writeString(bodyClass);
        dest.writeTypedList(users);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserList> CREATOR = new Creator<UserList>() {
        @Override
        public UserList createFromParcel(Parcel in) {
            return new UserList(in);
        }

        @Override
        public UserList[] newArray(int size) {
            return new UserList[size];
        }
    };

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIsAdminOrGlobalMod() {
        return isAdminOrGlobalMod;
    }

    public void setIsAdminOrGlobalMod(boolean isAdminOrGlobalMod) {
        this.isAdminOrGlobalMod = isAdminOrGlobalMod;
    }

    public boolean isSection_joindate() {
        return section_joindate;
    }

    public void setSection_joindate(boolean section_joindate) {
        this.section_joindate = section_joindate;
    }

    public String getMaximumInvites() {
        return maximumInvites;
    }

    public void setMaximumInvites(String maximumInvites) {
        this.maximumInvites = maximumInvites;
    }

    public boolean isInviteOnly() {
        return inviteOnly;
    }

    public void setInviteOnly(boolean inviteOnly) {
        this.inviteOnly = inviteOnly;
    }

    public boolean isAdminInviteOnly() {
        return adminInviteOnly;
    }

    public void setAdminInviteOnly(boolean adminInviteOnly) {
        this.adminInviteOnly = adminInviteOnly;
    }

    public boolean isReputationDisabled() {
        return reputationDisabled;
    }

    public void setReputationDisabled(boolean reputationDisabled) {
        this.reputationDisabled = reputationDisabled;
    }

    public int getInvites() {
        return invites;
    }

    public void setInvites(int invites) {
        this.invites = invites;
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
}
