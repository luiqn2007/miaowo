package org.miaowo.miaowo.ui.processView

import android.graphics.drawable.Drawable

/**
 * 可显示进度的控件
 * Created by lq2007 on 2017/9/27 0027.
 */
interface IProcessable {
    // 最大进度
    var maxProcess: Float
    // 最小进度
    var minProcess: Float
    // 当前进度
    var process: Float
    // 当前进度（0-1）
    var processPercent: Float
    // 显示方式（不显示，仅文本，仅进度，同时显示）
    var showType: ShowType
    // 进度显示方式（进度，进度百分比）
    var processType: ProcessType
    // 错误背景图
    var errorBackground: Drawable
    // 成功背景图
    var successfulBackground: Drawable
    // 进度提示文本
    var processText: CharSequence
    // 是否为错误
    var isError: Boolean
    // 进度动画是否已经显示完毕
    val showFinish: Boolean

    // 显示进度
    fun showProcess()

    // 隐藏进度
    fun hideProcess()

    enum class ShowType { Null, OnlyText, OnlyProcess, Both }

    enum class ProcessType { Number, Percent }
}