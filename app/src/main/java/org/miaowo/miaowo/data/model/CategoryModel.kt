package org.miaowo.miaowo.data.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.miaowo.miaowo.API
import org.miaowo.miaowo.activity.MainActivity
import org.miaowo.miaowo.data.bean.Category
import org.miaowo.miaowo.other.BaseHttpCallback
import retrofit2.Call
import retrofit2.Response

class CategoryModel: ViewModel() {

    val category = MutableLiveData<Category>()

    fun load(id: Int) {
        if (id == Category.UNREAD_ID) {
            API.Docs.unread().enqueue(object : BaseHttpCallback<Category>() {
                override fun onSucceed(call: Call<Category>?, response: Response<Category>) {
                    category.postValue(response.body())
                }
            })
        } else {
            API.Docs.category(id).enqueue(object : BaseHttpCallback<Category>() {
                override fun onSucceed(call: Call<Category>?, response: Response<Category>) {
                    category.postValue(response.body())
                }
            })
        }
    }

    fun next() {
        category.apply {
            val category = value
            if (category != null && category.pagination?.pageCount != category.pagination?.currentPage) {
                if (category.cid == Category.UNREAD_ID) {
                    API.Docs.unread(category.pagination?.next?.qs ?: "").enqueue(object : BaseHttpCallback<Category>() {
                        override fun onSucceed(call: Call<Category>?, response: Response<Category>) {
                            this@CategoryModel.category.postValue(response.body())
                        }
                    })
                } else {
                    API.Docs.category(category.cid, category.pagination?.next?.qs ?: "").enqueue(object : BaseHttpCallback<Category>() {
                        override fun onSucceed(call: Call<Category>?, response: Response<Category>) {
                            this@CategoryModel.category.postValue(response.body())
                        }
                    })
                }
            } else {
                load(Category.UNREAD_ID)
            }
        }
    }

    fun first() {
        category.apply {
            val category = value
            if (category != null) {
                if (category.cid == Category.UNREAD_ID) {
                    API.Docs.unread().enqueue(object : BaseHttpCallback<Category>() {
                        override fun onSucceed(call: Call<Category>?, response: Response<Category>) {
                            this@CategoryModel.category.postValue(response.body())
                        }
                    })
                } else {
                    API.Docs.category(category.cid).enqueue(object : BaseHttpCallback<Category>() {
                        override fun onSucceed(call: Call<Category>?, response: Response<Category>) {
                            this@CategoryModel.category.postValue(response.body())
                        }
                    })
                }
            } else {
                load(Category.UNREAD_ID)
            }
        }
    }
}