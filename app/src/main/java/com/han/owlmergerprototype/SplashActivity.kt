package com.han.owlmergerprototype

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.han.owlmergerprototype.noLoginTest.NoLoginBottonNavActivity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.Post

class SplashActivity : AppCompatActivity() {
    private val timeoutCount: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createDummyData()

        // 1.5초 이후 메인 커뮤니티 화면으로 이동
        Handler().postDelayed({
//            startActivity(Intent(this, CommunityMainActivity::class.java))
            startActivity(Intent(this, NoLoginBottonNavActivity::class.java))
            finish()
        }, timeoutCount)
    }

    private fun createDummyData(){

        Log.e("헤헿", "ㅎㅎㅎㅎ")

        // ----------------------------------------------------------------
        //      커뮤니티 메인 프래그먼트 dp용 더미데이터
        // ----------------------------------------------------------------
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


        // ----------------------------------------------------------------
        //      comment 테이블 더미데이터
        // ----------------------------------------------------------------
        val commentsDummyDataSet = mutableListOf<Comment>()
        /*
        * var id: Int,
          var createdAt: String,
          var updatedAt: String?,
          var contents: String,
          var postID: Int,
          var isParent: Boolean,
          var recomment: Int?,
          var userID: Int
        * */
        commentsDummyDataSet.add(Comment(11, "20210514092500", null, "야임마 똑바로해", 7, true, null, 1))
        commentsDummyDataSet.add(Comment(12, "20210514092626", null, "ㅇㅋ", 7, false, 11, 2))
        commentsDummyDataSet.add(Comment(13, "20210514092500", null, "ㅈㅅ", 7, false, 11, 3))
        commentsDummyDataSet.add(Comment(14, "20210514110024", null, "응 너나잘해~", 7, false, 11, 4))
        commentsDummyDataSet.add(Comment(15, "20210514123456", null, "hi", 7, true, 11, 1))
        commentsDummyDataSet.add(Comment(16, "20210514134543", null, "칼국수특) 백세칼국수 미만 반박 안받음", 7, true, null, 1))
        commentsDummyDataSet.add(Comment(17, "20210515000101", null, "111111111111111111111111111111111111111111111111111111111111", 7, true, null, 3))
        commentsDummyDataSet.add(Comment(18, "20210516161616", null, "날씨좋다", 7, true, null, 2))
        commentsDummyDataSet.add(Comment(19, "20210517080808", null, "집가고 싶어요", 7, true, null, 1))
        commentsDummyDataSet.add(Comment(20, "20210518233223", null, "me2", 7, false, 19, 2))
        commentsDummyDataSet.add(Comment(21, "20210520111111", null, "ㅇㅋ", 7, false, 11, 1))

        with (myShared.edit()) {
            putString(getString(R.string.dummy_comments_key), Gson().toJson(commentsDummyDataSet))
            apply()
        }

        // ----------------------------------------------------------------
        //      when fetching from shared preferences
        // ----------------------------------------------------------------
        val dummyCommPostsType = object: TypeToken<MutableList<Post>>() {}.type
        val dummyDataSetFromSharedPreferences: MutableList<Post> = Gson().fromJson(myShared.getString(sharedKey, ""), dummyCommPostsType)
        Log.e("[Splash]", dummyDataSetFromSharedPreferences[0].contents)

    }
}