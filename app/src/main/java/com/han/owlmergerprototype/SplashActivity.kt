package com.han.owlmergerprototype

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.han.owlmergerprototype.noLoginTest.NoLoginBottonNavActivity

class SplashActivity : AppCompatActivity() {
    private val timeoutCount: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1.5초 이후 메인 커뮤니티 화면으로 이동
        Handler().postDelayed({
//            startActivity(Intent(this, CommunityMainActivity::class.java))
            startActivity(Intent(this, NoLoginBottonNavActivity::class.java))
            finish()
        }, timeoutCount)
    }
}