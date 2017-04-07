package org.miaowo.miaowo.root;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

import com.sdsmdg.tastytoast.TastyToast;

import org.miaowo.miaowo.set.Exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建的所有Activity的基类
 * Created by luqin on 16-12-28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private ArrayList<Runnable> permissionRequestList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        permissionRequestList = new ArrayList<>();
    }

    public void handleError(Exception e) {
        e.printStackTrace();
        TastyToast.makeText(this, e.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
    }

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
                permissionRequestList.add(action);
                requestPermissions(disallowedPermissions.toArray(pms), permissionRequestList.size() - 1);
            } else {
                action.run();
            }
        } else {
            action.run();
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Runnable action = permissionRequestList.get(requestCode);
        if (action != null) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                action.run();
            } else {
                handleError(Exceptions.E_NON_PERMISSION);
            }
        }
    }
}
