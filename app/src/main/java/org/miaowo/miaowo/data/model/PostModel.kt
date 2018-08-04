package org.miaowo.miaowo.data.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.miaowo.miaowo.API
import org.miaowo.miaowo.data.bean.Topic
import org.miaowo.miaowo.other.template.EmptyCallback
import retrofit2.Call
import retrofit2.Response

class PostModel: ViewModel() {
    private val mPost = MutableLiveData<Topic>()

    fun load(id: Int): LiveData<Topic> {
        API.Docs.topic(id).enqueue(object : EmptyCallback<Topic>() {
            override fun onResponse(call: Call<Topic>?, response: Response<Topic>?) {
                mPost.postValue(response?.body())
            }
        })
        return mPost
    }

    fun refresh() {
        val id = mPost.value?.tid
        if (id != null) {
            API.Docs.topic(id).enqueue(object : EmptyCallback<Topic>() {
                override fun onResponse(call: Call<Topic>?, response: Response<Topic>?) {
                    mPost.postValue(response?.body())
                }
            })
        }
    }
}