package org.miaowo.miaowo.custom;

import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class PwdShowListener implements View.OnTouchListener {

    private EditText bind;

    public PwdShowListener(EditText pwdInput) {
        bind = pwdInput;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                bind.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                break;
            case MotionEvent.ACTION_UP:
                bind.setInputType(InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        return true;
    }
}
