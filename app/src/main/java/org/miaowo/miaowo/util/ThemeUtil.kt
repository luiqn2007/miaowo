package org.miaowo.miaowo.util

import android.content.Context
import com.blankj.utilcode.util.FileUtils
import org.miaowo.miaowo.data.config.Theme
import java.io.File

/**
 * 主题换肤
 * Created by luqin on 17-6-1.
 */
object ThemeUtil {
    private lateinit var sThemeDir: File
    private var mAppliedTheme: Theme? = null

    val appliedTheme get() = mAppliedTheme

    val themes
        get() = sThemeDir.listFiles().map { Theme(it) }

    fun init(context: Context) {
        sThemeDir = File(context.filesDir, "theme")
        FileUtils.createOrExistsDir(sThemeDir)
    }

    fun install(theme: File): Theme? {
        if (!theme.isFile) return null
        val target = File(sThemeDir, theme.name)
        FileUtils.copyFile(theme, target)
        if (target.exists())
            return Theme(target)
        return null
    }

    fun apply(theme: Theme) {
        mAppliedTheme = theme
    }
}