package com.han.owlmergerprototype.community

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.core.view.isVisible
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.Constants
import com.han.owlmergerprototype.common.RetrofitRESTService
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.data.ThemeEntity
import com.han.owlmergerprototype.rest.Ok
import com.han.owlmergerprototype.common.token
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.map.MapsMainActivity
import com.han.owlmergerprototype.mypage.boardActivity.NoticeActivity
import com.han.owlmergerprototype.noLoginTest.NoLoginBottomNavActivity
import com.han.owlmergerprototype.retrofit.OwlRetrofitManager
import com.han.owlmergerprototype.utils.SpaceDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommFragment: Fragment() {//인자 넣으면 default생성자 제공안해줌
private lateinit var floatBTN: FloatingActionButton
    private lateinit var inte: Intent

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerRestAdapter
    private lateinit var themeSelectorRv: RecyclerView
    private lateinit var tAdapter: ThemeSelectorRecyclerAdapter
    private var selectedCategoryPos: Int = -1

    // dummy post dataset
    private lateinit var dummyCommPostDatasets: MutableList<Post>
    private lateinit var autoLogin : SharedPreferences
//    private lateinit var dummyCommPostDatasets: MutableList<Post>

    // rest post dataset
    private lateinit var postModel: PostModel
    private lateinit var postList: MutableList<PostEntity>

    // category selection
    private var mCatetoryId: Int? = null
    private var mCursorId: Int? = null


    // sort by


    companion object{
        const val TAG : String = "로그"

        fun newInstance(owner: Activity) : CommFragment {
            return CommFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"CommFragment - onCreate() called")

//        Log.e("[oncreate]", postModel.toString())

        // kotlin.UninitializedPropertyAccessException: lateinit blah blah
        postModel = PostModel("FAIL", mutableListOf())
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
        val myShared = activity?.getSharedPreferences(
            getString(R.string.owl_shared_preferences_name),
            Context.MODE_PRIVATE
        )


        recyclerView = view1.findViewById(R.id.article_rv)

//        val manager: LinearLayoutManager = LinearLayoutManager(owner, LinearLayoutManager.VERTICAL, false)

        try {
            getPosts(mCursorId)
            Log.e("[calledGetPosts]", "1")
//            mCursorId = postModel.posts.last().id
            Log.e("[initcursorid]", "2")
        } catch(e: Exception) {
            Log.e("[getPostsException]", e.toString())
        } finally {
            if (postModel.posts.isNullOrEmpty() ) {
                postList = mutableListOf()
            }
        }

        recyclerView = view1.findViewById(R.id.article_rv)
        mAdapter = RecyclerRestAdapter(activity!!, postModel.posts)

        with (recyclerView) {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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

                            /*
                            * Handler().postDelayed({
//            startActivity(Intent(this, CommunityMainActivity::class.java))
            startActivity(Intent(this, NoLoginBottomNavActivity::class.java))
            finish()
        }, timeoutCount)
                            * */
                            Handler().postDelayed({
                                Toast.makeText(activity, "스크롤 끝에 도달했습니다", Toast.LENGTH_SHORT).show()

                                getPosts(mCursorId)
                            }, 250)

                            //
                            // 1. postdelayed
//                            Handler().postDelayed({
//                                getPosts(mCursorId)
//                            }, 300)

                            // 2. 1px up


                            // 3. asynch -> boolean block -> release
                        }
                    }
                }
            }
        }


        // ---------------------------------------------------------------------
        //  인기순 받아오기
        // ---------------------------------------------------------------------
        val popularSortBtn = view1.findViewById<TextView>(R.id.comment_sort_by_popularity_btn)//comment_sort_by_popularity_btn
        with (popularSortBtn) {
            setOnClickListener {

            }
        }



        // ---------------------------------------------------------------------
        // theme selector rv
        // ---------------------------------------------------------------------
        themeSelectorRv = view1.findViewById(R.id.comm_theme_selector_recyclerview)

        val manager = LinearLayoutManager(activity as Activity, LinearLayoutManager.HORIZONTAL, false)

        tAdapter = ThemeSelectorRecyclerAdapter(Constants().getCategoryList(), activity as Activity, true)
        with (themeSelectorRv) {
            layoutManager = manager
            androidx.recyclerview.widget.DividerItemDecoration(
                context,LinearLayoutManager.HORIZONTAL)

            adapter = tAdapter
            selectedCategoryPos = tAdapter.pos
            setOnClickListener {
                Toast.makeText(activity, "selectedPos: $tAdapter.pos", Toast.LENGTH_SHORT).show()
            }
        }

        // FAB
        floatBTN = view1.findViewById(R.id.fab)

        floatBTN.setOnClickListener {
            if(TestUser.verify==true){
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
                val agreeAllCV:CheckBox = dialog.findViewById(R.id.agree_all_policy_cb)
                val agree1CB:CheckBox = dialog.findViewById(R.id.agree_for_service_cb)
                val agree2CB:CheckBox = dialog.findViewById(R.id.agree_for_gps_info_cb)
                val agree3CB:CheckBox = dialog.findViewById(R.id.agree_for_marketing_cb)
                val retrofit = Retrofit.Builder()
                    .baseUrl(ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val loginService = retrofit.create(RetrofitRESTService::class.java)



                agreeAllCV.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked){
                        agree1CB.isChecked=true
                        agree2CB.isChecked=true
                        agree3CB.isChecked=true

                    }else{
                        if(agree1CB.isChecked&&agree2CB.isChecked&&agree3CB.isChecked){
                            agree1CB.isChecked=false
                            agree2CB.isChecked=false
                            agree3CB.isChecked=false
                        }

                    }
                }
                agree1CB.setOnCheckedChangeListener { buttonView, isChecked ->

                    if(agree2CB.isChecked&&agree3CB.isChecked) {
                        agreeAllCV.isChecked = isChecked
                    }
                }
                agree2CB.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(agree1CB.isChecked&&agree3CB.isChecked){
                        agreeAllCV.isChecked = isChecked
                    }
                }
                agree3CB.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(agree2CB.isChecked&&agree1CB.isChecked){
                        agreeAllCV.isChecked = isChecked
                    }
                }


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
                        if(phoneET.length()>=10){
                            Log.d(TAG,"${phoneET.length()}")

                            sendBTN.isClickable=true
                            sendBTN.setOnClickListener {
                                authLayout.isVisible = true
                                val phoneNum = phoneET.text.toString()
                                Log.d(TAG,"${phoneET.length()},phonenum = ${phoneNum.substring(1)}")

                                /*loginService.getVerifyCode(TestUser.token,phoneNum.substring(1)).enqueue(object : Callback<Ok> {
                                    override fun onFailure(call: Call<Ok>, t: Throwable) {
                                        val dialog = AlertDialog.Builder(dialog.context)
                                        dialog.setTitle("통신실패")
                                        dialog.setMessage("실패")
                                        dialog.show()
                                    }
                                    override fun onResponse(call: Call<Ok>, response: Response<Ok>) {
                                        val ok = response.body()
                                        if(ok!!.ok){
                                            Toast.makeText(dialog.context,"문자갑니둥", Toast.LENGTH_SHORT).show()

                                        }else{
                                            Toast.makeText(dialog.context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                                        }


//                                              val dialog = AlertDialog.Builder(this@LoginActivity)
//                                              dialog.setTitle("통신성공")
//                                              dialog.setMessage("ok: ${login?.ok.toString()} , token: ${login?.token}")
//                                              dialog.show()

                                    }


                                })*/

                            }










                        }
                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

                })



                authET.addTextChangedListener(object : TextWatcher {
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
                        if (authET.length() >= 4) {
                            authBTN.isClickable = true
                            comBtnLayout.isVisible = true
                            authBTN.setOnClickListener(View.OnClickListener {
                                if (agree2CB.isChecked && agree1CB.isChecked) {
                                    TestUser.verify = true
                                    val verifiedCode = authET.text.toString().toInt()
                                    Log.d(TAG, "onTextChanged: ${verifiedCode-1}")
                                    dialog.dismiss()

                                    /*loginService.verifyPhoneNumber(TestUser.token,verifiedCode).enqueue(object : Callback<Ok> {
                                        override fun onFailure(call: Call<Ok>, t: Throwable) {
                                            val dialog = AlertDialog.Builder(dialog.context)
                                            dialog.setTitle("통신실패")
                                            dialog.setMessage("실패")
                                            dialog.show()
                                        }
                                        override fun onResponse(call: Call<Ok>, response: Response<Ok>) {
                                            val ok = response.body()
                                            if(ok!!.ok){

                                                autoLogin = context!!.getSharedPreferences("autoLogin",Activity.MODE_PRIVATE)
                                                val editor = autoLogin.edit()
                                                editor.putBoolean("verified",true)
                                                editor.apply()
                                                TestUser.verify=true
                                                Toast.makeText(context,"인증완료", Toast.LENGTH_SHORT).show()
                                                dialog.dismiss()

                                            }else{
                                                Log.d(TAG, "onResponse: ${ok.error}")
                                                Toast.makeText(context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                                            }


//                                              val dialog = AlertDialog.Builder(this@LoginActivity)
//                                              dialog.setTitle("통신성공")
//                                              dialog.setMessage("ok: ${login?.ok.toString()} , token: ${login?.token}")
//                                              dialog.show()

                                        }


                                    })*/

                                } else {
                                    Toast.makeText(context, "약관동의를 확인해주세요", Toast.LENGTH_SHORT).show()
                                }

                            })
                        }

                    }

                    override fun afterTextChanged(s: Editable?) {

                    }

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
            val content: TextView = itemView.findViewById(R.id.content_tv)
            val datetime: TextView = itemView.findViewById(R.id.comm_post_date_created_tv)

            val likeCount:TextView = itemView.findViewById(R.id.article_favorite_num_btn)
            val commentCount: TextView = itemView.findViewById(R.id.article_comment_num_btn)
            val isLike:ToggleButton = itemView.findViewById(R.id.article_favorite_btn)
            val isBookmark:ToggleButton = itemView.findViewById(R.id.article_bookmark_btn)

            // listener DX
            fun bindListener(item: CommentEntity) {
                itemView.setOnClickListener { /*itemListener(item)*/ }
            }
            fun setText(txt:String){
                this.isLike.text = txt
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
//            Log.e("[Adapter]", "dataset to be changed!")
//            notifyDataSetChanged()
        }
        fun changeLikeTxt(holder:PostHolder,postId:Int){
            val call: Call<CountLike> = OwlRetrofitManager.OwlRestService.owlRestService.getLikeCount(postId)
            Log.e("[retrofitCall]", call.request().toString())
            call.enqueue(object: Callback<CountLike> {
                override fun onResponse(call: Call<CountLike>, response: Response<CountLike>) {
                    val count = response.body()!!
                    if (response.isSuccessful) {
                        holder.likeCount.text = count.countLike.toString()
                    }

                }
                override fun onFailure(call: Call<CountLike>, t: Throwable) {
                    Log.e("[getCommentsFailure]", "F A I L ${t.toString()}")
                }
            })

        }
        fun changeCommentTxt(postId:Int){
            val call: Call<CountComment> = OwlRetrofitManager.OwlRestService.owlRestService.getCommentCount(postId)
            Log.e("[retrofitCall]", call.request().toString())
            call.enqueue(object: Callback<CountComment> {
                override fun onResponse(call: Call<CountComment>, response: Response<CountComment>) {
                    val count = response.body()!!
                    if (response.isSuccessful) {

//                        binding.articleCommentCountTv.text = count.countComments.toString()

                    }
                }
                override fun onFailure(call: Call<CountComment>, t: Throwable) {
                    Log.e("[getCommentsFailure]", "F A I L ${t.toString()}")
                }
            })

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


                ////////////is-bookmark is-like

                val call: Call<IsBookmark> = OwlRetrofitManager.OwlRestService.owlRestService.getIsBookmark(TestUser.token,postEntity.id)

                Log.e("[retrofitCall]", call.request().toString())
                call.enqueue(object: Callback<IsBookmark> {
                    override fun onResponse(call: Call<IsBookmark>, response: Response<IsBookmark>) {

                        val isbm = response.body()
                        if (response.isSuccessful) {
                            isBookmark.isChecked = isbm!!.isBookmark
                        } else{
                            Log.d(TAG, "onResponse:")
                        }

                    }
                    override fun onFailure(call: Call<IsBookmark>, t: Throwable) {
                        Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
                    }
                })

                val call2: Call<IsLike> = OwlRetrofitManager.OwlRestService.owlRestService.getIsLike(TestUser.token,postEntity.id)
                Log.e("[retrofitCall]", call.request().toString())
                call2.enqueue(object: Callback<IsLike> {
                    override fun onResponse(call: Call<IsLike>, response: Response<IsLike>) {
                        val islk = response.body()

                        if (response.isSuccessful) {
                            isLike.isChecked = islk!!.isLike
                        } else{
                            Log.d(TAG, "onResponse: ")
                        }

                    }
                    override fun onFailure(call: Call<IsLike>, t: Throwable) {
                        Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
                    }
                })




            }

            holder.itemView.setOnClickListener {
                val intent = Intent(owner, ArticleActivity::class.java).apply {
                    val selectedPost = Gson().toJson(postEntity)
                    Log.e("[postSelected]", selectedPost.toString())
                    putExtra(getString(R.string.dummy_post_id), selectedPost)
                    putExtra("selectedPost", Gson().toJson(postEntity))
                }
                owner.startActivity(intent)
            }
            holder.isLike.setOnClickListener {
                val call: Call<IsLike> = OwlRetrofitManager.OwlRestService.owlRestService.postLike(TestUser.token,postEntity.id)

                Log.e("[retrofitCall]", call.request().toString())
                call.enqueue(object: Callback<IsLike> {
                    override fun onResponse(call: Call<IsLike>, response: Response<IsLike>) {
                        val isbm = response.body()
                        if (response.isSuccessful) {
                            changeLikeTxt(holder,postEntity.id)
                            Log.e(TAG,"[retrofitResult]: ${isbm?.isLike}")

                        } else{
                            Log.d(TAG, "onResponse:")
                        }

                    }
                    override fun onFailure(call: Call<IsLike>, t: Throwable) {
                        Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
                    }
                })
            }
            holder.isBookmark.setOnClickListener {
                val call: Call<IsBookmark> = OwlRetrofitManager.OwlRestService.owlRestService.postBookmark(TestUser.token,postEntity.id)

                Log.e("[retrofitCall]", call.request().toString())
                call.enqueue(object: Callback<IsBookmark> {
                    override fun onResponse(call: Call<IsBookmark>, response: Response<IsBookmark>) {
                        val isbm = response.body()
                        if (response.isSuccessful) {
                            Log.e(TAG,"[retrofitResult]: ${isbm?.isBookmark}")

                        } else{
                            Log.d(TAG, "onResponse:")
                        }

                    }
                    override fun onFailure(call: Call<IsBookmark>, t: Throwable) {
                        Log.e("[getPostsFailure]", "F A I L ${t.toString()}")
                    }
                })
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

//        val myShared = activity?.getSharedPreferences(
        /*val myShared = owner.getSharedPreferences(
            getString(R.string.owl_shared_preferences_name),
            Context.MODE_PRIVATE
        )

        val dummyCommPostsType = object: TypeToken<MutableList<Post>>() {}.type
        val dummyCommunityPostsList: MutableList<Post> =
            Gson().fromJson(myShared?.getString(
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
                    Log.e("[getPostResult]", postModel.posts.toString())

                    // empty거나 null 이거나 이전에 받아온 모델하고 last post id가 같으면 처리 안함
                    if (postModel.posts.isNullOrEmpty() || mCursorId == postModel.posts.last().id)
                        return
                    postList.addAll(postModel.posts)
                    mCursorId = postModel.posts.last().id
                    Log.e("[new_cursorId]", mCursorId.toString())

                    mAdapter.reloadDataWithRetrofitResponse(postList)
                    activity!!.runOnUiThread {
//                        recyclerView.adapter = mAdapter
                        mAdapter.notifyDataSetChanged() // 이거할때 runOnUiThread 써야됨!
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