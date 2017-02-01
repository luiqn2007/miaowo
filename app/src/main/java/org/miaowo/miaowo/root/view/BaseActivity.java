package org.miaowo.miaowo.root.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import org.miaowo.miaowo.D;
import org.miaowo.miaowo.impl.interfaces.NotSingle.ExceptionHandled;
import org.miaowo.miaowo.ui.FloatView;

/**
 * 创建的所有Activity的基类
 * Created by luqin on 16-12-28.
 */

public class BaseActivity extends AppCompatActivity implements ExceptionHandled {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onResume() {
        D.getInstance().activeActivity = this;
        super.onResume();
//        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Bugtags.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (D.getInstance().activeActivity.equals(this)) {
            D.getInstance().activeActivity = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void handleError(Exception e) {
        e.printStackTrace();
        Snackbar.make(getWindow().getDecorView(), e.getMessage(), Snackbar.LENGTH_SHORT).show();
    }
}
