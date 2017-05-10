package org.miaowo.miaowo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.bean.data.Post;
import org.miaowo.miaowo.root.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

public class Add extends BaseActivity {
    public static String TAG = "type";
    public static String TITLE = "title";
    public static String CONTENT = "content";

    @BindViews({R.id.tv_daily, R.id.tv_announcement, R.id.tv_ask, R.id.tv_water})
    List<TextView> mTypes;
    @BindView(R.id.textInputLayout) TextInputLayout title;
    @BindView(R.id.et_content) EditText content;
    private int mSelectedType;
    private Intent result;
    private boolean canSend = false;
    private boolean isReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    @Override
    public void initActivity() {
        mSelectedType = getIntent().getIntExtra(TAG, -1);
        assert title.getEditText() != null;
        isReply = mSelectedType == -1;
        if (isReply) {
            canSend = true;
            mTypes.forEach(textView -> {
                textView.setEnabled(false);
                textView.setActivated(false);
            });
            Post item = getIntent().getParcelableExtra(TITLE);
            mSelectedType = item.getTid();
            title.setEnabled(false);
            title.getEditText().setText("回复: " + item.getUser().getUsername());
        } else {
            mTypes.get(mSelectedType).setActivated(true);
            for (int i = 0; i < mTypes.size(); i++) {
                int pos = i;
                mTypes.get(pos).setOnClickListener(v -> {
                    if (mSelectedType != pos) {
                        mTypes.get(pos).setActivated(true);
                        mTypes.get(mSelectedType).setActivated(false);
                        mSelectedType = pos;
                    }});
            }

            title.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0) {
                        title.setError("标题不可为空");
                        canSend = false;
                    } else canSend = true;
                }
            });
        }
        result = new Intent();
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        setResult(-1, result);
        finish();
    }

    @OnClick(R.id.send)
    public void send() {
        assert title.getEditText() != null;
        if (canSend) {
            result.putExtra(TAG, mSelectedType);
            result.putExtra(TITLE, title.getEditText().getText().toString());
            result.putExtra(CONTENT, content.getText().toString());
            setResult(1, result);
            finish();
        } else {
            BaseActivity.get.toast("错误", TastyToast.ERROR);
        }
    }
}
