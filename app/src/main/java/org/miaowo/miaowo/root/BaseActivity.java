package org.miaowo.miaowo.root;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.R;
import org.miaowo.miaowo.util.UpdateUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Response;

/**
 * 创建的所有Activity的基类
 * Created by luqin on 16-12-28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 当前活动的 Activity
     */
    public static BaseActivity get;

    private ArrayList<Runnable> mPermissionRequestList;
    private Unbinder mUnbinder = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        get = this;
        mPermissionRequestList = new ArrayList<>();
    }
    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mUnbinder = ButterKnife.bind(this);
        initActivity();
    }
    @Override
    public void onResume() {
        super.onResume();
        get = this;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Runnable action = mPermissionRequestList.get(requestCode);
        if (action != null) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                action.run();
            } else {
                toast(getString(R.string.err_permissions), TastyToast.ERROR);
            }
        }
    }

    /**
     * 在此处初始化, 以便绑定控件
     */
    public abstract void initActivity();

    /**
     * 直接使用主线程发送一条 Toast
     * @param msg 消息
     * @param type TastyToast样式
     */
    public void toast(String msg, int type) {
        runOnUiThreadIgnoreError(() -> TastyToast.makeText(getBaseContext(), msg, TastyToast.LENGTH_SHORT, type).show());
    }

    /**
     * 直接使用主线程发送一条 Toast
     * @param stringId 文本 Id
     * @param type TastyToast样式
     */
    public void toast(@StringRes int stringId, int type) {
        runOnUiThreadIgnoreError(() -> TastyToast.makeText(getBaseContext(), getString(stringId), TastyToast.LENGTH_SHORT, type).show());
    }

    /**
     * 执行需要执行时权限的操作
     * @param action 需要执行的操作
     * @param permissions 需要检查的敏感权限
     */
    public void runWithPermission(Runnable action, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> disallowedPermissions = new ArrayList<>();
            for (String permission : permissions) {
                int chkResult = PermissionChecker.checkSelfPermission(this, permission);
                if (chkResult != PermissionChecker.PERMISSION_GRANTED) {
                    disallowedPermissions.add(permission);
                }
            }
            if (disallowedPermissions.size() != 0) {
                String[] pms = new String[disallowedPermissions.size()];
                mPermissionRequestList.add(action);
                requestPermissions(disallowedPermissions.toArray(pms), mPermissionRequestList.size() - 1);
            } else {
                action.run();
            }
        } else {
            action.run();
        }
    }

    /**
     * 设置 Fragment 显示的进度
     * @param process 进度 [0-100]
     * @param message 伴随进度显示的消息
     */
    public void setProcess(int process, String message) {
        List<Fragment> visibleFragment = getSupportFragmentManager().getFragments();
        if (visibleFragment == null || visibleFragment.size() == 0) return;
        if (visibleFragment.get(0) instanceof BaseFragment) {
            runOnUiThreadIgnoreError(() -> ((BaseFragment) visibleFragment.get(0))
                    .getProcessController().setProcess(process, message));
        }
    }

    /**
     * 当需要显示进度的操作出现错误时调用
     * @param e 错误
     */
    public void processError(Exception e) {
        List<Fragment> visibleFragment = getSupportFragmentManager().getFragments();
        if (visibleFragment == null || visibleFragment.size() == 0) return;
        if (visibleFragment.get(0) instanceof BaseFragment) {
            runOnUiThreadIgnoreError(() -> ((BaseFragment) visibleFragment.get(0))
                    .getProcessController().processError(e));
        }

    }

    /**
     * 停止进度显示
     */
    public void stopProcess() {
        List<Fragment> visibleFragment = getSupportFragmentManager().getFragments();
        if (visibleFragment == null || visibleFragment.size() == 0) return;
        if (visibleFragment.get(0) instanceof BaseFragment) {
            runOnUiThreadIgnoreError(() -> ((BaseFragment) visibleFragment.get(0))
                    .getProcessController().stopProcess());
        }
    }

    /**
     * 进行升级操作
     */
    public void update(Response response) {
        runWithPermission(() -> new Thread(() -> {
            try {
                if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    BaseActivity.get.toast(R.string.err_sdcard, TastyToast.ERROR);
                    return;
                }
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "miaowo");
                if (!dir.isDirectory()) if (!dir.mkdirs()) throw new Exception("无法创建安装文件文件夹");
                File app = new File(dir, "miaowo.apk");
                if (app.exists()) if (!app.delete()) throw new Exception("无法删除已存在安装包");
                if (!app.createNewFile()) throw new Exception("无法创建安装文件");
                FileOutputStream fis = new FileOutputStream(app);
                BufferedInputStream bis = new BufferedInputStream(response.body().byteStream());
                byte[] bs = new byte[1024 * 512];
                int length = bis.read(bs);
                while (length > 0) {
                    fis.write(bs, 0, length);
                    length = bis.read(bs);
                }
                bis.close();
                fis.close();
                UpdateUtil.utils().install(app);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 在 UI 线程执行操作, 但忽略错误信息
     * 这样不会造成 APP 崩溃, 但可能造成程序逻辑错误
     * @param action 需要执行的内容
     */
    public void runOnUiThreadIgnoreError(Runnable action) {
        try {
            runOnUiThread(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示 Fragment
     * @param container 容器 id
     * @param show 要显示的 Fragment
     */
    public void loadFragment(@IdRes int container, BaseFragment show) {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getFragments() == null || manager.getFragments().isEmpty()) {
            manager.beginTransaction().add(container, show).show(show).commit();
        } else {
            manager.beginTransaction()
                    .replace(container, show)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .setCustomAnimations(R.anim.fg_in, R.anim.fg_out, R.anim.fg_pop_in, R.anim.fg_pop_out)
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * 自定义处理错误信息
     * @param err 错误信息
     * @param handler 自定义处理方法
     */
    public void handleError(Throwable err, Consumer<Throwable> handler) {
        Throwable hErr = err == null ? new Exception(getString(R.string.err_empty)) : err;
        Consumer<Throwable> hHandler = handler == null ? throwable -> {
                throwable.printStackTrace();
                toast(hErr.getMessage(), TastyToast.ERROR);
            } : handler;

        hHandler.accept(hErr);
    }

    /**
     * 处理错误信息 弹出ERROR主题的Toast
     * @param err 错误信息
     */
    public void handleError(Throwable err) {
        handleError(err, throwable -> {
            throwable.printStackTrace();
            toast(err.getMessage(), TastyToast.ERROR);
        });
    }

    /**
     * 处理错误信息 弹出ERROR主题的Toast
     * @param err 错误信息
     */
    public void handleError(@StringRes int err) {
        Exception rErr = new Exception(get.getString(err));
        handleError(rErr, throwable -> {
            throwable.printStackTrace();
            toast(throwable.getMessage(), TastyToast.ERROR);
        });
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }
}
