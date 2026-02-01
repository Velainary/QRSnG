package com.example.qrsng.ui.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.qrsng.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment(R.layout.fragment_history) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pager = view.findViewById<ViewPager2>(R.id.viewPager)
        val tabs = view.findViewById<TabLayout>(R.id.tabLayout)

        pager.adapter = HistoryPagerAdapter(this)

        TabLayoutMediator(tabs, pager) { tab, pos ->
            tab.text = if (pos == 0) "Scanned" else "Generated"
        }.attach()
    }
}
