package com.han.owlmergerprototype

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.han.owlmergerprototype.common.ADDRESS_KAKAO
import com.han.owlmergerprototype.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

       /*
*/


        with(binding.webView){
            settings.javaScriptEnabled = true
            //webViewClient = WebViewClient()
            loadUrl("""$ADDRESS_KAKAO""")
        }
    }
}