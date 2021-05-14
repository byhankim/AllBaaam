package com.han.owlmergerprototype.noLoginTest

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
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
import com.han.owlmergerprototype.community.CreateArticleActivity
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.data.ThemeEntity
import com.han.owlmergerprototype.utils.SpaceDecoration
import org.w3c.dom.Text

@Suppress("DEPRECATION")
class NoLoginCommFragment(var owner: Activity): Fragment() {
    private lateinit var floatBTN: FloatingActionButton
    private lateinit var inte: Intent
    private lateinit var themeSelectorRv: RecyclerView


    private lateinit var recyclerView: RecyclerView
    val nickname = arrayListOf(
        "배고픈 수현이","야근하는 다미","피곤한 한울이","고민하는 현진이","화가난 성현이"
    )
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

            adapter = RecyclerAdapter(owner, dummyCommunityPostsList)
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
                    com.han.owlmergerprototype.R.drawable.owl2,
                    com.han.owlmergerprototype.R.color.style1_5,
                    com.han.owlmergerprototype.R.color.black,
                    1,
                    false
                )
            )
            testList.add(
                com.han.owlmergerprototype.data.ThemeEntity(
                    getString(com.han.owlmergerprototype.R.string.comm_stocks_overseas),
                    com.han.owlmergerprototype.R.drawable.like_btn,
                    com.han.owlmergerprototype.R.color.style1_4,
                    com.han.owlmergerprototype.R.color.black,
                    2,
                    false
                )
            )
            testList.add(
                com.han.owlmergerprototype.data.ThemeEntity(
                    getString(com.han.owlmergerprototype.R.string.comm_sports_overseas),
                    com.han.owlmergerprototype.R.drawable.owl2,
                    com.han.owlmergerprototype.R.color.style1_3,
                    com.han.owlmergerprototype.R.color.black,
                    3,
                    false
                )
            )
            testList.add(
                com.han.owlmergerprototype.data.ThemeEntity(
                    getString(com.han.owlmergerprototype.R.string.comm_latenight_food),
                    com.han.owlmergerprototype.R.drawable.back_icon_24,
                    com.han.owlmergerprototype.R.color.style1_2,
                    com.han.owlmergerprototype.R.color.black,
                    4,
                    false
                )
            )
            testList.add(
                com.han.owlmergerprototype.data.ThemeEntity(
                    getString(com.han.owlmergerprototype.R.string.comm_study_hard),
                    com.han.owlmergerprototype.R.drawable.owl2,
                    com.han.owlmergerprototype.R.color.style1_1,
                    com.han.owlmergerprototype.R.color.black,
                    5,
                    false
                )
            )
            adapter = com.han.owlmergerprototype.community.ThemeSelectorRecyclerAdapter(
                testList,
                owner
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

        //데이터 셋팅
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
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

                    })
                    dialog.show()




                }
            }

        }

        //리사이클러뷰의 항목갯수 반환
        override fun getItemCount(): Int {
            return 4
        }


        inner class ViewHolderClass(itemView:View) : RecyclerView.ViewHolder(itemView){
            //항목View 내부의 View 상속
            val category: TextView = itemView.findViewById(R.id.tv_badge)
            val userName: TextView = itemView.findViewById(R.id.tv_nicname)
            val content: TextView = itemView.findViewById(R.id.user_name_txt)
            val datetime: TextView = itemView.findViewById(R.id.textView)

            val lastItemBlur: RealtimeBlurView = itemView.findViewById(R.id.article_blur)
            val loginView:RelativeLayout = itemView.findViewById(R.id.login_view)
            val loginBTN:Button = itemView.findViewById(R.id.comm_login_btn)
        }



    }
}