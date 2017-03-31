package org.miaowo.miaowo.root;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.util.AttributeSet;

import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

/**
 * 创建的所有Activity的基类
 * Created by luqin on 16-12-28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void handleError(Exception e) {
        e.printStackTrace();
        TastyToast.makeText(this, e.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
    }

    public void setProcess(int process, String message) {
        List<Fragment> visibleFragment = getSupportFragmentManager().getFragments();
        if (visibleFragment == null || visibleFragment.size() == 0) return;
        if (visibleFragment.get(0) instanceof BaseFragment) {
            runOnUiThread(() -> ((BaseFragment) visibleFragment.get(0))
                    .getProcessController().setProcess(process, message));
        }
    }

    public void processError(Exception e) {
        List<Fragment> visibleFragment = getSupportFragmentManager().getFragments();
        if (visibleFragment == null || visibleFragment.size() == 0) return;
        if (visibleFragment.get(0) instanceof BaseFragment) {
            runOnUiThread(() -> ((BaseFragment) visibleFragment.get(0))
                    .getProcessController().processError(e));
        }

    }

    public void stopProcess() {
        List<Fragment> visibleFragment = getSupportFragmentManager().getFragments();
        if (visibleFragment == null || visibleFragment.size() == 0) return;
        if (visibleFragment.get(0) instanceof BaseFragment) {
            runOnUiThread(() -> ((BaseFragment) visibleFragment.get(0))
                    .getProcessController().stopProcess());
        }
    }

    // 用于实现 Fragment 切换动画 自定义动画
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public class MyTransitionSet extends TransitionSet {
        public MyTransitionSet() {
            init();
        }

        // 允许资源文件使用
        public MyTransitionSet(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            setOrdering(ORDERING_TOGETHER);
            addTransition(new ChangeBounds()).
                    addTransition(new ChangeTransform()).
                    addTransition(new ChangeImageTransform()).addTransition(new Fade());
        }
    }
}
