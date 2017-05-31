package org.miaowo.miaowo.bean.config

/**
 * 软件版本信息
 * Created by lq2007 on 16-11-26.
 */

data class VersionMessage(
        val version: Int, // 版本编号
        val versionName: String, // 当前版本
        val message: String, // 版本介绍
        val url: String)  // 下载地址