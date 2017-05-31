package org.miaowo.miaowo.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sdsmdg.tastytoast.TastyToast
import okhttp3.Response
import org.miaowo.miaowo.R
import org.miaowo.miaowo.util.UpdateUtil
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.properties.Delegates

/**
 * 创建的所有Activity的基类
 * Created by luqin on 16-12-28.
 */

abstract class BaseActivity private constructor() : AppCompatActivity() {

    companion object {
        /**
         * 当前活动的 Activity
         */
        var get: BaseActivity? = null
    }

    private var layoutLoader by Delegates.notNull<Runnable>()

    constructor(@LayoutRes layoutId : Int) : this() {
        layoutLoader = Runnable {
            setContentView(layoutId)
        }
    }

    constructor(view : View) : this() {
        layoutLoader = Runnable {
            setContentView(view)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutLoader.run()
    }

    private var mPermissionRequestList = mutableListOf<() -> Unit>()

    public override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initActivity()
    }

    public override fun onResume() {
        super.onResume()
        get = this
    }

    override fun onDestroy() {
        super.onDestroy()
        if (get == this) get = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val action = mPermissionRequestList[requestCode]
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            action()
        } else {
            toast(getString(R.string.err_permissions), TastyToast.ERROR)
        }
    }

    /**
     * 在此处初始化, 以便绑定控件
     */
    abstract fun initActivity()

    /**
     * 直接使用主线程发送一条 Toast
     * @param msg 消息
     * @param type TastyToast样式
     */
    fun toast(msg: String, type: Int) {
        TastyToast.makeText(baseContext, msg, TastyToast.LENGTH_SHORT, type).show()
    }

    /**
     * 直接使用主线程发送一条 Toast
     * @param stringId 文本 Id
     * @param type TastyToast样式
     */
    fun toast(@StringRes stringId: Int, type: Int) {
        TastyToast.makeText(baseContext, getString(stringId), TastyToast.LENGTH_SHORT, type).show()
    }

    /**
     * 执行需要执行时权限的操作
     * @param action 需要执行的操作
     * *
     * @param permissions 需要检查的敏感权限
     */
    fun runWithPermission(action: () -> Unit, vararg permissions: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val disallowedPermissions = ArrayList<String>()
            for (permission in permissions) {
                val chkResult = PermissionChecker.checkSelfPermission(this, permission)
                if (chkResult != PermissionChecker.PERMISSION_GRANTED) {
                    disallowedPermissions.add(permission)
                }
            }
            if (disallowedPermissions.size != 0) {
                mPermissionRequestList.add(action)
                requestPermissions(disallowedPermissions.toTypedArray(), mPermissionRequestList.size - 1)
            } else action()
        } else action()
    }

    /**
     * 设置 Fragment 显示的进度
     * @param process 进度 [0-100]
     * *
     * @param message 伴随进度显示的消息
     */
    fun setProcess(process: Int, message: String) {
        val visibleFragment = supportFragmentManager.fragments
        if (visibleFragment == null || visibleFragment.size == 0) return
        if (visibleFragment[0] is BaseFragment) {
            (visibleFragment[0] as BaseFragment)
                    .processController?.setProcess(process, message)
        }
    }

    /**
     * 当需要显示进度的操作出现错误时调用
     * @param e 错误
     */
    fun processError(e: Exception) {
        val visibleFragment = supportFragmentManager.fragments
        if (visibleFragment == null || visibleFragment.size == 0) return
        if (visibleFragment[0] is BaseFragment) {
            (visibleFragment[0] as BaseFragment)
                    .processController?.processError(e)
        }
    }

    /**
     * 停止进度显示
     */
    fun stopProcess() {
        val visibleFragment = supportFragmentManager.fragments
        if (visibleFragment == null || visibleFragment.size == 0) return
        if (visibleFragment[0] is BaseFragment) {
            (visibleFragment[0] as BaseFragment)
                    .processController?.stopProcess()
        }
    }

    /**
     * 进行升级操作
     */
    fun update(response: Response) {
        runWithPermission({
            Thread {
                try {
                    if (Environment.MEDIA_MOUNTED != Environment.getExternalStorageState()) {
                        toast(R.string.err_sdcard, TastyToast.ERROR)
                    } else {
                        val dir = File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "miaowo")
                        if (!dir.isDirectory) if (!dir.mkdirs()) throw Exception(getString(R.string.err_dir))
                        val app = File(dir, "miaowo.apk")
                        if (app.exists()) if (!app.delete()) throw Exception(getString(R.string.err_del))
                        if (!app.createNewFile()) throw Exception(getString(R.string.err_apk))
                        val fis = FileOutputStream(app)
                        val bis = BufferedInputStream(response.body()!!.byteStream())
                        val bs = ByteArray(1024 * 512)
                        var length = bis.read(bs)
                        while (length > 0) {
                            fis.write(bs, 0, length)
                            length = bis.read(bs)
                        }
                        bis.close()
                        fis.close()
                        UpdateUtil.install(app)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.run()
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    /**
     * 在 UI 线程执行操作, 但忽略错误信息
     * 这样不会造成 APP 崩溃, 但可能造成程序逻辑错误
     * @param action 需要执行的内容
     */
    fun runOnUiThreadIgnoreError(action : () -> Unit) {
        try {
            runOnUiThread { action() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 显示 Fragment
     * @param container 容器 id
     * *
     * @param show 要显示的 Fragment
     */
    fun loadFragment(@IdRes container: Int = R.id.container, show: BaseFragment) {
        val manager = supportFragmentManager
        if (manager.fragments == null || manager.fragments.isEmpty()) {
            manager.beginTransaction().add(container, show).show(show).commit()
        } else {
            manager.beginTransaction()
                    .replace(container, show)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .setCustomAnimations(R.anim.fg_in, R.anim.fg_out, R.anim.fg_pop_in, R.anim.fg_pop_out)
                    .addToBackStack(null)
                    .commit()
        }
    }

    /**
     * 处理错误信息
     * @param err 错误信息
     * @param handler 自定义处理方法 默认弹出 ERROR 主题的 Toast
     */
    fun handleError(err: Throwable?, handler: (Throwable?) -> Unit = {
        if (err != null) {
            it?.printStackTrace()
            toast(it?.message ?: getString(R.string.err_ill), TastyToast.ERROR)
        }
    }) {
        handler(err)
    }

    /**
     * 处理错误信息 弹出ERROR主题的Toast
     * @param err 错误信息
     */
    fun handleError(@StringRes err: Int) {
        handleError(Exception(getString(err)))
    }
}
