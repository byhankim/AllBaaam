package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.Context
import android.graphics.drawable.Drawable
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
        binding.tvBadge.text = when (selectedPost.category) {
            "TIP" -> "#꿀팁"
            "STOCK" -> "#해외주식"
            "STUDY" -> "#빡공"
            "SPORTS" -> "#해외 스포츠"
            "FOOD" -> "#야식"
            "GAME" -> "#게임"
            else -> "#고장났어~"
        }

        binding.articleTimestampTv.text = selectedPost.createdAt
        binding.articleUname.text = selectedPost.user.userName
        binding.articleCommentCountTv.text = selectedPost.comments.size.toString()
        binding.articleContentTv.text = selectedPost.contents

        binding.articleFavoriteCountTv.text = selectedPost.like.size.toString()
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


        // fav btn
        /*with (binding.articleBookmarkBtn) {
            val dummyBookmarkType = object: TypeToken<MutableList<Bookmark>>() {}.type
            dummyBookmarksDataSet = Gson().fromJson(myShared.getString(getString(R.string.dummy_bookmarks_key), ""), dummyBookmarkType)

            // default
            val currentBookmark = dummyBookmarksDataSet.filter {
                (it.postID == dummyPostId) && (it.userID == TestUser.userID)
            }
            Log.e("[crntBkmrk(single)]", "currentBookmark is empty: ${currentBookmark.isEmpty()}")
            binding.articleBookmarkBtn.background = if (currentBookmark.isNotEmpty()) {
                isBookMarked = true
                getDrawable(R.drawable.outline_bookmark_24)
            } else {
                isBookMarked = false
                getDrawable(R.drawable.outline_bookmark_border_24)
            }

            // click listener
            setOnClickListener {
                isBookMarked = !isBookMarked
                var index = -1

                // 기존 데이터셋에 중복되어있는지 여부 확인
                val isBookmarkDuplicate = dummyBookmarksDataSet.filter {
                    (it.postID == dummyPostId) && (it.userID == TestUser.userID)
                }

                // 북마크 데이터 중복이 아니면 dataset 리스트에 더해주고
                // index도 바꿔줌
                if (isBookMarked) {
                    if (isBookmarkDuplicate.isEmpty()) {
                        dummyBookmarksDataSet.add(
                            Bookmark(
                                dummyBookmarksDataSet.size + 1,
                                DateTimeFormatManager.getCurrentDatetime(),
                                null,
                                false,
                                TestUser.userID,
                                dummyPostId
                            )
                        )
                        index = dummyBookmarksDataSet.size // 이미 하나 늘어났으므로
                    }
                } else {
                    // 리스트에서 제거
                    dummyBookmarksDataSet = dummyBookmarksDataSet.filterNot {
                        (it.postID == dummyPostId) && (it.userID == TestUser.userID)
                    } as MutableList<Bookmark>
                }

                // 배경 바꾸기
                background = if (isBookMarked) {
                    getDrawable(R.drawable.outline_bookmark_24)
                } else {
                    getDrawable(R.drawable.outline_bookmark_border_24)
                }
                Log.e("[Article_BookmarakBTN]", "length: " + dummyBookmarksDataSet.size.toString())

                // sharedpref에 저장
                with (myShared.edit()) {
                    putString(getString(R.string.dummy_bookmarks_key), Gson().toJson(dummyBookmarksDataSet))
                    commit()
                }

            }
        }*/

        // like btn
        /*with (binding.articleFavoriteBtn) {
            val dummyLikesType = object: TypeToken<MutableList<Like>>() {}.type
            dummyLikesDataSet = Gson().fromJson(myShared.getString(getString(R.string.dummy_likes_key),""), dummyLikesType)

            // default
            val currentLike = dummyLikesDataSet.filter {
                (it.postID == dummyPostId)
            }

            // current like count
            binding.articleFavoriteCountTv.text = if (currentLike.isNotEmpty()) {
                currentLike.size.toString()
            } else {
                "0"
            }
            Log.e("[currentLikes]", currentLike.size.toString())

            // did the user press like?
            val userLike = currentLike.filter {
                it.userID == TestUser.userID
            }
            isLikePressed = userLike.isNotEmpty()
            Log.e("[userLikesToThisPost]", "${userLike.isNotEmpty()}")

            // default Liked state
            background = if (isLikePressed) {
                getDrawable(R.drawable.outline_favorite_24)
            } else {
                getDrawable(R.drawable.outline_favorite_border_24)
            }

            // click listener
            setOnClickListener {
                isLikePressed = !isLikePressed

                val isLikeDuplicate = dummyLikesDataSet.filter {
                    (it.postID == dummyPostId) && (it.userID == TestUser.userID)
                }
                if (isLikeDuplicate.isNotEmpty())
                    Log.e("[like_duplicate:]", isLikeDuplicate.toString())

                // 중복 없다면 더하기
                if (isLikePressed) {
                    if (isLikeDuplicate.isEmpty()) {
                        dummyLikesDataSet.add(Like(
                            dummyLikesDataSet.size+1,
                            DateTimeFormatManager.getCurrentDatetime(),
                            null,
                            TestUser.userID,
                            dummyPostId
                        ))
                    }
                } else {
                    // 좋아요 취소
                    dummyLikesDataSet = dummyLikesDataSet.filterNot{
                        // 이 유저가 / 이 글에서 누른 좋아요만 빼기
                        ((it.userID == TestUser.userID) && (it.postID == dummyPostId))
                    } as MutableList<Like>
                    Log.e("[좋아요취소시기존좋아요수]", dummyLikesDataSet.filter {
                        (it.postID == dummyPostId)
                    }.size.toString() + ", 전체 데이터셋크기:${dummyLikesDataSet.size.toString()}")
                }

                // icon exchange
                background = if (isLikePressed) {
                    getDrawable(R.drawable.outline_favorite_24)
                } else {
                    getDrawable(R.drawable.outline_favorite_border_24)
                }

                // heart count renew
                binding.articleFavoriteCountTv.text = dummyLikesDataSet.filter {
                    (it.postID == dummyPostId)
                }.size.toString()

                Log.e("[Like]", dummyLikesDataSet.filter { (it.postID == dummyPostId) }.size.toString())

                // save to sharedPreferences
                with (myShared.edit()) {
                    putString(getString(R.string.dummy_likes_key), Gson().toJson(dummyLikesDataSet))
                    commit()
                }
            }
        }*/


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
}