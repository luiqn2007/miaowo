package org.miaowo.miaowo.fragment.setting

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_setting.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.interfaces.IMiaoListener
import org.miaowo.miaowo.base.extra.inflateId
import org.miaowo.miaowo.base.extra.loadFragment
import org.miaowo.miaowo.other.Const

/**
 * 设置
 */
class SettingFragment : Fragment() {

    companion object {
        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
            val args = Bundle()
            fragment.arguments = args
            args.putString(Const.TAG, fragment.javaClass.name)
            return fragment
        }
    }

    private val mUserSetting = UserSetting.newInstance()
    private val mAppSetting = AppSetting.newInstance()
    var mListenerI: IMiaoListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is IMiaoListener) mListenerI = context
    }

    override fun onDetach() {
        super.onDetach()
        mListenerI = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflateId(R.layout.fragment_setting, inflater, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mListenerI?.toolbar?.title = getString(R.string.setting)
        loadFragment(mUserSetting)
        tab.run {
            addTab(tab.newTab().setText(R.string.setting_user), 0)
            addTab(tab.newTab().setText(R.string.setting_sys), 1)
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
                override fun onTabSelected(tab: TabLayout.Tab) {
                    when (tab.position) {
                        0 -> childFragmentManager.beginTransaction()
                                .replace(R.id.container, mUserSetting).commit()
                        1 -> childFragmentManager.beginTransaction()
                                .replace(R.id.container, mAppSetting).commit()
                    }
                }
            })
        }
    }
}
