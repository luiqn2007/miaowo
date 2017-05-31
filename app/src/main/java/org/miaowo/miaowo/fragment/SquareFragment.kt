package org.miaowo.miaowo.fragment


import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.v4.content.res.ResourcesCompat
import android.view.MenuItem
import android.view.View
import com.mikepenz.fontawesome_typeface_library.FontAwesome
import com.mikepenz.iconics.IconicsDrawable
import kotlinx.android.synthetic.main.fragment_square.*
import org.miaowo.miaowo.R
import org.miaowo.miaowo.activity.Add
import org.miaowo.miaowo.adapter.TitleListAdapter
import org.miaowo.miaowo.base.BaseActivity
import org.miaowo.miaowo.base.BaseFragment
import org.miaowo.miaowo.base.BaseListFragment
import org.miaowo.miaowo.bean.data.Category
import org.miaowo.miaowo.bean.data.Title
import org.miaowo.miaowo.other.Const
import org.miaowo.miaowo.other.template.MyOnTabSelectedListener
import org.miaowo.miaowo.util.API
import org.miaowo.miaowo.util.SpUtil

class SquareFragment : BaseFragment(R.layout.fragment_square), BottomNavigationView.OnNavigationItemSelectedListener {
    private var mFragments = mutableListOf<TitleListFragment>()
    private var mFragmentId = -1

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mFragmentId = item.itemId
        if (!fab.isEnabled) {
            ObjectAnimator.ofPropertyValuesHolder(fab, PropertyValuesHolder.ofFloat("translationY", 500f, 0f), 
                    PropertyValuesHolder.ofFloat("alpha", 0f, 1f))
                    .setDuration(300).start()
            fab.isEnabled = true
        }
        childFragmentManager.beginTransaction()
                .replace(R.id.container, mFragments[mFragmentId])
                .commit()
        tab.getTabAt(mFragmentId)?.select()
        return true
    }

    override fun initView(view: View?) {
        API.Doc.home {
            bottomNavigation.setOnNavigationItemSelectedListener(this)
            bottomNavigation.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.md_amber_100, null))
            bottomNavigation.itemIconTintList = ResourcesCompat.getColorStateList(resources, R.color.selector_icon, null)
            val menu = bottomNavigation.menu

            it?.categories?.forEachIndexed { index, category ->
                mFragments.add(index, TitleListFragment.newInstance(category))
                tab.addTab(tab.newTab().setText(category.name), index)
                val icon = category.icon?.replace("-", "_")?.replace("fa_", "faw_") ?: "faw_glass"
                menu.add(0, index, index, category.name)
                menu.getItem(index).icon = IconicsDrawable(context).icon(FontAwesome.Icon.valueOf(icon))
            }

            tab.tabMode = TabLayout.MODE_FIXED
            tab.addOnTabSelectedListener(MyOnTabSelectedListener {
                if (it != null && mFragmentId != it.position)
                    bottomNavigation.selectedItemId = it.position
            })
            // navigation
            bottomNavigation.setOnNavigationItemSelectedListener(this)
            bottomNavigation.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.md_amber_100, null))
            bottomNavigation.itemIconTintList = ResourcesCompat.getColorStateList(resources, R.color.selector_icon, null)
            // fab
            fab.setOnClickListener {
                val intent = Intent(context, Add::class.java)
                intent.putExtra(Const.TAG, mFragmentId)
                startActivityForResult(intent, 0)
            }
            // other
            if (mFragmentId < 0) bottomNavigation.selectedItemId = 0
            val modeTab = SpUtil.getBoolean(Const.SP_USE_TAB, false)
            tab.visibility = if (modeTab) View.VISIBLE else View.GONE
            bottomNavigation.visibility = if (modeTab) View.GONE else View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && resultCode > 0) {
            val pageId = data.getIntExtra(Const.TAG, bottomNavigation.selectedItemId)
            API.Use.sendTitle(mFragments[pageId].cid, data.getStringExtra(Const.TITLE), data.getStringExtra(Const.CONTENT)) {
                if ("ok" != it) BaseActivity.get?.handleError(Exception(it))
            }
        }
    }

    class TitleListFragment : BaseListFragment<Title>() {
        val cid: Int
            get() = arguments.getInt("cid")

        override fun setAdapter() = TitleListAdapter()

        override fun setItems(page: Int, set: (list: List<Title>) -> Unit) {
            API.Doc.category(arguments[Const.SLUG].toString(), page) {
                set(it?.topics ?: listOf())
            }
        }

        companion object {
            fun newInstance(c: Category): TitleListFragment {
                val args = Bundle()
                val fragment = TitleListFragment()
                args.putString(Const.SLUG, c.slug)
                args.putInt(Const.ID, c.cid)
                fragment.arguments = args
                return fragment
            }
        }
    }

    companion object {
        fun newInstance(): SquareFragment {
            val fragment = SquareFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
