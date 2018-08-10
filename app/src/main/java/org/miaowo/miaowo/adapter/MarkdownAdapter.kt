package org.miaowo.miaowo.adapter

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.ListAdapter
import org.miaowo.miaowo.base.ListHolder
import org.miaowo.miaowo.other.BaseListTouchListener

class MarkdownAdapter(val context: AppCompatActivity): ListAdapter<InputViewContent>(object : ListAdapter.ViewCreator<InputViewContent> {

    override fun createHolder(parent: ViewGroup, viewType: Int): ListHolder {
        // TextInputLayout 必须用 AppCompatActivity 作为 Context
        return ListHolder(R.layout.list_input, parent, context)
    }

    override fun bindView(item: InputViewContent, holder: ListHolder, type: Int) {
        holder.itemView.apply {
            findViewById<TextInputLayout>(R.id.input_layout)?.apply {
                hint = item.hint
                editText?.apply {
                    addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(s: Editable?) {}
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            item.s = s
                        }
                    })
                    maxLines = item.maxLines
                }
            }

            findViewById<ImageView>(R.id.add)?.visibility = if (item.addVisible) View.VISIBLE else View.GONE

            findViewById<ImageView>(R.id.remove)?.visibility = if (item.removeVisible) View.VISIBLE else View.GONE
        }
    }

}) {
    val results get() = items.map { it.s }
}

class MarkdownListener(context: Context, val adapter: MarkdownAdapter): BaseListTouchListener(context) {

    override fun onClick(view: View?, position: Int): Boolean {
        return when (view?.id) {
            R.id.add -> {
                val i = position + 1
                adapter.insert(adapter.items[0].copyEmpty(), i)
                true
            }
            R.id.remove -> {
                adapter.remove(position)
                true
            }
            else -> false
        }
    }
}

data class InputViewContent(val hint: String?, val addVisible: Boolean, val removeVisible: Boolean, val maxLines: Int) {
    var s: CharSequence? = null
    fun copyEmpty() = copy().apply { s = null }
}