package org.miaowo.miaowo.bean.data;

/**
 * 表示问题的bean类
 *
 * Created by lq2007 on 16-11-21.
 */

public class Question {
    public int tid;
    public int uid;
    public int cid;
    public int mainPid;
    public int postcount;
    public int viewcount;
    public int teaserPid;
    public int bookmark;
    public int index;
    public long timestamp;
    public long lastposttime;
    public boolean unread;
    public boolean unreplied;
    public String title;
    public String slug;
    public String titleRaw;
    public String timestampISO;
    public String lastposttimeISO;
    public String[] tags;
    public Category category;
    public User user;
    public Answer teaser;
    public Answer[] posts;
}