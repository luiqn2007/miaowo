package org.miaowo.miaowo.handler

import android.app.Activity
import okhttp3.ResponseBody
import org.json.JSONObject
import org.miaowo.miaowo.API
import org.miaowo.miaowo.data.model.ChatModel
import org.miaowo.miaowo.other.ActivityHttpCallback
import retrofit2.Call
import retrofit2.Response

class ChatHandler(activity: Activity) {

    private val mActivity: Activity = activity
    var roomId: Int? = null
    var chatWith: Int? = null
    var chatModel: ChatModel? = null

    fun sendMessage(message: String) {
        if (roomId != null && chatWith != null) {
            // chat room is existed
            API.Users.chat(chatWith!!, message, roomId!!).enqueue(object : ActivityHttpCallback<ResponseBody>(mActivity) {
                override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                    chatModel?.loadRoom(roomId)
                }
            })
        }

        if (roomId == null && chatWith != null) {
            // chat room is not existed
            API.Users.chat(chatWith!!, message).enqueue(object : ActivityHttpCallback<ResponseBody>(mActivity) {
                override fun onSucceed(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                    val obj = JSONObject(response.body()?.string())
                    chatModel?.loadRooms()
                    chatModel?.loadRoom(obj.getJSONObject("payload").getInt("roomId"))
                }
            })
        }
    }

    fun changeRoom(room: Int?, chatWith: Int?) {
        if (room != roomId || (room == null && chatWith != this.chatWith)) {
            this.roomId = room
            this.chatWith = chatWith
            chatModel?.loadRoom(roomId)
        }
    }

}