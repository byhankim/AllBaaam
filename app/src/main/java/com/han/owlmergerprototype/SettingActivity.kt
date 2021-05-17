package com.han.owlmergerprototype

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SettingActivity: AppCompatActivity() {

    private lateinit var settingLV1:ListView
    private lateinit var settingLV2:ListView
    private lateinit var settingLV3:ListView
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar = findViewById(R.id.setting_toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        toolbar.setNavigationOnClickListener {
            finish()
        }



    }
}