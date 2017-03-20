package org.miaowo.miaowo.bean.data.event;

import org.miaowo.miaowo.root.BaseEvent;

import java.io.File;

import okhttp3.Call;

/**
 * 文件信息
 * Created by luqin on 17-3-12.
 */

public class FileEvent extends BaseEvent {
    public File file;

    public FileEvent(Call call, File file) {
        super(call);
        this.file = file;
    }
}
