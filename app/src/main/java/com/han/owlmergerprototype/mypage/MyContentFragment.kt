package com.han.owlmergerprototype.mypage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.AlarmFragment
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.sharedTest.MyContentsRecyclerAdapter
import com.han.owlmergerprototype.sharedTest.SharedPrefManager
import com.han.owlmergerprototype.utils.SpaceDecoration

class MyContentFragment:Fragment() {

    private lateinit var myContentRecyclerAdapter: MyContentsRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    val dateArray = arrayListOf(
        "21/04/28","21/10/08","21/06/22","21/03/24","21/03/14"
    )
    var articleList =ArrayList<Post>()
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

        this.articleList = SharedPrefManager.getUserContentsList() as ArrayList<Post>


        this.MyContentsRecyblerViewSetting(this.articleList)





        /*val adap2 = RecyclerAdapter()
        recyclerView = view.findViewById(R.id.mycontens_rcyView)
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        recyclerView.addItemDecoration(deco)
        recyclerView.adapter = adap2
        recyclerView.layoutManager = LinearLayoutManager(context)*/
        return view

    }

    private fun MyContentsRecyblerViewSetting(articleList: ArrayList<Post>){
        this.myContentRecyclerAdapter = MyContentsRecyclerAdapter()
        this.myContentRecyclerAdapter.submitList(this.articleList)
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

    inner class RecyclerAdapter:RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>(){
        //항목 구성을 위해 사용할 viewholder 객체가 필요할때 호출되는 메서드
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView = layoutInflater.inflate(R.layout.my_contents_box_layout,null)
            val holder = ViewHolderClass(itemView)
            return holder
        }

        //데이터 셋팅
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowTextView.text = dateArray[position]
        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount(): Int {
            return dateArray.size
        }


        inner class ViewHolderClass(itemView:View) :RecyclerView.ViewHolder(itemView){
            //항목View 내부의 View 상속
            val rowTextView:TextView = itemView.findViewById(R.id.date_tv)
        }



    }
}