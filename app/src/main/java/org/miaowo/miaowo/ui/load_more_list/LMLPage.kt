package org.miaowo.miaowo.ui.load_more_list

import org.miaowo.miaowo.other.Const

/**
 * 作为列表的一页
 * Created by luqin on 17-5-4.
 */

class LMLPage<E> {
    var id: Int = 0
    var items: List<E>

    constructor(id: Int, items: List<E>) {
        this.id = id
        this.items = items
    }

    constructor(items: List<E>) {
        this.items = items
        this.id = Const.NO_ID
    }

    constructor() {
        this.id = Const.NO_ID
        this.items = mutableListOf()
    }
}
