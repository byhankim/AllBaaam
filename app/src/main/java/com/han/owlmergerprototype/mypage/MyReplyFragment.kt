package com.han.owlmergerprototype.mypage

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.RetrofitRESTService
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.rest.MyComment
import com.han.owlmergerprototype.sharedTest.MyCommentsRecyclerAdapter
import com.han.owlmergerprototype.utils.SpaceDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyReplyFragment:Fragment() {
    private lateinit var myCommentsRecyclerAdapter: MyCommentsRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    var myCommentList = ArrayList<Comment>()
    companion object{

        fun newInstance() : MyReplyFragment {


            return MyReplyFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_myripples,container,false)
        recyclerView = view.findViewById(R.id.myripples_rcyView)


        val retrofit = Retrofit.Builder()
            .baseUrl(ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val loginService = retrofit.create(RetrofitRESTService::class.java)
        loginService.getMyComment(TestUser.token).enqueue(object : Callback<MyComment> {
            override fun onFailure(call: Call<MyComment>, t: Throwable) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("통신실패")
                dialog.setMessage("실패")
                dialog.show()
            }
            override fun onResponse(call: Call<MyComment>, response: Response<MyComment>) {
                val myComment = response.body()

                if(myComment?.ok==true){
                    if(response.isSuccessful) {

                        activity?.runOnUiThread {
                            MyCommentsRecyclerViewSetting(myComment!!.comments)
                        }
                    }
                }else{
                    Toast.makeText(context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                }


            }


        })



        Log.d(MyContentFragment.TAG, "onCreateView1: ${myCommentList}")




        return view

    }


    private fun MyCommentsRecyclerViewSetting(myCommentList: ArrayList<Comment>){
        val myCommentsRecyclerAdapter = MyCommentsRecyclerAdapter(context!!,myCommentList)
        val myLinearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        myLinearLayoutManager.stackFromEnd = true
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)

        recyclerView.apply{
            layoutManager = myLinearLayoutManager
            addItemDecoration(deco)
            adapter = myCommentsRecyclerAdapter
        }

    }


}