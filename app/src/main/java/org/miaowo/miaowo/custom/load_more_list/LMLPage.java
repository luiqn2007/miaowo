package org.miaowo.miaowo.custom.load_more_list;

import java.util.ArrayList;
import java.util.List;

/**
 * 作为列表的一页
 * Created by luqin on 17-5-4.
 */

public class LMLPage<E> {
    public int id;
    public List<E> items;
    public Object tag;
    public boolean index;

    public LMLPage(int id, List<E> items) {
        this.index = false;
        this.id = id;
        this.items = items;
    }

    public LMLPage(List<E> items) {
        this.index = false;
        this.items = items;
        this.id = LMLPageAdapter.NO_ID;
    }

    public LMLPage() {
        this.index = false;
        this.id = LMLPageAdapter.NO_ID;
        this.items = new ArrayList<>();
    }
}
