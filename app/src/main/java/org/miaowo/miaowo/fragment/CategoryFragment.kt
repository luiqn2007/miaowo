package org.miaowo.miaowo.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.fragment_list.*
import okhttp3.ResponseBody
import org.json.JSONObject
import org.miaowo.miaowo.API
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.PostActivity
import org.miaowo.miaowo.activity.UserActivity
import org.miaowo.miaowo.adapter.TopicAdapter
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.base.extra.error
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.data.model.CategoryModel
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyCallback
import retrofit2.Call
import retrofit2.Response

class CategoryFragment : BaseListFragment() {
    companion object {
        private val sCategories = mutableMapOf<Int, CategoryFragment>()

        const val UNREAD = -1

        operator fun get(cid: Int) = sCategories[cid] ?: newInstance(cid)

        fun newInstance(cid: Int): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putString(Const.TAG, "${fragment.javaClass.name}_categoryId_$cid")
            args.putInt(Const.ID, cid)
            fragment.arguments = args
            sCategories[cid] = fragment
            return fragment
        }
    }

    private var mAdapter: TopicAdapter? = null
    private var mCategory: CategoryModel? = null
    private var mRefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCategory = ViewModelProviders.of(this)[CategoryModel::class.java]
        mCategory!![arguments?.getInt(Const.ID)!!].observe(this, Observer {
            val adapter = list.adapter as? TopicAdapter
            if (it != null && adapter != null) {
                onLoadmore()
                if (mRefresh) {
                    adapter.update(it.topics)
                    mRefresh = false
                } else {
                    adapter.append(it.topics)
                }
                loadOver()
            }
        })
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) onRefresh()
    }

    override fun setAdapter(list: RecyclerView) {
        mAdapter = TopicAdapter(true, arguments?.getInt(Const.ID) != UNREAD)
        list.adapter = mAdapter
    }

    override fun onLoadmore() {
        mCategory?.next(arguments?.getInt(Const.ID)!!)
    }

    override fun onRefresh() {
        mCategory?.first(arguments?.getInt(Const.ID)!!)
    }

    override fun onClickListener(view: View, position: Int): Boolean {
        mAdapter?.getItem(position)?.let {
            when (view.id) {
                R.id.head -> startActivity(Intent(App.i, UserActivity::class.java)
                        .putExtra(Const.TYPE, UserActivity.USER_FROM_NAME)
                        .putExtra(Const.NAME, it.user?.username ?: it.posts.firstOrNull()?.user?.username ?: ""))
                R.id.like -> API.Topics.follow(it.tid).enqueue(object : EmptyCallback<ResponseBody>() {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        val obj = JSONObject(response?.body()?.string())
                        if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                            var err = obj.getString("message")
                            if (err.isNullOrBlank()) err = obj.getString("code")
                            if (err.isNullOrBlank()) err = getString(R.string.err_ill)
                            error(err)
                        } else {
                            (view as? ImageView)?.setImageDrawable(IconicsDrawable(App.i, FontAwesome.Icon.faw_heart).color(Color.RED).actionBar())
                        }
                    }
                })
                else -> {
                    startActivity(Intent(context, PostActivity::class.java).putExtra(Const.ID, it.tid))
                }
            }
            return true
        }
        return false
    }
}