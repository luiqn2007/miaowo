package org.miaowo.miaowo.fragment.welcome


import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import kotlinx.android.synthetic.main.fragment_index.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.extra.spGet
import org.miaowo.miaowo.bean.config.TextIconConfig
import org.miaowo.miaowo.databinding.FragmentIndexBinding
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.API
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
                args.putBoolean(Const.FG_TO_BACKSTACK, false)
                fragment.arguments = args
                sInstance = fragment
            }
            return sInstance!!
        }
    }

    lateinit var binding: FragmentIndexBinding
    private var mMiaoListener: IMiaoListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mMiaoListener = context
        mMiaoListener?.decorationVisible = false
    }

    override fun onDetach() {
        super.onDetach()
        @Suppress("DEPRECATION")
        mMiaoListener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_index, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.drawables = arrayListOf(
                ImageUtil.textIcon(TextIconConfig(Color.rgb(255, 61, 0), Color.WHITE, FontAwesome.Icon.faw_question)),
                ImageUtil.textIcon(TextIconConfig(Color.rgb(255, 98, 0), Color.WHITE, FontAwesome.Icon.faw_commenting_o)),
                ImageUtil.textIcon(TextIconConfig(Color.rgb(139, 195, 74), Color.WHITE, FontAwesome.Icon.faw_calendar_check_o))
        )
        val arr = resources.getStringArray(R.array.index)
        binding.texts = arrayListOf<String>(arr[0], arr[1], arr[2])
        login.setOnClickListener { mMiaoListener?.jump(IMiaoListener.JumpFragment.Login) }
        register.setOnClickListener { mMiaoListener?.jump(IMiaoListener.JumpFragment.Register) }
    }
}
