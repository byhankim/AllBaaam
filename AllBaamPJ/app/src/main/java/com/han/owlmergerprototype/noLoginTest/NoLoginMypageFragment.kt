package com.han.owlmergerprototype.noLoginTest

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.setting.SettingActivity
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.mypage.boardActivity.NoticeActivity
import com.han.owlmergerprototype.mypage.boardActivity.PolicyActivity
import com.han.owlmergerprototype.mypage.boardActivity.SuggestionActivity

class NoLoginMypageFragment : Fragment() {
    private lateinit var my_contents_btn:Button
    private lateinit var mysavedBTN:Button
    private lateinit var mypageLV:ListView
    private lateinit var inte:Intent
    private lateinit var nameTV:TextView
    private lateinit var toolbar: Toolbar

    companion object{
        const val TAG : String = "looooog"

        fun newInstance() : NoLoginMypageFragment {
            return NoLoginMypageFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"MypageFragment - onCreate() called")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"MypageFragment - onAttach() called")
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"MypageFragment - onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_mypage,container,false)
        my_contents_btn = view.findViewById(R.id.my_contents_btn)
        mypageLV = view.findViewById(R.id.mypage_listView)
        nameTV = view.findViewById(R.id.user_name_tv)
        nameTV.text = TestUser.userName
        val array = getResources().getStringArray(R.array.mypage_board)

        val adap1 = ArrayAdapter(context!!,R.layout.list_textview_layout,R.id.mypage_list_row,array)
        mypageLV.adapter=adap1

        my_contents_btn.setOnClickListener {
            setDialog()
        }

        toolbar = view.findViewById(R.id.mypage_toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        toolbar.setNavigationOnClickListener {
            val fragmentManager = getActivity()!!.getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(this).commit();
            fragmentManager.popBackStack()
        }

        toolbar.inflateMenu(R.menu.mypage_menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.setting_btn -> {
                    inte = Intent(context, SettingActivity::class.java)
                    startActivity(inte)
                    true
                }else->{
                true
            }

            }
        }



        mysavedBTN=view.findViewById(R.id.my_saved_btn)
        mysavedBTN.setOnClickListener {
            setDialog()
        }




        mypageLV.setOnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String

            when(position){
                0 ->{
                    inte = Intent(context, NoticeActivity::class.java)
                    startActivity(inte)
                }
                1 ->{
                    inte = Intent(context, SuggestionActivity::class.java)
                    startActivity(inte)
                }
                2 ->{
                    inte = Intent(context, PolicyActivity::class.java)
                    startActivity(inte)
                }
            }

        }

        return view
    }

    fun setDialog(){
        val dialog = Dialog(context!!)
        dialog.getWindow()!!.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        dialog.setContentView(R.layout.dialog_login)
        val cancelBTN:TextView = dialog.findViewById<TextView>(R.id.login_dialog_cancel_btn)
        cancelBTN.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        val kakaoLoginBTN:TextView = dialog.findViewById<TextView>(R.id.kakao_login_btn)
        kakaoLoginBTN.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            TestUser.userName ="?????? ?????? ?????????"
            TestUser.userID = 1
            inte = Intent(context, BottomNavActivity::class.java)
            startActivity(inte)
            val fragmentManager = getActivity()!!.getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(this).commit();
            fragmentManager.popBackStack()
            activity!!.finish()

        })
        dialog.show()
    }
}