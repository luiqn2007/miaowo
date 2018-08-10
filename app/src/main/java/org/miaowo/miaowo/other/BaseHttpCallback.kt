package org.miaowo.miaowo.other

import org.json.JSONObject
import org.miaowo.miaowo.other.template.EmptyHttpCallback
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

open class BaseHttpCallback<T>: EmptyHttpCallback<T>() {
    final override fun onFailure(call: okhttp3.Call?, e: IOException?) {
        onFailure(call, e, null)
    }
    final override fun onFailure(call: Call<T>?, t: Throwable?) {
        onFailure(call, t, null)
    }
    final override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
        try {
            if (response?.isSuccessful != true) {
                val errMsg = response?.body()?.string() ?: ""
                if (errMsg.startsWith("{") && errMsg.endsWith("}"))
                    onFailure(call, null, JSONObject(errMsg))
                else if (errMsg.startsWith("[[") && errMsg.endsWith("]]"))
                    onFailure(call, null, errMsg.substring(2, errMsg.length - 2))
                else
                    onFailure(call, null, errMsg)
            } else onSucceed(call, response)
        } catch (e: IOException) {
            onFailure(call, e)
        }
    }
    final override fun onResponse(call: Call<T>?, response: Response<T>?) {
        try {
            if (response?.isSuccessful != true) {
                val errMsg = response?.errorBody()?.string() ?: ""
                if (errMsg.startsWith("{") && errMsg.endsWith("}"))
                    onFailure(call, null, JSONObject(errMsg))
                else if (errMsg.startsWith("[[") && errMsg.endsWith("]]"))
                    onFailure(call, null, errMsg.substring(2, errMsg.length - 2))
                else
                    onFailure(call, null, errMsg)
            } else onSucceed(call, response)
        } catch (e: Throwable) {
            onFailure(call, e)
        }
    }

    open fun onSucceed(call: okhttp3.Call?, response: okhttp3.Response) {}
    open fun onSucceed(call: Call<T>?, response: Response<T>) {}
    open fun onFailure(call: okhttp3.Call?, e: IOException?, errMsg: Any? /* String, JSONObject, null */)
            = e?.printStackTrace() ?: Unit
    open fun onFailure(call: Call<T>?, t: Throwable?, errMsg: Any? /* String, JSONObject, null */)
            = t?.printStackTrace() ?: Unit
}