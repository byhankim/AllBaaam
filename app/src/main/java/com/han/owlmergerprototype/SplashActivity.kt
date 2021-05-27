package com.han.owlmergerprototype

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.han.owlmergerprototype.noLoginTest.NoLoginBottomNavActivity
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.data.Bookmark
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.Like
import com.han.owlmergerprototype.data.Post
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

const val DUMMY_USER_ID = 1

class SplashActivity : AppCompatActivity() {
    private val timeoutCount: Long = 1500
    override fun setTheme(theme: Resources.Theme?) {


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val long_now = System.currentTimeMillis()
        val t_date = Date(long_now)
//        val t_dateFormat = SimpleDateFormat("HH:mm:ss E", Locale("ko", "KR"))
        val t_dateFormat = SimpleDateFormat("mm", Locale("ko", "KR"))
        // 현재 시간을 dateFormat 에 선언한 형태의 String 으로 변환
        val str_date = t_dateFormat.format(t_date)
        Log.d("TEST", "onCreate: ${str_date}")

        if(str_date.toInt()%3 ==0){
            super.setTheme(R.style.SplashActivityDay)
        }else{
            super.setTheme(R.style.SplashActivity)
        }

        super.onCreate(savedInstanceState)

        if(str_date.toInt()%2 ==0){
            Handler().postDelayed({
//            startActivity(Intent(this, CommunityMainActivity::class.java))

                finish()
            }, timeoutCount)
        }else{
            Handler().postDelayed({
//            startActivity(Intent(this, CommunityMainActivity::class.java))
                startActivity(Intent(this, NoLoginBottomNavActivity::class.java))
                finish()
            }, timeoutCount)
        }


        createDummyData()

        // 1.5초 이후 메인 커뮤니티 화면으로 이동

    }

