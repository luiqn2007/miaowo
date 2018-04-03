package org.miaowo.miaowo.fragment.welcome


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.fragment_index.*
import org.miaowo.miaowo.Miao
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.loadSelf
import org.miaowo.miaowo.bean.config.TextIconConfig
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.ImageUtil

/**
 * 欢迎页面
 */
class IndexFragment : Fragment() {

    companion object {
        private var sInstance: IndexFragment? = null
        val INSTANCE = sInstance ?: newInstance()

        fun newInstance(): IndexFragment {
            if (sInstance == null) {
                val fragment = IndexFragment()
                val args = Bundle()
                args.putString(Const.TAG, fragment.javaClass.name)
                args.putBoolean(Const.FG_ADD_TO_BACK_STACK, false)
                args.putBoolean(Const.FG_POP_ALL, true)
                fragment.arguments = args
                sInstance = fragment
            }
            return sInstance!!
        }
    }

    private var mMiaoListener: IMiaoListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mMiaoListener = context
    }

    override fun onDetach() {
        super.onDetach()
        @Suppress("DEPRECATION")
        mMiaoListener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(inflater, R.layout.fragment_index, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMiaoListener?.decorationVisible = false
        setDisplay()
        setAction()
    }

    private fun setDisplay() {
        val texts = resources.getStringArray(R.array.index)
        img1.setImageDrawable(ImageUtil.textIcon(TextIconConfig(Color.rgb(255, 61, 0), Color.WHITE, FontAwesome.Icon.faw_question)))
        img1.contentDescription = texts[0]
        img2.setImageDrawable(ImageUtil.textIcon(TextIconConfig(Color.rgb(255, 98, 0), Color.WHITE, FontAwesome.Icon.faw_commenting_o)))
        img2.contentDescription = texts[1]
        img3.setImageDrawable(ImageUtil.textIcon(TextIconConfig(Color.rgb(139, 195, 74), Color.WHITE, FontAwesome.Icon.faw_calendar_check_o)))
        img3.contentDescription = texts[2]
        txt1.text = texts[0]
        txt2.text = texts[1]
        txt3.text = texts[2]
    }

    private fun setAction() {
        login.setOnClickListener { LoginFragment.INSTANCE.loadSelf(Miao.i) }
        register.setOnClickListener { RegisterFragment.INSTANCE.loadSelf(Miao.i) }
    }
}
