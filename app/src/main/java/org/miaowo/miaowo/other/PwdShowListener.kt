package org.miaowo.miaowo.other

import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.widget.EditText

class PwdShowListener(private val bind: EditText) : View.OnTouchListener {

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                bind.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            MotionEvent.ACTION_UP ->
                bind.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        return true
    }
}
