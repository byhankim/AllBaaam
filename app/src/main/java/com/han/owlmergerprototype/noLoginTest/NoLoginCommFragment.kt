package com.han.owlmergerprototype.noLoginTest

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.han.owlmergerprototype.LoginActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.RetrofitRESTService
import com.han.owlmergerprototype.common.token
import com.han.owlmergerprototype.common.token2
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.rest.UserInfo
import com.han.owlmergerprototype.retrofit.OwlRetrofitManager
import com.han.owlmergerprototype.utils.SpaceDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

enum class sortBy {
    LATEST, POPULARITY
}

@Suppress("DEPRECATION")
class NoLoginCommFragment(var owner: Activity): Fragment() {

    private lateinit var floatBTN: FloatingActionButton
    private lateinit var inte: Intent
    private lateinit var themeSelectorRv: RecyclerView


    // rest post dataset :(
    private lateinit var postModel: PostModel
    private lateinit var postList: MutableList<PostEntity>
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerAdapter

    private lateinit var autoLogin :SharedPreferences

    private var catetoryId: Int? = null
    private var sortByFlag = sortBy.LATEST


    companion object{
        const val TAG : String = "로그"

        fun newInstance(owner: Activity) : NoLoginCommFragment {
            return NoLoginCommFragment(owner)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"CommFragment - onCreate() called")

        autoLogin = context!!.getSharedPreferences("autoLogin",Activity.MODE_PRIVATE)
        postModel = PostModel("FAIL", mutableListOf(PostEntity()))
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

        postModel = PostModel("FAIL", mutableListOf(PostEntity()))


        try {
            getPosts(null)
            Log.e("[calledGetPosts]", "1")
            Log.e("[initcursorid]", "2")
        } catch(e: Exception) {
            Log.e("[getPostsException]", e.toString())
        }

        recyclerView = view1.findViewById(R.id.article_rv)
        mAdapter = RecyclerAdapter(owner, postModel.posts)

        with (recyclerView) {
            layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

            adapter = mAdapter
        }
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        recyclerView.addItemDecoration(deco)




        val myShared = owner.getSharedPreferences(
            getString(R.string.owl_shared_preferences_name),
            Context.MODE_PRIVATE
        )

//        val dummyCommPostsType = object: TypeToken<MutableList<Post>>() {}.type
//        val dummyCommunityPostsList: MutableList<Post> =
//            Gson().fromJson(myShared.getString(
//                getString(R.string.owl_shared_preferences_dummy_comm_posts),
//                ""),
//                dummyCommPostsType
//            )
//        recyclerView = view1.findViewById(R.id.article_rv)
//        mAdapter = RecyclerAdapter(owner, postModel.posts)

//        val manager: LinearLayoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)




        floatBTN = view1.findViewById(R.id.fab)
        floatBTN.isVisible = false

        themeSelectorRv = view1.findViewById(R.id.comm_theme_selector_recyclerview)

        val manager = LinearLayoutManager(owner, LinearLayoutManager.HORIZONTAL, true)


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


        // ---------------------------------------------------------------------
        //  인기순 받아오기
        // ---------------------------------------------------------------------
        val popularSortBtn = view1.findViewById<TextView>(R.id.comm_sort_by_popularity_btn)//comment_sort_by_popularity_btn
        popularSortBtn.setOnClickListener {
            if (sortByFlag != sortBy.POPULARITY) {
                postList.clear()
                getPostsByPopularity()
                sortByFlag = sortBy.POPULARITY
            }
        }


        // ---------------------------------------------------------------------
        //  최신순 받아오기
        // ---------------------------------------------------------------------
        val latestSortBtn = view1.findViewById<TextView>(R.id.comm_sort_by_time_btn)
        latestSortBtn.setOnClickListener {
            if (sortByFlag != sortBy.LATEST) {
                postList.clear()
                getPosts(null)
                sortByFlag = sortBy.LATEST
            }
        }



        return view1
    }

    inner class RecyclerAdapter(
        private val owner: Activity,
        private var commPostList: MutableList<PostEntity>
    ): RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>(){

        //항목 구성을 위해 사용할 viewholder 객체가 필요할때 호출되는 메서드
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView = layoutInflater.inflate(R.layout.layout_recycler_item,null)
            val holder = ViewHolderClass(itemView)
            return holder
        }
        fun getCategoryNameInArticle(category: String):String{
            val cateInArt :String = "#$category"
            return cateInArt
        }

