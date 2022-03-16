package com.han.owlmergerprototype.mypage

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
            appendFragment(MyReplyFragment.newInstance())
        }

        val viewP=binding.viewPager
        viewP.adapter = myAdapter
        binding.myContentsdTab.setupWithViewPager(viewP)


        binding.mycontentsToolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        binding.mycontentsToolbar.setNavigationOnClickListener {
            finish()
        }

    }
}