package org.miaowo.miaowo.other.template

import android.support.design.widget.TabLayout

class MyOnTabSelectedListener(selected: (p0: TabLayout.Tab?) -> Unit): TabLayout.OnTabSelectedListener {
    val selected = selected

    override fun onTabReselected(p0: TabLayout.Tab?) {}

    override fun onTabUnselected(p0: TabLayout.Tab?) {}

    override fun onTabSelected(p0: TabLayout.Tab?) {
        selected(p0)
    }
}