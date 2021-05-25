package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.token
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.databinding.ArticleLayoutBinding
import com.han.owlmergerprototype.retrofit.OwlRetrofitManager
import com.han.owlmergerprototype.map.MapsMainActivity
import com.han.owlmergerprototype.utils.DateTimeFormatManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class ArticleActivity : AppCompatActivity(){

    private lateinit var binding: ArticleLayoutBinding
    private lateinit var selectedPost: PostEntity

    // comments
    private lateinit var commentsList: MutableList<CommentRESTEntity>
    private lateinit var cmtAdapter: CommentRecyclerAdapter

    // icon toggle
    var isBookMarked = false
    var isLikePressed = false

    private var commentCount = -1

    // likes
    private lateinit var dummyLikesDataSet: MutableList<BookmarkEntity>

    // bookmarks
    private lateinit var dummyBookmarksDataSet: MutableList<BookmarkEntity>
    private lateinit var drawable : GradientDrawable


//    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // RETROFIT REST NETWORKING
//        intent.get

        // shared pref
        val myShared = getSharedPreferences(getString(R.string.owl_shared_preferences_name), MODE_PRIVATE)
        commentsList = mutableListOf()


        //== map cmnt object ==//
//        val mapCmntObject = Gson().fromJson(intent.getStringExtra("mapCmntObject"), MapsMainActivity::class.java)
//        Log.d("로그", "ArticleActivity - onCreate() called / mapCmntObject = ${mapCmntObject}")

        // post id
//        dummyPostId = intent.getIntExtra(getString(R.string.dummy_post_id), -1)
//        Log.e("[ArticleBody]", "dummy post id: $dummyPostId")

        // post
        selectedPost = Gson().fromJson(intent.getStringExtra("selectedPost"), PostEntity::class.java)
        Log.e("[[ArticleActivity]]", selectedPost.toString())

        binding = ArticleLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.articleToolbar)

        // Comments RV
        cmtAdapter = CommentRecyclerAdapter(commentsList, this) { }

        with (binding.commentsRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

            adapter = cmtAdapter
        }
        getComments(selectedPost.id)




    // POST BODY
        when (selectedPost.category){
            "TIP" ->{
            binding.tvBadge.text  = getCategoryNameInArticle(getString(R.string.comm_honey_tip))
            binding.tvBadge.setTextColor(this.resources.getColor(R.color.style1_5, null))
            drawable = binding.clickedArticleTopicLayout.background as GradientDrawable
            drawable.setStroke(2,this.resources.getColor(R.color.style1_5, null))
        }
        "STOCK" -> {
            binding.tvBadge.text =getCategoryNameInArticle(getString(R.string.comm_stocks_overseas))
            binding.tvBadge.setTextColor(this.resources.getColor(R.color.style1_4, null))
            drawable = binding.clickedArticleTopicLayout.background as GradientDrawable
            drawable.setStroke(2,this.resources.getColor(R.color.style1_4, null))
        }
        "STUDY" -> {
            binding.tvBadge.text = getCategoryNameInArticle(getString(R.string.comm_study_hard))
            binding.tvBadge.setTextColor(this.resources.getColor(R.color.style1_6, null))
            drawable = binding.clickedArticleTopicLayout.background as GradientDrawable
            drawable.setStroke(2,this.resources.getColor(R.color.style1_6, null))
        }
        "SPORTS" -> {
            binding.tvBadge.text =getCategoryNameInArticle(getString(R.string.comm_sports_overseas))
            binding.tvBadge.setTextColor(this.resources.getColor(R.color.style1_3, null))
            drawable = binding.clickedArticleTopicLayout.background as GradientDrawable
            drawable.setStroke(2,this.resources.getColor(R.color.style1_3, null))
        }
        "FOOD" -> {
            binding.tvBadge.text =getCategoryNameInArticle(getString(R.string.comm_latenight_food))
            binding.tvBadge.setTextColor(this.resources.getColor(R.color.style1_2, null))
            drawable = binding.clickedArticleTopicLayout.background as GradientDrawable
            drawable.setStroke(2,this.resources.getColor(R.color.style1_2, null))
        }
        "GAME" -> {
            binding.tvBadge.text =getCategoryNameInArticle(getString(R.string.comm_games))
            binding.tvBadge.setTextColor(this.resources.getColor(R.color.style1_7, null))
            drawable = binding.clickedArticleTopicLayout.background as GradientDrawable
            drawable.setStroke(2,this.resources.getColor(R.color.style1_7, null))
        }
        else -> binding.tvBadge.text =getCategoryNameInArticle(getString(R.string.comm_theme_not_found))
    }

        binding.articleTimestampTv.text = selectedPost.createdAt
        binding.articleUname.text = selectedPost.user.userName
       // binding.articleCommentCountTv.text = selectedPost.comments.size.toString()
        binding.articleCommentCountTv.text = changeCommentTxt(selectedPost.id).toString()
        binding.articleContentTv.text = selectedPost.contents
       // binding.articleFavoriteCountTv.text = selectedPost.like.size.toString()
       binding.articleFavoriteCountTv.text = changeLikeTxt(selectedPost.id).toString()
        val call: Call<IsBookmark> = OwlRetrofitManager.OwlRestService.owlRestService.getIsBookmark(TestUser.token,selectedPost.id)

        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<IsBookmark> {
            override fun onResponse(call: Call<IsBookmark>, response: Response<IsBookmark>) {

                val isbm = response.body()
                if (response.isSuccessful) {
                    binding.articleBookmarkBtn.isChecked = isbm!!.isBookmark
                } else{
                    Log.d(CommFragment.TAG, "onResponse:")
                }

            }
            override fun onFailure(call: Call<IsBookmark>, t: Throwable) {
                Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
            }
        })

        val call2: Call<IsLike> = OwlRetrofitManager.OwlRestService.owlRestService.getIsLike(TestUser.token,selectedPost.id)
        Log.e("[retrofitCall]", call.request().toString())
        call2.enqueue(object: Callback<IsLike> {
            override fun onResponse(call: Call<IsLike>, response: Response<IsLike>) {
                val islk = response.body()

                if (response.isSuccessful) {
                    binding.articleFavoriteBtn.isChecked = islk!!.isLike
                } else{
                    Log.d(CommFragment.TAG, "onResponse: ")
                }

            }
            override fun onFailure(call: Call<IsLike>, t: Throwable) {
                Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
            }
        })

    binding.articleBookmarkBtn.setOnClickListener {
        val call: Call<IsBookmark> = OwlRetrofitManager.OwlRestService.owlRestService.postBookmark(TestUser.token,selectedPost.id)

        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<IsBookmark> {
            override fun onResponse(call: Call<IsBookmark>, response: Response<IsBookmark>) {
                val isbm = response.body()
                if (response.isSuccessful) {
                    Log.e(CommFragment.TAG,"[retrofitResult]: ${isbm?.isBookmark}")

                } else{
                    Log.d(CommFragment.TAG, "onResponse:")
                }

            }
            override fun onFailure(call: Call<IsBookmark>, t: Throwable) {
                Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
            }
        })

    }



    // click listener
    binding.articleFavoriteBtn.setOnClickListener {
        val call: Call<IsLike> = OwlRetrofitManager.OwlRestService.owlRestService.postLike(TestUser.token,selectedPost.id)

        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<IsLike> {
            override fun onResponse(call: Call<IsLike>, response: Response<IsLike>) {

                val isbm = response.body()
                if (response.isSuccessful) {
                    changeLikeTxt(selectedPost.id)
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
//        binding.articleCommentCountTv.text = selectedPost.comments.size.toString()
        // time gap in text
//        binding.articleTimestampTv.text = DateTimeFormatManager.getTimeGapFromNow(myPost.createdAt)
//        binding.articleContentTv.text = myPost.contents


        // RV
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        with (binding.commentsRv) {
            layoutManager = manager
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

            // get comment data from shared preferences
            // (opt) where article id == current post id
            val sharedCommentsKey = getString(R.string.dummy_comments_key)

            val dummyCommentsType = object: TypeToken<MutableList<CommentRESTEntity>>() {}.type

            // NOT HERE!!!!
//            commentsList = selectedPost.comments as MutableList<CommentRESTEntity>
            // filter by postID!!

            adapter = CommentRecyclerAdapter(
                commentsList,
                this@ArticleActivity
            ) {
//                 setOnClickListener {}
            }

//            Log.e("[ArticleActivity]", dummyCommentsDataSet.size.toString())
            commentCount = commentsList.size
        }

        // comment count
        binding.articleCommentCountTv.text = commentsList.size.toString()


        // ------------------------------------------------------------------------------
        //          add a comment
        // ------------------------------------------------------------------------------
        with (binding.replyContentEt) {
            if (binding.replyContentEt.text.toString().isEmpty()) {
                requestFocus()
                Toast.makeText(this@ArticleActivity, "댓글을 달려면 내용을 작성해주세요!", Toast.LENGTH_SHORT).show()
                return
            }
            addComment(selectedPost.id, binding.replyContentEt.text.toString())
        }





        // share btn
        with (binding.articleShareBtn) {
            // TODO share btn -> to kakaotalk
        }

        // leave a comment
        /*with (binding.replyAddBtn) {
            setOnClickListener {
                if (binding.replyContentEt.text.toString().isNotBlank()) { // not empty nor whitespaces
                    dummyCommentsDataSet.add(Comment(
                        dummyCommentsDataSet.size+1,
                        DateTimeFormatManager.getCurrentDatetime(),
                        null,
                        binding.replyContentEt.text.toString(),
                        dummyPostId,
                        true,
                        -1,
                        TestUser.userID
                    ))

                    with (myShared.edit()) {
                        val sharedKey = getString(R.string.dummy_comments_key)
                        putString(sharedKey, Gson().toJson(dummyCommentsDataSet))
                        commit()
                    }
                    binding.commentsRv.adapter?.notifyDataSetChanged()
                    binding.articleCommentCountTv.text = dummyCommentsDataSet.size.toString()

                }
                binding.replyContentEt.text.clear()

                // hide softkey
                val inputMng:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMng.hideSoftInputFromWindow(binding.replyContentEt.windowToken, 0)
            }
        }*/



    }
    fun getCategoryNameInArticle(category: String):String{
        val cateInArt :String = "#$category"
        return cateInArt
    }

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(TestUser.userName!=""){
            binding.articleToolbar.inflateMenu(R.menu.article_menu)
            if(TestUser.userID != selectedPost.userId ){
                binding.articleToolbar.menu.removeItem(R.id.delete_article_btn)
            }
        }
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.delete_article_btn -> {
                Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.declaration_btn ->{
                val dialog = Dialog(this)
                dialog.getWindow()!!.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.setContentView(R.layout.dialog_declaration)
                val cancelBTN: TextView = dialog.findViewById<TextView>(R.id.exit_dialog_declaration)
                cancelBTN.setOnClickListener(View.OnClickListener {
                    Toast.makeText(this, getString(R.string.reason_of_declaration1), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                })
                val reason1BTN: TextView = dialog.findViewById<TextView>(R.id.declare_reason1_btn)
                reason1BTN.setOnClickListener(View.OnClickListener {
                    Toast.makeText(this, getString(R.string.reason_of_declaration1), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()

                })
                val reason2BTN: TextView = dialog.findViewById<TextView>(R.id.declare_reason2_btn)
                reason2BTN.setOnClickListener(View.OnClickListener {
                    Toast.makeText(this, getString(R.string.reason_of_declaration2), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()

                })
                val reason3BTN: TextView = dialog.findViewById<TextView>(R.id.declare_reason3_btn)
                reason3BTN.setOnClickListener(View.OnClickListener {
                    Toast.makeText(this, getString(R.string.reason_of_declaration3), Toast.LENGTH_SHORT).show()
                    dialog.dismiss()

                })
                dialog.show()
            }
        }
        return true
    }
    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }


    // ===========================================================================
    //              RETROFIT NETWORKING
    // ===========================================================================
    private fun getComments(postId: Int) {
        // no progressbar!!
        val call: Call<CommentRestModel> = OwlRetrofitManager.OwlRestService.owlRestService.getPostComments(postId)
        // log?
//        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<CommentRestModel> {
            override fun onResponse(call: Call<CommentRestModel>, response: Response<CommentRestModel>) {
                if (response.isSuccessful) {
                    val commentModel = response.body() as CommentRestModel
                    if (commentModel.comments.isNullOrEmpty())
                        return


                    Log.e("[CommentModelSuccess]", commentModel.comments.toString())
                    cmtAdapter.refreshCommentsDataSet(commentModel.comments)
                    Log.e("[CommentModelRefresh]", "힝")
                    runOnUiThread {
                        cmtAdapter.refreshCommentsDataSet(commentModel.comments)
                        with (binding.commentsRv) { adapter = cmtAdapter}
                        cmtAdapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<CommentRestModel>, t: Throwable) {
                Log.e("[getCommentsFailure]", "F A I L ${t.toString()}")
            }
        })
    }

    private fun addComment(postId: Int, contents: String) {
        val result: Call<OkFailResult> = OwlRetrofitManager.OwlRestService.owlRestService.createComment(
            token, "")
    }


    fun changeLikeTxt(postId:Int){
        val call: Call<CountLike> = OwlRetrofitManager.OwlRestService.owlRestService.getLikeCount(postId)
        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<CountLike> {
            override fun onResponse(call: Call<CountLike>, response: Response<CountLike>) {
                val count = response.body()!!
                if (response.isSuccessful) {

                        binding.articleFavoriteCountTv.text = count.countLike.toString()
                    }

            }
            override fun onFailure(call: Call<CountLike>, t: Throwable) {
                Log.e("[getCommentsFailure]", "F A I L ${t.toString()}")
            }
        })

    }
    fun changeCommentTxt(postId:Int){
        val call: Call<CountComment> = OwlRetrofitManager.OwlRestService.owlRestService.getCommentCount(postId)
        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<CountComment> {
            override fun onResponse(call: Call<CountComment>, response: Response<CountComment>) {
                val count = response.body()!!
                if (response.isSuccessful) {

                    binding.articleCommentCountTv.text = count.countComments.toString()

                    return
                }
            }
            override fun onFailure(call: Call<CountComment>, t: Throwable) {
                Log.e("[getCommentsFailure]", "F A I L ${t.toString()}")
            }
        })

    }
}