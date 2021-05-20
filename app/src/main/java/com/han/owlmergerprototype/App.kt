package com.han.owlmergerprototype

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        KakaoSdk.init(this, "c99b7c499a9d8e3ba072e874edbcd52e")
    }

}