package org.miaowo.miaowo.fragment.setting

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_setting_app.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.databinding.ListTokenBinding
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.base.ListBindingHolder
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.base.App
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
        val adapter = TokenAdapter(App.i)
        rv_tokens.adapter = adapter

        API.Users.getTokens {
            activity?.runOnUiThread {
            if (it.isEmpty()) activity?.handleError(R.string.err_get)
            else adapter.update(it)
            }
        }
    }

    private class TokenAdapter(val context: Context) : RecyclerView.Adapter<ListBindingHolder<ListTokenBinding>>() {
        private var mTokens: MutableList<String> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ListBindingHolder<ListTokenBinding>(context, R.layout.list_token, parent, false)

        override fun onBindViewHolder(holder: ListBindingHolder<ListTokenBinding>, position: Int) {
            val tk = mTokens[position]
            with(holder.binder) {
                setToken(tk)
                isNew = tk == API.user.token
                remove.setOnClickListener {
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
