package com.han.owlmergerprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.databinding.ActivityMypg004Binding
import com.han.owlmergerprototype.rest.MyPosts
import com.han.owlmergerprototype.rest.CommunityPost
import com.han.owlmergerprototype.rest.RestService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Mypg004Activity : AppCompatActivity(), MyRecyclerviewInterface {

    //  private lateinit var binding: LayoutOkhttpRestActivityBinding
//    private var activityMainBinding: ActivityMainBinding? = null

    // 뷰가 사라질 때, 즉 메모리에서 날아갈 때 같이 날리기 위해 따로 빼두기
    private lateinit var binding: ActivityMypg004Binding
    val TAG: String = "로그"

    //데이터를 담을 그릇, 즉 배열
    var modelList = ArrayList<CommunityPost>()

    private lateinit var myRecyclerAdapter: MyRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {

        try {

            Log.e(TAG, "Mypg004Activity - onCreate() called")
            super.onCreate(savedInstanceState)
            binding = ActivityMypg004Binding.inflate(layoutInflater)
            //binding = binding
            setContentView(binding.root)

            binding.mysavedToolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
            binding.mysavedToolbar.setNavigationOnClickListener {
                finish()
            }

            val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()


            val loginService = retrofit.create(RestService::class.java)



            loginService.getMyBookMark(TestUser.token).enqueue(object : Callback<MyPosts> {
                override fun onFailure(call: Call<MyPosts>, t: Throwable) {
                    val dialog = android.app.AlertDialog.Builder(this@Mypg004Activity)
                    dialog.setTitle("통신실패")
                    dialog.setMessage("실패")
                    dialog.show()
                }
                override fun onResponse(call: Call<MyPosts>, response: Response<MyPosts>) {
                    val post = response.body()

                    if(post?.ok==true){

                        modelList=post.posts
                        Log.d(TAG, "여기는 ok : ${modelList}")
                        Log.d(TAG, "여기는 ok : ${post.posts}")
                        val manager = LinearLayoutManager(this@Mypg004Activity,
                            LinearLayoutManager.VERTICAL, false)



                        with(binding.myRecyclerView) {
                            layoutManager = manager
                            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)


                            adapter = MyRecyclerAdapter(modelList,this@Mypg004Activity)
                        }

                    }else{
                        Log.d(TAG, "false")
                        Toast.makeText(this@Mypg004Activity, "false", Toast.LENGTH_SHORT).show()

                    }


                    /*  val dialog = AlertDialog.Builder(this@LoginActivity)
                      dialog.setTitle("통신성공")
                      dialog.setMessage("ok: ${login?.ok.toString()} , token: ${login?.token}")
                      dialog.show()*/

                }


            })

            Log.d(TAG, "여기는 통신 끝 : ${modelList}")

            // 어답터 인스턴스 생성


            // 리사이클러뷰 설정
            // my_recycler_view.apply {

        } catch (e : Exception) {
            Log.d(TAG, "onCreate: Exception / exception : ${e}")
            e.printStackTrace()
        }


    }


    fun onBackButtonClicked(view: View){
        Log.d(TAG, "SecondActivity - onBackButtonClicked() called")
        finish()
    }

    override fun onItemClicked(position: Int) {
        Log.d(TAG, "Mypg004Activity - onItemClicked() called/ position: $position")

        var name: String? = null

        // 값이 비어있으면 ""을 넣는다
        // unwrapping



        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage("$title 와 함께하는 빡코딩! :)")
            .setPositiveButton("오케이") { dialog, id ->
                Log.d(TAG, "MainActivity - 다이얼로그 확인 버튼 클릭했음")
            }
            .show()
    }


}