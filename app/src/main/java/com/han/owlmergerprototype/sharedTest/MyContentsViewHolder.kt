package com.han.owlmergerprototype.sharedTest

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.han.owlmergerprototype.App
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.Post

class MyContentsViewHolder(itemView: View)
                        : RecyclerView.ViewHolder(itemView),
                            View.OnClickListener{
    val TAG = "로그"

    private val categoryTV:TextView = itemView.findViewById(R.id.category_tv)
    private val dateTV:TextView = itemView.findViewById(R.id.date_tv)
    private val contentTV:TextView = itemView.findViewById(R.id.context_tv)

    init{
        dateTV.setOnClickListener (this)//연결
        Log.d(TAG, "MyContentsViewHolder: init{} called")
    }

    fun bind(articleEntity: Post){
        Log.d(TAG, "MyContentsViewHolder: bind{} called")
        categoryTV.text = "해외 스포츠"
        dateTV.text = articleEntity.createdAt
        contentTV.text = articleEntity.contents
        Log.d(TAG, "bind: ${contentTV.text}")

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
        when(v){
            dateTV ->Log.d(TAG, "MyContentsViewHolder: bind{} called")
        }
    }

}