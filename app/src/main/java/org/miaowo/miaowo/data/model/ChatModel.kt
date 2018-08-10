package org.miaowo.miaowo.data.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.miaowo.miaowo.API
import org.miaowo.miaowo.data.bean.ChatRoom
import org.miaowo.miaowo.data.bean.ChatRooms
import org.miaowo.miaowo.other.BaseHttpCallback
import org.miaowo.miaowo.other.template.EmptyHttpCallback
import retrofit2.Call
import retrofit2.Response

class ChatModel: ViewModel() {
    val rooms: MutableLiveData<ChatRooms> = MutableLiveData()
    val room: MutableLiveData<ChatRoom> = MutableLiveData()

    fun loadRooms() {
        rooms.postValue(null)
        API.Docs.chatRoom().enqueue(object : BaseHttpCallback<ChatRooms>() {
            override fun onSucceed(call: Call<ChatRooms>?, response: Response<ChatRooms>) {
                rooms.postValue(response.body())
            }
        })
    }

    fun loadRoom(roomId: Int?) {
        if (roomId == null) {
            room.postValue(null)
            return
        }

        API.Docs.chatMessage(roomId).enqueue(object : BaseHttpCallback<ChatRoom>() {
            override fun onSucceed(call: Call<ChatRoom>?, response: Response<ChatRoom>) {
                room.postValue(response.body())
            }
        })
    }
}