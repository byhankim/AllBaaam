package com.han.owlmergerprototype.mypage

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.AlarmFragment
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.RetrofitRESTService
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.rest.*
import com.han.owlmergerprototype.sharedTest.MyContentsRecyclerAdapter
import com.han.owlmergerprototype.sharedTest.SharedPrefManager
import com.han.owlmergerprototype.utils.SpaceDecoration
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyContentFragment:Fragment() {

    private lateinit var myContentRecyclerAdapter: MyContentsRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    companion object{
        const val TAG : String = "MyContentFragment"

        fun newInstance() : MyContentFragment {

            return MyContentFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_mycontents,container,false)

        recyclerView = view.findViewById(R.id.mycontens_rcyView)


        val retrofit = Retrofit.Builder()
            .baseUrl(ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val loginService = retrofit.create(RetrofitRESTService::class.java)
        Log.e(TAG, "onCreateView0: 이거먼저")
        loginService.getMyPost(TestUser.token).enqueue(object : Callback<MyPosts> {
            override fun onFailure(call: Call<MyPosts>, t: Throwable) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("통신실패")
                dialog.setMessage("실패")
                dialog.show()
            }
            override fun onResponse(call: Call<MyPosts>, response: Response<MyPosts>) {
                val myPosts = response.body()

                if(response.isSuccessful){

                    activity?.runOnUiThread {
                        MyContentsRecyblerViewSetting(myPosts!!.posts)
                    }


                }else{
                    Toast.makeText(context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                }
            }
        })
        return view

    }


    private fun MyContentsRecyblerViewSetting(articleList: ArrayList<PostForMy>){
        myContentRecyclerAdapter = MyContentsRecyclerAdapter(context!!,articleList)
        val myLinearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        myLinearLayoutManager.stackFromEnd = true
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)

        recyclerView.apply{
            layoutManager = myLinearLayoutManager
            addItemDecoration(deco)
            adapter = myContentRecyclerAdapter
        }

    }


}