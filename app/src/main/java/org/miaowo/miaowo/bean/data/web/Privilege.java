package org.miaowo.miaowo.bean.data.web;

import com.google.gson.annotations.SerializedName;

public class Privilege {
    @SerializedName("topics:reply")
    private boolean topicsReply;
    @SerializedName("topics:read")
    private boolean topicsRead;
    @SerializedName("topics:delete")
    private boolean topicsDelete;
    @SerializedName("posts:edit")
    private boolean postsEdit;
    @SerializedName("posts:delete")
    private boolean postsDelete;
    private boolean read;
    private boolean view_thread_tools;
    private boolean editable;
    private boolean deletable;
    private boolean view_deleted;
    private boolean isAdminOrMod;
    private boolean disabled;
    private int cid;
    private int tid;
    private int uid;

    public boolean isTopicsReply() {
        return topicsReply;
    }

    public void setTopicsReply(boolean topicsReply) {
        this.topicsReply = topicsReply;
    }

    public boolean isTopicsRead() {
        return topicsRead;
    }

    public void setTopicsRead(boolean topicsRead) {
        this.topicsRead = topicsRead;
    }

    public boolean isTopicsDelete() {
        return topicsDelete;
    }

    public void setTopicsDelete(boolean topicsDelete) {
        this.topicsDelete = topicsDelete;
    }

    public boolean isPostsEdit() {
        return postsEdit;
    }

    public void setPostsEdit(boolean postsEdit) {
        this.postsEdit = postsEdit;
    }

    public boolean isPostsDelete() {
        return postsDelete;
    }

    public void setPostsDelete(boolean postsDelete) {
        this.postsDelete = postsDelete;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isView_thread_tools() {
        return view_thread_tools;
    }

    public void setView_thread_tools(boolean view_thread_tools) {
        this.view_thread_tools = view_thread_tools;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
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

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

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
}
