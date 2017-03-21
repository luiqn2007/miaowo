package org.miaowo.miaowo.bean.data.web;

/**
 * page : 1
 * active : false
 * qs : page=1
 */

public class Page {
    /**
     * page : 1
     * active : false
     * qs : page=1
     */

    private int page;
    private boolean active;
    private String qs;

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
