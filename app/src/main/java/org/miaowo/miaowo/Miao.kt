package org.miaowo.miaowo

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.MenuItem
import android.widget.TextView
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badge
import co.zsmb.materialdrawerkt.draweritems.badgeable.SecondaryDrawerItemKt
import co.zsmb.materialdrawerkt.draweritems.badgeable.secondaryItem
import co.zsmb.materialdrawerkt.draweritems.sectionItem
import com.amulyakhare.textdrawable.TextDrawable
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.holder.BadgeStyle
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.sdsmdg.tastytoast.TastyToast
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_miao.*
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.bean.config.TextIconConfig
import org.miaowo.miaowo.bean.data.Category
import org.miaowo.miaowo.fragment.*
import org.miaowo.miaowo.fragment.chat.ChatListFragment
import org.miaowo.miaowo.fragment.user.UserFragment
import org.miaowo.miaowo.fragment.website.BlogFragment
import org.miaowo.miaowo.fragment.website.FeedbackFragment
import org.miaowo.miaowo.fragment.website.StatusFragment
import org.miaowo.miaowo.fragment.welcome.IndexFragment
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.MiaoHandler
import org.miaowo.miaowo.util.ImageUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates
import com.mikepenz.materialdrawer.R as R2

class Miao(private val handler: MiaoHandler) : AppCompatActivity(), IMiaoListener by handler {
    @Suppress("unused")
    constructor() : this(MiaoHandler())

    companion object {
        var i by Delegates.notNull<Miao>()
    }

    init {
        i = this
    }

    // Fragment
    private val mFgNotification = NotificationFragment.newInstance()
    private val mFgInbox = InboxFragment.newInstance()
    private val mFgChat = ChatListFragment.newInstance()
    private val mFgUser = UserFragment.newInstance()
    private val mFgSetting = SettingFragment.newInstance()
    private val mFgWebsiteStatus = StatusFragment.newInstance()
    private val mFgWebsiteBlog = BlogFragment.newInstance()
    private val mFgWebsiteFeedback = FeedbackFragment.newInstance()
    private val mFgCategory = CategoryFragment.newInstance()
    private val mFgUnread = UnreadFragment.newInstance()

    internal lateinit var mNavigation: Drawer
    internal val mLoginUser = mutableListOf<String>()
    internal val mLoginResult = mutableListOf<Pair<Long, Boolean>>()
    private lateinit var mNavigationAccount: AccountHeader
    private lateinit var mToolbarDrawerToggle: ActionBarDrawerToggle
    private val mItemStyle: SecondaryDrawerItemKt.() -> Unit = {
        selectedColor = Color.WHITE.toLong()
        selectedIconColor = Color.RED.toLong()
        selectedTextColor = Color.RED.toLong()
    }
    private val mBadgedItemStyle: SecondaryDrawerItemKt.() -> Unit = {
        mItemStyle(this)
        badgeStyle = BadgeStyle(R2.drawable.material_drawer_badge, Color.WHITE, Color.WHITE, Color.DKGRAY)
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
            val d = TextDrawable.builder().beginConfig().run {
                textColor(color)
                useFont(FontAwesome().getTypeface(this@Miao))
                endConfig()
            }.buildRound(FontAwesome.Icon.faw_circle.character.toString(), Color.WHITE)
            return ImageSpan(d)
        }
    private val mStatusText: String
        get() {
            return when (API.user.status) {
                "online" -> getString(R.string.statusOnline)
                "dnd" -> getString(R.string.statusDnd)
                "away" -> getString(R.string.statusAway)
                else -> getString(R.string.statusOffline)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_miao)
        // 首次启动
        if (spGet(Const.SP_FIRST_BOOT, true)) {
            AlertDialog.Builder(this)
                    .setTitle("人人都有\"萌\"的一面")
                    .setMessage("\"聪明\"解决人类37%的问题；\n\"萌\"负责剩下的85%")
                    .setNegativeButton("我最萌(•‾̑⌣‾̑•)✧˖°") { dialog, _ -> dialog.dismiss() }
                    .show()
            spPut(Const.SP_FIRST_BOOT, false)
        }
        setSupportActionBar(toolBar)
        // Navigation
        initNavigation()
        // User
        updateUserProfile()
        login(null, null)
        // Toolbar
        initToolbar()
        // FloatingActionButton
        initFab()
        // 加载欢迎界面
        supportFragmentManager.registerFragmentLifecycleCallbacks(MyFragmentLifeRecycleCallback, true)
        loadFragment(IndexFragment.INSTANCE)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        // Toolbar
        mToolbarDrawerToggle.syncState()
        // 更新
        checkUpdate()
    }

