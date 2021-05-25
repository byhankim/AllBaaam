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

class MyBookmarkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val rootView = itemView
    val categoryTV:TextView = itemView.findViewById(R.id.tv_badge)
    val dateTV:TextView = itemView.findViewById(R.id.comm_post_date_created_tv)
    val userNameTV:TextView = itemView.findViewById(R.id.tv_nicname)
    val contentTV:TextView = itemView.findViewById(R.id.content_tv)
}