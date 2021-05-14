package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.databinding.ArticleLayoutBinding
import kotlin.properties.Delegates

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ArticleLayoutBinding
    private var dummyPostId by Delegates.notNull<Int>()

    // icon toggle
    var isBookMarked = false
    var isLikePressed = false


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
            val dummyCommentsDataSet: MutableList<Comment> =
                Gson().fromJson(myShared.getString(sharedCommentsKey, ""), dummyCommentsType)/*.filter{  }*/

            adapter = CommentRecyclerAdapter(
                dummyCommentsDataSet,
                this@ArticleActivity
            ) {
//                 setOnClickListener {}
            }

            Log.e("[ArticleActivity]", dummyCommentsDataSet.size.toString())
        }

        with (binding.articleFavoriteBtn) {
            setOnClickListener {
                isLikePressed = !isLikePressed
                if (isLikePressed) {
                    background = getDrawable(R.drawable.outline_favorite_24)
                } else {
                    background = getDrawable(R.drawable.outline_favorite_border_24)
                }
            }
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.articleToolbar.inflateMenu(R.menu.article_menu)
        binding.articleToolbar.title = "" // 앱제목 없어지게 처리

        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_more_vert_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "신고들어갑니다잉~", Toast.LENGTH_SHORT).show()
            }
            R.id.bookmark_btn -> {
                isBookMarked = !isBookMarked
                Toast.makeText(this, "북마크 눌럿성", Toast.LENGTH_SHORT).show()
                if (isBookMarked) {
                    item.setIcon(R.drawable.outline_bookmark_24)
                } else {
                    item.setIcon(R.drawable.outline_bookmark_border_24)
                }
            }
        }
        return true
    }
}