package com.davidturkalj.pcbuildlogger.ui.tabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager.widget.ViewPager
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.databinding.ActivityTabsBinding
import com.davidturkalj.pcbuildlogger.ui.components.view.ComponentsFragment
import com.davidturkalj.pcbuildlogger.utilities.DialogListener
import com.google.android.material.tabs.TabLayout
import com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.view.PcBuildFragment
import com.davidturkalj.pcbuildlogger.utilities.DatabaseHelper

class TabsActivity : AppCompatActivity(), DialogListener {

    lateinit var tabsBinding: ActivityTabsBinding
    private lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabsBinding = ActivityTabsBinding.inflate(layoutInflater)

        setContentView(tabsBinding.root)

        setUserId(intent.getStringExtra("user_id"))

        tabLayout = findViewById(R.id.tabLayout)

        viewPagerAdapter = TabAdapter(this, supportFragmentManager)
        for (i in 0 until viewPagerAdapter.count) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPagerAdapter.getPageTitle(i)))
        }
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun onDialogPositiveClick(name: String) {
        PcBuildFragment().onDialogPositiveClick(name)
    }

    override fun onDialogPositiveClick(name: String, imageLink: String) {
        PcBuildFragment().onDialogPositiveClick(name, imageLink)
    }

    private fun setUserId(id: String?) {
        Log.d(Log.DEBUG.toString(), id.toString())
        DatabaseHelper.changeUserId(id)
    }

}