package org.miaowo.miaowo.adapter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.activity.UserActivity
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.other.BaseListTouchListener
import org.miaowo.miaowo.other.Const

class FeedbackAdapter(context: Context): ListAdapter<Array<String>>(object : ViewCreator<Array<String>> {
    override fun createHolder(parent: ViewGroup, viewType: Int) =
            ListHolder(android.R.layout.simple_list_item_activated_2, parent, context)

    override fun bindView(item: Array<String>, holder: ListHolder, type: Int) {
        if (item.size >= 2) {
            holder.find<TextView>(android.R.id.text1)?.text = item[0]
            holder.find<TextView>(android.R.id.text2)?.text = item[1]
        }
    }
})

class FeedbackListener(context: Context): BaseListTouchListener(context) {
    override fun onClick(view: View?, position: Int): Boolean {
        var ret = true
        when (position) {
            0 -> openUri("mqqwpa://im/chat?chat_type=group&uin=385231397&version=1", "无法打开 QQ 临时会话")
            1 -> openUri("mqqwpa://im/chat?chat_type=wpa&uin=1105188240", "无法打开 QQ 临时会话")
            2 -> context.startActivity(Intent(context, UserActivity::class.java).putExtra(Const.TYPE, UserActivity.USER_FROM_NAME).putExtra(Const.NAME, "Systemd"))
            3 -> openUri("mqqwpa://im/chat?chat_type=wpa&uin=1289770378", "无法打开 QQ 临时会话")
            else -> ret = false
        }
        return ret
    }

    private fun openUri(uri: String, errString: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        val support = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (support?.isNotEmpty() == true) context.startActivity(intent)
        else ActivityUtils.getTopActivity().toast(errString, TastyToast.ERROR)
    }
}