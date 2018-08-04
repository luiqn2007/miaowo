package org.miaowo.miaowo.activity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
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
import com.blankj.utilcode.util.FragmentUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.holder.BadgeStyle
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.data.bean.Category
import org.miaowo.miaowo.data.config.TextIconConfig
import org.miaowo.miaowo.fragment.CategoryFragment
import org.miaowo.miaowo.fragment.InboxFragment
import org.miaowo.miaowo.fragment.NotificationFragment
import org.miaowo.miaowo.fragment.website.BlogFragment
import org.miaowo.miaowo.fragment.website.FeedbackFragment
import org.miaowo.miaowo.fragment.website.StatusFragment
import org.miaowo.miaowo.handler.MainHandler
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.FabScrollBehavior
import org.miaowo.miaowo.util.ImageUtil
import com.mikepenz.materialdrawer.R as R2

class MainActivity : AppCompatActivity() {
    companion object {
        var CATEGORY_LIST: List<Category>? = null
    }

    private lateinit var mNavigation: Drawer
    private lateinit var mToolbarDrawerToggle: ActionBarDrawerToggle
    @Suppress("UNCHECKED_CAST")
    private val mItemStyle: SecondaryDrawerItem.() -> Unit = {
        withSelectedColor(Color.WHITE)
        withSelectedIconColor(Color.RED)
        withSelectedTextColor(Color.RED)
    }
    private val mBadgedItemStyle: SecondaryDrawerItem.() -> Unit = {
        mItemStyle(this)
        withBadgeStyle(BadgeStyle(R2.drawable.material_drawer_badge, Color.WHITE, Color.WHITE, Color.DKGRAY))
    }
    private val mSpannedStatus = SpannableStringBuilder("IS")
    private val mStatusSpan: ImageSpan
        get() {
            val color = when (API.user.status) {
                "online" -> ResourcesCompat.getColor(resources, R.color.colorOnline, null)
                "dnd" -> ResourcesCompat.getColor(resources, R.color.colorDnd, null)
                "away" -> ResourcesCompat.getColor(resources, R.color.colorAway, null)
                else -> ResourcesCompat.getColor(resources, R.color.colorHide, null)
            }
            val d = TextDrawable.builder().beginConfig().apply {
                textColor(color)
                useFont(FontAwesome().getTypeface(this@MainActivity))
            }.endConfig().buildRound(FontAwesome.Icon.faw_circle.character.toString(), Color.WHITE)
            return ImageSpan(d)
        }
    private val mHandler = MainHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        initNavigation()
    }

    // 初始化侧栏
    private fun initNavigation() {
        val drawerItems = arrayOfNulls<IDrawerItem<*, *>>(9 + (CATEGORY_LIST?.size ?: 0))
        drawerItems[0] = SecondaryDrawerItem().apply {
            mBadgedItemStyle(this)
            withTag(R.string.notification)
            withName(R.string.notification)
            withIcon(FontAwesome.Icon.faw_bell)
        }
        drawerItems[1] = SecondaryDrawerItem().apply {
            mBadgedItemStyle(this)
            withTag(R.string.inbox)
            withName(R.string.inbox)
            withIcon(FontAwesome.Icon.faw_inbox)
            withBadge("52")
        }
        drawerItems[2] = SecondaryDrawerItem().apply {
            mBadgedItemStyle(this)
            withTag(R.string.chat)
            withName(R.string.chat)
            withIcon(FontAwesome.Icon.faw_commenting)
            withBadge("25")
        }
        // square
        drawerItems[3] = SectionDrawerItem().apply {
            withName(R.string.square)
            withTag(R.string.square)
            withDivider(true)
        }
        CATEGORY_LIST?.forEachIndexed { index, category ->
            drawerItems[index + 4] = SecondaryDrawerItem().apply {
                withSelectedColor(Color.WHITE)
                withSelectedIconColor(Color.RED)
                withSelectedTextColor(Color.RED)
                withName(category.name)
                val icon = category.icon.toLowerCase().replace("-", "_").replace("fa_", "faw_")
                withIcon(FontAwesome.Icon.values().firstOrNull { it.name == icon }
                        ?: FontAwesome.Icon.faw_question)
                withIdentifier(category.cid.toLong())
                withTag(category)
            }
        }
        drawerItems[drawerItems.size - 5] = SecondaryDrawerItem().apply {
            withSelectedColor(Color.WHITE)
            withSelectedIconColor(Color.RED)
            withSelectedTextColor(Color.RED)
            withName(R.string.unread)
            withIcon(FontAwesome.Icon.faw_inbox)
            withTag(R.string.unread)
        }
        // site
        drawerItems[drawerItems.size - 4] = SectionDrawerItem().apply {
            withName(R.string.site)
            withTag(R.string.site)
            withDivider(true)
        }
        drawerItems[drawerItems.size - 3] = SecondaryDrawerItem().apply {
            mItemStyle(this)
            withTag(R.string.status)
            withName(R.string.status)
            withIcon(FontAwesome.Icon.faw_tasks)
            withBadge("√")
            withBadgeStyle(BadgeStyle(R2.drawable.material_drawer_badge, Color.WHITE, Color.WHITE, Color.GREEN))
        }
        drawerItems[drawerItems.size - 2] = SecondaryDrawerItem().apply {
            mItemStyle(this)
            withTag(R.string.blog)
            withName(R.string.blog)
            withIcon(FontAwesome.Icon.faw_rocket)
        }
        drawerItems[drawerItems.size - 1] = SecondaryDrawerItem().apply {
            mItemStyle(this)
            withTag(R.string.feedback)
            withName(R.string.feedback)
            withIcon(FontAwesome.Icon.faw_comments)
        }

        mNavigation = DrawerBuilder(this).apply {
            val user = API.user

            withHeaderPadding(true)
            withOnDrawerItemClickListener { _, _, drawerItem -> onChoose(drawerItem) }
            withShowDrawerOnFirstLaunch(true)

            withAccountHeader(AccountHeaderBuilder().apply {
                withActivity(this@MainActivity)
                withOnlyMainProfileImageVisible(true)
                withTextColor(Color.BLACK)
                withNameTypeface(Typeface.DEFAULT_BOLD)
                withEmailTypeface(Typeface.MONOSPACE)
                withHeaderBackground(
                        if (user.coverUrl.isBlank()) ImageHolder(R.drawable.def_bg)
                        else ImageHolder("${Const.URL_BASE}${user.coverUrl}")
                )
                addProfiles(ProfileDrawerItem().apply {
                    withName(user.username)
                    withNameShown(true)
                    withIdentifier(user.uid.toLong())
                    withSelectedBackgroundAnimated(true)
                    // icon
                    when {
                        user.picture.isNotBlank() -> withIcon("${Const.URL_BASE}${user.picture}")
                        user.iconText.isNotBlank() ->
                            withIcon(ImageUtil.textIcon(TextIconConfig(ImageUtil.colorFromUser(user.iconBgColor), Color.WHITE), user.iconText))
                        else -> withIcon(ImageUtil.textIcon(TextIconConfig(Color.BLACK, Color.WHITE), "M"))
                    }
                })
                addDrawerItems(*drawerItems)
                addStickyDrawerItems(
                        SecondaryDrawerItem().apply {
                            withSelectedColor(Color.WHITE)
                            withSelectedIconColor(Color.RED)
                            withSelectedTextColor(Color.RED)
                            withName(R.string.setting)
                            withTag(R.string.setting)
                            withIcon(FontAwesome.Icon.faw_cogs)
                        },
                        SecondaryDrawerItem().apply {
                            withSelectedColor(Color.WHITE)
                            withSelectedIconColor(Color.RED)
                            withSelectedTextColor(Color.RED)
                            withName("${getString(R.string.logout)}}")
                            withTag(R.string.logout)
                            withIcon(FontAwesome.Icon.faw_share_square)
                        }
                )
            }.build().apply {
                view.findViewById<TextView>(R2.id.material_drawer_account_header_name).textSize = 20f
                view.findViewById<TextView>(R2.id.material_drawer_account_header_email).text = mSpannedStatus
                setActiveProfile(user.uid.toLong())
            })
        }.build()
        // Users
        mSpannedStatus.setSpan(mStatusSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mSpannedStatus.replace(1, mSpannedStatus.length, getString(API.user.statusStr))
        mToolbarDrawerToggle = ActionBarDrawerToggle(this, mNavigation.drawerLayout, toolBar, R.string.toggle_toolbar_open, R.string.toggle_toolbar_close)
        mToolbarDrawerToggle.syncState()
        mNavigation.actionBarDrawerToggle = mToolbarDrawerToggle
    }

    private fun onChoose(drawerItem: IDrawerItem<out Any, out RecyclerView.ViewHolder>): Boolean {
        val tag = drawerItem.tag
        when (tag) {
            // 通知
            R.string.notification -> FragmentUtils.add(supportFragmentManager, NotificationFragment.newInstance(), R.id.container)
            // 收件箱
            R.string.inbox -> FragmentUtils.add(supportFragmentManager, InboxFragment.newInstance(), R.id.container)
            // 聊天
            R.string.chat -> startActivity(Intent(this, ChatActivity::class.java).putExtra(Const.TYPE, ChatActivity.OPEN_LIST))
            // 状态
            R.string.status -> FragmentUtils.add(supportFragmentManager, StatusFragment.newInstance(), R.id.container)
            // Blog
            R.string.blog -> FragmentUtils.add(supportFragmentManager, BlogFragment.newInstance(), R.id.container)
            // 反馈
            R.string.feedback -> FragmentUtils.add(supportFragmentManager, FeedbackFragment.newInstance(), R.id.container)
            // 登出
            R.string.logout -> mHandler.logout()
            // 设置
            R.string.setting -> startActivity(Intent(this, SettingActivity::class.java))
            // 未读
            R.string.unread -> {
                toolBar.setTitle(R.string.unread)
                fab.visibility = View.GONE
                FragmentUtils.add(supportFragmentManager, CategoryFragment[CategoryFragment.UNREAD], R.id.container)
            }
            // 其他各 Category
            is Category -> {
                ctl.title = tag.name
                fab.run {
                    visibility = View.VISIBLE
                    (layoutParams as CoordinatorLayout.LayoutParams).behavior = FabScrollBehavior(applicationContext, null)
                    setOnClickListener {
                        startActivity(Intent(this@MainActivity, SendActivity::class.java)
                                .putExtra(Const.ID, tag.cid)
                                .putExtra(Const.TYPE, SendActivity.TYPE_POST))
                    }
                }
                FragmentUtils.add(supportFragmentManager, CategoryFragment[tag.cid], R.id.container)
            }
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
}