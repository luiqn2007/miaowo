package org.miaowo.miaowo.fragment.setting

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_setting_app.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.spGet
import org.miaowo.miaowo.base.extra.spPut
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.ViewBindHelper
import java.util.*

/**
 * 设置-用户设置
 * Created by luqin on 17-5-11.
 */

class AppSetting : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_setting_app, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sw_tab.isChecked = spGet(Const.SP_USE_TAB, false)
        sw_clean.isChecked = spGet(Const.SP_CLEAN_TOKENS, true)
        sw_tab.setOnClickListener { spPut(Const.SP_USE_TAB, sw_tab.isChecked) }
        sw_clean.setOnClickListener { spPut(Const.SP_CLEAN_TOKENS, sw_clean.isChecked) }

        rv_tokens.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
        val adapter = TokenAdapter()
        rv_tokens.adapter = adapter

        API.Users.getTokens {
            activity?.runOnUiThread {
            if (it.isEmpty()) activity?.handleError(R.string.err_get)
            else adapter.update(it)
            }
        }
    }

    private class TokenAdapter : RecyclerView.Adapter<ListHolder>() {
        private var mTokens: MutableList<String> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ListHolder(R.layout.list_token, parent)

        override fun onBindViewHolder(holder: ListHolder, position: Int) {
            val tk = mTokens[position]
            val isNew = tk == API.token.token
            holder.find<TextView>(R.id.token)?.text = tk
            ViewBindHelper.setVisible(holder[R.id.thisToken], isNew)
            ViewBindHelper.setVisible(holder[R.id.remove], !isNew)
            holder[R.id.remove]?.setOnClickListener {
                API.Users.removeToken(tk) {
                    Miao.i.runOnUiThread {
                        if (it == Const.RET_OK) {
                            val dPosition = mTokens.indexOf(tk)
                            if (dPosition < 0) return@runOnUiThread
                            mTokens.removeAt(dPosition)
                            notifyItemRemoved(dPosition)
                        } else Miao.i.handleError(it)
                    }
                }
            }
        }

        override fun getItemCount() = mTokens.size

        fun update(tokens: MutableList<String>) {
            mTokens = tokens
            notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(): AppSetting {
            val args = Bundle()
            val fragment = AppSetting()
            fragment.arguments = args
            return fragment
        }
    }
}
