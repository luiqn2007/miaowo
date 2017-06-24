package org.miaowo.miaowo.fragment

import android.animation.Animator
import android.content.Context
import android.os.Bundle
import android.support.transition.ChangeBounds
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.support.transition.TransitionSet
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cleveroad.fanlayoutmanager.FanLayoutManager
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings
import com.cleveroad.fanlayoutmanager.callbacks.FanChildDrawingOrderCallback
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import kotlinx.android.synthetic.main.fragment_welcome.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Miao
import org.miaowo.miaowo.base.BaseViewHolder
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.MoveTransition
import org.miaowo.miaowo.other.PwdShowListener
import org.miaowo.miaowo.ui.ChatButton
import org.miaowo.miaowo.util.*
import java.util.Random
import kotlin.properties.Delegates

class MiaoFragment : Fragment() {
    /* ================================================================================ */
    // 构造，创建，绑定及与 Activity 交互
    private var mListener: OnFragmentInteractionListener? = null
    private var mContainer by Delegates.notNull<ViewGroup>()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mContainer = container!!
        processController = object : ProcessController {
            private var isShow = false
            override fun setProcess(process: Int, message: String) {
                if (!isShow) {
                    TransitionManager.beginDelayedTransition(mContainer, mTransitionTogether)
                    show(pb_process, tv_message)
                    pb_process.max = 100
                    isShow = true
                }
                pb_process.progress = if (process > 100) 100 else process
                tv_message.setTextColor(ResourcesCompat.getColor(resources, R.color.md_green_900, null))
                tv_message.text = message
            }
            override fun processError(e: Exception) {
                prepareLogin()
                if (!isShow) {
                    TransitionManager.beginDelayedTransition(mContainer, mTransitionTogether)
                    show(pb_process, tv_message)
                    pb_process.max = 100
                    isShow = true
                }
                tv_message.setTextColor(ResourcesCompat.getColor(resources, R.color.md_red_A700, null))
                tv_message.text = e.message
            }
            override fun stopProcess() {
                if (isShow) {
                    TransitionManager.beginDelayedTransition(mContainer, mTransitionTogether)
                    hide(pb_process, tv_message)
                    isShow = false
                }
            }
        }
        return inflateId(R.layout.fragment_welcome, inflater, container)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) mListener = context
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        fun onChooserClick(position: Int)
    }

    /* ================================================================================ */
    private var mTransitionTogether = TransitionSet()

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mTransitionTogether.ordering = TransitionSet.ORDERING_TOGETHER
        mTransitionTogether.duration = 300
        mTransitionTogether.addTransition(Fade()).addTransition(ChangeBounds()).addTransition(MoveTransition())
        initPageChooser()
        show.setOnTouchListener(PwdShowListener(et_password))
        cb_save.isChecked = spGet(Const.SP_SAVE, false)
        et_password.setText(spGet(Const.SP_PWD, ""))
        et_user.setText(spGet(Const.SP_USER, ""))
        et_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    btn_login.setText(R.string.rlogin)
                    btn_login.setOnClickListener {
                        API.Login.login(
                                et_user.text.toString(),
                                et_password.text.toString())
                        spPut(Const.SP_USER, et_user.text.toString())
                        spPut(Const.SP_PWD, et_password.text.toString())
                        spPut(Const.SP_SAVE, cb_save.isChecked)
                    }
                } else {
                    btn_login.setText(R.string.rregister)
                    btn_login.setOnClickListener {
                        API.Login.register(et_user.text.toString(), et_password.text.toString(), s.toString())
                        spPut(Const.SP_USER, et_user.text.toString())
                        spPut(Const.SP_PWD, et_password.text.toString())
                        spPut(Const.SP_SAVE, cb_save.isChecked)
                    }
                }
            }
        })

        if (API.loginUser != null) {
            rv_page.visibility = View.VISIBLE
            et_email.isEnabled = false
            et_user.isEnabled = false
            et_password.isEnabled = false
            cb_save.isEnabled = false
            btn_login.isEnabled = false
        } else prepareLogin()
    }

    private fun initPageChooser() {
        val OColors = intArrayOf(
                ResourcesCompat.getColor(resources, R.color.md_amber_300, null),
                ResourcesCompat.getColor(resources, R.color.md_blue_300, null),
                ResourcesCompat.getColor(resources, R.color.md_brown_300, null),
                ResourcesCompat.getColor(resources, R.color.md_cyan_300, null),
                ResourcesCompat.getColor(resources, R.color.md_green_300, null),
                ResourcesCompat.getColor(resources, R.color.md_grey_300, null),
                ResourcesCompat.getColor(resources, R.color.md_indigo_300, null),
                ResourcesCompat.getColor(resources, R.color.md_lime_300, null),
                ResourcesCompat.getColor(resources, R.color.md_orange_300, null),
                ResourcesCompat.getColor(resources, R.color.md_pink_300, null),
                ResourcesCompat.getColor(resources, R.color.md_purple_300, null),
                ResourcesCompat.getColor(resources, R.color.md_red_300, null),
                ResourcesCompat.getColor(resources, R.color.md_teal_300, null),
                ResourcesCompat.getColor(resources, R.color.md_yellow_300, null),
                ResourcesCompat.getColor(resources, R.color.md_blue_grey_300, null),
                ResourcesCompat.getColor(resources, R.color.md_deep_orange_300, null),
                ResourcesCompat.getColor(resources, R.color.md_deep_purple_300, null),
                ResourcesCompat.getColor(resources, R.color.md_light_blue_300, null),
                ResourcesCompat.getColor(resources, R.color.md_light_green_300, null))

        if (activity is Miao) {
            val names = arrayOf(
                    getString(R.string.square), getString(R.string.unread), getString(R.string.topic), getString(R.string.user), getString(R.string.search), getString(R.string.setting), getString(R.string.logout))
            val icons = arrayOf(
                    getIcon(FontAwesome.Icon.faw_calendar_check_o), getIcon(FontAwesome.Icon.faw_inbox),
                    getIcon(FontAwesome.Icon.faw_tags), getIcon(FontAwesome.Icon.faw_github_alt),
                    getIcon(FontAwesome.Icon.faw_search),
                    getIcon(FontAwesome.Icon.faw_wrench),
                    getIcon(FontAwesome.Icon.faw_sign_out))
            val colors = IntArray(names.size)
            val random = Random(System.currentTimeMillis())
            names.indices.forEach { colors[it] = OColors[random.nextInt(OColors.size)] }
            val layout = FanLayoutManager(context,
                    FanLayoutManagerSettings.newBuilder(context)
                            .withFanRadius(true)
                            .withAngleItemBounce(5f)
                            .withViewHeightDp(130f)
                            .withViewWidthDp(100f)
                            .build())
            rv_page.layoutManager = layout
            rv_page.adapter = object : RecyclerView.Adapter<BaseViewHolder>() {

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
                        BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.list_page, parent, false))

                override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                    holder.setText(R.id.tv_page, names[position])
                    holder.setDrawable(R.id.iv_page, icons[position])
                    holder.view.setBackgroundColor(colors[position])

                    holder.view.setOnClickListener {
                        if (layout.selectedItemPosition == position) {
                            layout.straightenSelectedItem(object : Animator.AnimatorListener {
                                override fun onAnimationStart(animator: Animator) {}
                                override fun onAnimationCancel(animator: Animator) {}
                                override fun onAnimationRepeat(animator: Animator) {}
                                override fun onAnimationEnd(animator: Animator) {
                                    mListener?.onChooserClick(position)
                                }
                            })
                        } else layout.switchItem(rv_page, position)
                    }
                }

                override fun getItemCount() = names.size
            }
            rv_page.itemAnimator = DefaultItemAnimator()
            rv_page.setChildDrawingOrderCallback(FanChildDrawingOrderCallback(rv_page.layoutManager))
        }
    }

    fun loginSucceed() {
        rv_page.visibility = View.VISIBLE

        TransitionManager.beginDelayedTransition(mContainer, mTransitionTogether)
        hide(et_email, et_user, et_password, cb_save, btn_login)
        hide(tv_email, tv_user, tv_password, show)
        show(welcome_eyes, welcome_mouth)
        move(-250f, welcome_eyes, welcome_mouth)

        et_email.isEnabled = false
        et_user.isEnabled = false
        et_password.isEnabled = false
        cb_save.isEnabled = false
        show.isEnabled = false
        btn_login.isEnabled = false
        processController?.stopProcess()

        cleanTokens()
        ChatButton.show()
    }

    private fun cleanTokens() {
        if (spGet(Const.SP_CLEAN_TOKENS, true) && !API.token.isEmpty()) {
            API.Use.getTokens {
                it.filter { it != API.token }.forEach { API.Use.removeToken(it) }
            }
        }
    }

    fun prepareLogin() {
        rv_page.visibility = View.GONE

        TransitionManager.beginDelayedTransition(mContainer, mTransitionTogether)
        move(-200f, welcome_eyes, welcome_mouth)
        show(btn_login)

        btn_login.setText(R.string.login)
        btn_login.isEnabled = true
        btn_login.setOnClickListener {
            et_email.isEnabled = true
            et_user.isEnabled = true
            et_password.isEnabled = true
            cb_save.isEnabled = true

            TransitionManager.beginDelayedTransition(mContainer, mTransitionTogether)
            show(et_email, et_user, et_password, cb_save)
            show(tv_email, tv_user, tv_password, show)
            hide(welcome_eyes, welcome_mouth)
            move(-500f, welcome_eyes, welcome_mouth)

            show.isEnabled = true
            btn_login.setText(R.string.rlogin)
            btn_login.setOnClickListener {
                btn_login.isEnabled = false
                API.Login.login(et_user.text.toString(), et_password.text.toString())
                spPut(Const.SP_SAVE, cb_save.isChecked)
                if (cb_save.isChecked) {
                    spPut(Const.SP_USER, et_user.text.toString())
                    spPut(Const.SP_PWD, et_password.text.toString())
                }
            }
        }
    }

    private fun hide(vararg views: View) {
        views.forEach {
            it.visibility = View.INVISIBLE
            it.isEnabled = false
        }
    }

    private fun show(vararg views: View) {
        views.forEach {
            it.visibility = View.VISIBLE
            it.isEnabled = true
        }
    }

    private fun move(translationY: Float, vararg views: View) =
            views.forEach { it.translationY = translationY }

    private fun getIcon(iicon: IIcon) = IconicsDrawable(context, iicon).actionBar()

    companion object {
        fun newInstance(): MiaoFragment {
            val fragment = MiaoFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
