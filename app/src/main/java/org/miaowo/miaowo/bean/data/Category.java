package org.miaowo.miaowo.bean.data;

import java.util.Arrays;

/**
 * 类别
 * Created by luqin on 17-3-9.
 */

public class Category {
    public int cid;
    public String name;
    public String icon;
    public String bgColor;
    public String color;
    public String slug;
    public String parentCid;
    public int topic_count;
    public int post_count;
    public Question[] topics;
    public boolean disabled;
    public String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (cid != category.cid) return false;
        return name.equals(category.name);

    }

    @Override
    public int hashCode() {
        int result = cid;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", color='" + color + '\'' +
                ", slug='" + slug + '\'' +
                ", parentCid='" + parentCid + '\'' +
                ", topic_count=" + topic_count +
                ", post_count=" + post_count +
                ", topics=" + Arrays.toString(topics) +
                ", disabled=" + disabled +
                ", image=" + image +
                '}';
    }
}
