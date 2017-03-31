package org.miaowo.miaowo.root;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
    private ProcessController mProcessController;

    public ProcessController getProcessController() {
        if (mProcessController == null) {
            mProcessController = setProcessController();
        }
        return mProcessController;
    }

    protected ProcessController setProcessController() {
        return new ProcessController() {
            @Override
            public void setProcess(int process, String message) {

            }

            @Override
            public void processError(Exception e) {

            }

            @Override
            public void stopProcess() {

            }
        };
    }

    public interface ProcessController {
        // Do when the process is shown and its process is changed
        // The maxValue is 100 and the minValue is 0
        void setProcess(int process, String message);

        // Do when throw a exception while the process is running
        void processError(Exception e);

        // Do if you need interrupt the process
        void stopProcess();
    }
}


