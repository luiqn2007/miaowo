package org.miaowo.miaowo.activity

import android.app.FragmentTransaction
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.materialdrawer.holder.ImageHolder
import kotlinx.android.synthetic.main.activity_send.*
import okhttp3.ResponseBody
import org.miaowo.miaowo.API
import org.miaowo.miaowo.R
import org.miaowo.miaowo.data.model.MessageModel
import org.miaowo.miaowo.fragment.MarkdownFragment
import org.miaowo.miaowo.other.ActivityHttpCallback
import org.miaowo.miaowo.other.Const
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

    private lateinit var mMessageModel: MessageModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        setSupportActionBar(toolBar)
        mMessageModel = ViewModelProviders.of(this)[MessageModel::class.java]
        mMessageModel.observe(this, Observer {
            toolBar.menu.getItem(0)?.isEnabled = !it.isNullOrBlank()
            content.editText!!.setText(it)
        })
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
                                tags.editText!!.text.toString().split(";")).enqueue(object : ActivityHttpCallback<ResponseBody>(this@SendActivity) {
                            override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                                finish()
                            }

                            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?, errMsg: Any?) {
                                super.onFailure(call, t, errMsg)
                                isEnabled = true
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
                                content.editText!!.text.toString()).enqueue(object : ActivityHttpCallback<ResponseBody>(this@SendActivity) {
                            override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                                finish()
                            }

                            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?, errMsg: Any?) {
                                super.onFailure(call, t, errMsg)
                                isEnabled = true
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
                                intent.getIntExtra(Const.ID2,-1)).enqueue(object : ActivityHttpCallback<ResponseBody>(this@SendActivity) {
                            override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                                finish()
                            }

                            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?, errMsg: Any?) {
                                super.onFailure(call, t, errMsg)
                                isEnabled = true
                            }
                        })
                        true
                    }
                }
            }
        }
    }

    private fun popupTextInput(type: Int) {
        mMessageModel.set(content.editText!!.text)
        supportFragmentManager.beginTransaction().apply {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            MarkdownFragment.TYPE = type
            MarkdownFragment().show(this, "input_$type")
        }.commitNow()
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
