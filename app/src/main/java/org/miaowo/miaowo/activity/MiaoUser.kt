package org.miaowo.miaowo.activity

import android.app.AlertDialog
import android.content.Intent
import android.support.v4.content.res.ResourcesCompat
import android.widget.PopupMenu
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_user.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.bean.data.User
import org.miaowo.miaowo.fragment.user_detail.*
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.FormatUtil
import org.miaowo.miaowo.util.ImageUtil
import org.miaowo.miaowo.util.LogUtil

class MiaoUser : BaseActivity(R.layout.activity_user) {
    companion object {
        fun showUser(name: String = API.loginUser!!.username) =
                App.i.startActivity(Intent(App.i, MiaoUser::class.java).putExtra(Const.NAME, name))
    }

    var userMenu: PopupMenu? = null
    var mFocusList: FocusFragment? = null
    var mLikeList: FollowerFragment? = null
    var mPostList: PostFragment? = null
    var mInfoList: InfoFragment? = null
    var mTopicList: TopicFragment? = null

    override fun initActivity() {
        val name = intent.getStringExtra(Const.NAME)

        ImageUtil.fill(iv_user, Const.DEF, null)
        tv_user.text = name
        tv_about.text = App.i.getString(R.string.data_loading)
        tv_signature.text = App.i.getString(R.string.data_loading)
        tv_time_reg.text = App.i.getString(R.string.data_loading)
        tv_time_login.text = App.i.getString(R.string.data_loading)
        tv_website.text = App.i.getString(R.string.data_loading)
        tv_position.text = App.i.getString(R.string.data_loading)
        tv_age.text = App.i.getString(R.string.data_loading)
        FormatUtil.run {
            fillCount(tv_ask, 0)
            fillCount(tv_scan, 0)
            fillCount(tv_like, 0)
            fillCount(tv_focus, 0)
        }

        API.Doc.user(name) { user ->
            try {
                ImageUtil.setUser(iv_user, user, false)
                tv_about.text = FormatUtil.parseHtml(user!!.aboutme)
                tv_signature.text = FormatUtil.parseHtml(user.signature)
                tv_time_reg.text = FormatUtil.date(user.joindate)
                tv_time_login.text = FormatUtil.date(user.lastonline)
                tv_website.text = FormatUtil.parseHtml(user.website)
                tv_position.text = FormatUtil.parseHtml(user.location)
                tv_age.text = user.age.toString()

                fab.backgroundTintList = ResourcesCompat.getColorStateList(resources,
                        when (user.status) {
                            "online" -> R.color.colorOnline
                            "away" -> R.color.colorAway
                            "dnd" -> R.color.colorDnd
                            else -> R.color.colorHide
                        }, null)

                FormatUtil.fillCount(tv_ask, user.postcount)
                FormatUtil.fillCount(tv_scan, user.profileviews)
                FormatUtil.fillCount(tv_like, user.followerCount)
                FormatUtil.fillCount(tv_focus, user.followingCount)

                fab.setOnClickListener {
                    if (userMenu == null) buildMenu(user)
                    userMenu!!.show()
                }

                buildMenu(user)
                buildList(user)

                loadFragment(show = mPostList)
            } catch (e: Exception) {
                tv_about.text = getString(R.string.err_load)
                tv_signature.text = getString(R.string.err_load)
                tv_time_reg.text = getString(R.string.err_load)
                tv_time_login.text = getString(R.string.err_load)
                tv_website.text = getString(R.string.err_load)
                tv_position.text = getString(R.string.err_load)
                tv_age.text = getString(R.string.err_load)
                fab.solidColor
                fab.backgroundTintList =
                        ResourcesCompat.getColorStateList(resources, R.color.colorHide, null)
                e.printStackTrace()
            }
        }
    }

    private fun buildList(user: User) {
        mPostList = PostFragment.newInstance(user.username)
        mFocusList = FocusFragment.newInstance(user.username)
        mLikeList = FollowerFragment.newInstance(user.username)
        mTopicList = TopicFragment.newInstance(user.username)
        mInfoList = InfoFragment.newInstance(user.username)
    }

