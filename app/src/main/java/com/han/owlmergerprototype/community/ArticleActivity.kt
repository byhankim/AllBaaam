package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.databinding.ArticleLayoutBinding

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ArticleLayoutBinding

    // icon toggle
    var isBookMarked = false
    var isLikePressed = false


//    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ArticleLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.articleToolbar)



        // RV
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        with (binding.commentsRv) {
            layoutManager = manager
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

            val tempList = mutableListOf<CommentEntity>()
            for (i in 1..20) {
                tempList.add(CommentEntity())
            }

            val temp = CommentEntity()
            temp.isStandaloneComment = false
            tempList.add(temp)

            adapter = CommentRecyclerAdapter(
                tempList,
                this@ArticleActivity
            ) {
//                 setOnClickListener {}
            }
        }

        binding.articleContentTv.text = intent.getStringExtra("article_content")
        /*
    val uIcon: Int = -1,
    val datetime: String = "",
    var fixedDatetime: String = "",
    var uname: String = "",
    var content: String = "",
    var images: MutableList<Int>
        */
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