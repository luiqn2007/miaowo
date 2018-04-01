package org.miaowo.miaowo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.base.App
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.util.FormatUtil

/**
 * 展示图片
 * Created by lq200 on 2018/3/19.
 */
class ImageFragment : Fragment() {
    companion object {
        fun getInstance(imgUrl: String): ImageFragment {
            val fragment = ImageFragment()
            val args = Bundle()
            args.putString(Const.NAME, imgUrl)
            fragment.arguments = args
            return fragment
        }
    }

    var imgUrl = ""
    var mMiaoListener: IMiaoListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imgUrl = arguments?.get(Const.NAME) as String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflateId(inflater, R.layout.fragment_image, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMiaoListener?.decorationVisible = false
        val screenSize = FormatUtil.screenSize
        val minSize = Math.min(screenSize.x, screenSize.y)
        Picasso.with(App.i).load(imgUrl)
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_loading)
                .resize(minSize, minSize)
                .into(image)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mMiaoListener = context as? IMiaoListener
    }

    override fun onDetach() {
        mMiaoListener = null
        super.onDetach()
    }
}