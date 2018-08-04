package org.miaowo.miaowo.other.template

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

open class EmptyCallback<T>: Callback<T>, okhttp3.Callback {
    override fun onFailure(call: okhttp3.Call?, e: IOException?) = e?.printStackTrace() ?: Unit
    override fun onFailure(call: Call<T>?, t: Throwable?) = t?.printStackTrace() ?: Unit
    override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {}
    override fun onResponse(call: Call<T>?, response: Response<T>?) {}
}