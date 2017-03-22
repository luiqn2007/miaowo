package org.miaowo.miaowo.bean.data.web;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * rel : [{"rel":"next","href":"?page=2"}]
 * pages : [{"page":1,"active":true,"qs":"page=1"},{"page":2,"active":false,"qs":"page=2"},{"page":3,"active":false,"qs":"page=3"},{"page":4,"active":false,"qs":"page=4"},{"page":5,"active":false,"qs":"page=5"},{"separator":true},{"page":11,"active":false,"qs":"page=11"},{"page":12,"active":false,"qs":"page=12"}]
 * currentPage : 1
 * pageCount : 12
 * prev : {"page":1,"active":false,"qs":"page=1"}
 * next : {"page":2,"active":true,"qs":"page=2"}
 */

public class Pagination implements Parcelable {
    private int currentPage;
    private int pageCount;
    private Page prev;
    private Page next;
    private List<Page.Rel> rel;
    private List<Page> pages;

    protected Pagination(Parcel in) {
        currentPage = in.readInt();
        pageCount = in.readInt();
        prev = in.readParcelable(Page.class.getClassLoader());
        next = in.readParcelable(Page.class.getClassLoader());
        pages = in.createTypedArrayList(Page.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentPage);
        dest.writeInt(pageCount);
        dest.writeParcelable(prev, flags);
        dest.writeParcelable(next, flags);
        dest.writeTypedList(pages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pagination> CREATOR = new Creator<Pagination>() {
        @Override
        public Pagination createFromParcel(Parcel in) {
            return new Pagination(in);
        }

        @Override
        public Pagination[] newArray(int size) {
            return new Pagination[size];
        }
    };

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

    public Page getPrev() {
        return prev;
    }

    public void setPrev(Page prev) {
        this.prev = prev;
    }

    public Page getNext() {
        return next;
    }

    public void setNext(Page next) {
        this.next = next;
    }

    public List<Page.Rel> getRel() {
        return rel;
    }

    public void setRel(List<Page.Rel> rel) {
        this.rel = rel;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }
}
