package org.miaowo.miaowo.ui.load_more_list

interface LMLAdapter<E> {
    fun getItem(position: Int): E?
    fun update(newItems: List<E>)
    fun append(newItems: List<E>, toHead: Boolean)
    fun insert(item: E, toHead: Boolean)
    fun clear()
}