        //데이터 셋팅
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            val postEntity = commPostList[position]
            lateinit var drawable : GradientDrawable

            with (holder) {
                when (postEntity.category) {
                    "TIP" -> {
                        category.text = getCategoryNameInArticle(getString(R.string.comm_honey_tip))
                        category.setTextColor(owner.resources.getColor(R.color.style1_5))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_5))
                    }
                    "STOCK" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_stocks_overseas))
                        category.setTextColor(owner.resources.getColor(R.color.style1_4))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_4))
                    }
                    "STUDY" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_study_hard))
                        category.setTextColor(owner.resources.getColor(R.color.style1_6))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_6))
                    }
                    "SPORTS" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_sports_overseas))
                        category.setTextColor(owner.resources.getColor(R.color.style1_3))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_3))
                    }
                    "FOOD" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_latenight_food))
                        category.setTextColor(owner.resources.getColor(R.color.style1_1))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_1))
                    }
                    "GAME" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_games))
                        category.setTextColor(owner.resources.getColor(R.color.style1_7))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_7))
                    }
                    else -> category.text =getCategoryNameInArticle(getString(R.string.comm_theme_not_found))
                }
                userName.text = postEntity.user.userName
                datetime.text = postEntity.createdAt
                content.text = postEntity.contents
            }
            if(position==3){
                holder.lastItemBlur.isVisible = true
                holder.loginView.isVisible = true
                holder.loginBTN.setOnClickListener {
                    val dialog = Dialog(context!!)
                    dialog.getWindow()!!.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                    dialog.setContentView(R.layout.dialog_login)
                    val cancelBTN:TextView = dialog.findViewById<TextView>(R.id.login_dialog_cancel_btn)
                    cancelBTN.setOnClickListener(View.OnClickListener {
                        dialog.dismiss()
                    })
                    val kakaoLoginBTN:TextView = dialog.findViewById<TextView>(R.id.kakao_login_btn)
                    kakaoLoginBTN.setOnClickListener(View.OnClickListener {

                        val retrofit = Retrofit.Builder()
                            .baseUrl(ADDRESS)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()


                        val loginService = retrofit.create(RetrofitRESTService::class.java)
                        loginService.getUserInfo(token).enqueue(object : Callback<UserInfo> {
                            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                                val dialog = AlertDialog.Builder(dialog.context)
                                dialog.setTitle("통신실패")
                                dialog.setMessage("실패")
                                dialog.show()
                            }
                            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                                val userInfo = response.body()

                                if(userInfo?.ok==true){

                                    TestUser.token =token
                                    TestUser.userName = userInfo.userName
                                    TestUser.userID =userInfo.id
                                    TestUser.verify = userInfo.verified

                                    val editor = autoLogin.edit()
                                    editor.putString("token",token)
                                    editor.putString("userName",userInfo.userName)
                                    editor.putInt("userId",userInfo.id)
                                    editor.putBoolean("verified",userInfo.verified)
                                    editor.apply()

                                    inte = Intent(context, BottomNavActivity::class.java)
                                    startActivity(inte)
                                    activity!!.finish()


                                }else{
                                    Toast.makeText(dialog.context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                                }


                            }


                        })


//                        val sooToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjUsImlhdCI6MTYyMTgzNDk0NH0.63ZWsr5ulOofO0W4dHdxPQwMEP_EUOcFGiP94OyWDic"
//
//                        val retrofit = Retrofit.Builder()
//                            .baseUrl(ADDRESS)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build()
//
//
//                        val loginService = retrofit.create(RestService::class.java)
//                        loginService.getUserInfo(sooToken).enqueue(object : Callback<UserInfo> {
//                            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
//                                val dialog = AlertDialog.Builder(dialog.context)
//                                dialog.setTitle("통신실패")
//                                dialog.setMessage("실패")
//                                dialog.show()
//                            }
//                            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
//                                val userInfo = response.body()
//
//                                if(userInfo?.ok==true){
//
//                                    TestUser.token =sooToken
//                                    TestUser.userName = userInfo.userName
//                                    TestUser.userID =userInfo.id
//                                    TestUser.verify = userInfo.verified
//
//                                    val editor = autoLogin.edit()
//                                    editor.putString("token",sooToken)
//                                    editor.putString("userName",userInfo.userName)
//                                    editor.putInt("userID",userInfo.id)
//                                    editor.putBoolean("verified",userInfo.verified)
//                                    editor.apply()
//
//
//                                    inte = Intent(context, BottomNavActivity::class.java)
//                                    startActivity(inte)
//                                    activity!!.finish()
//
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










                    })

                    val naverLoginBTN:TextView = dialog.findViewById<TextView>(R.id.naver_login_btn)
                    naverLoginBTN.setOnClickListener(View.OnClickListener {



                        val retrofit = Retrofit.Builder()
                            .baseUrl(ADDRESS)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()


                        val loginService = retrofit.create(RetrofitRESTService::class.java)
                        loginService.getUserInfo(token2).enqueue(object : Callback<UserInfo> {
                            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                                val dialog = AlertDialog.Builder(dialog.context)
                                dialog.setTitle("통신실패")
                                dialog.setMessage("실패")
                                dialog.show()
                            }
                            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                                val userInfo = response.body()

                                if(userInfo?.ok==true){

                                    TestUser.token =token2
                                    TestUser.userName = userInfo.userName
                                    TestUser.userID =userInfo.id
                                    TestUser.verify = userInfo.verified

                                    val editor = autoLogin.edit()
                                    editor.putString("token",token2)
                                    editor.putString("userName",userInfo.userName)
                                    editor.putInt("userId",userInfo.id)
                                    editor.putBoolean("verified",userInfo.verified)
                                    editor.apply()

                                    inte = Intent(context, BottomNavActivity::class.java)
                                    startActivity(inte)
                                    activity!!.finish()


                                }else{
                                    Toast.makeText(dialog.context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                                }


                            }


                        })

//                        val retrofit = Retrofit.Builder()
//                            .baseUrl("https://64aa493c7cf5.ngrok.io/")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build()
//
//                        val loginService = retrofit.create(RestService::class.java)
//                        loginService.naverLogin().enqueue(object : Callback<Login> {
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
                    putExtra("selectedPost", Gson().toJson(postEntity))
                }
                owner.startActivity(intent)
            }
        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount() = commPostList.size

        fun reloadDataWithRetrofitResponse(newPostList: MutableList<PostEntity>) {
            commPostList = newPostList
//            notifyDataSetChanged()
        }

        inner class ViewHolderClass(itemView:View) : RecyclerView.ViewHolder(itemView){
            //항목View 내부의 View 상속
            val category: TextView = itemView.findViewById(R.id.tv_badge)
            val userName: TextView = itemView.findViewById(R.id.tv_nicname)
            val content: TextView = itemView.findViewById(R.id.content_tv)
            val datetime: TextView = itemView.findViewById(R.id.comm_post_date_created_tv)
            val categoryColor: RelativeLayout = itemView.findViewById(R.id.category_in_article_layout)
            val lastItemBlur: RealtimeBlurView = itemView.findViewById(R.id.article_blur)
            val loginView:RelativeLayout = itemView.findViewById(R.id.login_view)
            val loginBTN:Button = itemView.findViewById(R.id.comm_login_btn)
        }
    }

    // ===========================================================================
    //              RETROFIT NETWORKING
    // ===========================================================================
    private fun getPosts(cursorId: Int?) {
        Log.e("[getPost]", "-.-")
        // no progressbar!!

        val call: Call<PostModel> = OwlRetrofitManager.OwlRestService.owlRestService.getPosts(null, null)

        call.enqueue(object: Callback<PostModel> {
            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                if (response.isSuccessful) {
                    postModel = response.body() as PostModel
                    Log.e("[getPostSuccess]", postModel.toString())
                    mAdapter.reloadDataWithRetrofitResponse(postModel.posts)
                    owner.runOnUiThread {
//                        recyclerView.adapter = mAdapter
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<PostModel>, t: Throwable) {
                Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
                postModel = PostModel("error", mutableListOf(PostEntity()))
            }
        })
    }

    private fun getPostsByPopularity() {
        val call: Call<PopularPostModel> = OwlRetrofitManager.OwlRestService.owlRestService.getPopularPosts()

        call.enqueue(object: Callback<PopularPostModel> {
            override fun onResponse(
                call: Call<PopularPostModel>,
                response: Response<PopularPostModel>
            ) {
                val popularPostModel = response.body() as PopularPostModel

                if (popularPostModel.posts.isNullOrEmpty()) return
                postList = popularPostModel.posts

                mAdapter.reloadDataWithRetrofitResponse(postList)
                activity!!.runOnUiThread {
                    mAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<PopularPostModel>, t: Throwable) {
                Log.e("[popPostsFailure]", "F A I L $t")
                postModel = PostModel("error", mutableListOf(PostEntity()))
            }
        })
    }
}