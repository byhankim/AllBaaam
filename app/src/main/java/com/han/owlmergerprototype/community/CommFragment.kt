package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.databinding.adapters.AbsListViewBindingAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.token
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.map.MapsMainActivity
import com.han.owlmergerprototype.mypage.boardActivity.NoticeActivity
import com.han.owlmergerprototype.retrofit.OwlRetrofitManager
import com.han.owlmergerprototype.utils.SpaceDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommFragment(var owner: Activity): Fragment() {
    private lateinit var floatBTN: FloatingActionButton
    private lateinit var inte: Intent

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerRestAdapter
    private lateinit var themeSelectorRv: RecyclerView

    // dummy post dataset
//    private lateinit var dummyCommPostDatasets: MutableList<Post>

    // rest post dataset
    private lateinit var postModel: PostModel
    private lateinit var postList: MutableList<PostEntity>

    // category selection
    private var mCatetoryId: Int? = null
    private var mCursorId: Int? = null



    companion object{
        const val TAG : String = "looooog"

        fun newInstance(owner: Activity) : CommFragment {
            return CommFragment(owner)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"CommFragment - onCreate() called")

//        Log.e("[oncreate]", postModel.toString())

        // kotlin.UninitializedPropertyAccessException: lateinit blah blah
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
        val nScrollView: NestedScrollView = view1.findViewById(R.id.comm_post_section_nestedscrollview)

        // toolbar -> NONE!!!
        // (owner as AppCompatActivity).setSupportActionBar(toolbar)

        // ---------------------------------------------------------------------
        // Community Posts RV
        // ---------------------------------------------------------------------


        try {
            getPosts(mCursorId)
            Log.e("[calledGetPosts]", "1")
//            mCursorId = postModel.posts.last().id
            Log.e("[initcursorid]", "2")
        } catch(e: Exception) {
            Log.e("[getPostsException]", e.toString())
        }

        recyclerView = view1.findViewById(R.id.article_rv)
        mAdapter = RecyclerRestAdapter(owner, postModel.posts)

        with (recyclerView) {
            layoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

            adapter = mAdapter
        }
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        recyclerView.addItemDecoration(deco)

        // scroll event listener
//        recyclerView.addOnScrollListener(RecyclerView.OnScrollListener() {
//            /*
//            * recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//    @Override
//    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//        super.onScrollStateChanged(recyclerView, newState);
//
//        if (!recyclerView.canScrollVertically(1)) {
//            Toast.makeText(YourActivity.this, "Last", Toast.LENGTH_LONG).show();
//
//        }
//    }
//});
//            * */
//            onScrollStateChanged(recyclerView: RecyclerView, int newState) {
//                super.onScrollStateChanged()
//            }
//        })

        // ---------------------------------------------------------------------
        // nestedScrollView refresh
        // ---------------------------------------------------------------------
        nScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            /*
            * if (v.getChildAt(v.getChildCount() - 1) != null)
                {
                    if (scrollY > oldScrollY)
                    {
                        if (scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight()))
                        {
                            //code to fetch more data for endless scrolling
                        }
                    }
                }
            * */
            if (v != null) {
                if (v.getChildAt(v.childCount - 1) != null) {
                    if (scrollY > oldScrollY) {
                        if (scrollY >= (v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight)) {
                            Toast.makeText(owner, "스크롤 끝에 도달했습니다", Toast.LENGTH_SHORT).show()
                            getPosts(mCursorId)
                        }
                    }
                }
            }
        }


        // ---------------------------------------------------------------------
        // theme selector rv
        // ---------------------------------------------------------------------
        themeSelectorRv = view1.findViewById(R.id.comm_theme_selector_recyclerview)

        val manager = LinearLayoutManager(owner, LinearLayoutManager.HORIZONTAL, false)


        with (themeSelectorRv) {
            layoutManager = manager
            androidx.recyclerview.widget.DividerItemDecoration(
                context,LinearLayoutManager.HORIZONTAL)
            Log.e("[THEME_SELECTOR]", "aasaaaaaaaaaaahhh!!!")
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


        // FAB
        floatBTN = view1.findViewById(R.id.fab)

        floatBTN.setOnClickListener {
            if(TestUser.phoneCheck==true){
                inte = Intent(context, CreateArticleActivity::class.java)
                startActivity(inte)
            }else{
                val dialog = Dialog(context!!)
                dialog.getWindow()!!.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                dialog.setContentView(R.layout.dialog_phone_auth)
                val cancelBTN:TextView = dialog.findViewById<TextView>(R.id.auth_cancel_btn)
                cancelBTN.setOnClickListener(View.OnClickListener {
                    dialog.dismiss()
                })
                val phoneET:EditText = dialog.findViewById<EditText>(R.id.phone_number_et)
                val sendBTN: Button = dialog.findViewById<Button>(R.id.send_auth_msg_btn)
                val authLayout:RelativeLayout = dialog.findViewById(R.id.put_auth_number_layout)
                val comBtnLayout:RelativeLayout = dialog.findViewById(R.id.complete_auth_btn_tint_layout)
                val authET:EditText = dialog.findViewById(R.id.put_auth_number_et)
                val authBTN:TextView = dialog.findViewById<TextView>(R.id.complete_auth_btn)

                phoneET.addTextChangedListener(object: TextWatcher{
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            if(it.toString().length>=10){
                                sendBTN.isClickable=true
                            }
                        }

                        override fun afterTextChanged(s: Editable?) {

                        }

                    })

                sendBTN.setOnClickListener {
                    authLayout.isVisible = true
                }

                authET.addTextChangedListener(object: TextWatcher{
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if(it.toString().length>=4){
                            authBTN.isClickable=true
                            comBtnLayout.isVisible = true
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                })
                authBTN.setOnClickListener(View.OnClickListener {
                    TestUser.phoneCheck = true
                    dialog.dismiss()
                })

                dialog.show()

            }

        }


        return view1
    }

    inner class RecyclerRestAdapter(
        private val owner: Activity,
        private var commPostList: MutableList<PostEntity> /*, private val itemListener: (CommentEntity) -> Unit */
    ): RecyclerView.Adapter<RecyclerRestAdapter.PostHolder>(){

        inner class PostHolder(itemView: View/*, itemListener: (Post) -> Unit*/): RecyclerView.ViewHolder(itemView) {

            val category: TextView = itemView.findViewById(R.id.tv_badge)
            val categoryColor: RelativeLayout = itemView.findViewById(R.id.category_in_article_layout)
            val userName: TextView = itemView.findViewById(R.id.tv_nicname)
            val content: TextView = itemView.findViewById(R.id.user_name_txt)
            val datetime: TextView = itemView.findViewById(R.id.comm_post_date_created_tv)

            val likeCount: TextView = itemView.findViewById(R.id.article_favorite_num_btn)
            val commentCount: TextView = itemView.findViewById(R.id.article_comment_num_btn)

            // listener DX
            fun bindListener(item: CommentEntity) {
                itemView.setOnClickListener { /*itemListener(item)*/ }
            }
        }

        //항목 구성을 위해 사용할 viewholder 객체가 필요할때 호출되는 메서드
        @SuppressLint("ResourceType")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
            val itemView = layoutInflater.inflate(R.layout.layout_recycler_item,null)
            return PostHolder(itemView)
        }

        fun getCategoryNameInArticle(category: String):String{
            val cateInArt :String = "#$category"
            return cateInArt
        }

        fun reloadDataWithRetrofitResponse(newPostList: MutableList<PostEntity>) {
            commPostList = newPostList
            Log.e("[Adapter]", "dataset to be changed!")
            notifyDataSetChanged()
        }

        //데이터 셋팅
        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: PostHolder, position: Int) {
            val postEntity = commPostList[position]
            lateinit var drawable : GradientDrawable

            with (holder) {
                when (postEntity.category) {
                    "TIP" -> {
                        category.text = getCategoryNameInArticle(getString(R.string.comm_honey_tip))
                        category.setTextColor(owner.resources.getColor(R.color.style1_5, null))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_5, null))
                    }
                    "STOCK" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_stocks_overseas))
                        category.setTextColor(owner.resources.getColor(R.color.style1_4, null))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_4, null))
                    }
                    "STUDY" -> {
                        category.text = getCategoryNameInArticle(getString(R.string.comm_study_hard))
                        category.setTextColor(owner.resources.getColor(R.color.style1_6, null))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_6, null))
                    }
                    "SPORTS" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_sports_overseas))
                        category.setTextColor(owner.resources.getColor(R.color.style1_3, null))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_3, null))
                    }
                    "FOOD" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_latenight_food))
                        category.setTextColor(owner.resources.getColor(R.color.style1_2, null))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_2, null))
                    }
                    "GAME" -> {
                        category.text =getCategoryNameInArticle(getString(R.string.comm_games))
                        category.setTextColor(owner.resources.getColor(R.color.style1_7, null))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_7, null))
                    }
                    else -> category.text =getCategoryNameInArticle(getString(R.string.comm_theme_not_found))
                }
                userName.text = postEntity.user.userName
                datetime.text = postEntity.createdAt
                content.text = postEntity.contents

                likeCount.text = postEntity.like.size.toString()
                commentCount.text = postEntity.comments.size.toString()
            }

            holder.itemView.setOnClickListener {
                Log.e("[CommFrag_itemview]", "clicked post id: ${postEntity.id}")
                val intent = Intent(owner, ArticleActivity::class.java).apply {
                    val selectedPost = Gson().toJson(postEntity)
                    Log.e("[postSelected]", selectedPost.toString())
                    putExtra(getString(R.string.dummy_post_id), selectedPost)
                    putExtra("selectedPost", Gson().toJson(postEntity))
                }
                owner.startActivity(intent)
            }
        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount(): Int {
            return commPostList.size
        }


        inner class ViewHolderClass(itemView:View) : RecyclerView.ViewHolder(itemView){
            //항목View 내부의 View 상속
            val rowTextView: TextView = itemView.findViewById(R.id.tv_nicname)
        }

    }

    override fun onResume() {
        super.onResume()

        Log.e("[HI]", "comm Fragment's onResume :3")

        /*val myShared = owner.getSharedPreferences(
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

        recyclerView.adapter?.notifyDataSetChanged()
        Log.e("[CommFrag]", "dummy list size: ${dummyCommunityPostsList.size}...")*/
    }

    // ===========================================================================
    //              RETROFIT NETWORKING
    // ===========================================================================
    private fun getPosts(cursorId: Int?) {
        Log.e("[getPost]", "-.-cursorid: $cursorId")
        // no progressbar!!

        val call: Call<PostModel> = OwlRetrofitManager.OwlRestService.owlRestService.getPosts(cursorId, token)
        // log?
        Log.e("[retrofitCall]", call.request().toString())
        call.enqueue(object: Callback<PostModel> {
            override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                if (response.isSuccessful) {
                    postModel = response.body() as PostModel
                    Log.e("[getPostSuccess]", response.body().toString())
                    mCursorId = postModel.posts.last().id
                    Log.e("[new_cursorId]", mCursorId.toString())
                    if (postModel.posts.size == 0)
                        return
                    owner.runOnUiThread {
                        mAdapter.reloadDataWithRetrofitResponse(postModel.posts)
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
}