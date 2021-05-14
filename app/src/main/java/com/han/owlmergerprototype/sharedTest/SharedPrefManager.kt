package com.han.owlmergerprototype.sharedTest

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.han.owlmergerprototype.App
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser

object SharedPrefManager {
    const val TAG = "로그"
    private const val SHARED_MYCONTENTS = "owltest"
    private const val KEY_NAME ="dummyPosts"

    fun storeUserContents(myContentsList:MutableList<ArticleEntity>){
        Log.d(TAG, "storeUserContents called")

        val myContentsListString:String = Gson().toJson(myContentsList)
        Log.d(TAG, "storeUserContents: ${myContentsListString}")
        val shared :SharedPreferences = App.instance.getSharedPreferences(SHARED_MYCONTENTS,
            Context.MODE_PRIVATE)

        val editor :SharedPreferences.Editor = shared.edit()

        editor.putString(KEY_NAME,myContentsListString)

        editor.commit()
    }

    fun getUserContentsList():MutableList<Post>{
        val userList = ArrayList<Post>()
        val shared :SharedPreferences = App.instance.getSharedPreferences(SHARED_MYCONTENTS,
            Context.MODE_PRIVATE)

        val storedContentsListString:String = shared.getString(KEY_NAME,"")!!

        var storedContentsList = ArrayList<Post>()

        if(storedContentsListString.isNotEmpty()){
            storedContentsList = Gson().fromJson(storedContentsListString, Array<Post>::class.java).toMutableList() as ArrayList<Post>

            for(i in storedContentsList){
                if(i.userID == TestUser.userID) userList.add(i)
            }

        }
        return userList
    }
}