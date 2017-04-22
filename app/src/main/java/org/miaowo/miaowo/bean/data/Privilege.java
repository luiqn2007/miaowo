package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Privilege implements Parcelable {
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

    protected Privilege(Parcel in) {
        topicsReply = in.readByte() != 0;
        topicsRead = in.readByte() != 0;
        topicsDelete = in.readByte() != 0;
        postsEdit = in.readByte() != 0;
        postsDelete = in.readByte() != 0;
        read = in.readByte() != 0;
        view_thread_tools = in.readByte() != 0;
        editable = in.readByte() != 0;
        deletable = in.readByte() != 0;
        view_deleted = in.readByte() != 0;
        isAdminOrMod = in.readByte() != 0;
        disabled = in.readByte() != 0;
        cid = in.readInt();
        tid = in.readInt();
        uid = in.readInt();
    }

    public static final Creator<Privilege> CREATOR = new Creator<Privilege>() {
        @Override
        public Privilege createFromParcel(Parcel in) {
            return new Privilege(in);
        }

        @Override
        public Privilege[] newArray(int size) {
            return new Privilege[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (topicsReply ? 1 : 0));
        dest.writeByte((byte) (topicsRead ? 1 : 0));
        dest.writeByte((byte) (topicsDelete ? 1 : 0));
        dest.writeByte((byte) (postsEdit ? 1 : 0));
        dest.writeByte((byte) (postsDelete ? 1 : 0));
        dest.writeByte((byte) (read ? 1 : 0));
        dest.writeByte((byte) (view_thread_tools ? 1 : 0));
        dest.writeByte((byte) (editable ? 1 : 0));
        dest.writeByte((byte) (deletable ? 1 : 0));
        dest.writeByte((byte) (view_deleted ? 1 : 0));
        dest.writeByte((byte) (isAdminOrMod ? 1 : 0));
        dest.writeByte((byte) (disabled ? 1 : 0));
        dest.writeInt(cid);
        dest.writeInt(tid);
        dest.writeInt(uid);
    }
}
