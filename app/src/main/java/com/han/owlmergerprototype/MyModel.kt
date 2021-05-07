package com.han.owlmergerprototype

import android.util.Log

data class MyModel(var name: String? = null, var profileImage: String? = null) {
//class MyModel(var name: String? = null, var profileImage: String? = null) {
    val TAG = "로그"

    // 기본 생성자
    init {
        Log.d(TAG, "MyModel - () called $name")
    }

}
