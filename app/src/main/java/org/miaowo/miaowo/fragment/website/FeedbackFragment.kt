package org.miaowo.miaowo.fragment.website

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.base.extra.loadSelf
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.fragment.user.UserFragment
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import kotlin.math.min

/**
 * 状态
 * Created by lq2007 on 2017/7/22 0022.
 */
class FeedbackFragment : BaseListFragment() {

    private val mAdapter = ListAdapter(object : ListAdapter.ViewCreator<Array<String>> {
        override fun createHolder(parent: ViewGroup?, viewType: Int): ListHolder {
            return ListHolder(android.R.layout.simple_list_item_activated_2, parent, App.i)
        }

        override fun bindView(item: Array<String>?, holder: ListHolder?, type: Int) {
            if (item != null && item.size >= 2) {
                holder?.find<TextView>(android.R.id.text1)?.text = item[0]
                holder?.find<TextView>(android.R.id.text2)?.text = item[1]
            }
        }

    })

    override fun setAdapter(list: RecyclerView) {
        list.adapter = mAdapter
    }

    companion object {
        fun newInstance(): FeedbackFragment {
            val fragment = FeedbackFragment()
            val args = Bundle()
            args.putString(Const.TAG, fragment.javaClass.name)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        when (position) {
            0 -> {
                val uri = "mqqwpa://im/chat?chat_type=group&uin=385231397&version=1"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                val support = context?.packageManager?.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                if (support == null || support.isEmpty()) {
                    activity?.toast("无法打开 QQ", TastyToast.ERROR)
                } else {
                    startActivity(intent)
                }
            }
            1 -> {
                val uri = "mqqwpa://im/chat?chat_type=wpa&uin=1105188240"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                val support = context?.packageManager?.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                if (support == null || support.isEmpty()) {
                    activity?.toast("无法打开 QQ 临时会话", TastyToast.ERROR)
                } else {
                    startActivity(intent)
                }
            }
            2 -> {
                UserFragment.newInstance("Systemd").loadSelf(Miao.i)
            }
            3 -> {
                val uri = "mqqwpa://im/chat?chat_type=wpa&uin=1289770378"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                val support = context?.packageManager?.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                if (support == null || support.isEmpty()) {
                    activity?.toast("无法打开 QQ 临时会话", TastyToast.ERROR)
                } else {
                    startActivity(intent)
                }
            }
            else -> return false
        }
        return true
    }

    override fun onRefresh() {
        val arrTitle = App.i.resources.getStringArray(R.array.feedback_title)
        val arrValue = App.i.resources.getStringArray(R.array.feedback_value)
        val listArr = mutableListOf<Array<String>>()
        for (i in 0 until min(arrTitle.size, arrValue.size)) {
            listArr.add(arrayOf(arrTitle[i], arrValue[i]))
        }
        mAdapter.update(listArr)
        super.onRefresh()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (attach as? IMiaoListener)?.setToolbar(App.i.getString(R.string.feedback))
    }
}