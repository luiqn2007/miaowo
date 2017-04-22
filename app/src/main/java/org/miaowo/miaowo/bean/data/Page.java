package org.miaowo.miaowo.bean.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * page : 1
 * active : false
 * qs : page=1
 */

public class Page implements Parcelable {
    /**
     * page : 1
     * active : false
     * qs : page=1
     */

    private int page;
    private boolean active;
    private String qs;

    protected Page(Parcel in) {
        page = in.readInt();
        active = in.readByte() != 0;
        qs = in.readString();
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

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

    public String getQs() {
        return qs;
    }

    public void setQs(String qs) {
        this.qs = qs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeString(qs);
    }

    static class Rel {
        /**
         * rel : next
         * href : ?page=2
         */

        private String rel;
        private String href;

        public String getRel() {
            return rel;
        }

        public void setRel(String rel) {
            this.rel = rel;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }
}
