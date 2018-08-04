package org.miaowo.miaowo.data.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.miaowo.miaowo.API
import org.miaowo.miaowo.data.bean.User
import org.miaowo.miaowo.other.template.EmptyCallback
import retrofit2.Call
import retrofit2.Response

class UserModel: ViewModel() {

    private var mUser: MutableLiveData<User> = MutableLiveData()
    private var mUserSearch: Thread? = null

    init {
        mUser.postValue(API.user)
    }

    operator fun get(user: Any?): LiveData<User> {
        return when (user) {
            is Int -> load(user)
            is String -> load(user)
            else -> load()
        }
    }

    private fun load(): LiveData<User> {
        mUser.postValue(API.user)
        return mUser
    }

    private fun load(username: String): LiveData<User> {
        API.Docs.user(username).enqueue(object : EmptyCallback<User>() {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                mUser.postValue(response?.body())
            }
        })
        return mUser
    }

    private fun load(uid: Int): LiveData<User> {
        if (mUserSearch == null) mUserSearch = Thread {
            try {
                var response = API.Docs.users().execute()
                var finish = false
                do {
                    if (response.isSuccessful) {
                        val users = response.body()
                        users?.users?.filter { it.uid == uid }?.apply {
                            if (isNotEmpty()) {
                                mUser.postValue(first())
                                finish = true
                            } else {
                                if (users.pagination?.currentPage != users.pagination?.pageCount) {
                                    response = API.Docs.users(users.pagination?.next?.qs).execute()
                                } else {
                                    finish = true
                                }
                            }
                        }
                    } else {
                        finish = true
                    }
                } while (!finish)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (mUserSearch!!.isAlive) mUserSearch!!.interrupt()
        mUserSearch!!.start()
        return mUser
    }
}