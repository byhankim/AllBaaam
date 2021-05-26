package com.han.owlmergerprototype.sharedTest

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.community.CommFragment
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.rest.PostForMy
import com.han.owlmergerprototype.retrofit.OwlRetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyContentsRecyclerAdapter(val context: Context, val articleList: ArrayList<PostForMy>): RecyclerView.Adapter<MyContentsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyContentsViewHolder {
        return MyContentsViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.my_contents_box_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyContentsViewHolder, position: Int) {
        val article = articleList[position]

        holder.rootView.setOnClickListener {
            val intent = Intent(context, ArticleActivity::class.java).apply {
                val selectedPost = Gson().toJson(article)
                Log.e("[postSelected]", selectedPost.toString())
                putExtra(context.resources.getString(R.string.dummy_post_id), selectedPost)
                putExtra("selectedPost", Gson().toJson(article))
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
            changeLikeTxt(holder,article.id)
            changeCommentTxt(holder,article.id)
            getIsLike(holder,article.id)


            holder.isLike.setOnClickListener {
                val call: Call<IsLike> = OwlRetrofitManager.OwlRestService.owlRestService.postLike(TestUser.token,article.id)

                Log.e("[retrofitCall]", call.request().toString())
                call.enqueue(object: Callback<IsLike> {
                    override fun onResponse(call: Call<IsLike>, response: Response<IsLike>) {
                        val isbm = response.body()
                        if (response.isSuccessful) {
                            changeLikeTxt(holder,article.id)
                            Log.e(CommFragment.TAG,"[retrofitResult]: ${isbm?.isLike}")

                        } else{
                            Log.d(CommFragment.TAG, "onResponse:")
                        }

                    }
                    override fun onFailure(call: Call<IsLike>, t: Throwable) {
                        Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
                    }
                })
            }

        }
    }
    override fun getItemCount() = articleList.size

    fun changeLikeTxt(holder: MyContentsViewHolder,postId:Int){
        val call: Call<CountLike> = OwlRetrofitManager.OwlRestService.owlRestService.getLikeCount(postId)
        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<CountLike> {
            override fun onResponse(call: Call<CountLike>, response: Response<CountLike>) {
                val count = response.body()!!
                if (response.isSuccessful) {
                    holder.likeCountTV.text = count.countLike.toString()
                }

            }
            override fun onFailure(call: Call<CountLike>, t: Throwable) {
                Log.e("[getCommentsFailure]", "F A I L ${t.toString()}")
            }
        })

    }

    fun changeCommentTxt(holder: MyContentsViewHolder,postId:Int){
        val call: Call<CountComment> = OwlRetrofitManager.OwlRestService.owlRestService.getCommentCount(postId)
        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<CountComment> {
            override fun onResponse(call: Call<CountComment>, response: Response<CountComment>) {
                val count = response.body()!!
                if (response.isSuccessful) {

                        holder.commentCountTV.text = count.countComments.toString()

                }
            }
            override fun onFailure(call: Call<CountComment>, t: Throwable) {
                Log.e("[getCommentsFailure]", "F A I L ${t.toString()}")
            }
        })

    }

    fun getIsLike(holder:MyContentsViewHolder,postId: Int){
        val call: Call<IsLike> = OwlRetrofitManager.OwlRestService.owlRestService.getIsLike(
            TestUser.token,postId)
        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<IsLike> {
            override fun onResponse(call: Call<IsLike>, response: Response<IsLike>) {
                val islk = response.body()

                if (response.isSuccessful) {
                    holder.isLike.isChecked = islk!!.isLike
                } else{
                    Log.d(CommFragment.TAG, "onResponse: ")
                }

            }
            override fun onFailure(call: Call<IsLike>, t: Throwable) {
                Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
            }
        })
    }
}