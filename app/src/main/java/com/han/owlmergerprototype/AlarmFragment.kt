package com.han.owlmergerprototype

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlarmFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    val timeArray = arrayListOf(
            "10 분전","1 일전","1 일전","5 일전","6 일전"
    )
    companion object{
        const val TAG : String = "looooog"

        fun newInstance() : AlarmFragment {
            return AlarmFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"AlarmFragment - onCreate() called")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"AlarmFragment - onAttach() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view1 = inflater.inflate(R.layout.fragment_alarm,container,false)
        val adap1 = RecyclerAdapter()
        recyclerView = view1.findViewById(R.id.alarm_rcyView)

        recyclerView.adapter = adap1
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view1

    }


    inner class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>() {
        //항목 구성을 위해 사용할 viewholder 객체가 필요할때 호출되는 메서드
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView = layoutInflater.inflate(R.layout.alarm_box_layout, null)
            val holder = ViewHolderClass(itemView)
            return holder
        }

        //데이터 셋팅
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowTextView.text = timeArray[position]
        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount(): Int {
            return timeArray.size
        }


        inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
            //항목View 내부의 View 상속
            val rowTextView: TextView = itemView.findViewById(R.id.alarm_time_textView)
        }

    }

}