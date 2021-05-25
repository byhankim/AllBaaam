package com.han.owlmergerprototype.mypage

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.han.owlmergerprototype.MyRecyclerviewInterface
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.RetrofitRESTService
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.databinding.ActivityMybookmarkBinding
import com.han.owlmergerprototype.rest.MyPosts
import com.han.owlmergerprototype.rest.PostForMy
import com.han.owlmergerprototype.sharedTest.MyBookmarkRecyclerAdapter
import com.han.owlmergerprototype.sharedTest.MyContentsRecyclerAdapter
import com.han.owlmergerprototype.utils.SpaceDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyBookmarkActivity : AppCompatActivity(),
    MyRecyclerviewInterface {


    private lateinit var binding: ActivityMybookmarkBinding
    val TAG: String = "로그"


    private lateinit var myBookmarkRecyclerAdapter: MyBookmarkRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {


        Log.e(TAG, "Mypg004Activity - onCreate() called")
        super.onCreate(savedInstanceState)
        binding = ActivityMybookmarkBinding.inflate(layoutInflater)
        //binding = binding
        setContentView(binding.root)

        binding.mysavedToolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        binding.mysavedToolbar.setNavigationOnClickListener {
            finish()
        }


        val retrofit = Retrofit.Builder()
            .baseUrl(ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val loginService = retrofit.create(RetrofitRESTService::class.java)
        loginService.getMyBookMark(TestUser.token).enqueue(object : Callback<MyPosts> {
            override fun onFailure(call: Call<MyPosts>, t: Throwable) {
                val dialog = AlertDialog.Builder(this@MyBookmarkActivity)
                dialog.setTitle("통신실패")
                dialog.setMessage("실패")
                dialog.show()
            }

            override fun onResponse(call: Call<MyPosts>, response: Response<MyPosts>) {
                val myPosts = response.body()
                Log.e(TAG, "onResponse22: ${myPosts!!.posts}")

                if (response.isSuccessful) {
                    runOnUiThread {
                        MyBookmarkRecyclerViewSetting(myPosts!!.posts)
                    }

                } else {

                }
            }
        })


    }

    override fun onItemClicked(position: Int) {

    }

    private fun MyBookmarkRecyclerViewSetting(articleList: ArrayList<PostForMy>){
        myBookmarkRecyclerAdapter = MyBookmarkRecyclerAdapter(this,articleList)
        val myLinearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true)
        myLinearLayoutManager.stackFromEnd = true
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)

        binding.myRecyclerView.apply{
            layoutManager = myLinearLayoutManager
            addItemDecoration(deco)
            adapter = myBookmarkRecyclerAdapter
        }

    }


}