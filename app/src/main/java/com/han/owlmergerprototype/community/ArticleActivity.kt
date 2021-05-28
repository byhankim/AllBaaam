package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
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
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.sortBy
import com.han.owlmergerprototype.common.token
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.databinding.ArticleLayoutBinding
import com.han.owlmergerprototype.retrofit.OwlRetrofitManager
import com.han.owlmergerprototype.utils.DateTimeFormatManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArticleActivity : AppCompatActivity(){

    private lateinit var binding: ArticleLayoutBinding
    private lateinit var selectedPost: PostEntity
    private var selectedCmtId: Int? = null

    // comments
    private lateinit var commentsList: MutableList<CommentRESTEntity>
    private lateinit var cmtAdapter: CommentRecyclerAdapter
    private var sortByFlag = sortBy.LATEST

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
        DateTimeFormatManager.fromNow("2021-05-27T01:08:33.395Z=Z")


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

        // Image - Glide
        if (selectedPost.images.isNotEmpty()) {
            Glide.with(this).load(selectedPost.images[0].url)
                .into(binding.articleGlideImageIv)
            binding.articleGlideImageIv.visibility = View.VISIBLE
        } else {
            binding.articleGlideImageIv.visibility = View.GONE
        }


        /*
        RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(image_url).apply(options).into(imageView);
         */
//        binding.articleContentTv.setC
//        Glide.with(this).load(image_url).apply(options).into(imageView)

        // Comments RV
        cmtAdapter = CommentRecyclerAdapter(commentsList, this) { }

        /*

            mAdapter.setItemClickListener(object : ThemeSelectorRecyclerAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    Log.d(TAG, "onClick: ${position}")
                    selectedCategory = position
                }
            })
         */
        cmtAdapter.setItemClickListener(object:CommentRecyclerAdapter.ItemClickListener {
            override fun onClick(view: View, commentId: Int) {
                selectedCmtId = cmtAdapter.selectedCmtId
                /*if (view.id == R.id.comment_reply_btn) {
                    selectedCmtId = if (commentsList[commentId].id != selectedCmtId) {
                        commentsList[commentId].id
                    } else {
                        null
                    }


                    val text = if (selectedCmtId == null) "댓글달기" else "대댓달기"
                    Toast.makeText(this@ArticleActivity, text, Toast.LENGTH_SHORT).show()
                    Log.e("[reReply]", "selectedCmtId: $selectedCmtId")
                }*/
            }
        })

        with (binding.commentsRv) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

            adapter = cmtAdapter

            commentCount = commentsList.size
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

        binding.articleTimestampTv.text = DateTimeFormatManager.getTimeGapFromNow(selectedPost.createdAt)
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

        // Image
        /*
        RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(image_url).apply(options).into(imageView);
         */
         // cmt
         binding.commentSortByTimeBtn.setOnClickListener {
              if (sortByFlag == sortBy.POPULARITY) {
                  binding.commentSectionSortByTimeIndicatingCircle.visibility = View.VISIBLE
                  binding.commentSectionSortByPopularIndicatingCircle.visibility = View.INVISIBLE
              } else {
                  binding.commentSectionSortByTimeIndicatingCircle.visibility = View.INVISIBLE
                  binding.commentSectionSortByPopularIndicatingCircle.visibility = View.VISIBLE
              }
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
        /*val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        with (binding.commentsRv) {
            layoutManager = manager
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            adapter = CommentRecyclerAdapter(commentsList, this@ArticleActivity) {}
        }

        // comment count
        binding.articleCommentCountTv.text = commentsList.size.toString()*/


        // ------------------------------------------------------------------------------
        //          add a comment
        // ------------------------------------------------------------------------------
        with (binding.replyAddBtn) {
            setOnClickListener {
                if (binding.replyContentEt.text.toString().isEmpty()) {
                    requestFocus()
                    Toast.makeText(this@ArticleActivity, "댓글을 달려면 내용을 작성해주세요!", Toast.LENGTH_SHORT).show()
                } else {
                    selectedCmtId = cmtAdapter.selectedCmtId
                    Log.e("[ArticleActivityCmtId]", "댓글달기 - Cmt Id: $selectedCmtId")
                    addComment(binding.replyContentEt.text.toString(), selectedPost.id, selectedCmtId)

                    // btn select reset
                    selectedCmtId = null
                    cmtAdapter.selectedCmtId = null
                }
            }
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
                deletePost(selectedPost.id)
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

    private fun addComment(contents: String, postId: Int, parentId: Int?) {
        val myJson = when (parentId) {
            null -> {
                Gson().toJson(CreateCommentEntity(contents, postId))
            }
            else -> {
                Gson().toJson(CreateReCommentEntity(contents, postId, parentId))
            }
        }

        Log.e("[jenjeanne]", myJson)

        val result: Call<OkFailResult> = OwlRetrofitManager.OwlRestService.owlRestService.createComment(
            token, myJson)
        result.enqueue(object: Callback<OkFailResult> {
            override fun onResponse(call: Call<OkFailResult>, response: Response<OkFailResult>) {
                if (response.isSuccessful) {
                    val okFail = response.body()
                    okFail?.let {
                        Toast.makeText(this@ArticleActivity, "댓글을 작성하였습니다!", Toast.LENGTH_SHORT).show()
                        Log.e("[AddCommentSuccess]", "yey")

                        // get rid of keyboard
                        binding.replyContentEt.setText("")
                        val imm = this@ArticleActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.replyContentEt.windowToken, 0)

                        // refresh comment
                        getComments(selectedPost.id)
                    }
                }
            }

            override fun onFailure(call: Call<OkFailResult>, t: Throwable) {
                Toast.makeText(this@ArticleActivity, t.toString(), Toast.LENGTH_SHORT).show()
                Log.e("[AddCommentFail]", "$t")
            }
        })
    }

    fun deletePost(postId: Int) {
        val call: Call<OkFailResult> = OwlRetrofitManager.OwlRestService.owlRestService.deletePost(postId, token)
        call.enqueue(object: Callback<OkFailResult> {
            override fun onResponse(call: Call<OkFailResult>, response: Response<OkFailResult>) {
                if (response.isSuccessful) {
                    Log.e("[delPostSuccess]", "deleted")
                    Toast.makeText(this@ArticleActivity, "글을 삭제합니다", Toast.LENGTH_SHORT).show()
                    this@ArticleActivity.finish()
                }
            }

            override fun onFailure(call: Call<OkFailResult>, t: Throwable) {
                Log.e("[delPostFail]", "delete error")
                Toast.makeText(this@ArticleActivity, "글 삭제에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deleteComment(commentId: Int) {
        val result: Call<OkFailResult> = OwlRetrofitManager.OwlRestService.owlRestService.deleteComment(commentId, token)

        result.enqueue(object: Callback<OkFailResult>{
            override fun onResponse(call: Call<OkFailResult>, response: Response<OkFailResult>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ArticleActivity, "댓글을 삭제합니다", Toast.LENGTH_SHORT).show()
                    Log.e("[delCommentSuccess]", "deleted")
                    getComments(selectedPost.id)
                }
            }

            override fun onFailure(call: Call<OkFailResult>, t: Throwable) {
                Log.e("[delCommentFail]", "fail")
                Toast.makeText(this@ArticleActivity, "댓글 작세에 실패했수다! ${t.toString()}", Toast.LENGTH_SHORT).show()
            }
        })
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