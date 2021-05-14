package com.han.owlmergerprototype.community

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.text.MessageFormat.format
import android.os.Bundle
import android.text.format.DateFormat.format
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.internal.bind.util.ISO8601Utils.format
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.ThemeEntity
import com.han.owlmergerprototype.databinding.ActivityCreateArticleBinding
import com.han.owlmergerprototype.sharedTest.SharedPrefManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.utils.DateTimeFormatManager


class CreateArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateArticleBinding
    private lateinit var newArticleList : ArrayList<ArticleEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.commMainToolbar)

        // set maxlinesx
        binding.commWriteArticleContentEt.maxLines = 5

        // theme selector rv
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        with (binding.themeSelectorRecyclerview) {
            layoutManager = manager
            DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)

            val testList = mutableListOf<ThemeEntity>()
            testList.add(ThemeEntity(getString(R.string.comm_honey_tip), R.drawable.owl2, R.color.style1_5, R.color.black, 1, false))
            testList.add(ThemeEntity(getString(R.string.comm_stocks_overseas), R.drawable.like_btn, R.color.style1_4, R.color.black, 2, false))
            testList.add(ThemeEntity(getString(R.string.comm_sports_overseas), R.drawable.owl2, R.color.style1_3, R.color.black, 3, false))
            testList.add(ThemeEntity(getString(R.string.comm_latenight_food), R.drawable.back_icon_24, R.color.style1_2, R.color.black, 4, false))
            testList.add(ThemeEntity(getString(R.string.comm_study_hard), R.drawable.owl2, R.color.style1_1, R.color.black, 5, false))
            adapter = ThemeSelectorRecyclerAdapter(testList, this@CreateArticleActivity) /*{
                setOnClickListener { Toast.makeText(context, "theme selected!", Toast.LENGTH_SHORT).show() }
            }*/
        }


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
        // theme
        /*binding.commListLr.setOnClickListener {
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
        }*/

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_write_article -> {
                // TO DO("Store them in SharedPreferences")
                val sharedPrefName = getString(R.string.owl_shared_preferences_name)
                val myShared = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)

                val sharedKey = getString(R.string.owl_shared_preferences_dummy_comm_posts)

                val dummyCommPostsType = object: TypeToken<MutableList<Post>>() {}.type
                val dummyDataSetFromSharedPreferences: MutableList<Post> = Gson().fromJson(myShared.getString(sharedKey, ""), dummyCommPostsType)
                dummyDataSetFromSharedPreferences.add(Post(
                    id = dummyDataSetFromSharedPreferences.size + 1,
                    createdAt = DateTimeFormatManager.getCurrentDatetime(),
                    contents = binding.commWriteArticleContentEt.text.toString(),
                    category = R.string.comm_latenight_food
                ))

                with (myShared.edit()) {
                    putString(sharedKey, Gson().toJson(dummyDataSetFromSharedPreferences))
                    commit()
                }

                // Back to community main fragment
//                val createPostIntent: Intent = Intent(this, BottomNavActivity::class.java)
//                createPostIntent.putExtra("postWritten", 200)
                startActivity(Intent(this, BottomNavActivity::class.java))
                finish()
            }
        }
        return true
    }
}