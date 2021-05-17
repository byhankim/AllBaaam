package com.han.owlmergerprototype.mypage

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.han.owlmergerprototype.Mypg004Activity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.SettingActivity
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.mypage.boardActivity.NoticeActivity
import com.han.owlmergerprototype.mypage.boardActivity.PolicyActivity
import com.han.owlmergerprototype.mypage.boardActivity.SuggestionActivity


class MypageFragment : Fragment() {
    private lateinit var my_contents_btn:Button
    private lateinit var mysavedBTN:Button
    private lateinit var mypageLV:ListView
    private lateinit var inte:Intent
    private lateinit var nameTV:TextView
    private lateinit var toolbar: Toolbar
    companion object{
        const val TAG : String = "looooog"

        fun newInstance() : MypageFragment {
            return MypageFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MypageFragment - onCreate() called")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "MypageFragment - onAttach() called")
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "MypageFragment - onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)
        my_contents_btn = view.findViewById(R.id.my_contents_btn)
        mypageLV = view.findViewById(R.id.mypage_listView)
        nameTV = view.findViewById(R.id.user_name_tv)
        nameTV.text = TestUser.userName
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
        val array = getResources().getStringArray(R.array.mypage_board)

        val adap1 = ArrayAdapter(
            context!!,
            R.layout.list_textview_layout,
            R.id.mypage_list_row,
            array
        )
        mypageLV.adapter=adap1

        my_contents_btn.setOnClickListener {
            inte = Intent(context, MyContentsActivity::class.java)
            startActivity(inte)
        }



        mysavedBTN=view.findViewById(R.id.my_saved_btn)
        mysavedBTN.setOnClickListener {
            inte = Intent(context, Mypg004Activity::class.java)
            startActivity(inte)
        }

//        backBTN = view.findViewById(R.id.)
//        backBTN.setOnClickListener {
//
//            val fragmentManager = getActivity()!!.getSupportFragmentManager();
//            fragmentManager.beginTransaction().remove(this).commit();
//            fragmentManager.popBackStack()
//
//        }



        mypageLV.setOnItemClickListener { parent, view, position, id ->
            // Get the selected item text from ListView
            val selectedItem = parent.getItemAtPosition(position) as String

            when(position){
                0 -> {
                    inte = Intent(context, NoticeActivity::class.java)
                    startActivity(inte)
                }
                1 -> {
                    inte = Intent(context, SuggestionActivity::class.java)
                    startActivity(inte)
                }
                2 -> {
                    inte = Intent(context, PolicyActivity::class.java)
                    startActivity(inte)
                }
            }

        }

        return view
    }






}