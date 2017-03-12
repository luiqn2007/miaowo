package org.miaowo.miaowo.root;

import java.util.HashMap;

/**
 * 自用方法集合基类
 * Created by luqin on 17-1-25.
 */

public class BaseSet {
    private HashMap<String, Object> arguments;

    public HashMap<String, Object> getArguments() {
        if (arguments == null) {
            arguments = new HashMap<>();
        }
        return arguments;
    }
}
