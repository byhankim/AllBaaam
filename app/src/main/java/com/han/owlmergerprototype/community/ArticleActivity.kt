package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.databinding.ArticleLayoutBinding
import com.han.owlmergerprototype.utils.DateTimeFormatManager
import kotlin.properties.Delegates

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ArticleLayoutBinding
    private var dummyPostId by Delegates.notNull<Int>()

    // icon toggle
    var isBookMarked = false
    var isLikePressed = false

    // comments
    private lateinit var dummyCommentsDataSet: MutableList<Comment>
    private var commentCount = -1

    // likes
    private lateinit var dummyLikesDataSet: MutableList<Like>

    // bookmarks
    private lateinit var dummyBookmarksDataSet: MutableList<Bookmark>


//    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // shared pref
        val myShared = getSharedPreferences(getString(R.string.owl_shared_preferences_name), MODE_PRIVATE)


        // post id
        dummyPostId = intent.getIntExtra(getString(R.string.dummy_post_id), -1)
        Log.e("[ArticleBody]", "dummy post id: $dummyPostId")

        binding = ArticleLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.articleToolbar)


        // get Article Post Body from intent post_id
        val myPost: Post = when (dummyPostId) {
            -1 -> Post(contents=getString(R.string.article_content_for_dummy_post_id_retrive_error))
            else -> {
                val sharedKey = getString(R.string.owl_shared_preferences_dummy_comm_posts)

                val dummyPostType = object: TypeToken<MutableList<Post>>() {}.type
                val dummyDataSetFromSharedPreferences: MutableList<Post> =
                    Gson().fromJson(myShared.getString(sharedKey, ""), dummyPostType)

                dummyDataSetFromSharedPreferences.filter { it.id == dummyPostId }[0]
            }
        }
        binding.articleContentTv.text = myPost.contents

//        binding.articleContentTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.crazy_human, 0, 0)

        // RV
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        with (binding.commentsRv) {
            layoutManager = manager
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

            // get comment data from shared preferences
            // (opt) where article id == current post id
            val sharedCommentsKey = getString(R.string.dummy_comments_key)

            val dummyCommentsType = object: TypeToken<MutableList<Comment>>() {}.type

            // filter by postID!!
            dummyCommentsDataSet =
                Gson().fromJson(myShared.getString(sharedCommentsKey, ""), dummyCommentsType)/*.filter{  }*/

            adapter = CommentRecyclerAdapter(
                dummyCommentsDataSet,
                this@ArticleActivity
            ) {
//                 setOnClickListener {}
            }

            Log.e("[ArticleActivity]", dummyCommentsDataSet.size.toString())
            commentCount = dummyCommentsDataSet.size
        }

        // comment count
        binding.articleCommentCountTv.text = dummyCommentsDataSet.size.toString()


        // fav btn
        with (binding.articleBookmarkBtn) {
            val dummyBookmarkType = object: TypeToken<MutableList<Bookmark>>() {}.type
            dummyBookmarksDataSet = Gson().fromJson(myShared.getString(getString(R.string.dummy_bookmarks_key), ""), dummyBookmarkType)

            // default
            val currentBookmark = dummyBookmarksDataSet.filter {
                (it.postID == dummyPostId) && (it.userID == R.string.dummy_uid_1)
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
                    (it.postID == dummyPostId) && (it.userID == R.string.dummy_uid_1)
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
                                R.string.dummy_uid_1,
                                dummyPostId
                            )
                        )
                        index = dummyBookmarksDataSet.size // 이미 하나 늘어났으므로
                    }
                } else {
                    // 리스트에서 제거
                    dummyBookmarksDataSet = dummyBookmarksDataSet.filterNot {
                        (it.postID == dummyPostId) && (it.userID == R.string.dummy_uid_1)
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
        }

        // like btn
        with (binding.articleFavoriteBtn) {
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

            // did the user press like?
            val userLike = currentLike.filter {
                it.userID == R.string.dummy_uid_1
            }
            isLikePressed = currentLike.isNotEmpty()
            Log.e("[userLikes]", "${currentLike.isNotEmpty()}")

            // default Liked state
            background = if (isLikePressed) {
                getDrawable(R.drawable.outline_favorite_24)
            } else {
                getDrawable(R.drawable.outline_favorite_border_24)
            }

            // click listener
            setOnClickListener {
                isLikePressed = !isLikePressed

                if (isLikePressed) {
                    dummyLikesDataSet.add(Like(
                        dummyLikesDataSet.size+1,
                        DateTimeFormatManager.getCurrentDatetime(),
                        null,
                        R.string.dummy_uid_1,
                        dummyPostId
                    ))
                } else {
                    // 좋아요 눌렀다가 이제 끈거면 해당 아이템 제거해버리기
                    dummyLikesDataSet = dummyBookmarksDataSet.filter{
                        it.userID == R.string.dummy_uid_1
                    } as MutableList<Like>
                }

                // icon exchange
                background = if (isLikePressed) {
                    getDrawable(R.drawable.outline_favorite_24)
                } else {
                    getDrawable(R.drawable.outline_favorite_border_24)
                }

                // heart count renew
                binding.articleFavoriteCountTv.text = dummyLikesDataSet.size.toString()

                // save to sharedPreferences
                with (myShared.edit()) {
                    putString(getString(R.string.dummy_likes_key), Gson().toJson(dummyLikesDataSet))
                    commit()
                }
            }
        }

        // share btn
        with (binding.articleShareBtn) {
            // TODO share btn -> to kakaotalk
        }

        // leave a comment
        with (binding.replyAddBtn) {
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
                        R.string.dummy_username_1
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
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.articleToolbar.inflateMenu(R.menu.article_menu)
        binding.articleToolbar.title = "" // 앱제목 없어지게 처리

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
//                Toast.makeText(this, "신고들어갑니다잉~", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.report_btn -> {
                isBookMarked = !isBookMarked
                Toast.makeText(this, "신고 럿성", Toast.LENGTH_SHORT).show()
//                if (isBookMarked) {
//                    item.setIcon(R.drawable.outline_bookmark_24)
//                } else {
//                    item.setIcon(R.drawable.outline_bookmark_border_24)
//                }
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}