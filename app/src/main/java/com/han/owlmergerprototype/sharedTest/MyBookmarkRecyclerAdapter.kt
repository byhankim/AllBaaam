package com.han.owlmergerprototype.sharedTest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.rest.PostForMy

class MyBookmarkRecyclerAdapter(val context: Context, val articleList: ArrayList<PostForMy>): RecyclerView.Adapter<MyBookmarkViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookmarkViewHolder {
        return MyBookmarkViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.layout_recycler_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyBookmarkViewHolder, position: Int) {
        val article = articleList[position]

        holder.rootView.setOnClickListener {
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra(context.getString(R.string.dummy_post_id), article.id)

            }
            context.startActivity(intent)
        }
        with(holder){
            categoryTV.text = when(article.category){
                "TIP"-> context.resources.getString(R.string.comm_honey_tip)
                "STOCK"-> context.resources.getString(R.string.comm_honey_tip)
                "STUDY"-> context.resources.getString(R.string.comm_honey_tip)
                "SPORTS"-> context.resources.getString(R.string.comm_honey_tip)
                "FOOD"-> context.resources.getString(R.string.comm_honey_tip)
                "GAME"-> context.resources.getString(R.string.comm_honey_tip)
                else -> "없엉"
            }
            dateTV.text = article.createdAt
            contentTV.text = article.contents
            userNameTV.text = article.user.userName
        }
    }
    override fun getItemCount() = articleList.size
}