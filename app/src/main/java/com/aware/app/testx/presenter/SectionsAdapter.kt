package com.aware.app.testx.presenter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.aware.app.testx.R

class SectionsAdapter(fm: FragmentManager,
                      private val context: Context,
                      private val fragments: List<Fragment>,
                      private val dotsLayout: LinearLayout,
                      private val btnPrevious: Button,
                      private val btnNext: Button) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    var listener = object : OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageSelected(position: Int) {
            // update dots view
            updateDots(position)

            // update buttons text
            if (position == 0) {
                btnPrevious.visibility = View.INVISIBLE
            } else {
                btnPrevious.visibility = View.VISIBLE
            }

            if (position == fragments.size - 1) {
                btnNext.text = context.getString(R.string.btn_continue)
            } else {
                btnNext.text = context.getString(R.string.btn_next)
            }
        }
    }

    fun updateDots(currentPage: Int) {
        dotsLayout.removeAllViews()
        for (i in fragments.indices) {
            val dot = TextView(context)
            dot.text = Html.fromHtml("&#8226;")
            dot.textSize = 35f
            if (i == currentPage) dot.setTextColor(context.resources.getColor(R.color.dot_active))
            else dot.setTextColor(context.resources.getColor(R.color.dot_inactive))
            dotsLayout.addView(dot)
        }
    }
}
