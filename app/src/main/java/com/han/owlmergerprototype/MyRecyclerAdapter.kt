package com.han.owlmergerprototype

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.MyRecyclerviewInterface
import com.han.owlmergerprototype.MyViewHolder
import com.han.owlmergerprototype.databinding.ActivityMypg004Binding
import com.han.owlmergerprototype.databinding.LayoutRecyclerItemBinding

class MyRecyclerAdapter(myRecyclerviewInterface: MyRecyclerviewInterface): RecyclerView.Adapter<MyViewHolder>() {
    val TAG: String = "로그"
    private var modelList = ArrayList<MyModel>()
    private var myRecyclerviewInterface: MyRecyclerviewInterface? = null

    // 생성자
    init {
        this.myRecyclerviewInterface = myRecyclerviewInterface
    }

    // 뷰 홀더가 생성되었을 때
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        // 연결할 레이아웃 설정
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_item, parent, false), this.myRecyclerviewInterface!!)

    }

    // 목록의 아이템 수
    override fun getItemCount(): Int {
        return this.modelList.size

    }

    // 뷰와 뷰 홀더가 묶였을 때
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "MyRecyclerAdapter - onBindViewHolder() called / position: $position")
        holder.bind(this.modelList[position])
    }

    // 외부에서 데이터 보내기
    fun submitList(modelList: ArrayList<MyModel>) {
        this.modelList = modelList
    }
}