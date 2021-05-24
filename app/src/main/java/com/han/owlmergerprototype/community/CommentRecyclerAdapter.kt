package com.han.owlmergerprototype.community

import android.app.Activity
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.data.CommentRESTEntity
import com.han.owlmergerprototype.utils.DateTimeFormatManager

class CommentRecyclerAdapter (
    private var commentsList: MutableList<CommentRESTEntity>,
    private val owner: ArticleActivity,
    private val itemListener: (CommentRESTEntity) -> Unit
): RecyclerView.Adapter<CommentRecyclerAdapter.CommentHolder>() {
    inner class CommentHolder(itemView: View, itemListenr: (CommentRESTEntity) -> Unit): RecyclerView.ViewHolder(itemView){
        val userName: TextView = itemView.findViewById(R.id.comment_username)
        val timePassed: TextView = itemView.findViewById(R.id.comment_time_passed)
        val content: TextView = itemView.findViewById(R.id.comment_content)
        val likeBtn: TextView = itemView.findViewById(R.id.comment_fav_btn)
        val replyBtn: TextView = itemView.findViewById(R.id.comment_reply_btn)

        var hiddenSpace: TextView = itemView.findViewById(R.id.comment_depth_dummy_tv)

        fun bindListener(item: CommentRESTEntity) {
            itemView.setOnClickListener { itemListener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view = LayoutInflater.from(owner).inflate(R.layout.comment_layout, parent, false)
        return CommentHolder(view, itemListener)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val commentEntity = commentsList[position]
        with (holder) {
            userName.text = commentEntity.userId.toString()
            timePassed.text = commentEntity.createdAt
            content.text = commentEntity.contents

            if (!commentEntity.isParent) {
                hiddenSpace.visibility = TextView.VISIBLE
            }
        }
        holder.likeBtn.setOnClickListener {
            Toast.makeText(owner, "좋아요는 안눌렀어~", Toast.LENGTH_SHORT).show()
        }
        holder.replyBtn.setOnClickListener {
            Toast.makeText(owner, "답글은 여기서 답시다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = commentsList.size

    fun refreshCommentsDataSet(newCommentList: MutableList<CommentRESTEntity>) {
        commentsList = newCommentList
        notifyDataSetChanged()
    }
}