package com.han.owlmergerprototype.noLoginTest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.han.owlmergerprototype.AlarmFragment
import com.han.owlmergerprototype.community.CommFragment
import com.han.owlmergerprototype.MapFragment
import com.han.owlmergerprototype.R

class NoLoginBottonNavActivity : AppCompatActivity() {

    private lateinit var mapFragment: MapFragment
    private lateinit var alarmFragment: AlarmFragment
    private lateinit var nologinMypageFragment:NoLoginMypageFragment
    private lateinit var commFragment: CommFragment
    private lateinit var noLoginCommFragment: NoLoginCommFragment
    private lateinit var nav:BottomNavigationView
    private lateinit var switch:SwitchCompat
    private lateinit var fragmentManager : FragmentManager


    companion object{
        const val TAG:String = "로그"
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_nav_layout)
        nav = findViewById(R.id.bottom_nav)
        switch = findViewById(R.id.bottom_switch)
        fragmentManager = supportFragmentManager
       

        nav.menu.getItem(0).isCheckable = false


        Log.d(TAG, "BottomActivity - onCreate() called")

        //nav.setOnNavigationItemSelectedListener(this)
        nav.setOnNavigationItemSelectedListener(onBottomNavigationSelectedListener)

        noLoginCommFragment = NoLoginCommFragment.newInstance(this)
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, noLoginCommFragment).commit()



        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if(fragmentManager.backStackEntryCount !=0) {
                Log.d(TAG, "BottomActivity - run if")
                fragmentManager.popBackStack()
            }


                if (isChecked) {
                    Log.d(TAG, "BottomActivity - switch map")

                    mapFragment = MapFragment.newInstance()
                   
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, mapFragment).commit()
                } else {

                    noLoginCommFragment = NoLoginCommFragment.newInstance(this)
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, noLoginCommFragment).commit()

                }
            

        }




    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private val onBottomNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{


       


        when(it.itemId){
            R.id.alarm_btn -> {
                Log.d(TAG, "BottomActivity - 알람 클릭")
                nav.menu.getItem(0).isCheckable = true
                alarmFragment = AlarmFragment.newInstance()

                if(fragmentManager.backStackEntryCount !=0){
                    fragmentManager.popBackStack()
                }
                fragmentManager.beginTransaction()
                        .add(R.id.fragments_frame,alarmFragment)
                        .addToBackStack(null)
                        .commit()



            }

            R.id.mypage_btn -> {
                Log.d(TAG, "BottomActivity - 마페 클릭")
                nologinMypageFragment = NoLoginMypageFragment.newInstance()
                if(fragmentManager.backStackEntryCount !=0){
                    fragmentManager.popBackStack()
                }
                supportFragmentManager.beginTransaction()
                        .add(R.id.fragments_frame,nologinMypageFragment)
                        .addToBackStack(null)
                        .commit()



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