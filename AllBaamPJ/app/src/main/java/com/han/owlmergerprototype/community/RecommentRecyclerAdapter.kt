package com.han.owlmergerprototype.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.CommentRESTEntity
import com.han.owlmergerprototype.data.RecommentRESTEntity
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.utils.DateTimeFormatManager

class RecommentRecyclerAdapter(
    private var reCommentsList: MutableList<RecommentRESTEntity>,
    private val owner: ArticleActivity/*,
    private var parentAdapter: CommentRecyclerAdapter*/
): RecyclerView.Adapter<RecommentRecyclerAdapter.RecommentHolder>() {
    inner class RecommentHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.comment_re_username)
        val timePassed: TextView = itemView.findViewById(R.id.comment_re_time_passed)
        val content: TextView = itemView.findViewById(R.id.comment_re_content)
        val likeBtn: TextView = itemView.findViewById(R.id.comment_re_fav_btn)
        val delBtn: TextView = itemView.findViewById(R.id.comment_re_delete_btn)

        val bottomMarginBlock: TextView = itemView.findViewById(R.id.comment_re_section_margin_bottom_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommentHolder {
        val view = LayoutInflater.from(owner).inflate(R.layout.comment_re_layout, parent, false)
        return RecommentHolder(view)
    }

    override fun onBindViewHolder(holder: RecommentHolder, position: Int) {
        val recommentEntity = reCommentsList[position]
        with (holder) {
            userName.text = recommentEntity.user.userName
            timePassed.text = DateTimeFormatManager.getTimeGapFromNow(recommentEntity.createdAt)
            content.text = recommentEntity.contents
            delBtn.visibility = when (recommentEntity.userId) {
                TestUser.userID -> View.VISIBLE
                else -> View.GONE
            }
            if (position == reCommentsList.size-1) {
                bottomMarginBlock.visibility = View.GONE
            }
        }
        holder.likeBtn.setOnClickListener {
            Toast.makeText(owner, "좋아요는 안눌렀어~", Toast.LENGTH_SHORT).show()
        }
        if (holder.delBtn.visibility == View.VISIBLE) {
            holder.delBtn.setOnClickListener {
                owner.deleteComment(recommentEntity.id)
            }
        }
    }

    override fun getItemCount() = reCommentsList.size

    fun refreshRecommentsDataSet(newRecommentList: MutableList<RecommentRESTEntity>) {
        reCommentsList = newRecommentList
        //notifyDataSetChanged()
    }
}