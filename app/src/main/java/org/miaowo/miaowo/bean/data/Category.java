package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {

    /**
     * value : all
     * text : [[unread:all_categories]]
     * cid : 5
     * name : 提问
     * description :
     * descriptionParsed :
     * icon : fa-question
     * bgColor : #e95c5a
     * color : #fff
     * slug : 5/提问
     * parentCid : 0
     * topic_count : 67
     * post_count : 452
     * disabled : false
     * order : 5
     * link :
     * numRecentReplies : 1
     * class : col-md-3 col-xs-6
     * imageClass : cover
     * undefined : on
     * totalPostCount : 452
     * totalTopicCount : 67
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
    private String imageClass;
    private String undefined;
    private int totalPostCount;
    private int totalTopicCount;

    protected Category(Parcel in) {
        value = in.readString();
        text = in.readString();
        cid = in.readInt();
        name = in.readString();
        description = in.readString();
        descriptionParsed = in.readString();
        icon = in.readString();
        bgColor = in.readString();
        color = in.readString();
        slug = in.readString();
        parentCid = in.readInt();
        topic_count = in.readInt();
        post_count = in.readInt();
        disabled = in.readByte() != 0;
        order = in.readInt();
        link = in.readString();
        numRecentReplies = in.readInt();
        imageClass = in.readString();
        undefined = in.readString();
        totalPostCount = in.readInt();
        totalTopicCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
        dest.writeString(text);
        dest.writeInt(cid);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(descriptionParsed);
        dest.writeString(icon);
        dest.writeString(bgColor);
        dest.writeString(color);
        dest.writeString(slug);
        dest.writeInt(parentCid);
        dest.writeInt(topic_count);
        dest.writeInt(post_count);
        dest.writeByte((byte) (disabled ? 1 : 0));
        dest.writeInt(order);
        dest.writeString(link);
        dest.writeInt(numRecentReplies);
        dest.writeString(imageClass);
        dest.writeString(undefined);
        dest.writeInt(totalPostCount);
        dest.writeInt(totalTopicCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

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

}
