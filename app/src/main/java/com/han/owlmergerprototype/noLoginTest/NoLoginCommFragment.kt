package com.han.owlmergerprototype.noLoginTest

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mmin18.widget.RealtimeBlurView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.CreateArticleActivity
import com.han.owlmergerprototype.utils.SpaceDecoration
import org.w3c.dom.Text

@Suppress("DEPRECATION")
class NoLoginCommFragment: Fragment() {
    private lateinit var floatBTN: FloatingActionButton
    private lateinit var inte: Intent


    private lateinit var recyclerView: RecyclerView
    val nickname = arrayListOf(
        "배고픈 수현이","야근하는 다미","피곤한 한울이","고민하는 현진이","화가난 성현이"
    )
    companion object{
        const val TAG : String = "looooog"

        fun newInstance() : NoLoginCommFragment {
            return NoLoginCommFragment()
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
        val adap1 = RecyclerAdapter()
        recyclerView = view1.findViewById(R.id.article_rv)
        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
        val deco = SpaceDecoration(size)
        floatBTN = view1.findViewById(R.id.fab)
        floatBTN.isVisible = false

        floatBTN.setOnClickListener {
            inte = Intent(context, CreateArticleActivity::class.java)
            startActivity(inte)
        }


        recyclerView.addItemDecoration(deco)

        recyclerView.adapter = adap1
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view1
    }

    inner class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>(){
        lateinit var cancelBTN:Button
        //항목 구성을 위해 사용할 viewholder 객체가 필요할때 호출되는 메서드
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView = layoutInflater.inflate(R.layout.layout_recycler_item,null)
            val holder = ViewHolderClass(itemView)
            return holder
        }

        //데이터 셋팅
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

                holder.rowTextView.text = nickname[position]
            if(position==3){
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
            val rowTextView: TextView = itemView.findViewById(R.id.tv_nicname)
            val lastItemBlur: RealtimeBlurView = itemView.findViewById(R.id.article_blur)
            val loginView:RelativeLayout = itemView.findViewById(R.id.login_view)
            val loginBTN:Button = itemView.findViewById(R.id.comm_login_btn)
        }



    }
}