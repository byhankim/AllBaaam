package com.han.owlmergerprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat

class CommunityMainActivity : AppCompatActivity() {
    private lateinit var switch:SwitchCompat
    private lateinit var alarmbtn: Button
    private lateinit var mypagebtn: Button
    private lateinit var inte:Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_map)

    }


}