package com.davidturkalj.pcbuildlogger.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.databinding.ActivityTabsBinding
import com.davidturkalj.pcbuildlogger.ui.adapters.TabAdapter
import com.davidturkalj.pcbuildlogger.ui.viewmodels.ComponentsViewModel
import com.davidturkalj.pcbuildlogger.ui.viewmodels.TabsViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TabsActivity : AppCompatActivity() {

    private val TAG = "TabsActivity"

    private lateinit var tabsBinding: ActivityTabsBinding
    private lateinit var tabLayout: TabLayout

    private val viewModel by viewModel<TabsViewModel>()
    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabsBinding = ActivityTabsBinding.inflate(layoutInflater)

        setContentView(tabsBinding.root)
        tabLayout = findViewById(R.id.tabLayout)
        setUpTabLayout()
    }

    private fun setUpTabLayout() {
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
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_CANCELED){
            if(requestCode == ComponentsViewModel.REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK){
                CoroutineScope(Dispatchers.IO).launch {
                    try{
                        viewModel.photo(data)
                    } catch (e: Exception){
                        Log.d(TAG, e.message.toString())
                    }
                }
            } else if(requestCode == ComponentsViewModel.REQUEST_SELECT_IMAGE && resultCode == Activity.RESULT_OK){
                val imageUri = data?.data
                if (imageUri != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.image(imageUri)
                    }
                }
            }
        }
    }
}