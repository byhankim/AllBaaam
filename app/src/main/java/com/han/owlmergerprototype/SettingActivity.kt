package com.han.owlmergerprototype

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class SettingActivity: AppCompatActivity() {

    private lateinit var settingLV1:ListView
    private lateinit var settingLV2:ListView
    private lateinit var settingLV3:ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)



    }
}