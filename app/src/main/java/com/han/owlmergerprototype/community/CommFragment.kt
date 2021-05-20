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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.data.ThemeEntity
import com.han.owlmergerprototype.map.MapsMainActivity
import com.han.owlmergerprototype.mypage.boardActivity.NoticeActivity
import com.han.owlmergerprototype.utils.SpaceDecoration

class CommFragment(var owner: Activity): Fragment() {
    private lateinit var floatBTN: FloatingActionButton
    private lateinit var inte: Intent

    private lateinit var recyclerView: RecyclerView
    private lateinit var themeSelectorRv: RecyclerView

    // dummy post dataset
    private lateinit var dummyCommPostDatasets: MutableList<Post>



    companion object{
        const val TAG : String = "로그"

        fun newInstance(owner: Activity) : CommFragment {
            return CommFragment(owner)
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

        // toolbar -> NONE!!!
        // (owner as AppCompatActivity).setSupportActionBar(toolbar)


        // ---------------------------------------------------------------------
        // Community Posts RV
        // ---------------------------------------------------------------------
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

            adapter = RecyclerAdapter(owner, dummyCommunityPostsList)
        }
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        recyclerView.addItemDecoration(deco)


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
                val agreeAllCV:CheckBox = dialog.findViewById(R.id.agree_all_policy_cb)
                val agree1CB:CheckBox = dialog.findViewById(R.id.agree_for_service_cb)
                val agree2CB:CheckBox = dialog.findViewById(R.id.agree_for_gps_info_cb)
                val agree3CB:CheckBox = dialog.findViewById(R.id.agree_for_marketing_cb)

                var flag:Int = 0

                agreeAllCV.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked){
                        agree1CB.isChecked=true
                        agree2CB.isChecked=true
                        agree3CB.isChecked=true

                    }else{
//                        if(agree1CB.isChecked||agree2CB.isChecked||agree3CB.isChecked){
//
//                        }else{
//                            agree1CB.isChecked=false
//                            agree2CB.isChecked=false
//                            agree3CB.isChecked=false
//                        }
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
                                    TestUser.phoneCheck = true
                                    dialog.dismiss()
                                } else {
                                    Toast.makeText(dialog.context, "약관동의를 확인해주세요", Toast.LENGTH_SHORT).show()
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

    inner class RecyclerAdapter(
        private val owner: Activity,
        private val dummyPostsList: MutableList<Post> /*, private val itemListener: (CommentEntity) -> Unit */
    ): RecyclerView.Adapter<RecyclerAdapter.PostHolder>(){

        inner class PostHolder(itemView: View/*, itemListener: (Post) -> Unit*/): RecyclerView.ViewHolder(itemView) {

            val category: TextView = itemView.findViewById(R.id.tv_badge)
            val categoryColor: RelativeLayout = itemView.findViewById(R.id.category_in_article_layout)
            val userName: TextView = itemView.findViewById(R.id.tv_nicname)
            val content: TextView = itemView.findViewById(R.id.user_name_txt)
            val datetime: TextView = itemView.findViewById(R.id.comm_post_date_created_tv)

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
            val cateInArt :String = "#"+category
            return cateInArt
        }

        //데이터 셋팅
        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: PostHolder, position: Int) {
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
                        category.text = getCategoryNameInArticle(getString(R.string.comm_study_hard))
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
                        category.setTextColor(owner.resources.getColor(R.color.style1_2))
                        drawable = categoryColor.background as GradientDrawable
                        drawable.setStroke(2,owner.resources.getColor(R.color.style1_2))
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

            holder.itemView.setOnClickListener {
                Log.e("[CommFrag_itemview]", "clicked post id: ${postEntity.id}")
                val intent = Intent(owner, ArticleActivity::class.java).apply {
                    putExtra(getString(R.string.dummy_post_id), postEntity.id)
                }
                owner.startActivity(intent)
            }
        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount(): Int {
            return dummyPostsList.size
        }


        inner class ViewHolderClass(itemView:View) : RecyclerView.ViewHolder(itemView){
            //항목View 내부의 View 상속
            val rowTextView: TextView = itemView.findViewById(R.id.tv_nicname)
        }



    }

    override fun onResume() {
        super.onResume()

        Log.e("[HI]", "comm Fragment's onResume :3")

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

        recyclerView.adapter?.notifyDataSetChanged()
        Log.e("[CommFrag]", "dummy list size: ${dummyCommunityPostsList.size}...")
    }
}