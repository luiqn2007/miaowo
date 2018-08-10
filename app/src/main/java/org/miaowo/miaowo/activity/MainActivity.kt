package org.miaowo.miaowo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.amulyakhare.textdrawable.TextDrawable
import com.blankj.utilcode.util.KeyboardUtils
import com.liaoinstan.springview.widget.SpringView
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.TopicAdapter
import org.miaowo.miaowo.data.bean.Category
import org.miaowo.miaowo.data.model.CategoryModel
import org.miaowo.miaowo.handler.MainHandler
import org.miaowo.miaowo.other.Const
import com.mikepenz.materialdrawer.R as R2

class MainActivity : AppCompatActivity(), SpringView.OnFreshListener {
    companion object {
        var CATEGORY_LIST: List<Category>? = null
    }

    private lateinit var mNavigation: Drawer
    private lateinit var mToolbarDrawerToggle: ActionBarDrawerToggle
    private lateinit var mCategoryModel: CategoryModel
    private val mSpannedStatus = SpannableStringBuilder("IS")
    private val mStatusSpan: ImageSpan
        get() {
            val d = TextDrawable.builder().beginConfig().apply {
                textColor(ResourcesCompat.getColor(resources, API.user.statusColor, null))
                useFont(FontAwesome().getTypeface(this@MainActivity))
            }.endConfig().buildRound(FontAwesome.Icon.faw_circle.character.toString(), Color.WHITE)
            return ImageSpan(d)
        }
    private val mHandler = MainHandler(this)
    private val mAdapter = TopicAdapter(true, false)
    private var mCategoryRefresh = false
    private var mLoadedCid = Category.UNREAD_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCategoryModel = ViewModelProviders.of(this)[CategoryModel::class.java].apply {
            category.observe(this@MainActivity, Observer {
                if (it == null) {
                    mAdapter.clear()
                    ctl.title = ""
                    fab.visibility = View.GONE
                } else {
                    if (mCategoryRefresh || mLoadedCid != it.cid) {
                        mLoadedCid = it.cid
                        mAdapter.update(it.topics)
                    } else {
                        mAdapter.append(it.topics)
                    }
                    if (mLoadedCid == Category.UNREAD_ID) {
                        ctl.title = getString(R.string.unread)
                        fab.visibility = View.GONE
                    } else {
                        ctl.title = it.name
                        fab.visibility = View.VISIBLE
                    }
                }
                springView.onFinishFreshAndLoad()
            })
        }
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        initNavigation()
        list.adapter = mAdapter
        springView.setListener(this@MainActivity)
        onRefresh()
    }

    @Suppress("UNUSED_PARAMETER")
    fun fabClick(view: View) {
        startActivity(Intent(this@MainActivity, SendActivity::class.java)
                .putExtra(Const.ID, mLoadedCid)
                .putExtra(Const.TYPE, SendActivity.TYPE_POST))
    }

    // 初始化侧栏
    private fun initNavigation() {
        mNavigation = mHandler.inflateNavigate(R.xml.navigation)
        mNavigation.header.apply {
            findViewById<TextView>(R2.id.material_drawer_account_header_name).textSize = 20f
            findViewById<TextView>(R2.id.material_drawer_account_header_email).text = mSpannedStatus
        }
        // Users
        mSpannedStatus.setSpan(mStatusSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mSpannedStatus.replace(1, mSpannedStatus.length, getString(API.user.statusStr))
        mToolbarDrawerToggle = ActionBarDrawerToggle(this, mNavigation.drawerLayout, toolBar, R.string.toggle_toolbar_open, R.string.toggle_toolbar_close)
        mToolbarDrawerToggle.syncState()
        mNavigation.actionBarDrawerToggle = mToolbarDrawerToggle
    }

    @Suppress("unused", "UNUSED_PARAMETER")
    fun onChoose(view: View?, position: Int?, drawerItem: IDrawerItem<out Any, out RecyclerView.ViewHolder>): Boolean {
        val tag = drawerItem.tag
        when (tag) {
            // 通知
            R.string.notification -> startActivity(Intent(this, ListActivity::class.java).putExtra(Const.TYPE, ListActivity.TYPE_NOTIFICATION))
            // 收件箱
            R.string.inbox -> startActivity(Intent(this, ListActivity::class.java).putExtra(Const.TYPE, ListActivity.TYPE_INBOX))
            // 聊天
            R.string.chat -> startActivity(Intent(this, ChatActivity::class.java).putExtra(Const.TYPE, ChatActivity.OPEN_LIST))
            // 状态
            R.string.status -> startActivity(Intent(this, ListActivity::class.java).putExtra(Const.TYPE, ListActivity.TYPE_STATUS))
            // Blog
            R.string.blog -> startActivity(Intent(this, ListActivity::class.java).putExtra(Const.TYPE, ListActivity.TYPE_BLOG))
            // 反馈
            R.string.feedback -> startActivity(Intent(this, ListActivity::class.java).putExtra(Const.TYPE, ListActivity.TYPE_FEEDBACK))
            // 登出
            R.string.logout -> mHandler.logout()
            // 设置
            R.string.setting -> startActivity(Intent(this, SettingActivity::class.java))
            // 未读
            R.string.unread -> mCategoryModel.load(Category.UNREAD_ID)
            // 其他各 Category
            is Category -> mCategoryModel.load(tag.cid)
        }
        mNavigation.closeDrawer()
        return true
    }

    override fun onBackPressed() {
        when {
            KeyboardUtils.isSoftInputVisible(this) -> KeyboardUtils.hideSoftInput(this)
            mNavigation.isDrawerOpen -> mNavigation.closeDrawer()
            else -> mHandler.logout()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = mToolbarDrawerToggle.onOptionsItemSelected(item)

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mToolbarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onLoadmore() {
        mCategoryRefresh = false
        mCategoryModel.next()
    }

    override fun onRefresh() {
        mCategoryRefresh = true
        mCategoryModel.first()
    }
}