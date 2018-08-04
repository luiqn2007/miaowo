package org.miaowo.miaowo.data.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import org.miaowo.miaowo.API
import org.miaowo.miaowo.data.bean.Category
import org.miaowo.miaowo.fragment.CategoryFragment
import org.miaowo.miaowo.other.template.EmptyCallback
import retrofit2.Call
import retrofit2.Response

class CategoryModel: ViewModel() {

    private val sCategories = mutableMapOf<Int, MutableLiveData<Category>>()

    operator fun get(id: Int): LiveData<Category> {
        if (sCategories[id] == null) sCategories[id] = MutableLiveData()
        if (id == CategoryFragment.UNREAD) {
            API.Docs.unread().enqueue(object : EmptyCallback<Category>() {
                override fun onResponse(call: Call<Category>?, response: Response<Category>?) {
                    sCategories[id]!!.postValue(response?.body())
                }
            })
        } else {
            API.Docs.category(id).enqueue(object : EmptyCallback<Category>() {
                override fun onResponse(call: Call<Category>?, response: Response<Category>?) {
                    sCategories[id]!!.postValue(response?.body())
                }
            })
        }
        return sCategories[id]!!
    }

    fun next(id: Int) {
        sCategories[id]?.apply {
            val category = value
            if (category != null && category.pagination?.pageCount != category.pagination?.currentPage) {
                if (id == CategoryFragment.UNREAD) {
                    API.Docs.unread(category.pagination?.next?.qs).enqueue(object : EmptyCallback<Category>() {
                        override fun onResponse(call: Call<Category>?, response: Response<Category>?) {
                            sCategories[id]!!.postValue(response?.body())
                        }
                    })
                } else {
                    API.Docs.category(id, category.pagination?.next?.qs ?: "").enqueue(object : EmptyCallback<Category>() {
                        override fun onResponse(call: Call<Category>?, response: Response<Category>?) {
                            sCategories[id]!!.postValue(response?.body())
                        }
                    })
                }
            } else {
                postValue(null)
            }
        }
    }

    fun first(id: Int) {
        sCategories[id]?.apply {
            if (id == CategoryFragment.UNREAD) {
                API.Docs.unread().enqueue(object : EmptyCallback<Category>() {
                    override fun onResponse(call: Call<Category>?, response: Response<Category>?) {
                        sCategories[id]!!.postValue(response?.body())
                    }
                })
            } else {
                API.Docs.category(id).enqueue(object : EmptyCallback<Category>() {
                    override fun onResponse(call: Call<Category>?, response: Response<Category>?) {
                        sCategories[id]!!.postValue(response?.body())
                    }
                })
            }
        }
    }
}