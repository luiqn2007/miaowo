package org.miaowo.miaowo.custom.load_more_list;

import java.util.List;

public interface LMLAdapter<E> {
    E getItem(int position);
    void update(List<E> newItems);
    void append(List<E> newItems, boolean toHead);
    void insert(E item, boolean toHead);
    void clear();
    boolean isIndex();
}
