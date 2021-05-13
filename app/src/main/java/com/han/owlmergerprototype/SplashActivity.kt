package com.han.owlmergerprototype

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.data.Post

class SplashActivity : AppCompatActivity() {
    private val timeoutCount: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createDummyData()

        // 1.5초 이후 메인 커뮤니티 화면으로 이동
        Handler().postDelayed({
//            startActivity(Intent(this, CommunityMainActivity::class.java))
            startActivity(Intent(this, BottomNavActivity::class.java))
            finish()
        }, timeoutCount)
    }

    private fun createDummyData(){

        Log.e("헤헿", "ㅎㅎㅎㅎ")

        // fakes
//        var id: Int = -1,
//        var createdAt: String = "",
//        var updatedAt: String = "",
//        var contents: String = "",
//        var category: Int = -1,
//        var cmntID: Int = -1,
//        var userID: Int = -1
        val dummyDataSet = mutableListOf<Post>()
        dummyDataSet.add(Post(1, "20210511", "20210512", "아무도 나랑 마포 떡볶이 맛집에 가주지 않았다", 1, -1, 1))
        dummyDataSet.add(Post(2, "20210512", "20210513", "니하오?", 2, -1, 2))
        dummyDataSet.add(Post(3, "20210513", "20210514", "짜이찌엔?", 2, -1, 2))
        dummyDataSet.add(Post(4, "20210514", "20210515", "recyclerview 극혐 ㅋㅋ ㄹㅇ 인정각 날카롭게 서는 부분 반박시 -틀-", 3, -1, 3))
        dummyDataSet.add(Post(5, "20210515", "20210516", "아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니", 1, -1, 1))
        dummyDataSet.add(Post(6, "20210516", "20210517", "내가 가장 싫어하는것은 첫번째는 말을 하다 마는 것이고 두번째는", 4, -1, 3))
        dummyDataSet.add(Post(7, "20210517", "20210518", "오늘 나랑 마포 떡볶이 맛집 탐방 갈 사람?? 진짜 내 전재산 걸고 존맛탱임 ㄹㅇ ㅋ", 1, -1, 1))

        val sharedPrefName = getString(R.string.owl_shared_preferences_name)
        val myShared = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
        val sharedKey = getString(R.string.owl_shared_preferences_dummy_comm_posts)

        with (myShared.edit()) {
            putString(sharedKey, Gson().toJson(dummyDataSet))
            apply()
        }

        // when fetching
        val dummyCommPostsType = object: TypeToken<MutableList<Post>>() {}.type
        val dummyDataSetFromSharedPreferences: MutableList<Post> = Gson().fromJson(myShared.getString(sharedKey, ""), dummyCommPostsType)
        Log.e("[SPlash]", dummyDataSetFromSharedPreferences[0].contents)

    }
}