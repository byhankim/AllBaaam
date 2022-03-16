package com.han.owlmergerprototype.mypage.boardActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.han.owlmergerprototype.R

class NoticeActivity : AppCompatActivity() {
    val TAG = "몰라"
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d(TAG, "NoticeActivity - onCreate() called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

    }
}
