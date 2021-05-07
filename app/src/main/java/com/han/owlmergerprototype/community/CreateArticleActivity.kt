package com.han.owlmergerprototype.community

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.databinding.ActivityCreateArticleBinding


class CreateArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateArticleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.commMainToolbar)

        // set maxlinesx
        binding.commWriteArticleContentEt.maxLines = 5

        /*
        // left side button
        val backBtn: Button = Button(this)
        val lParams1: Toolbar.LayoutParams = Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
        lParams1.gravity = Gravity.START
        backBtn.layoutParams = lParams1
//        backBtn.text = "Back"
        backBtn.background = getDrawable(R.drawable.arrow_back)
        backBtn.width = 50
        backBtn.height = 50
        binding.commMainToolbar.addView(backBtn)

        // middle textview section
        val titleTv = TextView(this)
        val lParams2: Toolbar.LayoutParams = Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
        lParams2.gravity = Gravity.CENTER
        titleTv.layoutParams = lParams2
        titleTv.text = "글 작성하기"
        binding.commMainToolbar.addView(titleTv)

        // end side button
        val writeBtn: Button = Button(this)
        val lParams3: Toolbar.LayoutParams = Toolbar.LayoutParams(
            Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT)
        lParams3.gravity = Gravity.END
        writeBtn.layoutParams = lParams3
//        writeBtn.text = "Back"
        writeBtn.background = getDrawable(R.drawable.done)
        writeBtn.width = 50
        writeBtn.height = 50
        binding.commMainToolbar.addView(writeBtn)

        backBtn.setOnClickListener {
            Toast.makeText(this, "응~ 뒤로 안가줘~", Toast.LENGTH_SHORT).show()
        }
        writeBtn.setOnClickListener {
            Toast.makeText(this, "응~ 글 안써줘~", Toast.LENGTH_SHORT).show()
        }
        */

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.commMainToolbar.inflateMenu(R.menu.comm_create_article_menu)

        // buttons
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24) // set drawable icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        binding.commMainToolbar.title = "글 작성 ㄱ"


        binding.commWriteArticleAddImgBtn.setOnClickListener {
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startActivityForResult(intent, RESUlT_IMAGE_LOAD)
            Toast.makeText(this, "이미지 아직 못 골라유~", Toast.LENGTH_SHORT).show()
        }
        binding.commWriteArticleAddEmojiBtn.setOnClickListener {
            Toast.makeText(this, "이모지 아직 못 골라유~", Toast.LENGTH_SHORT).show()
        }

        // TODO TODO TODO
        binding.commListLr.setOnClickListener {
            var category = "none"
            category = when (it.id) {
                R.id.cate_honey_tips -> "꿀팁"
                R.id.cate_stocks_overseas -> "해외주식"
                R.id.cate_soccor_games -> "해외축구"
                R.id.cate_job_talks -> "이직잡담"
                R.id.cate_study_hard -> "빡공하는 올빼미"
                else -> "none"
            }
            Toast.makeText(this, category, Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "응~ 뒤로 안갈거야~", Toast.LENGTH_SHORT).show()
            }

            /*
            *
        val uIcon: Int = -1,
        val datetime: String = "",
        var fixedDatetime: String = "",
        var uname: String = "",
        var content: String = "",
        var images: MutableList<Int>
            * */
            R.id.action_write_article -> {
                val intent = Intent(this, ArticleActivity::class.java).apply {
//                    putExtra("article_title", binding.commWriteArticleTitleEt.text.toString())
                    putExtra("uIcon",
                        R.drawable.crazy_human
                    )
                    putExtra("datetime", "")
                    putExtra("article_content", binding.commWriteArticleContentEt.text.toString())
                }
                startActivity(intent)
            }
        }
        return true
    }
}