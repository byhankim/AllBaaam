package com.han.owlmergerprototype.sharedTest

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.han.owlmergerprototype.App
import com.han.owlmergerprototype.data.ArticleEntity

object SharedPrefManager {
    const val TAG = "로그"
    private const val SHARED_MYCONTENTS = "shared_mycontents"
    private const val KEY_NAME = "key_name"

    fun storeUserContents(myContentsList:MutableList<ArticleEntity>){
        Log.d(TAG, "storeUserContents called")

        val myContentsListString:String = Gson().toJson(myContentsList)
        Log.d(TAG, "storeUserContents: ${myContentsListString}")
        val shared :SharedPreferences = App.instance.getSharedPreferences(SHARED_MYCONTENTS,
            Context.MODE_PRIVATE)

        val editor :SharedPreferences.Editor = shared.edit()

        editor.putString(KEY_NAME,myContentsListString)

        editor.apply()
    }

    fun getUserContentsList():MutableList<ArticleEntity>{
        val shared :SharedPreferences = App.instance.getSharedPreferences(SHARED_MYCONTENTS,
            Context.MODE_PRIVATE)

        val storedContentsListString:String = shared.getString(KEY_NAME,"")!!

        var storedContentsList = ArrayList<ArticleEntity>()

        if(storedContentsListString.isNotEmpty()){
            storedContentsList = Gson().fromJson(storedContentsListString, Array<ArticleEntity>::class.java).toMutableList() as ArrayList<ArticleEntity>

        }
        return storedContentsList
    }
}