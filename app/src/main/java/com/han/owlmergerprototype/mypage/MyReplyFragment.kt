package com.han.owlmergerprototype.mypage

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.rest.MyComment
import com.han.owlmergerprototype.rest.RestService
import com.han.owlmergerprototype.rest.UserInfo
import com.han.owlmergerprototype.utils.SpaceDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyReplyFragment:Fragment() {
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

        val view1 = inflater.inflate(R.layout.fragment_myripples,container,false)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://64aa493c7cf5.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val loginService = retrofit.create(RestService::class.java)
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
                    Log.d(MyContentFragment.TAG, "onCreateView2: ${myComment.comments}")
                    myCommentList=myComment.comments
                }else{
                    Toast.makeText(context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                }


            }


        })



        Log.d(MyContentFragment.TAG, "onCreateView1: ${myCommentList}")

        val adap1 = RecyclerAdapter()
        recyclerView = view1.findViewById(R.id.myripples_rcyView)
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        recyclerView.addItemDecoration(deco)
        recyclerView.adapter = adap1
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view1

    }


    inner class RecyclerAdapter:RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>(){
        //항목 구성을 위해 사용할 viewholder 객체가 필요할때 호출되는 메서드
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
                val itemView = layoutInflater.inflate(R.layout.my_ripples_box_layout,null)
                val holder = ViewHolderClass(itemView)

                return holder
        }

        //데이터 셋팅
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
//            holder.rowTextView.text = myCommentList[position]

        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount(): Int {
            return myCommentList.size
        }


        inner class ViewHolderClass(itemView:View) :RecyclerView.ViewHolder(itemView){
            //항목View 내부의 View 상속
            val rowTextView: TextView = itemView.findViewById(R.id.ripple_date_tv)
        }



    }


}