package org.miaowo.miaowo.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.other.Const
import kotlin.properties.Delegates

class Add : BaseActivity(R.layout.activity_add) {

    private var mTypes by Delegates.notNull<List<TextView>>()
    private val result = Intent()
    private var canSend = false
    private var mSelectedType = 0

    override fun initActivity() {
        mTypes = listOf(tv_daily, tv_announcement, tv_ask, tv_water)

        mSelectedType = intent.getIntExtra(Const.TAG, -1)
        if (mSelectedType == -1) {
            // 回复
            canSend = true
            mTypes.forEach {
                it.isEnabled = false
                it.isActivated = false
            }
            val tid = intent.getIntExtra(Const.ID, 0)
            val name = intent.getStringExtra(Const.NAME)
            mSelectedType = tid
            textInputLayout.isEnabled = false
            textInputLayout.editText?.setText("回复: $name")
        } else {
            // 新主题
            mTypes[mSelectedType].isActivated = true
            mTypes.indices.forEach {
                val pos = it
                mTypes[it].setOnClickListener {
                    if (!it.isActivated) {
                        mTypes[mSelectedType].isActivated = false
                        it.isActivated = true
                        mSelectedType = pos
                    }
                }
            }

            textInputLayout.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.isEmpty()) {
                        textInputLayout.error = getString(R.string.err_title_empty)
                        canSend = false
                    } else canSend = true
                }
            })
        }

        cancel.setOnClickListener {
            setResult(-1, result)
            finish()
        }

        send.setOnClickListener {
            if (canSend) {
                result.putExtra(Const.TAG, mSelectedType)
                result.putExtra(Const.TITLE, textInputLayout.editText?.text.toString())
                result.putExtra(Const.CONTENT, et_content.text.toString())
                setResult(1, result)
                finish()
            } else handleError(R.string.err_no_message)
        }
    }
}
