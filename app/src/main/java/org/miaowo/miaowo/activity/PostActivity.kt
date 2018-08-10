package org.miaowo.miaowo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.liaoinstan.springview.container.DefaultFooter
import com.liaoinstan.springview.container.DefaultHeader
import com.liaoinstan.springview.widget.SpringView
import kotlinx.android.synthetic.main.activity_post.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.adapter.PostListener
import org.miaowo.miaowo.data.model.PostModel
import org.miaowo.miaowo.other.Const

class PostActivity : AppCompatActivity() {

    private lateinit var mPostModel: PostModel
    private val mAdapter = PostAdapter(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        setSupportActionBar(toolBar)
        toolBar.setTitle(R.string.question_detail)

        initList()

        mPostModel = ViewModelProviders.of(this)[PostModel::class.java]
        mPostModel.load(intent.getIntExtra(Const.ID, -1)).observe(this, Observer {
            mAdapter.update(it?.posts)
            springView.onFinishFreshAndLoad()
        })
    }

    private fun initList() {
        springView.apply {
            footer = DefaultFooter(this@PostActivity)
            header = DefaultHeader(this@PostActivity)
            setListener(object : SpringView.OnFreshListener {
                override fun onLoadmore() = springView.onFinishFreshAndLoad()
                override fun onRefresh() = mPostModel.refresh()
            })
        }

        list.apply {
            layoutManager = LinearLayoutManager(this@PostActivity)
            adapter = mAdapter
            addOnItemTouchListener(PostListener(applicationContext, mAdapter))
        }
    }
}
