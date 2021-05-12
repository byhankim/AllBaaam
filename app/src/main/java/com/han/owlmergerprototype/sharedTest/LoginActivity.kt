package com.han.owlmergerprototype.sharedTest

import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.han.owlmergerprototype.BottonNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.databinding.ActivityCreateArticleBinding
import com.han.owlmergerprototype.databinding.ActivityLoginBinding
import com.han.owlmergerprototype.noLoginTest.NoLoginBottonNavActivity

class LoginActivity: AppCompatActivity()  {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var inte :Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            TestUser.userName = binding.loginNameEv.text.toString()
            inte = Intent(this,BottonNavActivity::class.java)
            startActivity(inte)
        }

        binding.noLoginBtn.setOnClickListener {
            TestUser.userName = "로그인 부탁"
            inte = Intent(this, NoLoginBottonNavActivity::class.java)
            startActivity(inte)
        }

    }
}