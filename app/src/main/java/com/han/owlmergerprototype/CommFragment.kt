package com.han.owlmergerprototype

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.community.CreateArticleActivity
import com.han.owlmergerprototype.data.CommentEntity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.data.ThemeEntity
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
        const val TAG : String = "looooog"

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
                    com.han.owlmergerprototype.R.color.style1_2_20,
                    com.han.owlmergerprototype.R.color.style2_2,
                    com.han.owlmergerprototype.R.color.style1_2,
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
                owner
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
                val authBTN:TextView = dialog.findViewById<TextView>(R.id.auth_Button)
                authBTN.setOnClickListener(View.OnClickListener {
                    TestUser.phoneCheck = true
                    dialog.dismiss()
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
            val userName: TextView = itemView.findViewById(R.id.tv_nicname)
            val content: TextView = itemView.findViewById(R.id.user_name_txt)
            val datetime: TextView = itemView.findViewById(R.id.textView)

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

        //데이터 셋팅
        override fun onBindViewHolder(holder: PostHolder, position: Int) {
            val postEntity = dummyPostsList[position]

            with (holder) {
                category.text = when (postEntity.category) {
                    1 -> getString(R.string.comm_honey_tip)
                    2 -> getString(R.string.comm_stocks_overseas)
                    3 -> getString(R.string.comm_study_hard)
                    4 -> getString(R.string.comm_sports_overseas)
                    5 -> getString(R.string.comm_latenight_food)
                    6 -> getString(R.string.comm_games)
                    else -> getString(R.string.comm_theme_not_found)
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
        // post just written?
        /*val returnVal = owner.intent.getIntExtra("postWritten", -100)
        Log.e("[ONRESUME]", "returnVal: $returnVal")
        if (returnVal != -1) {
            Toast.makeText(owner, returnVal.toString(),Toast.LENGTH_SHORT).show()

            val sharedPrefName = "owltest"
            val myShared = owner.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
            val spContent = myShared.getStringSet("postStringSet", setOf())

            Toast.makeText(owner, spContent.toString(), Toast.LENGTH_SHORT).show()

            // 가져왔으면 shared preferences 보여주기
        }*/

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