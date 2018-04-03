package org.miaowo.miaowo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.holder.ImageHolder
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.title as eTitle
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.submitAndRemoveCall
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

/**
 * 发送器
 */
class SendFragment : Fragment() {
    companion object {
        const val TYPE_POST = 0
        const val TYPE_REPLY = 1
        const val TYPE_REPLY_POST = 2

        fun newInstance(cid: Int): SendFragment {
            val fragment = SendFragment()
            val args = Bundle()
            args.putInt(Const.ID, cid)
            args.putInt(Const.TYPE, TYPE_POST)
            args.putString(Const.TAG, "${fragment.javaClass.name}.cid.$cid")
            fragment.arguments = args
            return fragment
        }

        fun newInstance(tid: Int, reply: String, call: String): SendFragment {
            val fragment = SendFragment()
            val args = Bundle()
            args.putInt(Const.ID, tid)
            args.putString(Const.NAME, reply)
            args.putString(Const.CALL, call)
            args.putInt(Const.TYPE, TYPE_REPLY)
            args.putString(Const.TAG, "${fragment.javaClass.name}.tid.$tid")
            fragment.arguments = args
            return fragment
        }

        fun newInstance(tid: Int, toPid: Int, reply: String, call: String): SendFragment {
            val fragment = SendFragment()
            val args = Bundle()
            args.putInt(Const.ID, tid)
            args.putInt(Const.REPLY, toPid)
            args.putString(Const.CALL, call)
            args.putString(Const.NAME, reply)
            args.putInt(Const.TYPE, TYPE_REPLY_POST)
            args.putString(Const.TAG, "${fragment.javaClass.name}.tid-pid.$tid-$toPid")
            fragment.arguments = args
            return fragment
        }
    }

