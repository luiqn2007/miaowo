package org.miaowo.miaowo.root.set;

import android.os.Bundle;

/**
 * 自用方法集合基类
 * Created by luqin on 17-1-25.
 */

public class SetRoot {
    private Bundle arguments;

    public Bundle getArguments() {
        if (arguments == null) {
            arguments = new Bundle();
        }
        return arguments;
    }
}
