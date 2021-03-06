package com.han.owlmergerprototype.community

import android.app.Activity
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.data.CommentRESTEntity
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.utils.DateTimeFormatManager

class CommentRecyclerAdapter (
    private var commentsList: MutableList<CommentRESTEntity>,
    private val owner: ArticleActivity,
    private val itemListener: (CommentRESTEntity) -> Unit
): RecyclerView.Adapter<CommentRecyclerAdapter.CommentHolder>() {
    var selectedCmtId: Int? = null
    inner class CommentHolder(itemView: View, itemListenr: (CommentRESTEntity) -> Unit): RecyclerView.ViewHolder(itemView){
        val userName: TextView = itemView.findViewById(R.id.comment_username)
        val timePassed: TextView = itemView.findViewById(R.id.comment_time_passed)
        val content: TextView = itemView.findViewById(R.id.comment_content)
        val likeBtn: TextView = itemView.findViewById(R.id.comment_fav_btn)
        val delBtn: TextView = itemView.findViewById(R.id.comment_delete_btn)
        val replyBtn: TextView = itemView.findViewById(R.id.comment_reply_btn)
        val recommentSection: RelativeLayout = itemView.findViewById(R.id.recomment_section_rl)
        val recommentRv: RecyclerView = itemView.findViewById(R.id.recomment_rv)

        val bottomMarginBlock: TextView = itemView.findViewById(R.id.comment_section_margin_bottom_tv)

        lateinit var recommAdapter: RecommentRecyclerAdapter


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
            userName.text = commentEntity.user.userName
            timePassed.text = DateTimeFormatManager.getTimeGapFromNow(commentEntity.createdAt)
            content.text = commentEntity.contents
            delBtn.visibility = when (commentEntity.userId) {
                TestUser.userID -> View.VISIBLE
                else -> View.GONE
            }

            if (commentEntity.reComments.size > 0) {
                recommentSection.visibility = View.VISIBLE
                Log.e("[recomms]", commentEntity.reComments.toString())

                // adapter & manager
                val manager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
                recommAdapter = RecommentRecyclerAdapter(commentEntity.reComments, owner)

                with (recommentRv) {
                    Log.e("[recomms2]", "asdf")
                    layoutManager = manager
                    DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                    adapter = recommAdapter
//                    notifyDataSetChanged()
                }
            } else {
                recommentSection.visibility = View.INVISIBLE
                Log.e("[recomms3]", "NOT VISIBLE")
            }

            if (position == commentsList.size - 1) {
                bottomMarginBlock.visibility = View.GONE
            }
        }
        holder.likeBtn.setOnClickListener {
            Toast.makeText(owner, "???????????? ????????????~", Toast.LENGTH_SHORT).show()
        }
        holder.replyBtn.setOnClickListener {
            selectedCmtId = if (selectedCmtId != commentEntity.id) {
                Toast.makeText(owner, "?????? ??? ?????? ????????? ????????? ???????????????", Toast.LENGTH_SHORT).show()
                commentEntity.id
            } else {
                null
            }
            Log.e("selectedcmtid", "$selectedCmtId -> replyBtn ????????????...?")
            /*
            with (binding.replyAddBtn) {
            setOnClickListener {
                if (binding.replyContentEt.text.toString().isEmpty()) {
                    requestFocus()
                    Toast.makeText(this@ArticleActivity, "????????? ????????? ????????? ??????????????????!", Toast.LENGTH_SHORT).show()
                } else {
                    addComment(binding.replyContentEt.text.toString(), selectedPost.id, null)
                }
            }
        }
             */
        }
        if (holder.delBtn.visibility == View.VISIBLE) {
            holder.delBtn.setOnClickListener {
                owner.deleteComment(commentEntity.id)
            }
        }
    }

    override fun getItemCount() = commentsList.size

    fun refreshCommentsDataSet(newCommentList: MutableList<CommentRESTEntity>) {
        commentsList = newCommentList
        //notifyDataSetChanged()
    }

    // reply to comment

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    //??????????????? ??????
    private lateinit var itemClickListner: ItemClickListener

    //??????????????? ?????? ?????????
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }
}