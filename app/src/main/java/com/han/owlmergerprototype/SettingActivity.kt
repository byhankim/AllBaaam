package com.han.owlmergerprototype

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.noLoginTest.NoLoginBottomNavActivity

class SettingActivity: AppCompatActivity() {

    private lateinit var settingLV1:ListView
    private lateinit var settingLV2:ListView
    private lateinit var settingLV3:ListView
    private lateinit var toolbar: Toolbar
    private lateinit var logoutBTN:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        toolbar = findViewById(R.id.setting_toolbar)
        logoutBTN = findViewById(R.id.logout_tv)

        toolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        toolbar.setNavigationOnClickListener {
            finish()
        }

        logoutBTN.setOnClickListener {
            val dialog = Dialog(this)
            dialog.getWindow()!!.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            dialog.setContentView(R.layout.dialog_logout)
            val cancelBTN:TextView = dialog.findViewById<TextView>(R.id.logout_cancel_btn)
            cancelBTN.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })
            val logoutBTN: Button = dialog.findViewById<Button>(R.id.logout_ok_btn)
            logoutBTN.setOnClickListener(View.OnClickListener {
                val autoLogin:SharedPreferences = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
                val editor = autoLogin.edit()
                editor.clear()
                editor.apply()
                TestUser.initialTestUser()
                val intent = Intent(this, NoLoginBottomNavActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            })
            dialog.show()


        }



    }
}