    private var mReplyUser: String? = null
    private var mCall: String? = null
    private var mId = -1
    private var mReplyId = -1
    private var mType = -1
    private var mListenerI: IMiaoListener? = null
    private var mPopupInput: PopupWindow? = null
    private var mAddButton: MenuItem? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
    }

    override fun onDetach() {
        super.onDetach()
        if (mAddButton != null) mListenerI?.toolbar?.menu?.removeItem(mAddButton!!.itemId)
        mListenerI = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCall = arguments?.getString(Const.CALL)
        mReplyUser = arguments?.getString(Const.NAME)
        mId = arguments?.getInt(Const.ID) ?: -1
        mReplyId = arguments?.getInt(Const.REPLY) ?: -1
        mType = arguments?.getInt(Const.TYPE) ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_add, inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        applyIconsAndActions()

        val isTitle = mReplyUser.isNullOrBlank()
        eTitle.isCounterEnabled = isTitle
        eTitle.editText!!.isEnabled = isTitle
        if (isTitle) eTitle.counterMaxLength = 50
        else eTitle.editText!!.setText(getString(R.string.reply_to, mReplyUser))
        mListenerI?.toolbar?.menu?.apply {
            clear()
            mAddButton = add(0, 0, 0, R.string.send)
            mAddButton?.apply {
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                setOnMenuItemClickListener {
                    val titleText = eTitle.editText!!.text.toString()
                    val contentText = content.editText!!.text.toString()
                    val tagArray = tags.editText!!.text.toString().split(";")
                    when (mType) {
                        TYPE_POST -> {
                            API.Topics.create(mId, titleText, contentText, tagArray.toList()) { msg ->
                                activity?.runOnUiThread {
                                    if (msg != Const.RET_OK) {
                                        activity?.handleError(msg)
                                        isEnabled = true
                                    } else activity?.supportFragmentManager?.popBackStackImmediate()
                                }
                            }
                        }
                        TYPE_REPLY -> {
                            // reply
                            API.Topics.reply(mId, contentText) { msg ->
                                activity?.runOnUiThread {
                                    if (msg != Const.RET_OK) {
                                        activity?.handleError(msg)
                                        isEnabled = true
                                    } else {
                                        submitAndRemoveCall(PostFragment.CALL_TAG)
                                        activity?.supportFragmentManager?.popBackStackImmediate()
                                    }
                                }
                            }
                        }
                        TYPE_REPLY_POST -> {
                            // reply
                            API.Topics.reply(mId, contentText, mReplyId) { msg ->
                                activity?.runOnUiThread {
                                    if (msg != Const.RET_OK) {
                                        activity?.handleError(msg)
                                        isEnabled = true
                                    } else {
                                        submitAndRemoveCall(PostFragment.CALL_TAG)
                                        activity?.supportFragmentManager?.popBackStackImmediate()
                                    }
                                }
                            }
                        }
                        else -> {
                            activity?.handleError(R.string.err_send_count)
                        }
                    }

                    true
                }
            }
        }

        if (mType == TYPE_POST) tags.visibility = View.VISIBLE
    }

    private fun popupTextInput(type: Int) {
        val beforeAlpha = activity?.window?.attributes?.alpha ?: 1f

        @SuppressLint("InflateParams")
        if (mPopupInput == null) {
            val mPopInputView = LayoutInflater.from(context).inflate(R.layout.window_input, null)
            mPopupInput = PopupWindow(mPopInputView)
            mPopupInput!!.run {
                isOutsideTouchable = false
                isTouchable = true
                isFocusable = true
                showAsDropDown(activity?.window?.decorView)
            }
        }
        activity?.window?.attributes?.alpha = 0.4f

        val textInput = mPopupInput!!.contentView.findViewById(R.id.input) as EditText
        val textInput2 = mPopupInput!!.contentView.findViewById(R.id.input2) as EditText
        when (type) {
            Const.MD_IMAGE -> {
                textInput.hint = "图片地址"
                textInput2.hint = "显示描述"
                textInput2.visibility = View.VISIBLE
            }
            Const.MD_LINK -> {
                textInput.hint = "地址"
                textInput2.hint = "显示标签"
                textInput2.visibility = View.VISIBLE
            }
            else -> {
                textInput.hint = "内容"
                textInput2.visibility = View.GONE
            }
        }

        mPopupInput!!.contentView.findViewById<Button>(R.id.send).setOnClickListener {
            content.editText!!.append(fromTextToHtml(type, textInput.text.toString(), textInput2.text.toString()))
        }
        mPopupInput!!.contentView.findViewById<Button>(R.id.cancel).setOnClickListener {
            activity?.window?.attributes?.alpha = beforeAlpha
            mPopupInput!!.dismiss()
        }
    }

    private fun fromTextToHtml(type: Int, content: String, extra: String?): String {
        return when (type) {
            Const.MD_BOLD -> "<b>$content</b>"
            Const.MD_CODE -> "<code>$content</code>"
            Const.MD_EMOJI -> content //TODO: 表情
            Const.MD_FULL -> content //TODO: 未知功能
            Const.MD_IMAGE -> "<img src=\"$content\"  alt=\"$extra\" />"
            Const.MD_ITALIC -> "<i>$content</i>"
            Const.MD_LINK -> "<a href=\"$content\">$extra</a>"
            Const.MD_LIST -> {
                val builder = StringBuilder("<ul>")
                content.lines().forEach { builder.append("<li>$it</li>") }
                builder.append("</ul>")
                builder.toString()
            }
            Const.MD_LIST_OL -> {
                val builder = StringBuilder("<ol>")
                content.lines().forEach { builder.append("<li>$it</li>") }
                builder.append("</ol>")
                builder.toString()
            }
            Const.MD_QUOTE -> content //TODO: 大逗号
            Const.MD_ST -> "<del>$content</del>"
            Const.MD_UL -> "<ins>$content</ins>"
            else -> content
        }
    }

    private fun applyIconsAndActions() {
        bold.setOnClickListener { popupTextInput(0) }
        italic.setOnClickListener { popupTextInput(1) }
        list.setOnClickListener { popupTextInput(2) }
        list_ol.setOnClickListener { popupTextInput(3) }
        strikethrough.setOnClickListener { popupTextInput(4) }
        underline.setOnClickListener { popupTextInput(5) }
        quote.setOnClickListener { popupTextInput(6) }
        code.setOnClickListener { popupTextInput(7) }
        full.setOnClickListener { popupTextInput(8) }

        ImageHolder(FontAwesome.Icon.faw_bold).applyTo(bold)
        ImageHolder(FontAwesome.Icon.faw_italic).applyTo(italic)
        ImageHolder(FontAwesome.Icon.faw_list_ul).applyTo(list)
        ImageHolder(FontAwesome.Icon.faw_list_ol).applyTo(list_ol)
        ImageHolder(FontAwesome.Icon.faw_strikethrough).applyTo(strikethrough)
        ImageHolder(FontAwesome.Icon.faw_underline).applyTo(underline)
        ImageHolder(FontAwesome.Icon.faw_quote_left).applyTo(quote)
        ImageHolder(FontAwesome.Icon.faw_code).applyTo(code)
        ImageHolder(FontAwesome.Icon.faw_square_o).applyTo(full)

        ImageHolder(FontAwesome.Icon.faw_file_image_o).applyTo(image)
        ImageHolder(FontAwesome.Icon.faw_smile_o).applyTo(emoji)
        ImageHolder(FontAwesome.Icon.faw_link).applyTo(link)
    }
}
