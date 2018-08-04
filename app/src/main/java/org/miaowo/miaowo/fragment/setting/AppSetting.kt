package org.miaowo.miaowo.fragment.setting

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.ActivityUtils.getTopActivity
import kotlinx.android.synthetic.main.fragment_setting_app.*
import okhttp3.ResponseBody
import org.json.JSONObject
import org.miaowo.miaowo.API
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.base.extra.error
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.other.ActivityCallback
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyCallback
import retrofit2.Call
import retrofit2.Response

/**
 * 设置-用户设置
 * Created by luqin on 17-5-11.
 */

class AppSetting : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(R.layout.fragment_setting_app, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sw_clean.isChecked = App.SP.getBoolean(Const.SP_CLEAN_TOKENS, true)
        sw_clean.setOnClickListener { App.SP.put(Const.SP_CLEAN_TOKENS, sw_clean.isChecked) }

        sw_hide.isChecked = App.SP.getBoolean(Const.SP_HIDE_BODY, false)
        sw_hide.setOnClickListener {
            val ret = sw_hide.isChecked
            App.SP.put(Const.SP_HIDE_BODY, ret)
            val visibility = if (ret) View.GONE else View.VISIBLE
            title_hide.visibility = visibility
            rg_hide.visibility = visibility
        }

        rg_hide.check(when (App.SP.getInt(Const.SP_SHOW_TYPE, Const.CBODY_LAST)) {
            Const.CBODY_FIRST -> R.id.rb_first
            Const.CBODY_CONTENT -> R.id.rb_content
            else -> R.id.rb_last
        })
        rg_hide.setOnCheckedChangeListener { _, checkedId ->
            App.SP.put(Const.SP_SHOW_TYPE, when (checkedId) {
                R.id.rb_first -> Const.CBODY_FIRST
                R.id.rb_content -> Const.CBODY_CONTENT
                else -> Const.CBODY_LAST
            })
        }

        rv_tokens.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically() = false
        }
        val adapter = TokenAdapter()
        rv_tokens.adapter = adapter
    }

    private class TokenAdapter : RecyclerView.Adapter<ListHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                ListHolder(R.layout.list_token, parent)

        override fun onBindViewHolder(holder: ListHolder, position: Int) {
            val tk = API.token[position]
            holder.find<TextView>(R.id.token)?.text = tk
            holder[R.id.remove]?.setOnClickListener {
                API.Users.tokenRemove(tk, API.user.uid).enqueue(object : ActivityCallback<ResponseBody>(getTopActivity()) {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        val obj = JSONObject(response?.body()?.string())
                        if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                            var err = obj.getString("message")
                            if (err.isNullOrBlank()) err = obj.getString("code")
                            if (err.isNullOrBlank()) err = activity.getString(R.string.err_ill)
                            getTopActivity().error(err)
                        } else {
                            API.token.remove(tk)
                            notifyDataSetChanged()
                        }
                    }
                })
            }
        }

        override fun getItemCount() = API.token.size
    }

    companion object {
        fun newInstance(): AppSetting {
            val args = Bundle()
            val fragment = AppSetting()
            args.putString(Const.TAG, fragment.javaClass.name)
            fragment.arguments = args
            return fragment
        }
    }
}