    private fun createDummyData(){

        Log.e("헤헿", "ㅎㅎㅎㅎ")

        // ----------------------------------------------------------------
        //      커뮤니티 메인 프래그먼트 dp용 더미데이터
        // ----------------------------------------------------------------
//        val dummyDataSet = mutableListOf<Post>()
//        dummyDataSet.add(Post(1, "20210511010101", "20210512010101", "아무도 나랑 마포 떡볶이 맛집에 가주지 않았다", 1, null,-1, 1))
//        dummyDataSet.add(Post(2, "20210512020202", "20210513020202", "니하오?", 2, null,-1, 2))
//        dummyDataSet.add(Post(3, "20210513030303", "20210514030303", "짜이찌엔?", 2, null,-1, 2))
//        dummyDataSet.add(Post(4, "20210514040404", "20210515040404", "recyclerview 극혐 ㅋㅋ ㄹㅇ 인정각 날카롭게 서는 부분 반박시 -틀-", 3, null,-1, 3))
//        dummyDataSet.add(Post(5, "20210515050505", "20210516050505", "아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니 근데 솔직히 진짜 아니", 1, null,-1, 1))
//        dummyDataSet.add(Post(6, "20210516060606", "20210517060606", "내가 가장 싫어하는것은 첫번째는 말을 하다 마는 것이고 두번째는", 4, null,-1, 3))
//        dummyDataSet.add(Post(7, "20210517070707", "20210518080808", "오늘 나랑 마포 떡볶이 맛집 탐방 갈 사람?? 진짜 내 전재산 걸고 존맛탱임 ㄹㅇ ㅋ", 1, null,-1, 1))

//        val sharedPrefName = getString(R.string.owl_shared_preferences_name)
//        val myShared = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
//        val sharedKey = getString(R.string.owl_shared_preferences_dummy_comm_posts)
/**/
//        with (myShared.edit()) {
//            putString(sharedKey, Gson().toJson(dummyDataSet))
//            apply()
//        }
/**/


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

/**/
//        with (myShared.edit()) {
//            putString(getString(R.string.dummy_comments_key), Gson().toJson(commentsDummyDataSet))
//            apply()
//        }



        // ----------------------------------------------------------------
        //      Like table dummy data
        // ----------------------------------------------------------------
//        val likeDatasets = mutableListOf<Like>()
//
//        likeDatasets.add(Like(1, "20210517030303", null, DUMMY_USER_ID, 1, -1))
//        likeDatasets.add(Like(2, "20210517040404", null, DUMMY_USER_ID, 2, -1))
//        likeDatasets.add(Like(3, "20210517050505", null, DUMMY_USER_ID, 3, -1))
//        likeDatasets.add(Like(4, "20210517060606", null, DUMMY_USER_ID, 4, -1))
//        likeDatasets.add(Like(5, "20210517070707", null, DUMMY_USER_ID, 5, -1))
//
//        likeDatasets.add(Like(6, "20210517080808", null, 2, 2, -1))
//        likeDatasets.add(Like(7, "20210517090909", null, 3, 2, -1))
//        likeDatasets.add(Like(8, "20210517101010", null, 4, 3, -1))
//
//
//        with (myShared.edit()) {
//            putString(getString(R.string.dummy_likes_key), Gson().toJson(likeDatasets))
//            apply()
//        }
//


        // ----------------------------------------------------------------
        //      Bookmark dummy table
        // ----------------------------------------------------------------
//        val bookmarkDatasets = mutableListOf<Bookmark>()
//
//        bookmarkDatasets.add(Bookmark(1, "20210517151733", null, true, DUMMY_USER_ID, 1))
//        bookmarkDatasets.add(Bookmark(2, "20210517151833", null, true, DUMMY_USER_ID, 2))
//        bookmarkDatasets.add(Bookmark(3, "20210517151933", null, true, DUMMY_USER_ID, 3))
//        bookmarkDatasets.add(Bookmark(4, "20210517152033", null, true, DUMMY_USER_ID, 4))
//        bookmarkDatasets.add(Bookmark(5, "20210517152133", null, true, DUMMY_USER_ID, 5))
//        bookmarkDatasets.add(Bookmark(6, "20210517152233", null, true, DUMMY_USER_ID, 6))
//        bookmarkDatasets.add(Bookmark(7, "20210517152333", null, true, DUMMY_USER_ID, 7))
////        bookmarkDatasets.add(Bookmark(8, "20210517152433", null, true, 1, 8))
//
//        bookmarkDatasets.add(Bookmark(9, "20210517152533", null, true, 2, 3))
//        bookmarkDatasets.add(Bookmark(10, "20210517152633", null, true, 2, 5))
//
//        with (myShared.edit()) {
//            putString(        commentsDummyDataSet.add(Comment(11, "20210514092500", null, "야임마 똑바로해", 7, true, null, 1))
//                    commentsDummyDataSet.add(Comment(12, "20210514092626", null, "ㅇㅋ", 7, false, 11, 2))
//                    commentsDummyDataSet.add(Comment(13, "20210514092500", null, "ㅈㅅ", 7, false, 11, 3))
//                    commentsDummyDataSet.add(Comment(14, "20210514110024", null, "응 너나잘해~", 7, false, 11, 4))
//                    commentsDummyDataSet.add(Comment(15, "20210514123456", null, "hi", 7, true, 11, 1))
//                    commentsDummyDataSet.add(Comment(16, "20210514134543", null, "칼국수특) 백세칼국수 미만 반박 안받음", 7, true, null, 1))
//                    commentsDummyDataSet.add(Comment(17, "20210515000101", null, "111111111111111111111111111111111111111111111111111111111111", 7, true, null, 3))
//                    commentsDummyDataSet.add(Comment(18, "20210516161616", null, "날씨좋다", 7, true, null, 2))
//                    commentsDummyDataSet.add(Comment(19, "20210517080808", null, "집가고 싶어요", 7, true, null, 1))
//                    commentsDummyDataSet.add(Comment(20, "20210518132313", null, "me2", 7, false, 19, 2))
//                    commentsDummyDataSet.add(Comment(21, "20210518111111", null, "ㅇㅋ", 7, false, 11, 1))
//                    getString(R.string.dummy_bookmarks_key), Gson().toJson(bookmarkDatasets))
//            commit()
//        }

        // ----------------------------------------------------------------
        //      when fetching from shared preferences
        // ----------------------------------------------------------------
//        val dummyCommPostsType = object: TypeToken<MutableList<Post>>() {}.type
//        val dummyDataSetFromSharedPreferences: MutableList<Post> = Gson().fromJson(myShared.getString(sharedKey, ""), dummyCommPostsType)
//        Log.e("[Splash]", dummyDataSetFromSharedPreferences[0].contents)

    }
}