package com.han.owlmergerprototype.mypage

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyContentsAdapter(fa:FragmentManager) : FragmentPagerAdapter(fa, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragments = arrayListOf<Fragment>()

    fun appendFragment(fragment: Fragment){
        Log.d("LOOOG!!","프레그먼트 추가 완료쨩")
        fragments.add(fragment)
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        Log.d("LOOOG!!","프레그먼트 추가 완료해야하는뎅")
        return fragments[position]
    }

}