package org.miaowo.miaowo.bean.data;

/**
 * 搜索结果
 * Created by luqin on 17-3-10.
 */

public class Search<T> {
    public T[] posts;
    public int matchCount;
    public int pageCount;
    public String search_query;
    public String time;
}
