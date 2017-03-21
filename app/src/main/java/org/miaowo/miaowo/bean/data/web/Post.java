package org.miaowo.miaowo.bean.data.web;

/**
 * pid : 1168
 * uid : 7
 * tid : 197
 * content : <p><a class="plugin-mentions-a" href="https://miaowo.org/uid/1">@Systemd</a><br />
 说实话，我查了查awesome，个人不喜欢这样的。不可重叠，硬伤，alt+tab效率也不差。</p>
 <p>暂时不喜换系统，webstorm我现在就装着，不过基本没用过 /(ㄒoㄒ)/~~</p>
 <p><a href="http://Blog.miaowo.org" rel="nofollow">Blog.miaowo.org</a> 现在还是空着的啊，不过既然计划要把公告去掉的话，我就不在APP里面预留了。</p>
 <p>rhel。。。也行，不过都差不多吧。说这些有点远，得先搞完喵窝APP再说。不然一不小心硬盘再挂掉哭都没地方哭去。</p>
 <p>装软件很烦吗？我感觉 webapp 依托服务器，万一服务器挂了就完了，不太安全持久，而且外面套一层浏览器的壳也挺难看的，显示效果也不好保证。而且webapp比一般APP多一步打开浏览器的步骤，比较麻烦。想用旧版本也不太可能，离线功能也是个问题。</p>
 <p>下一步到底是更进一步继续看 nativeApp 呢，还是看 webApp，有点迷茫了。nativeAPP 再深入就得研究 Linux，想看 root 后的功能，或者图形-&gt;游戏。webAPP的话，低平台依赖是个好处，而且如果解决了离线问题的话还是很不错的，但是好像基于web框架做的APP运行性能不如本地。不管C++还是JS我都是只看过一遍语法，也不存在什么难度不同。愁。不过幸好这个APP够我再写一个月的了，我还有比较充裕的时间考虑。</p>

 * timestamp : 1489774776511
 * deleted : false
 * upvotes : 0
 * downvotes : 0
 * user : {"username":"么么么喵","userslug":"么么么喵","picture":"/uploads/files/14828604360404e0e28381f30e92480e63b4a45086e061d95f715.jpg","uid":7,"icon:text":"么","icon:bgColor":"#1b5e20"}
 * title : {"tid":197,"uid":7,"cid":"6","mainPid":1128,"title":"搭好了leanote了","slug":"197/搭好了leanote了","postcount":16,"deleted":false}
 * category : {"cid":6,"name":"灌水","icon":"fa-comments","bgColor":"#32c3e3","color":"#fff","slug":"6/灌水","parentCid":0}
 * isMainPost : false
 * votes : 0
 * timestampISO : 2017-03-17T18:19:36.511Z
 */

public class Post {
    /**
     * pid : 1119
     * uid : 1
     * tid : 195
     * content : <p>我的</p>
     <ul>
     <li>水杯</li>
     <li>Kindle</li>
     <li>电车卡</li>
     <li>学生卡</li>
     <li>移动电源</li>
     <li>手机 x2</li>
     <li>各类数据线</li>
     <li>零钱</li>
     <li>一卷纸巾</li>
     <li>钥匙</li>
     </ul>

     * timestamp : 1489208654708
     * deleted : false
     * upvotes : 0
     * downvotes : 0
     * votes : 0
     * timestampISO : 2017-03-11T05:04:14.708Z
     * editedISO :
     * index : 0
     * user : {"username":"Systemd","userslug":"systemd","lastonline":1489670891717,"picture":"","fullname":"","signature":"","reputation":-1,"postcount":165,"banned":false,"status":"offline","uid":1,"groupTitle":"","icon:text":"S","icon:bgColor":"#673ab7","lastonlineISO":"2017-03-16T13:28:11.717Z","custom_profile_info":[]}
     * editor : null
     * bookmarked : false
     * upvoted : false
     * downvoted : false
     * replies : 0
     * selfPost : false
     * display_edit_tools : true
     * display_delete_tools : true
     * display_moderator_tools : true
     * display_move_tools : false
     * display_post_menu : true
     * edited : 1489378929756
     */

    private int pid;
    private int uid;
    private int tid;
    private String content;
    private long timestamp;
    private boolean deleted;
    private int upvotes;
    private int downvotes;
    private int votes;
    private String timestampISO;
    private String editedISO;
    private int index;
    private User user;
    private Object editor;
    private boolean bookmarked;
    private boolean upvoted;
    private boolean downvoted;
    private int replies;
    private boolean selfPost;
    private boolean display_edit_tools;
    private boolean display_delete_tools;
    private boolean display_moderator_tools;
    private boolean display_move_tools;
    private boolean display_post_menu;
    private long edited;
    private Title title;
    private Category category;
    private boolean isMainPost;

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

    public String getEditedISO() {
        return editedISO;
    }

    public void setEditedISO(String editedISO) {
        this.editedISO = editedISO;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Object getEditor() {
        return editor;
    }

    public void setEditor(Object editor) {
        this.editor = editor;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean isUpvoted() {
        return upvoted;
    }

    public void setUpvoted(boolean upvoted) {
        this.upvoted = upvoted;
    }

    public boolean isDownvoted() {
        return downvoted;
    }

    public void setDownvoted(boolean downvoted) {
        this.downvoted = downvoted;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public boolean isSelfPost() {
        return selfPost;
    }

    public void setSelfPost(boolean selfPost) {
        this.selfPost = selfPost;
    }

    public boolean isDisplay_edit_tools() {
        return display_edit_tools;
    }

    public void setDisplay_edit_tools(boolean display_edit_tools) {
        this.display_edit_tools = display_edit_tools;
    }

    public boolean isDisplay_delete_tools() {
        return display_delete_tools;
    }

    public void setDisplay_delete_tools(boolean display_delete_tools) {
        this.display_delete_tools = display_delete_tools;
    }

    public boolean isDisplay_moderator_tools() {
        return display_moderator_tools;
    }

    public void setDisplay_moderator_tools(boolean display_moderator_tools) {
        this.display_moderator_tools = display_moderator_tools;
    }

    public boolean isDisplay_move_tools() {
        return display_move_tools;
    }

    public void setDisplay_move_tools(boolean display_move_tools) {
        this.display_move_tools = display_move_tools;
    }

    public boolean isDisplay_post_menu() {
        return display_post_menu;
    }

    public void setDisplay_post_menu(boolean display_post_menu) {
        this.display_post_menu = display_post_menu;
    }

    public long getEdited() {
        return edited;
    }

    public void setEdited(long edited) {
        this.edited = edited;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isIsMainPost() {
        return isMainPost;
    }

    public void setIsMainPost(boolean isMainPost) {
        this.isMainPost = isMainPost;
    }
}
