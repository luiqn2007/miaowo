package org.miaowo.miaowo.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.liaoinstan.springview.container.DefaultFooter
import com.liaoinstan.springview.container.DefaultHeader
import com.liaoinstan.springview.widget.SpringView
import kotlinx.android.synthetic.main.activity_post.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.adapter.PostAdapter
import org.miaowo.miaowo.data.bean.Post
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

                override fun onRefresh() {
                    mPostModel.refresh()
                }
            })
        }

        list.apply {
            layoutManager = LinearLayoutManager(this@PostActivity)
            adapter = mAdapter
            addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                val touchGestureDetector = GestureDetector(
                        this@PostActivity.applicationContext,
                        object : GestureDetector.SimpleOnGestureListener() {
                            override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                                if (e == null) return false
                                return try {
                                    val child = list.findChildViewUnder(e.x, e.y) as ViewGroup
                                    val childView = findView(child, e.rawX.toInt(), e.rawY.toInt())
                                    val position = list.getChildAdapterPosition(child)
                                    onClickListener(childView, position)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    false
                                }
                            }
                        })

                override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?)
                        = touchGestureDetector.onTouchEvent(e)
            })
        }
    }

    private fun onClickListener(view: View, position: Int): Boolean {
        return when (view.id) {
            R.id.content -> {
                (mAdapter.getItem(position) as? Post)?.let {
                    if (position == 0)
                        startActivity(Intent(this, SendActivity::class.java).apply {
                            putExtra(Const.TYPE, SendActivity.TYPE_REPLY)
                            putExtra(Const.ID, it.tid)
                            putExtra(Const.REPLY, it.user?.username ?: "Unknown User")
                        })
                    else
                        startActivity(Intent(this, SendActivity::class.java).apply {
                            putExtra(Const.TYPE, SendActivity.TYPE_REPLY)
                            putExtra(Const.ID, it.tid)
                            putExtra(Const.ID2, it.pid)
                            putExtra(Const.REPLY, it.user?.username ?: "Unknown User")
                        })
                }
                true
            }
            else -> false
        }
    }

    private fun findView(viewGroup: ViewGroup, x: Int, y: Int, rect: Rect? = null): View {
        val count = viewGroup.childCount
        val viewRect = rect ?: Rect()
        for (i in 0 until count) {
            val child = viewGroup.getChildAt(i)
            child.getGlobalVisibleRect(viewRect)
            if (viewRect.contains(x, y)) {
                return if (child !is ViewGroup) child
                else findView(child, x, y, viewRect)
            }
        }
        return viewGroup
    }
}
