package org.miaowo.miaowo.base.extra

import android.app.Activity
import android.support.annotation.StringRes
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.R


/**
 * Activity 扩展方法
 * Created by luqin on 17-6-22.
 */

fun Activity.toast(msg: String, type: Int) = TastyToast.makeText(baseContext, msg, TastyToast.LENGTH_SHORT, type).show()
fun Activity.toast(@StringRes stringId: Int, type: Int) = TastyToast.makeText(baseContext, getString(stringId), TastyToast.LENGTH_SHORT, type).show()
fun Activity.error(@StringRes err: Int) = this.error(getString(err))
fun Activity.error(err: String?)  {
    if (err != null) {
        toast(err, TastyToast.ERROR)
    }
}
fun Activity.error(err: Throwable?) {
    if (err != null) {
        err.printStackTrace()
        toast(err.message ?: getString(R.string.err_ill), TastyToast.ERROR)
    }
}