package com.han.owlmergerprototype.widgetstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.han.owlmergerprototype.AlarmFragment
import com.han.owlmergerprototype.CommFragment
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.databinding.ActivityBottomNavTestBinding
import com.han.owlmergerprototype.mypage.MypageFragment

class BottomNavTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            with (supportFragmentManager.beginTransaction()) {
                add(R.id.fragment_container, AlarmFragment.newInstance())
                commit()
            }
        }

        binding.testBottomnAvivagion.setOnNavigationItemSelectedListener { item ->
            val ft = supportFragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.notice_menu_item -> {
                    ft.replace(R.id.fragment_container, AlarmFragment.newInstance())
                }
                R.id.owl_menu_item -> {
                    ft.replace(R.id.fragment_container, CommFragment.newInstance())
                }
                R.id.mypage_menu_item -> {
                    ft.replace(R.id.fragment_container, MypageFragment.newInstance())
                } else -> throw IllegalStateException("Unexpected vaule: " + item.itemId)
            }
            ft.commit()
            true
        }
    }
}