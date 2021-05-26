package com.han.owlmergerprototype.sharedTest

import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.han.owlmergerprototype.App
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.Post
import org.w3c.dom.Text

class MyContentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val rootView = itemView
    val categoryTV:TextView = itemView.findViewById(R.id.category_tv)
    val dateTV:TextView = itemView.findViewById(R.id.date_tv)
    val contentTV:TextView = itemView.findViewById(R.id.context_tv)
    val categoryColor: RelativeLayout = itemView.findViewById(R.id.category_in_article_layout)
    val isLike:ToggleButton = itemView.findViewById(R.id.article_favorite_btn)
    val likeCountTV:TextView = itemView.findViewById(R.id.article_favorite_num_btn)
    val commentCountTV:TextView = itemView.findViewById(R.id.article_comment_num_btn)
}