    private fun buildMenu(user: User) {
        userMenu = PopupMenu(this, fab)

        val menu = userMenu!!.menu

        if (user.isIsSelf) {
            val status = menu.addSubMenu(0, menu.size(), menu.size(), R.string.status)
            status.add(0, Const.ID_STATUS_ONLINE, status.size(), R.string.statusOnline)
            status.add(0, Const.ID_STATUS_AWAY, status.size(), R.string.statusAway)
            status.add(0, Const.ID_STATUS_DND, status.size(), R.string.statusDnd)
            status.add(0, Const.ID_STATUS_OFFLINE, status.size(), R.string.statusHide)
            menu.add(0, Const.ID_EDIT, menu.size(), R.string.edit)
            menu.add(0, Const.ID_INFO, menu.size(), R.string.info)
        } else {
            menu.add(1, Const.ID_CHAT, menu.size(), R.string.chat)
            menu.add(1, Const.ID_FOCUS, menu.size(), if (user.isIsFollowing) R.string.focus_cancel else R.string.focus)
        }
        menu.add(2, Const.ID_FOCUSED, menu.size(), R.string.focused)
        menu.add(2, Const.ID_LIKE, menu.size(), R.string.liked)
        menu.add(2, Const.ID_TOPIC, menu.size(), R.string.topic)
        menu.add(2, Const.ID_POST, menu.size(), R.string.post)
        if (API.loginUser?.isAdmin ?: false && !(user.isIsSelf)) {
            val manager = menu.addSubMenu(3, menu.size(), menu.size(), R.string.manager)
//            manager.add(3, Const.ID_MANAGER_EDIT, manager.size(), R.string.edit)
            manager.add(3, Const.ID_MANAGER_INFO, manager.size(), R.string.info)
            manager.add(3, Const.ID_MANAGER_BAN, manager.size(), R.string.ban)
            manager.add(3, Const.ID_MANAGER_DELETE, manager.size(), R.string.delete)
        }

        userMenu!!.setOnMenuItemClickListener {
            when (it.itemId) {
                Const.ID_STATUS_ONLINE -> LogUtil.TODO("状态-在线")
                Const.ID_STATUS_AWAY -> LogUtil.TODO("状态-离开")
                Const.ID_STATUS_DND -> LogUtil.TODO("状态-勿扰")
                Const.ID_STATUS_OFFLINE -> LogUtil.TODO("状态-离线/隐身")
                Const.ID_MANAGER_DELETE, Const.ID_EDIT -> {
                    AlertDialog.Builder(this)
                            .setTitle("确认删除")
                            .setMessage("确认删除 ${user.username} ?")
                            .setNegativeButton("手滑了", null)
                            .setPositiveButton("删了吧") { dialog, _ ->
                                API.Use.removeUser(user.uid) {
                                    if ("ok" == it) {
                                        toast("已删除", TastyToast.SUCCESS)
                                        finish()
                                    } else handleError(Exception(it))
                                }
                                dialog.dismiss()
                            }
                            .show()
                }
                Const.ID_MANAGER_BAN -> LogUtil.TODO("封禁/取消")
                Const.ID_MANAGER_INFO, Const.ID_INFO -> loadFragment(show = mInfoList)
                Const.ID_CHAT -> LogUtil.TODO("聊天")
                Const.ID_FOCUS -> {
                    API.Use.focus(user.uid) {
                        when (it) {
                            "ok" -> {
                                BaseActivity.get?.toast(R.string.focus_success, TastyToast.SUCCESS)
                                user.followerCount = user.followerCount + 1
                                FormatUtil.fillCount(tv_like, user.followerCount)
                                menu?.getItem(Const.ID_FOCUS)?.setTitle(R.string.focus_cancel)
                            }
                            "already-following" -> {
                                API.Use.unfocus(user.uid) {
                                    BaseActivity.get?.toast(R.string.focus_canceled, TastyToast.SUCCESS)
                                    user.followerCount = user.followerCount - 1
                                    FormatUtil.fillCount(tv_like, user.followerCount)
                                    menu?.getItem(Const.ID_FOCUS)?.setTitle(R.string.focus)
                                }
                            }
                            "you-cant-follow-yourself" -> {
                                BaseActivity.get?.handleError(R.string.err_focus_self)
                            }
                            else -> {
                                BaseActivity.get?.handleError(R.string.err_no_err)
                            }
                        }
                    }
                }
                Const.ID_FOCUSED -> loadFragment(show = mFocusList)
                Const.ID_LIKE -> loadFragment(show = mLikeList)
                Const.ID_TOPIC -> loadFragment(show = mTopicList)
                Const.ID_POST -> loadFragment(show = mPostList)
                else -> return@setOnMenuItemClickListener false
            }
            true
        }
    }
}
