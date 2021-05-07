package com.han.owlmergerprototype.mypage

import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.han.owlmergerprototype.AlarmFragment
import com.han.owlmergerprototype.BottonNavActivity
import com.han.owlmergerprototype.CommFragment
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.databinding.MyContentsLayoutBinding

class MyContentsActivity:AppCompatActivity() {

    private lateinit var binding : MyContentsLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.my_contents_layout)

        binding = MyContentsLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myAdapter = MyContentsAdapter(supportFragmentManager)
        with(myAdapter){

            appendFragment(MyContentFragment.newInstance())
            appendFragment(MyRippleFragment.newInstance())
            Log.d("LOOOG!!","프레그먼트 추가 완료")
        }

        val viewP=binding.viewPager
        viewP.adapter = myAdapter
        binding.myContentsdTab.setupWithViewPager(viewP)




    }
}