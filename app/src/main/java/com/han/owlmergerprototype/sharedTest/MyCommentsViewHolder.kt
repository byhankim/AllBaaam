package com.han.owlmergerprototype.sharedTest

import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R

class MyCommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val commentRootView = itemView
    val commentCategoryTV:TextView = itemView.findViewById(R.id.category_in_comment_tv)
    val titleTV:TextView = itemView.findViewById(R.id.content_title_tv)
    val commentDateTV:TextView = itemView.findViewById(R.id.comment_date_tv)
    val commentContextTV:TextView = itemView.findViewById(R.id.comment_context_tv)
    val categoryColor: RelativeLayout = itemView.findViewById(R.id.category_in_comment_layout)
}