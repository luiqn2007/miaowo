package org.miaowo.miaowo.data.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.miaowo.miaowo.API
import org.miaowo.miaowo.data.bean.ChatRoom
import org.miaowo.miaowo.data.bean.ChatRooms
import org.miaowo.miaowo.other.template.EmptyCallback
import retrofit2.Call
import retrofit2.Response

class ChatModel: ViewModel() {
    val rooms: MutableLiveData<ChatRooms> = MutableLiveData()
    val room: MutableLiveData<ChatRoom> = MutableLiveData()

    fun loadRooms() {
        rooms.postValue(null)
        API.Docs.chatRoom().enqueue(object : EmptyCallback<ChatRooms>() {
            override fun onResponse(call: Call<ChatRooms>?, response: Response<ChatRooms>?) {
                rooms.postValue(response?.body())
            }
        })
    }

    fun loadRoom(roomId: Int) {
        room.postValue(null)
        API.Docs.chatMessage(roomId).enqueue(object : EmptyCallback<ChatRoom>() {
            override fun onResponse(call: Call<ChatRoom>?, response: Response<ChatRoom>?) {
                room.postValue(response?.body())
            }
        })
    }
}