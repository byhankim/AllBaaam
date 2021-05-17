package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.databinding.ArticleLayoutBinding
import kotlin.properties.Delegates

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ArticleLayoutBinding
    private var dummyPostId by Delegates.notNull<Int>()
    private lateinit var myPost: Post

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
        myPost = when (dummyPostId) {
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

    @SuppressLint("ResourceType")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(TestUser.userName!=""){
            binding.articleToolbar.inflateMenu(R.menu.article_menu)
            if(TestUser.userID !=myPost.userID ){
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



}