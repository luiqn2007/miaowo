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
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.handleError
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const

/**
 * 发送器
 */
class SendFragment : Fragment() {
    companion object {
        fun newInstance(cid: Int, vararg tags: String): SendFragment {
            val fragment = SendFragment()
            val args = Bundle()
            args.putInt(Const.ID, cid)
            args.putStringArray(Const.TAG, tags)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(tid: Int, reply: String): SendFragment {
            val fragment = SendFragment()
            val args = Bundle()
            args.putInt(Const.ID, tid)
            args.putString(Const.REPLY, reply)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(tid: Int, toPid: Int, reply: String): SendFragment {
            val fragment = SendFragment()
            val args = Bundle()
            args.putInt(Const.ID, tid)
            args.putInt(Const.REPLY, toPid)
            args.putString(Const.NAME, reply)
            fragment.arguments = args
            return fragment
        }
    }

    private var mSendUser: String? = null
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_add, inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        applyIconsAndActions()

        mSendUser = arguments?.getString(Const.NAME)
        val isTitle = mSendUser.isNullOrBlank()
        title.isCounterEnabled = isTitle
        title.editText!!.isEnabled = isTitle
        if (isTitle) title.counterMaxLength = 50
        else title.editText!!.setText(getString(R.string.reply_to, mSendUser))
        mListenerI?.toolbar?.menu?.run {
            clear()
            val sendTitle = title.editText!!.text.toString()
            val sendContent = content.editText!!.text.toString()
            mAddButton = add(0, 0, 0, R.string.send)
            mAddButton?.run {
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                setOnMenuItemClickListener {
                    if (sendContent.length <= 3000 && sendTitle.length <= 50) {
                        isEnabled = false
                        if (isTitle) {
                            // Title
                            val cid = arguments?.getInt(Const.ID) ?: -1
                            val tags = arguments?.getStringArray(Const.TAG) ?: emptyArray()
                            API.Topics.create(cid, sendTitle, sendContent, tags.toList()) { msg ->
                                activity?.runOnUiThread {
                                    if (msg != Const.RET_OK) {
                                        activity?.handleError(msg)
                                        isEnabled = true
                                    } else activity?.supportFragmentManager?.popBackStackImmediate()
                                }
                            }
                        } else {
                            val toPid = arguments?.getInt(Const.REPLY)
                            val tid = arguments?.getInt(Const.ID) ?: -1
                            // reply
                            API.Topics.reply(tid, sendContent, toPid) { msg ->
                                activity?.runOnUiThread {
                                    if (msg != Const.RET_OK) {
                                        activity?.handleError(msg)
                                        isEnabled = true
                                    } else activity?.supportFragmentManager?.popBackStackImmediate()
                                }
                            }
                        }
                    } else activity?.handleError(R.string.err_send_count)
                    true
                }
            }
        }

        val savedTitle = savedInstanceState?.getString(Const.NAME)
        val savedContent = savedInstanceState?.getString(Const.SAVE)
        if (!savedTitle.isNullOrBlank()) title.editText?.setText(savedTitle)
        if (!savedContent.isNullOrBlank()) title.editText?.setText(savedContent)
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