    // 初始化侧栏
    private fun initNavigation() {
        mNavigation = drawer {
            closeOnClick = true
            headerPadding = false
            mNavigationAccount = accountHeader {
                selectionListEnabledForSingleProfile = false
                profileImagesVisible = true
                profileImagesClickable = true
                onlyMainProfileImageVisible = true
                textColor = Color.BLACK.toLong()
                nameTypeface = Typeface.DEFAULT_BOLD
                emailTypeface = Typeface.MONOSPACE
                onSelectionViewClick { _, _ ->
                    if (API.isLogin) {
                        mNavigation.closeDrawer()
                        if (mLoginUser.isNotEmpty())
                            AlertDialog.Builder(this@Miao).setItems(mLoginUser.toTypedArray()) { dialog, which ->
                                val ret = mLoginResult[which]
                                toast("""
                                    用户名: ${mLoginUser[which]}
                                    登录时间: ${SimpleDateFormat.getDateTimeInstance().format(Date(ret.first))}
                                    登录${if (ret.second) "成功" else "失败"}
                                """.trimIndent(), TastyToast.INFO)
                                dialog.dismiss()
                            }.create().show()
                        true
                    } else {
                        toast(R.string.please_login, TastyToast.ERROR)
                        false
                    }
                }
                onProfileImageClick { _, _, _ ->
                    if (API.isLogin) {
                        mNavigation.closeDrawer()
                        loadFragment(mFgUser)
                        true
                    } else {
                        toast(R.string.please_login, TastyToast.ERROR)
                        false
                    }
                }
            }
            onItemClick { _, _, drawerItem ->
                onChoose(drawerItem)
                true
            }
            showUntilDraggedOpened = false
            showOnFirstLaunch = true

            // 选择时可能要传递 Category, 不想再做个列表了, 统一使用 tag 进行判断
            secondaryItem {
                mBadgedItemStyle(this)
                tag = R.string.notification
                nameRes = R.string.notification
                iconDrawable = IconicsDrawable(this@Miao, FontAwesome.Icon.faw_bell).actionBar()
            }
            secondaryItem {
                mBadgedItemStyle(this)
                tag = R.string.inbox
                nameRes = R.string.inbox
                iconDrawable = IconicsDrawable(this@Miao, FontAwesome.Icon.faw_inbox).actionBar()
                badge("52")
            }
            secondaryItem {
                mBadgedItemStyle(this)
                tag = R.string.chat
                nameRes = R.string.chat
                iconDrawable = IconicsDrawable(this@Miao, FontAwesome.Icon.faw_commenting).actionBar()
                badge("52")
            }
            // square
            sectionItem(R.string.square) {
                tag = R.string.square
                identifier = R.string.square.toLong()
                divider = true
            }
            // site
            sectionItem(R.string.site) {
                tag = R.string.site
                identifier = R.string.site.toLong()
                divider = true
            }
            secondaryItem {
                mItemStyle(this)
                nameRes = R.string.status
                tag = R.string.status
                iconDrawable = IconicsDrawable(this@Miao, FontAwesome.Icon.faw_tasks).actionBar()
                badge("√")
                badgeStyle = BadgeStyle(R2.drawable.material_drawer_badge, Color.WHITE, Color.WHITE, Color.GREEN)
            }
            secondaryItem {
                mItemStyle(this)
                nameRes = R.string.blog
                tag = R.string.blog
                iconDrawable = IconicsDrawable(this@Miao, FontAwesome.Icon.faw_rocket).actionBar()
            }
            secondaryItem {
                mItemStyle(this)
                nameRes = R.string.feedback
                tag = R.string.feedback
                iconDrawable = IconicsDrawable(this@Miao, FontAwesome.Icon.faw_comments).actionBar()
            }
        }
        mNavigation.run {
            addStickyFooterItem(SecondaryDrawerItem().apply {
                        withSelectedColor(Color.WHITE)
                        withSelectedIconColor(Color.RED)
                        withSelectedTextColor(Color.RED)
                        withName(R.string.setting)
                        withTag(R.string.setting)
                        withIcon(FontAwesome.Icon.faw_cogs)
            })
            addStickyFooterItem(SecondaryDrawerItem().apply {
                        withSelectedColor(Color.WHITE)
                        withSelectedIconColor(Color.RED)
                        withSelectedTextColor(Color.RED)
                        withName("${getString(R.string.logout)} / ${getString(R.string.exit)}")
                        withTag(R.string.logout)
                        withIcon(FontAwesome.Icon.faw_share_square)
            })
            setOnDrawerNavigationListener {
                onBackPressed()
                true
            }
        }

        // 原本显示 email 的地方显示在线状态
        with(mNavigationAccount.view) {
            findViewById<TextView>(R2.id.material_drawer_account_header_name).textSize = 20f
            findViewById<TextView>(R2.id.material_drawer_account_header_email).text = mSpannedStatus
        }
        lInfo("initNavigation finished")
    }

