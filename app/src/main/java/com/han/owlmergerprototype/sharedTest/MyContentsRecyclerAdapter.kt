package com.han.owlmergerprototype.sharedTest

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.rest.PostForMy

class MyContentsRecyclerAdapter(val context: Context, val articleList: ArrayList<PostForMy>): RecyclerView.Adapter<MyContentsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContentsViewHolder {
        return MyContentsViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.my_contents_box_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyContentsViewHolder, position: Int) {
        val article = articleList[position]

        holder.rootView.setOnClickListener {
            val intent = Intent(context, ArticleActivity::class.java).apply {
                putExtra(context.getString(R.string.dummy_post_id), article.id)

            }
            context.startActivity(intent)
        }
        with(holder){
            lateinit var drawable : GradientDrawable
            when(article.category){
                "TIP"-> {
                    categoryTV.text = context.resources.getText(R.string.comm_honey_tip)
                    categoryTV.setTextColor(context.resources.getColor(R.color.style1_5))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_5)).toString()
                }
                "STOCK"-> {
                    categoryTV.text = context.resources.getText(R.string.comm_stocks_overseas)
                    categoryTV.setTextColor(context.resources.getColor(R.color.style1_4))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_4)).toString()
                }
                "STUDY"->{
                    categoryTV.text = context.resources.getText(R.string.comm_study_hard)
                    categoryTV.setTextColor(context.resources.getColor(R.color.style1_6))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_6)).toString()
                }
                "SPORTS"-> {
                    categoryTV.text = context.resources.getText(R.string.comm_sports_overseas)
                    categoryTV.setTextColor(context.resources.getColor(R.color.style1_3))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_3)).toString()
                }
                "FOOD"-> {
                    categoryTV.text = context.resources.getText(R.string.comm_food)
                    categoryTV.setTextColor(context.resources.getColor(R.color.style1_1))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_1)).toString()
                }
                "GAME"-> {
                    categoryTV.text = context.resources.getText(R.string.comm_games)
                    categoryTV.setTextColor(context.resources.getColor(R.color.style1_7))
                    drawable = categoryColor.background as GradientDrawable
                    drawable.setStroke(2, context.resources.getColor(R.color.style1_7)).toString()
                }
                else -> "없엉"
            }
            dateTV.text = article.createdAt
            contentTV.text = article.contents
        }
    }
    override fun getItemCount() = articleList.size
}