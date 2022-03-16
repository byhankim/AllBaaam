package com.han.owlmergerprototype.sharedTest

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.data.Comment

class MyCommentsRecyclerAdapter(val context: Context, val commentsList: ArrayList<Comment>): RecyclerView.Adapter<MyCommentsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCommentsViewHolder {
        return MyCommentsViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.my_comment_box_layout,parent,false))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyCommentsViewHolder, position: Int) {
        val comment = commentsList[position]


        holder.commentRootView.setOnClickListener {
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra(context.getString(R.string.dummy_post_id), comment.postId)

            }
            context.startActivity(intent)
        }
        with(holder){
            lateinit var drawable : GradientDrawable
            titleTV.text = comment.post!!.contents
            when(comment.post!!.category){
                "TIP"-> {
                    commentCategoryTV.text = context.resources.getText(R.string.comm_honey_tip)
                    commentCategoryTV.setTextColor(context.resources.getColor(R.color.style1_5))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_5)).toString()
                }
                "STOCK"-> {
                    commentCategoryTV.text = context.resources.getText(R.string.comm_stocks_overseas)
                    commentCategoryTV.setTextColor(context.resources.getColor(R.color.style1_4))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_4)).toString()
                }
                "STUDY"->{
                    commentCategoryTV.text = context.resources.getText(R.string.comm_study_hard)
                    commentCategoryTV.setTextColor(context.resources.getColor(R.color.style1_6))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_6)).toString()
                }
                "SPORTS"-> {
                    commentCategoryTV.text = context.resources.getText(R.string.comm_sports_overseas)
                    commentCategoryTV.setTextColor(context.resources.getColor(R.color.style1_3))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_3)).toString()
                }
                "FOOD"-> {
                    commentCategoryTV.text = context.resources.getText(R.string.comm_food)
                    commentCategoryTV.setTextColor(context.resources.getColor(R.color.style1_1))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_1)).toString()
                }
                "GAME"-> {
                    commentCategoryTV.text = context.resources.getText(R.string.comm_games)
                    commentCategoryTV.setTextColor(context.resources.getColor(R.color.style1_7))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_7)).toString()
                }
                else -> "없엉"
            }
            commentDateTV.text = comment.createdAt
            commentContextTV.text = comment.contents
        }
    }
    override fun getItemCount() = commentsList.size
}