package org.miaowo.miaowo.root;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.impl.interfaces.ExceptionHandled;

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
        super.onResume();
//        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void handleError(Exception e) {
        e.printStackTrace();
        TastyToast.makeText(this, e.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
    }
}
