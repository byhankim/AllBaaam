package com.han.owlmergerprototype.sharedTest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.MyViewHolder
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.mypage.MyContentFragment

class MyContentsRecyclerAdapter(val context: Context): RecyclerView.Adapter<MyContentsViewHolder>() {

    private var articleList = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContentsViewHolder {
        return MyContentsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_contents_box_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyContentsViewHolder, position: Int) {
        holder.bind(this.articleList[position])
        val article=this.articleList[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra(context.getString(R.string.dummy_post_id), article.id)

            }
            context.startActivity(intent)
        }
    }

    fun submitList(articleList:ArrayList<Post>){
        this.articleList = articleList
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}