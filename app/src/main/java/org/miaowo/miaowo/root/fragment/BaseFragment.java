package org.miaowo.miaowo.root.fragment;

import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;

import org.miaowo.miaowo.util.LogUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BaseFragment extends Fragment {
    private AnimatorController mAnimatorController;
    private ProcessController mProcessController;

    public AnimatorController getAnimatorController() {
        if (mAnimatorController == null) {
            mAnimatorController = setAnimatorController();
        }
        return mAnimatorController;
    }
    public ProcessController getProcessController() {
        if (mProcessController == null) {
            mProcessController = setProcessController();
        }
        return mProcessController;
    }

    protected AnimatorController setAnimatorController() {
        return new AnimatorController() {
            @Override
            public void animatorToDefault() {

            }
        };
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

    public class AnimatorController {
        private HashMap<String, List<ObjectAnimator>> mAnimators = new HashMap<>();
        private String mLastAnimatorGroup;

        // Start an animator group immediately
        public void startAnimatorGroups(String... groupNames) {
            mLastAnimatorGroup = groupNames[groupNames.length - 1];
            for (String groupName : groupNames) {
                if (!mAnimators.containsKey(groupName)) {
                    LogUtil.e("无效动画组: " + groupName);
                    return;
                }
                for (ObjectAnimator animator : mAnimators.get(groupName)) {
                    animator.start();
                }
            }
        }

        // Add a new animator group to the AnimatorMap
        public void addAnimatorGroup(String groupName, ObjectAnimator... animators) {
            mLastAnimatorGroup = groupName;
            mAnimators.put(groupName, Arrays.asList(animators));
        }

        // Remove an animator group
        public void removeAnimatorGroup(String groupName) {
            mLastAnimatorGroup = groupName;
            mAnimators.remove(groupName);
        }

        // Find animator list by group name;
        public List<ObjectAnimator> getAnimatorGroupByName(String groupName) {
            return mAnimators.get(groupName);
        }

        // Get last animator group's name
        public String getLastAnimatorGroupName() {
            return mLastAnimatorGroup;
        }

        // Return the fragment to default
        public void animatorToDefault() {

        }
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


