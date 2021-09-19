package com.davidturkalj.pcbuildlogger.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.davidturkalj.pcbuildlogger.ui.fragments.PcBuildFragment
import com.davidturkalj.pcbuildlogger.ui.fragments.UserFragment

class TabAdapter(private val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "User"
            }
            else -> "PC Builds"
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                UserFragment.create()
            }
            else -> PcBuildFragment.create()
        }
    }

    override fun getCount(): Int {
        return 2
    }

}