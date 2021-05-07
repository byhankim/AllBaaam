package com.han.owlmergerprototype.mypage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R

class MyRippleFragment:Fragment() {
    private lateinit var recyclerView: RecyclerView
    val dateArray = arrayListOf(
        "21/04/18","21/10/08","21/06/22","21/03/24","21/03/14"
    )
    companion object{

        fun newInstance() : MyRippleFragment {


            return MyRippleFragment()
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
        val adap1 = RecyclerAdapter()
        recyclerView = view1.findViewById(R.id.myripples_rcyView)

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
            holder.rowTextView.text = dateArray[position]
        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount(): Int {
            return dateArray.size
        }


        inner class ViewHolderClass(itemView:View) :RecyclerView.ViewHolder(itemView){
            //항목View 내부의 View 상속
            val rowTextView: TextView = itemView.findViewById(R.id.ripple_date_tv)
        }



    }


}