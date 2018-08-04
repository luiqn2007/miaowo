package org.miaowo.miaowo.other

import android.app.Activity
import com.sdsmdg.tastytoast.TastyToast
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.toast
import org.miaowo.miaowo.other.template.EmptyCallback
import retrofit2.Call
import java.io.IOException

abstract class ActivityCallback<T>(val activity: Activity): EmptyCallback<T>() {
    override fun onFailure(call: Call<T>?, t: Throwable?) {
        activity.toast(t?.message ?: activity.getString(R.string.err_ill), TastyToast.ERROR)
    }

    override fun onFailure(call: okhttp3.Call?, e: IOException?) {
        activity.toast(e?.message ?: activity.getString(R.string.err_ill), TastyToast.ERROR)
    }
}