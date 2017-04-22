package org.miaowo.miaowo.root;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    private ProcessController mProcessController;
    private Unbinder mUnbinder;

    public ProcessController getProcessController() {
        return mProcessController;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, view);
        initView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 在此处初始化页面
     * @param view 根视图
     */
    public abstract void initView(View view);

    protected void setProcessController(ProcessController controller) {
        mProcessController = controller;
    }

    public interface ProcessController {
        void setProcess(int process, String message);
        void processError(Exception e);
        void stopProcess();
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}


