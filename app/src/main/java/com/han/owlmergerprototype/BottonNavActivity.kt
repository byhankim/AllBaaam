package com.han.owlmergerprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.han.owlmergerprototype.CommFragment
import com.han.owlmergerprototype.MapFragment
import com.han.owlmergerprototype.mypage.MypageFragment

class BottonNavActivity : AppCompatActivity() {

    private lateinit var mapFragment: MapFragment
    private lateinit var alarmFragment: AlarmFragment
    private lateinit var mypageFragment: MypageFragment
    private lateinit var commFragment: CommFragment
    private lateinit var nav:BottomNavigationView
    private lateinit var switch:SwitchCompat


    companion object{
        const val TAG:String = "looooog"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav_layout)
        nav = findViewById(R.id.bottom_nav)
        switch = findViewById(R.id.bottom_switch)

        nav.menu.getItem(0).isCheckable = false


        Log.d(TAG, "BottomActivity - onCreate() called")

        //nav.setOnNavigationItemSelectedListener(this)
        nav.setOnNavigationItemSelectedListener(onBottomNavigationSelectedListener)

        commFragment = CommFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, commFragment).commit()



        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mapFragment = MapFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, mapFragment).commit()
            } else {
                commFragment = CommFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, commFragment).commit()

            }
        }




    }



    private val onBottomNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{

        when(it.itemId){
            R.id.alarm_btn -> {
                Log.d(TAG, "BottomActivity - 알람 클릭")
                nav.menu.getItem(0).isCheckable = true
                alarmFragment = AlarmFragment.newInstance()
                supportFragmentManager.beginTransaction().add(R.id.fragments_frame,alarmFragment).commit()


            }

            R.id.mypage_btn -> {
                Log.d(TAG, "BottomActivity - 마페 클릭")
                mypageFragment = MypageFragment.newInstance()
                supportFragmentManager.beginTransaction().add(R.id.fragments_frame,mypageFragment).commit()


            }
        }
        true
    }



//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        Log.d(TAG, "BottomActivity - onNavigationItemSelected() called")
//
//        when(item.itemId){
//            R.id.alarm_btn -> {
//                Log.d(TAG, "BottomActivity - 알람 클릭")
//            }
//
//            R.id.mypage_btn -> {
//                Log.d(TAG, "BottomActivity - 마페 클릭")
//            }
//        }
//
//        return true
//    }
}