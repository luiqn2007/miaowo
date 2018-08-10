package org.miaowo.miaowo.handler

import android.content.Intent
import android.content.res.XmlResourceParser
import android.graphics.Color
import android.graphics.Typeface
import android.support.annotation.XmlRes
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.holder.BadgeStyle
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import org.miaowo.miaowo.API
import org.miaowo.miaowo.activity.MainActivity
import org.miaowo.miaowo.activity.WelcomeActivity
import org.miaowo.miaowo.data.bean.User
import org.miaowo.miaowo.data.config.TextIconConfig
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.ImageUtil
import com.mikepenz.materialdrawer.R as R2

class MainHandler(val activity: MainActivity) {
    private val mTokenCleanList = mutableMapOf<Int, String>()
    private val mActivity = activity

    fun logout() {
        mTokenCleanList[API.user.uid] = API.token.first()
        API.token.clear()
        API.user = User.logout
        if (!ActivityUtils.isActivityExistsInStack(WelcomeActivity::class.java)) {
            mActivity.startActivity(Intent(mActivity.applicationContext, WelcomeActivity::class.java))
        }
        mActivity.finish()
    }

    fun inflateNavigate(@XmlRes xml: Int): Drawer {
        val parser = mActivity.resources.getXml(xml)
        var event = parser.eventType
        // navigate
        val drawer = DrawerBuilder(mActivity)
        val categories = mutableListOf<IDrawerItem<*, *>>()
        val stickies = mutableListOf<IDrawerItem<*, *>>()
        val items = mutableListOf<IDrawerItem<*, *>>()
        var header: AccountHeaderBuilder? = null
        var profile: IProfile<*>? = null
        var item: IDrawerItem<*, *>? = null
        var style: BadgeStyle? = null
        var addToSticky = false
        var addToItem = false
        while (event != XmlResourceParser.END_DOCUMENT) {
            when (event) {
                XmlResourceParser.START_TAG -> {
                    when (parser.name) {
                        "Drawer" -> drawer.apply {
                            (0 until parser.attributeCount).forEach {
                                when (parser.getAttributeName(it)) {
                                    "headerPadding" -> withHeaderPadding(parser.getAttributeBooleanValue(it, false))
                                    "onDrawerItemClickListener" -> withOnDrawerItemClickListener { view, position, drawerItem ->
                                        mActivity.javaClass
                                                .getMethod(parser.getAttributeValue(it), View::class.java, Int::class.java, IDrawerItem::class.java)
                                                .invoke(mActivity, view, position, drawerItem)
                                                as? Boolean ?: false
                                    }
                                    "showDrawerOnFirstLaunch" -> withShowDrawerOnFirstLaunch(parser.getAttributeBooleanValue(it, false))
                                }
                            }
                        }
                        "AccountHeader" -> header = AccountHeaderBuilder().withActivity(mActivity).apply {
                            (0 until parser.attributeCount).forEach {
                                when (parser.getAttributeName(it)) {
                                    "onlyMainProfileImageVisible" -> withOnlyMainProfileImageVisible(parser.getAttributeBooleanValue(it, false))
                                    "textColor" -> withTextColor(getField<Color, Int>(parser.getAttributeValue(it)))
                                    "nameTypeface" -> withNameTypeface(getField<Typeface, Typeface>(parser.getAttributeValue(it)))
                                    "emailTypeface" -> withNameTypeface(getField<Typeface, Typeface>(parser.getAttributeValue(it)))
                                }
                            }
                        }
                        "ProfileDrawerItem" -> profile = ProfileDrawerItem().apply {
                            (0 until parser.attributeCount).forEach {
                                when (parser.getAttributeName(it)) {
                                    "selectedBackgroundAnimated" -> withSelectedBackgroundAnimated(parser.getAttributeBooleanValue(it, false))
                                    "nameShown" -> withNameShown(parser.getAttributeBooleanValue(it, false))
                                }
                            }
                        }
                        "DrawerItems" -> addToItem = true
                        "SecondaryDrawerItem" -> item = SecondaryDrawerItem().apply {
                            (0 until parser.attributeCount).forEach {
                                when (parser.getAttributeName(it)) {
                                    "name" -> {
                                        val id = parser.getAttributeValue(it).substring(1).toInt()
                                        withName(id)
                                        withTag(id)
                                    }
                                    "icon" -> withIcon(FontAwesome.Icon.valueOf(parser.getAttributeValue(it)))
                                    "selectedColor" -> withSelectedColor(getField<Color, Int>(parser.getAttributeValue(it)))
                                    "selectedIconColor" -> withSelectedIconColor(getField<Color, Int>(parser.getAttributeValue(it)))
                                    "selectedTextColor" -> withSelectedTextColor(getField<Color, Int>(parser.getAttributeValue(it)))
                                    "badge" -> withBadge(parser.getAttributeValue(it))
                                }
                            }
                        }
                        "BadgeStyle" -> style = BadgeStyle().withGradientDrawable(R2.drawable.material_drawer_badge).apply {
                            (0 until parser.attributeCount).forEach {
                                when (parser.getAttributeName(it)) {
                                    "color" -> withColor(getField<Color, Int>(parser.getAttributeValue(it)))
                                    "pressedColor" -> withColorPressed(getField<Color, Int>(parser.getAttributeValue(it)))
                                    "textColor" -> withTextColor(getField<Color, Int>(parser.getAttributeValue(it)))
                                }
                            }
                        }
                        "SectionDrawerItem" -> item = SectionDrawerItem().apply {
                            (0 until parser.attributeCount).forEach {
                                when (parser.getAttributeName(it)) {
                                    "name" -> {
                                        val id = parser.getAttributeValue(it).substring(1).toInt()
                                        withName(id)
                                        withTag(id)
                                    }
                                    "divider" -> withDivider(parser.getAttributeBooleanValue(it, false))
                                }
                            }
                        }
                        "CategoryDrawerItems" -> MainActivity.CATEGORY_LIST?.map { category ->
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
                            }
                        }
                        "StickyDrawerItems" -> addToSticky = true
                    }
                }

                XmlResourceParser.TEXT -> {
                    when (parser.name) {
                        "ProfileDrawerItem" -> {
                            profile?.apply {
                                val user = API.user
                                withName(user.username)
                                withIdentifier(user.uid.toLong())
                                // icon
                                when {
                                    user.picture.isNotBlank() -> withIcon("${Const.URL_BASE}${user.picture}")
                                    user.iconText.isNotBlank() ->
                                        withIcon(ImageUtil.textIcon(TextIconConfig(ImageUtil.colorFromUser(user.iconBgColor), Color.WHITE), user.iconText))
                                    else -> withIcon(ImageUtil.textIcon(TextIconConfig(Color.BLACK, Color.WHITE), "M"))
                                }
                            }
                        }
                        "CategoryDrawerItems" -> {
                            MainActivity.CATEGORY_LIST?.forEach { category ->
                                categories.add(SecondaryDrawerItem().apply {
                                    withSelectedColor(Color.WHITE)
                                    withSelectedIconColor(Color.RED)
                                    withSelectedTextColor(Color.RED)
                                    withName(category.name)
                                    val icon = category.icon.toLowerCase().replace("-", "_").replace("fa_", "faw_")
                                    withIcon(FontAwesome.Icon.values().firstOrNull { it.name == icon }
                                            ?: FontAwesome.Icon.faw_question)
                                    withIdentifier(category.cid.toLong())
                                    withTag(category)
                                })
                            }
                        }
                    }
                }

                XmlResourceParser.END_TAG -> {
                    when (parser.name) {
                        "AccountHeader" -> {
                            if (header != null) {
                                val account = header.build()
                                account.setActiveProfile(API.user.uid.toLong())
                                drawer.withAccountHeader(account)
                            }
                        }
                        "ProfileDrawerItem" -> header?.addProfiles(profile)
                        "DrawerItems" -> {
                            addToItem = false
                            drawer.withDrawerItems(items)
                            items.clear()
                        }
                        "SecondaryDrawerItem", "SectionDrawerItem" -> {
                            if (addToSticky) stickies.add(item!!)
                            else if (addToItem) items.add(item!!)
                        }
                        "BadgeStyle" -> (item as? SecondaryDrawerItem)?.withBadgeStyle(style)
                        "CategoryDrawerItems" -> {
                            if (addToSticky) stickies.addAll(categories)
                            else if (addToItem) items.addAll(categories)
                            categories.clear()
                        }
                        "StickyDrawerItems" -> {
                            addToSticky = false
                            drawer.withStickyDrawerItems(stickies)
                            stickies.clear()
                        }
                    }
                }
            }
            event = parser.next()
        }
        return drawer.build()
    }

    private inline fun <reified CLASS, T> getField(name: String, obj: Any? = null): T {
        val field = CLASS::class.java.getDeclaredField(name)
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        return field.get(obj) as T
    }
}