    // 更新侧栏用户信息内容
    fun updateUserProfile() {
        val user = API.user
        // background
        if (user.coverUrl.isNotBlank())
            mNavigationAccount.setHeaderBackground(ImageHolder(getString(R.string.url_home_head, user.coverUrl)))
        else mNavigationAccount.setHeaderBackground(ImageHolder(R.drawable.def_bg))
        // profile
        val c: Boolean = mNavigationAccount.profiles.any { it.identifier == user.uid.toLong() }
        if (!c) {
            mNavigationAccount.addProfile(ProfileDrawerItem().apply {
                withName(user.username)
                withNameShown(true)
                // withIcon
                when {
                    user.picture.isNotBlank() -> withIcon(getString(R.string.url_home_head, user.picture))
                    user.iconText.isNotBlank() ->
                        withIcon(ImageUtil.textIcon(TextIconConfig(ImageUtil.colorFromUser(user.iconBgColor), Color.WHITE), user.iconText))
                    else -> withIcon(ImageUtil.textIcon(TextIconConfig(Color.BLACK, Color.WHITE), "M"))
                }
                withIdentifier(user.uid.toLong())
                withSelectedBackgroundAnimated(true)
                withNameShown(true)
            }, 0)
            mSpannedStatus.setSpan(mStatusSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            mSpannedStatus.replace(1, mSpannedStatus.length, mStatusText)
        }
        mNavigationAccount.setActiveProfile(user.uid.toLong())
        lInfo("updateUserProfile: finished")
    }

    // 更新侧栏
    fun updateNavigationItems(categories: List<Category>) {
        // 清空原 Category 数据
        val delIds = mNavigation.drawerItems
                .filter { it.tag is Category }
                .map { it.identifier }
        mNavigation.removeItems(*delIds.toLongArray())
        // 添加新信息
        val insertIndex = mNavigation.getPosition(mNavigation.getDrawerItem(R.string.square)) + 1
        categories.forEachIndexed { index, category ->
            mNavigation.addItemAtPosition(
                    SecondaryDrawerItem().apply {
                        withSelectedColor(Color.WHITE)
                        withSelectedIconColor(Color.RED)
                        withSelectedTextColor(Color.RED)
                        withName(category.name)
                        val icon = category.icon.toLowerCase().replace("-", "_").replace("fa_", "faw_")
                        withIcon(FontAwesome.Icon.values().firstOrNull { it.name == icon }
                                ?: FontAwesome.Icon.faw_question)
                        withIdentifier(category.cid.toLong())
                        withTag(category)
                    }, insertIndex + index
            )
        }
        if (categories.isNotEmpty()) {

        }
        mNavigation.adapter.notifyAdapterDataSetChanged()
        // Fragment
        if (categories.isNotEmpty()) {
            mNavigation.addItemAtPosition(
                    SecondaryDrawerItem().apply {
                        withSelectedColor(Color.WHITE)
                        withSelectedIconColor(Color.RED)
                        withSelectedTextColor(Color.RED)
                        withName(R.string.unread)
                        withIcon(FontAwesome.Icon.faw_inbox)
                        withTag(R.string.unread)
                    }, insertIndex + categories.size
            )
//            for (i in 0..supportFragmentManager.backStackEntryCount)
//                supportFragmentManager.popBackStackImmediate()
            mNavigation.setSelection(categories[0].cid.toLong())
        }
    }

    // 初始化 Toolbar
    private fun initToolbar() {
        toolBar.setTitleTextColor(ResourcesCompat.getColor(resources, R.color.md_white_1000, null))
        mToolbarDrawerToggle = ActionBarDrawerToggle(this, mNavigation.drawerLayout, toolbar, R.string.toggle_toolbar_open, R.string.toggle_toolbar_close)
        mToolbarDrawerToggle.syncState()
        mNavigation.actionBarDrawerToggle = mToolbarDrawerToggle
        with(ctl) {
            isTitleEnabled = false
            setCollapsedTitleTextColor(ResourcesCompat.getColor(resources, R.color.titleText, null))
            setExpandedTitleTextColor(ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.titleTextExpanded, null)))
            setContentScrimColor(ResourcesCompat.getColor(resources, R.color.toolbar, null))
        }
        lInfo("initToolbar: finished")
    }

    // 初始化 FloatActionButton
    private fun initFab() {
        with(fab) {
            with(layoutParams as CoordinatorLayout.LayoutParams) {
                width = resources.getDimensionPixelSize(R.dimen.fabSize)
                height = resources.getDimensionPixelSize(R.dimen.fabSize)
                rightMargin = resources.getDimensionPixelSize(R.dimen.fabMargin)
                bottomMargin = resources.getDimensionPixelSize(R.dimen.fabMargin)

            }
            compatElevation = resources.getDimension(R.dimen.fabElevation)
            backgroundTintList = ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.fabBackground, null))
            rippleColor = ResourcesCompat.getColor(resources, R.color.fabRipple, null)
            setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_create, null))
            setOnClickListener {
                toast("FAB", TastyToast.SUCCESS)
            }
        }
        lInfo("initFab: finished")
    }

    // 检查更新
    private fun checkUpdate() {
        API.Doc.version {
            if (it != null && it.version > packageManager.getPackageInfo(App.i.packageName, 0).versionCode) {
                with(AlertDialog.Builder(App.i)) {
                    setTitle(it.versionName)
                    setMessage(getString(R.string.update_msg, it.message))
                    setPositiveButton(R.string.update_start) { dialog, _ ->
                        App.i.update(it.url)
                        dialog.dismiss()
                    }
                    setNegativeButton(R.string.update_later, null)
                }.show()
            }
        }
        lInfo("checkUpdate: finished")
    }

    private fun onChoose(drawerItem: IDrawerItem<out Any, out RecyclerView.ViewHolder>) {
        lInfo("drawerClick: $drawerItem")
        val tag = drawerItem.tag
        when (tag) {
        // 通知
            R.string.notification -> if (API.isLogin) loadFragment(mFgNotification)
        // 收件箱
            R.string.inbox -> if (API.isLogin) loadFragment(mFgInbox)
        // 聊天
            R.string.chat -> if (API.isLogin) loadFragment(mFgChat)
        // 状态
            R.string.status -> if (API.isLogin) loadFragment(mFgWebsiteStatus)
        // Blog
            R.string.blog -> if (API.isLogin) loadFragment(mFgWebsiteBlog)
        // 反馈
            R.string.feedback -> if (API.isLogin) loadFragment(mFgWebsiteFeedback)
        // 登出
            R.string.logout -> if (API.isLogin) {
                API.Profile.logout()
                loadFragment(IndexFragment.INSTANCE)
            } else finish()
        // 设置
            R.string.setting -> if (API.isLogin) loadFragment(mFgSetting)
        // 未读
            R.string.unread -> if (API.isLogin) loadFragment(mFgUnread)
        // 其他各 Category
            is Category -> {
                if (API.isLogin) {
                    mFgCategory.loadCategory(tag)
                    loadFragment(mFgCategory)
                }
            }
        }
        mNavigation.closeDrawer()
    }

    override fun onBackPressed() {
        when {
            mNavigation.isDrawerOpen -> mNavigation.closeDrawer()
            supportFragmentManager.backStackEntryCount > 1 -> supportFragmentManager.popBackStack()
            API.isLogin -> login(null, null)
            else -> finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = mToolbarDrawerToggle.onOptionsItemSelected(item)

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        mToolbarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onDestroy() {
        RichText.recycle()
        super.onDestroy()
    }
}