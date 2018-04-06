package org.miaowo.miaowo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Button
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.fragment_add.*
import org.miaowo.miaowo.API
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.InputAdapter
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.*
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.ui.FloatView
import kotlinx.android.synthetic.main.fragment_add.title as eTitle

/**
 * 发送器
 */
class SendFragment : Fragment() {
    companion object {
        const val TYPE_POST = 0
        const val TYPE_REPLY = 1
        const val TYPE_REPLY_POST = 2

        fun newInstance(cid: Int, call: String): SendFragment {
            val fragment = SendFragment()
            val args = Bundle()
            args.putInt(Const.ID, cid)
            args.putInt(Const.TYPE, TYPE_POST)
            args.putString(Const.CALL, call)
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
    private var mInputDialog = FloatView(App.i.applicationContext)
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
        mListenerI?.addToolbarButton(0, 0, 0, R.string.send, MenuItem.SHOW_AS_ACTION_ALWAYS) {
            Miao.i.hideKeyboard()
            it.isEnabled = false
            val titleText = eTitle.editText!!.text.toString()
            val contentText = content.editText!!.text.toString()
            val tagArray = tags.editText!!.text.toString().split(";")
            when (mType) {
                TYPE_POST -> {
                    // send
                    API.Topics.create(mId, titleText, contentText, tagArray.toList()) { msg ->
                        activity?.runOnUiThread {
                            if (msg != Const.RET_OK) {
                                activity?.handleError(msg)
                                it.isEnabled = true
                            } else submitFinish(CategoryFragment.CALL_TAG)
                        }
                    }
                }
                TYPE_REPLY -> {
                    // reply
                    API.Topics.reply(mId, contentText) { msg ->
                        activity?.runOnUiThread {
                            if (msg != Const.RET_OK) {
                                activity?.handleError(msg)
                                it.isEnabled = true
                            } else submitFinish(PostFragment.CALL_TAG)
                        }
                    }
                }
                TYPE_REPLY_POST -> {
                    // reply
                    API.Topics.reply(mId, contentText, mReplyId) { msg ->
                        activity?.runOnUiThread {
                            if (msg != Const.RET_OK) {
                                activity?.handleError(msg)
                                it.isEnabled = true
                            } else submitFinish(PostFragment.CALL_TAG)
                        }
                    }
                }
                else -> activity?.handleError(R.string.err_send_count)
            }
            true
        }

        if (mType == TYPE_POST) tags.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        mListenerI?.resetToolbarButton()
        super.onDestroyView()
    }

    private fun popupTextInput(type: Int) {
        mInputDialog.reset("插入 Markdown 文本", R.layout.window_input, content.applicationWindowToken)
        val inputAdapter = InputAdapter()
        when (type) {
            Const.MD_EMOJI -> {
                // TODO: 表情
                activity?.toast("暂未实现，请先使用输入法表情功能代替", TastyToast.CONFUSING)
                return
            }
            Const.MD_FULL -> {
                // TODO: 未知功能
                activity?.toast("额 这个图标是个什么功能我也不知道蛤", TastyToast.CONFUSING)
                return
            }
            Const.MD_IMAGE -> {
                inputAdapter.add(InputAdapter.InputViewContent("图片描述", false, false, 1))
                inputAdapter.add(InputAdapter.InputViewContent("图片地址", false, false, 1))
            }
            Const.MD_LINK -> {
                inputAdapter.add(InputAdapter.InputViewContent("链接描述", false, false, 1))
                inputAdapter.add(InputAdapter.InputViewContent("链接地址", false, false, 1))
            }
            Const.MD_BOLD -> inputAdapter.add(InputAdapter.InputViewContent("粗体文字", false, false, 100))
            Const.MD_CODE -> inputAdapter.add(InputAdapter.InputViewContent("代码", false, false, 100))
            Const.MD_ITALIC -> inputAdapter.add(InputAdapter.InputViewContent("斜体文字", false, false, 100))
            Const.MD_QUOTE -> inputAdapter.add(InputAdapter.InputViewContent("引用内容", false, false, 100))
            Const.MD_ST -> inputAdapter.add(InputAdapter.InputViewContent("删除线", false, false, 100))
            Const.MD_UL -> inputAdapter.add(InputAdapter.InputViewContent("下划线", false, false, 100))
            Const.MD_LIST -> inputAdapter.add(InputAdapter.InputViewContent("列表内容", true, true, 1))
            Const.MD_LIST_OL -> inputAdapter.add(InputAdapter.InputViewContent("列表内容", true, true, 1))
            else -> inputAdapter.add(InputAdapter.InputViewContent("蛤？居然能到这里？给你个普通文字吧", false, false, 100))
        }

        mInputDialog.apply {
            view.apply {
                findViewById<RecyclerView>(R.id.inputs).apply {
                    adapter = inputAdapter
                    layoutManager = LinearLayoutManager(App.i)
                }
                findViewById<Button>(R.id.send).setOnClickListener {
                    content.editText?.append(fromTextToHtml(type, *inputAdapter.results.toTypedArray()))
                    mInputDialog.dismiss(true)
                }
                findViewById<Button>(R.id.cancel).setOnClickListener {
                    mInputDialog.dismiss(true)
                }
            }

            positionSave = { position, _ ->
                spPut(Const.SP_PW_INPUT_X, position.x)
                spPut(Const.SP_PW_INPUT_Y, position.y)
            }
        }.defaultBar().show(
                spGet(Const.SP_PW_INPUT_X, 0),
                spGet(Const.SP_PW_INPUT_Y, 0),
                Gravity.CENTER)
    }

    private fun fromTextToHtml(type: Int, vararg content: CharSequence?): CharSequence {
        return when (type) {
            Const.MD_BOLD -> "**${content[0]}**"
            Const.MD_CODE -> "```\n${content[0]}\n```"
            Const.MD_EMOJI -> content[0] ?: "" // TODO: 表情
            Const.MD_FULL -> content[0] ?: "" // TODO: 未知功能
            Const.MD_IMAGE -> "\n![${content.firstOrNull()}](${content.lastOrNull()})" // first: alt, last: url
            Const.MD_LINK -> "[${content.firstOrNull()}](${content.lastOrNull()})" // first: alt, last: url
            Const.MD_ITALIC -> "*${content[0]}*"
            Const.MD_LIST -> arrToStr({ "+ $it" }, content)
            Const.MD_LIST_OL -> arrToStrIndexed({ i, t -> "$i. $t" }, content)
            Const.MD_QUOTE -> arrToStr({ "> $it" }, content)
            Const.MD_ST -> "~~${content[0]}~~"
            Const.MD_UL -> "<u>${content[0]}</u>"
            else -> arrToStr({ it ?: "" }, content)
        }
    }

    private fun arrToStr(template: (ori: CharSequence?) -> CharSequence, content: Array<out CharSequence?>): String {
        return arrToStrIndexed({ _, ori -> template(ori) }, content)
    }

    private fun arrToStrIndexed(template: (index: Int, ori: CharSequence?) -> CharSequence, content: Array<out CharSequence?>): String {
        val sb = StringBuilder("\n")
        content.forEachIndexed { i, t -> sb.appendln(template(i, t)) }
        return sb.toString()
    }

    private fun applyIconsAndActions() {
        bold.setOnClickListener { popupTextInput(Const.MD_BOLD) }
        italic.setOnClickListener { popupTextInput(Const.MD_ITALIC) }
        list.setOnClickListener { popupTextInput(Const.MD_LIST) }
        list_ol.setOnClickListener { popupTextInput(Const.MD_LIST_OL) }
        strikethrough.setOnClickListener { popupTextInput(Const.MD_ST) }
        underline.setOnClickListener { popupTextInput(Const.MD_UL) }
        quote.setOnClickListener { popupTextInput(Const.MD_QUOTE) }
        code.setOnClickListener { popupTextInput(Const.MD_CODE) }
        full.setOnClickListener { popupTextInput(Const.MD_FULL) }

        ImageHolder(FontAwesome.Icon.faw_bold).applyTo(bold)
        ImageHolder(FontAwesome.Icon.faw_italic).applyTo(italic)
        ImageHolder(FontAwesome.Icon.faw_list_ul).applyTo(list)
        ImageHolder(FontAwesome.Icon.faw_list_ol).applyTo(list_ol)
        ImageHolder(FontAwesome.Icon.faw_strikethrough).applyTo(strikethrough)
        ImageHolder(FontAwesome.Icon.faw_underline).applyTo(underline)
        ImageHolder(FontAwesome.Icon.faw_quote_left).applyTo(quote)
        ImageHolder(FontAwesome.Icon.faw_code).applyTo(code)
        ImageHolder(FontAwesome.Icon.faw_square_o).applyTo(full)

        image.setOnClickListener { popupTextInput(Const.MD_IMAGE) }
        emoji.setOnClickListener { popupTextInput(Const.MD_EMOJI) }
        link.setOnClickListener { popupTextInput(Const.MD_LINK) }

        ImageHolder(FontAwesome.Icon.faw_file_image_o).applyTo(image)
        ImageHolder(FontAwesome.Icon.faw_smile_o).applyTo(emoji)
        ImageHolder(FontAwesome.Icon.faw_link).applyTo(link)
    }

    private fun submitFinish(call: String) {
        submitAndRemoveCall(call)
    }
}
