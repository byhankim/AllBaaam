package com.han.owlmergerprototype.mypage

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.rest.MyComment
import com.han.owlmergerprototype.rest.MyPosts
import com.han.owlmergerprototype.rest.RestService
import com.han.owlmergerprototype.rest.UserInfo
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

    private var articleList =ArrayList<Post>()

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : MyContentFragment {

            return MyContentFragment()
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




        val view = inflater.inflate(R.layout.fragment_mycontents,container,false)

        recyclerView = view.findViewById(R.id.mycontens_rcyView)


        val retrofit = Retrofit.Builder()
            .baseUrl("https://64aa493c7cf5.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val loginService = retrofit.create(RestService::class.java)
        Log.d(TAG, "onCreateView0: 이거먼저")
        loginService.getMyPost(TestUser.token).enqueue(object : Callback<MyPosts> {
            override fun onFailure(call: Call<MyPosts>, t: Throwable) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("통신실패")
                dialog.setMessage("실패")
                dialog.show()
            }
            override fun onResponse(call: Call<MyPosts>, response: Response<MyPosts>) {
                val myPosts = response.body()

                if(myPosts?.ok==true){
                    Log.d(TAG, "onCreateView2: ${myPosts.posts}")
                    articleList = myPosts.posts as ArrayList<Post>
                    Log.d(TAG, "onCreateView3: ${articleList}")
                    Toast.makeText(context,"좋아용", Toast.LENGTH_SHORT).show()


                }else{
                    Toast.makeText(context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                }


            }


        })





        Log.d(TAG, "onCreateView1: ${articleList}")
        MyContentsRecyblerViewSetting(articleList)

        return view



        /*val adap2 = RecyclerAdapter()
        recyclerView = view.findViewById(R.id.mycontens_rcyView)
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        recyclerView.addItemDecoration(deco)
        recyclerView.adapter = adap2
        recyclerView.layoutManager = LinearLayoutManager(context)*/


    }


    private fun MyContentsRecyblerViewSetting(articleList: ArrayList<Post>){
        myContentRecyclerAdapter = MyContentsRecyclerAdapter(context!!)


        myContentRecyclerAdapter.submitList(articleList)
        val myLinearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
        myLinearLayoutManager.stackFromEnd = true

        recyclerView.apply{
            layoutManager = myLinearLayoutManager
            adapter = myContentRecyclerAdapter
        }
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        recyclerView.addItemDecoration(deco)



    }



//    inner class RecyclerAdapter:RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>(){
//        //항목 구성을 위해 사용할 viewholder 객체가 필요할때 호출되는 메서드
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
//            val itemView = layoutInflater.inflate(R.layout.my_contents_box_layout,null)
//            val holder = ViewHolderClass(itemView)
//            return holder
//        }
//
//        //데이터 셋팅
//        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
//            holder.rowTextView.text = dateArray[position]
//            holder.
//        }
//
//        //리사이클러뷰의 항목갯수 반환
//        override fun getItemCount(): Int {
//            return dateArray.size
//        }
//
//
//        inner class ViewHolderClass(itemView:View) :RecyclerView.ViewHolder(itemView){
//            //항목View 내부의 View 상속
//            val rowTextView:TextView = itemView.findViewById(R.id.date_tv)
//        }
//
//
//
//    }
}