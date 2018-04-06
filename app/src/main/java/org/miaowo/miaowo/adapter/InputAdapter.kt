package org.miaowo.miaowo.adapter

import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R

class InputAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mContentList = mutableListOf<InputViewContent>()
    val contents get() = mContentList
    val results get() = mContentList.map { it.s }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // 啊啊啊啊啊啊啊啊啊 那个SB写的 TextInputLayout 必须用 AppCompatActivity 当 Context 啊啊
        val v = LayoutInflater.from(Miao.i).inflate(R.layout.list_input, parent, false)
        return object : RecyclerView.ViewHolder(v) {}
    }

    override fun getItemCount() = mContentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.apply {
            val h = mContentList[position]

            findViewById<TextInputLayout>(R.id.input_layout)?.apply {
                hint = h.hint
                editText?.apply {
                    addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {}
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            h.s = s
                        }
                    })
                    maxLines = h.maxLines
                }
            }

            findViewById<ImageView>(R.id.add)?.apply {
                visibility = if (h.addVisible) View.VISIBLE else View.GONE
                setOnClickListener { add(holder.adapterPosition, h.copyEmpty()) }
            }

            findViewById<ImageView>(R.id.remove)?.apply {
                visibility = if (h.removeVisible) View.VISIBLE else View.GONE
                setOnClickListener { remove(holder.adapterPosition) }
            }
        }
    }

    fun add(position: Int, content: InputViewContent) {
        val i = position + 1
        mContentList.add(i, content)
        notifyItemInserted(i)
    }

    fun remove(position: Int) {
        if (position == 1 && mContentList.size == 1) {
            mContentList[1] = mContentList[1].copyEmpty()
//            notifyDataSetChanged()
        } else {
            mContentList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun add(content: InputViewContent) = add(mContentList.size - 1, content)

    data class InputViewContent(val hint: String?, val addVisible: Boolean, val removeVisible: Boolean, val maxLines: Int) {
        var s: CharSequence? = null
        fun copyEmpty() = copy().apply { s = null }
    }
}