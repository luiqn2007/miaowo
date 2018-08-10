package org.miaowo.miaowo.data.model

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.support.annotation.NonNull

class MessageModel: ViewModel() {

    private val message = MutableLiveData<CharSequence>()
    private var buffer = StringBuffer()

    fun insert(offset: Int, msg: Any) {
        buffer.insert(offset, msg)
        message.postValue(buffer.toString())
    }

    fun append(msg: Any) {
        buffer.append(msg)
        message.postValue(buffer.toString())
    }

    fun replace(from: Int, to: Int, msg: String) {
        buffer.replace(from, to, msg)
        message.postValue(buffer.toString())
    }

    fun replace(regex: Regex, replacement: String) {
        buffer.replace(regex, replacement)
        message.postValue(buffer.toString())
    }

    fun delete(from: Int, to: Int) {
        buffer.delete(from, to)
        message.postValue(buffer.toString())
    }

    fun set(charSequence: CharSequence) {
        buffer = StringBuffer(charSequence)
        message.postValue(buffer.toString())
    }

    fun observe(@NonNull owner: LifecycleOwner, @NonNull observer: Observer<CharSequence>) {
        message.observe(owner, observer)
    }

    fun removeObserver(@NonNull observer: Observer<CharSequence>) {
        message.removeObserver(observer)
    }
}