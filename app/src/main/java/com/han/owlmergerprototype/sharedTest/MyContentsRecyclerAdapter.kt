package com.han.owlmergerprototype.sharedTest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.MyViewHolder
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.mypage.MyContentFragment

class MyContentsRecyclerAdapter: RecyclerView.Adapter<MyContentsViewHolder>() {

    private var articleList = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContentsViewHolder {
        return MyContentsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_contents_box_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyContentsViewHolder, position: Int) {
        holder.bind(this.articleList[position])
    }

    fun submitList(articleList:ArrayList<Post>){
        this.articleList = articleList
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}