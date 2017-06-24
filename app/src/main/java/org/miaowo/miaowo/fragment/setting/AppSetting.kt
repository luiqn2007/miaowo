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
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.base.extra.spGet
import org.miaowo.miaowo.base.extra.spPut
import java.util.*

/**
 * 设置-用户设置
 * Created by luqin on 17-5-11.
 */

class AppSetting : Fragment() {

    private var mToken = API.token

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_setting_app, inflater, container)
    }
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        sw_tab.isChecked = spGet(Const.SP_USE_TAB, false)
        sw_clean.isChecked = spGet(Const.SP_CLEAN_TOKENS, true)
        sw_tab.setOnClickListener { spPut(Const.SP_USE_TAB, sw_tab.isChecked) }
        sw_clean.setOnClickListener { spPut(Const.SP_CLEAN_TOKENS, sw_clean.isChecked) }

        rv_tokens.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
        val adapter = TokenAdapter()
        rv_tokens.adapter = adapter

        API.Use.getTokens {
            if (it.isEmpty()) activity?.handleError(R.string.err_get)
            else adapter.update(it)
        }
    }

    private inner class TokenAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        internal var mTokens: MutableList<String> = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = object :
                RecyclerView.ViewHolder(LayoutInflater.from(App.i).inflate(R.layout.list_token, parent, false)) {}

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val v = holder.itemView
            val tv_token = v.findViewById(R.id.token) as TextView
            val token = mTokens[position]
            tv_token.text = token
            val equals = token == mToken
            v.findViewById(R.id.thisToken).visibility = if (equals) View.VISIBLE else View.GONE
            v.findViewById(R.id.remove).visibility = if (equals) View.GONE else View.VISIBLE
            v.findViewById(R.id.remove).setOnClickListener { v1 ->
                if (equals) v1.visibility = View.GONE
                else {
                    API.Use.removeToken(token) {
                        if ("ok" == it) {
                            mTokens.removeAt(position)
                            notifyDataSetChanged()
                        } else activity?.handleError(Exception(it))
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
