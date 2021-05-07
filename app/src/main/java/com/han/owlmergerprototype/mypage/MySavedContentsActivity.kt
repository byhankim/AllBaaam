package com.han.owlmergerprototype.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.databinding.ActivityMySavedContentsBinding

class MySavedContentsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMySavedContentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_saved_contents)
        binding = ActivityMySavedContentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}