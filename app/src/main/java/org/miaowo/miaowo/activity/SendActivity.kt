package org.miaowo.miaowo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.holder.ImageHolder
import com.sdsmdg.tastytoast.TastyToast
import kotlinx.android.synthetic.main.activity_send.*
import okhttp3.ResponseBody
import org.json.JSONObject
import org.miaowo.miaowo.API
import org.miaowo.miaowo.App
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.InputAdapter
import org.miaowo.miaowo.base.extra.error
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.EmptyCallback
import org.miaowo.miaowo.ui.FloatView
import retrofit2.Call
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_send.title as eTitle

class SendActivity : AppCompatActivity() {
    companion object {
        // 新帖 ID
        const val TYPE_POST = 0
        // 回复 ID REPLY
        const val TYPE_REPLY = 1
        // 回复回复 ID ID2 REPLY
        const val TYPE_REPLY_POST = 2
    }

    private var mInputDialog = FloatView(App.i)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        setSupportActionBar(toolBar)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        applyIconsAndActions()

        val sendButton = toolBar.menu.add(0, 0, 0, R.string.send)
        when(intent.getIntExtra(Const.TYPE, TYPE_POST)) {
            TYPE_POST -> {
                eTitle.isCounterEnabled = true
                eTitle.editText!!.isEnabled = true
                eTitle.counterMaxLength = 50
                tags.visibility = View.VISIBLE
                sendButton.apply {
                    setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                    setOnMenuItemClickListener {
                        if (KeyboardUtils.isSoftInputVisible(this@SendActivity)) KeyboardUtils.hideSoftInput(this@SendActivity)
                        isEnabled = false
                        API.Topics.create(
                                intent.getIntExtra(Const.ID, -1),
                                eTitle.editText!!.text.toString(),
                                content.editText!!.text.toString(),
                                tags.editText!!.text.toString().split(";")).enqueue(object : EmptyCallback<ResponseBody>() {
                            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                val obj = JSONObject(response?.body()?.string())
                                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                                    var err = obj.getString("message")
                                    if (err.isNullOrBlank()) err = obj.getString("code")
                                    if (err.isNullOrBlank()) err = getString(R.string.err_ill)
                                    error(err)
                                    isEnabled = true
                                } else {
                                    finish()
                                }
                            }
                        })
                        true
                    }
                }
            }
            TYPE_REPLY -> {
                eTitle.editText!!.setText(getString(R.string.reply_to, intent.getStringExtra(Const.REPLY)))
                tags.visibility = View.GONE
                sendButton.apply {
                    setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                    setOnMenuItemClickListener {
                        if (KeyboardUtils.isSoftInputVisible(this@SendActivity)) KeyboardUtils.hideSoftInput(this@SendActivity)
                        isEnabled = false
                        API.Topics.reply(
                                intent.getIntExtra(Const.ID, -1),
                                content.editText!!.text.toString()).enqueue(object : EmptyCallback<ResponseBody>() {
                            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                val obj = JSONObject(response?.body()?.string())
                                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                                    var err = obj.getString("message")
                                    if (err.isNullOrBlank()) err = obj.getString("code")
                                    if (err.isNullOrBlank()) err = getString(R.string.err_ill)
                                    error(err)
                                    isEnabled = true
                                } else {
                                    finish()
                                }
                            }
                        })
                        true
                    }
                }
            }
            TYPE_REPLY_POST -> {
                eTitle.editText!!.setText(getString(R.string.reply_to, intent.getStringExtra(Const.REPLY)))
                tags.visibility = View.GONE
                sendButton.apply {
                    setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                    setOnMenuItemClickListener {
                        if (KeyboardUtils.isSoftInputVisible(this@SendActivity)) KeyboardUtils.hideSoftInput(this@SendActivity)
                        isEnabled = false
                        API.Topics.reply(
                                intent.getIntExtra(Const.ID, -1),
                                content.editText!!.text.toString(),
                                intent.getIntExtra(Const.ID2,-1)).enqueue(object : EmptyCallback<ResponseBody>() {
                            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                                val obj = JSONObject(response?.body()?.string())
                                if (obj.getString("code").toUpperCase() == Const.RET_OK) {
                                    var err = obj.getString("message")
                                    if (err.isNullOrBlank()) err = obj.getString("code")
                                    if (err.isNullOrBlank()) err = getString(R.string.err_ill)
                                    error(err)
                                    isEnabled = true
                                } else {
                                    finish()
                                }
                            }
                        })
                        true
                    }
                }
            }
        }
    }

    private fun popupTextInput(type: Int) {
        mInputDialog.reset("插入 Markdown 文本", R.layout.window_input, content.applicationWindowToken)
        val inputAdapter = InputAdapter()
        when (type) {
            Const.MD_EMOJI -> {
                // TODO: 表情
                toast("暂未实现，请先使用输入法表情功能代替", TastyToast.CONFUSING)
                return
            }
            Const.MD_FULL -> {
                // TODO: 未知功能
                toast("额 这个图标是个什么功能我也不知道蛤", TastyToast.CONFUSING)
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
                App.SP.run {
                    put(Const.SP_PW_INPUT_X, position.x)
                    put(Const.SP_PW_INPUT_Y, position.y)
                }
            }
        }.defaultBar().show(
                Gravity.CENTER,
                App.SP.getInt(Const.SP_PW_INPUT_X, 0),
                App.SP.getInt(Const.SP_PW_INPUT_Y, 0))
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
}
