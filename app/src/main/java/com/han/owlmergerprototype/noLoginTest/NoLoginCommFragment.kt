package com.han.owlmergerprototype.noLoginTest

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mmin18.widget.RealtimeBlurView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.community.CreateArticleActivity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.data.ThemeEntity
import com.han.owlmergerprototype.rest.Login
import com.han.owlmergerprototype.rest.RestService
import com.han.owlmergerprototype.utils.SpaceDecoration
import com.kakao.sdk.common.KakaoSdk
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class NoLoginCommFragment(var owner: Activity): Fragment() {
    private lateinit var floatBTN: FloatingActionButton
    private lateinit var inte: Intent
    private lateinit var themeSelectorRv: RecyclerView


    private lateinit var recyclerView: RecyclerView





    companion object{
        const val TAG : String = "looooog"

        fun newInstance(owner: Activity) : NoLoginCommFragment {
            return NoLoginCommFragment(owner)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"CommFragment - onCreate() called")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"CommFragment - onAttach() called")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view1 = inflater.inflate(R.layout.fragment_comm,container,false)


        val myShared = owner.getSharedPreferences(
            getString(R.string.owl_shared_preferences_name),
            Context.MODE_PRIVATE
        )

        val dummyCommPostsType = object: TypeToken<MutableList<Post>>() {}.type
        val dummyCommunityPostsList: MutableList<Post> =
            Gson().fromJson(myShared.getString(
                getString(R.string.owl_shared_preferences_dummy_comm_posts),
                ""),
                dummyCommPostsType
            )
        recyclerView = view1.findViewById(R.id.article_rv)

//        val manager: LinearLayoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)



        with (recyclerView) {
            layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, true)
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

            val size = dummyCommunityPostsList.size
            val postList: List<Post> = dummyCommunityPostsList.subList(size - 4, size)
            adapter = RecyclerAdapter(owner, postList as MutableList<Post>)
        }
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        floatBTN = view1.findViewById(R.id.fab)
        floatBTN.isVisible = false

        recyclerView.addItemDecoration(deco)

        themeSelectorRv = view1.findViewById(R.id.comm_theme_selector_recyclerview)

        val manager = LinearLayoutManager(owner, LinearLayoutManager.HORIZONTAL, false)


        with (themeSelectorRv) {
            layoutManager = manager
            androidx.recyclerview.widget.DividerItemDecoration(
                context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL)
            android.util.Log.e("[THEME_SELECTOR]", "aasaaaaaaaaaaahhh!!!")
            val testList = kotlin.collections.mutableListOf<ThemeEntity>()
            testList.add(
                    com.han.owlmergerprototype.data.ThemeEntity(
                            getString(com.han.owlmergerprototype.R.string.comm_honey_tip),
                            com.han.owlmergerprototype.R.drawable.ic_idea,
                            com.han.owlmergerprototype.R.color.style1_5_20,
                            com.han.owlmergerprototype.R.color.style2_5,
                            com.han.owlmergerprototype.R.color.style1_5,
                            1,
                            false
                    )
            )
            testList.add(
                    com.han.owlmergerprototype.data.ThemeEntity(
                            getString(com.han.owlmergerprototype.R.string.comm_stocks_overseas),
                            com.han.owlmergerprototype.R.drawable.ic_graph,
                            com.han.owlmergerprototype.R.color.style1_4_20,
                            com.han.owlmergerprototype.R.color.style2_4,
                            com.han.owlmergerprototype.R.color.style1_4,
                            2,
                            false
                    )
            )
            testList.add(
                    com.han.owlmergerprototype.data.ThemeEntity(
                            getString(com.han.owlmergerprototype.R.string.comm_sports_overseas),
                            com.han.owlmergerprototype.R.drawable.ic_sport,
                            com.han.owlmergerprototype.R.color.style1_3_20,
                            com.han.owlmergerprototype.R.color.style2_3,
                            com.han.owlmergerprototype.R.color.style1_3,
                            3,
                            false
                    )
            )
            testList.add(
                    com.han.owlmergerprototype.data.ThemeEntity(
                            getString(com.han.owlmergerprototype.R.string.comm_latenight_food),
                            com.han.owlmergerprototype.R.drawable.ic_chicken,
                            com.han.owlmergerprototype.R.color.style1_1_20,
                            com.han.owlmergerprototype.R.color.style2_1,
                            com.han.owlmergerprototype.R.color.style1_1,
                            4,
                            false
                    )
            )
            testList.add(
                    com.han.owlmergerprototype.data.ThemeEntity(
                            getString(com.han.owlmergerprototype.R.string.comm_study_hard),
                            com.han.owlmergerprototype.R.drawable.ic_book,
                            com.han.owlmergerprototype.R.color.style1_6_20,
                            com.han.owlmergerprototype.R.color.style2_6,
                            com.han.owlmergerprototype.R.color.style1_6,
                            5,
                            false
                    )
            )
            testList.add(
                    com.han.owlmergerprototype.data.ThemeEntity(
                            getString(com.han.owlmergerprototype.R.string.comm_games),
                            com.han.owlmergerprototype.R.drawable.ic_game,
                            com.han.owlmergerprototype.R.color.style1_7_20,
                            com.han.owlmergerprototype.R.color.style2_7,
                            com.han.owlmergerprototype.R.color.style1_7,
                            6,
                            false
                    )
            )
            adapter = com.han.owlmergerprototype.community.ThemeSelectorRecyclerAdapter(
                testList,
                owner,
                true
            )
        }



        return view1
    }

    inner class RecyclerAdapter(
        private val owner: Activity,
        private val dummyPostsList: MutableList<Post>
    ): RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>(){

        //항목 구성을 위해 사용할 viewholder 객체가 필요할때 호출되는 메서드
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView = layoutInflater.inflate(R.layout.layout_recycler_item,null)
            val holder = ViewHolderClass(itemView)
            return holder
        }
        fun getCategoryNameInArticle(category: String):String{
            val cateInArt :String = "#"+category
            return cateInArt
        }

        //데이터 셋팅
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            val postEntity = dummyPostsList[position]
            lateinit var drawable : GradientDrawable

            with (holder) {
                when (postEntity.category) {
                    1 -> {
                        category.text = getCategoryNameInArticle(getString(R.string.comm_honey_tip))
                        category.setTextColor(owner.resources.getColor(R.color.style1_5))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_5))
                    }
                    2 -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_stocks_overseas))
                        category.setTextColor(owner.resources.getColor(R.color.style1_4))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_4))
                    }
                    3 -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_study_hard))
                        category.setTextColor(owner.resources.getColor(R.color.style1_6))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_6))
                    }
                    4 -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_sports_overseas))
                        category.setTextColor(owner.resources.getColor(R.color.style1_3))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_3))
                    }
                    5 -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_latenight_food))
                        category.setTextColor(owner.resources.getColor(R.color.style1_1))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_1))
                    }
                    6 -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_games))
                        category.setTextColor(owner.resources.getColor(R.color.style1_7))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_7))
                    }
                    else -> category.text =getCategoryNameInArticle(getString(R.string.comm_theme_not_found))
                }
                userName.text = when (postEntity.userID) {
                    1 -> "떡볶이가 좋은 빙봉"
                    2 -> "배부른 하이에나"
                    3 -> "잠들지 못하는 소크라테스"
                    4 -> "집에 가고픈 야근가이"
                    else -> "롤이 재밌는 콩순이"
                }
                datetime.text = postEntity.createdAt
                content.text = postEntity.contents
            }
            if(position==0){
                holder.lastItemBlur.isVisible = true
                holder.loginView.isVisible = true
                holder.loginBTN.setOnClickListener {
                    val dialog = Dialog(context!!)
                    dialog.getWindow()!!.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    dialog.setContentView(R.layout.activity_login)
                    val cancelBTN:TextView = dialog.findViewById<TextView>(R.id.login_dialog_cancel_btn)
                    cancelBTN.setOnClickListener(View.OnClickListener {
                        dialog.dismiss()
                    })
                    val kakaoLoginBTN:TextView = dialog.findViewById<TextView>(R.id.kakao_login_btn)
                    kakaoLoginBTN.setOnClickListener(View.OnClickListener {
                        dialog.dismiss()
                        TestUser.userName ="떡볶이가 좋은 빙봉"
                        TestUser.userID = 1
                        inte = Intent(context, BottomNavActivity::class.java)
                        startActivity(inte)
                        activity!!.finish()

//                        val retrofit = Retrofit.Builder()
//                            .baseUrl("https://91c1ad0a482f.ngrok.")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build()
//
//                        val loginService = retrofit.create(RestService::class.java)
//                        loginService.loginAndGetToken().enqueue(object : Callback<Login> {
//                            override fun onFailure(call: Call<Login>, t: Throwable) {
//                                val dialog = AlertDialog.Builder(dialog.context)
//                                dialog.setTitle("통신실패")
//                                dialog.setMessage("실패")
//                                dialog.show()
//                            }
//                            override fun onResponse(call: Call<Login>, response: Response<Login>) {
//                                val login = response.body()
//
//                                if(login?.ok==true){
//                                    dialog.dismiss()
//                                    TestUser.token = login.token
//                                    inte = Intent(context, BottomNavActivity::class.java)
//                                    startActivity(inte)
//                                    activity!!.finish()
//
//                                }else{
//                                    Toast.makeText(dialog.context,"틀리셨어용", Toast.LENGTH_SHORT).show()
//                                }
//
//
//                                /*  val dialog = AlertDialog.Builder(this@LoginActivity)
//                                  dialog.setTitle("통신성공")
//                                  dialog.setMessage("ok: ${login?.ok.toString()} , token: ${login?.token}")
//                                  dialog.show()*/
//
//                            }
//
//
//                        })


//                        TestUser.userName ="떡볶이가 좋은 빙봉"
//                        TestUser.userID = 1

                    })
                    dialog.show()




                }
            }

            holder.itemView.setOnClickListener {
//                val intent = Intent(owner, BottomNavActivity::class.java).apply {
////                    putExtra("article_title", binding.commWriteArticleTitleEt.text.toString())
//                    putExtra("article_content", binding.commWriteArticleContentEt.text.toString())
//                }
//                startActivity(intent)
                Log.e("[CommFrag_itemview]", "clicked post id: ${postEntity.id}")
                val intent = Intent(owner, ArticleActivity::class.java).apply {
                    putExtra(getString(R.string.dummy_post_id), postEntity.id)
                }
                owner.startActivity(intent)
            }
        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount() = dummyPostsList.size


        inner class ViewHolderClass(itemView:View) : RecyclerView.ViewHolder(itemView){
            //항목View 내부의 View 상속
            val category: TextView = itemView.findViewById(R.id.tv_badge)
            val userName: TextView = itemView.findViewById(R.id.tv_nicname)
            val content: TextView = itemView.findViewById(R.id.user_name_txt)
            val datetime: TextView = itemView.findViewById(R.id.comm_post_date_created_tv)
            val categoryColor: RelativeLayout = itemView.findViewById(R.id.category_in_article_layout)
            val lastItemBlur: RealtimeBlurView = itemView.findViewById(R.id.article_blur)
            val loginView:RelativeLayout = itemView.findViewById(R.id.login_view)
            val loginBTN:Button = itemView.findViewById(R.id.comm_login_btn)
        }



    }
}