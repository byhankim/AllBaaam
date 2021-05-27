package com.han.owlmergerprototype.noLoginTest

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.han.owlmergerprototype.AlarmFragment
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.community.CommFragment
import com.han.owlmergerprototype.MapFragment
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.map.MapsMainActivity
import com.han.owlmergerprototype.map.MapsMainFragment
import com.han.owlmergerprototype.map.MapsMainNoLoginFragment
import com.han.owlmergerprototype.mypage.MypageFragment
import com.kakao.sdk.common.KakaoSdk

class NoLoginBottomNavActivity : AppCompatActivity() {

    private lateinit var mapsMainNoLoginFragment: MapsMainNoLoginFragment
    private lateinit var mapsMainFragment: MapsMainFragment
    private lateinit var mapFragment: MapFragment
    private lateinit var commFragment: CommFragment
    private lateinit var noLoginCommFragment: NoLoginCommFragment
    private lateinit var nav:BottomNavigationView
    private lateinit var switch:SwitchCompat
    private lateinit var fragmentManager : FragmentManager
    private lateinit var noLoginFragment:NoLoginFragment
    private lateinit var autoLogin : SharedPreferences
    private lateinit var inte: Intent
    private lateinit var fakeTrack: TextView


    companion object{
        const val TAG:String = "로그"
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        autoLogin = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)

        val token = autoLogin.getString("token",null)
        val uname = autoLogin.getString("userName",null)
        val uid = autoLogin.getInt("userId",-1)
        val uverify = autoLogin.getBoolean("verified",false)
        Log.d(NoLoginCommFragment.TAG,"onCreate() called : ${token}")

        if (token!=null&&uname!=null){
            TestUser.token = token
            TestUser.userName = uname
            TestUser.userID = uid
            TestUser.verify = uverify

            Log.e("[noLogBottom1]", "")
            inte = Intent(this, BottomNavActivity::class.java)
            Log.e("[noLogBottom2]", "")
            startActivity(inte)
            Log.e("[noLogBottom3]", "")
            finish()
        }

        setContentView(R.layout.bottom_nav_layout)
        nav = findViewById(R.id.bottom_nav)
        switch = findViewById(R.id.bottom_switch)
        fragmentManager = supportFragmentManager
        fakeTrack = findViewById(R.id.fake_track) // uninitialized b4 usage


        nav.menu.getItem(0).isCheckable = false


        Log.d(TAG, "BottomActivity - onCreate() called")

        //nav.setOnNavigationItemSelectedListener(this)
        nav.setOnNavigationItemSelectedListener(onBottomNavigationSelectedListener)

        noLoginCommFragment = NoLoginCommFragment.newInstance(this)

//        noLoginCommFragment = NoLoginCommFragment.newInstance(this)
//        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, noLoginCommFragment).commit()

//        mapsMainFragment = MapsMainFragment.newInstance()
//        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, mapsMainFragment).commit()

        mapsMainNoLoginFragment = MapsMainNoLoginFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, mapsMainNoLoginFragment).commit()


        switch.setOnCheckedChangeListener { buttonView, isChecked ->

            if(fragmentManager.backStackEntryCount !=0) {
                Log.d(TAG, "BottomActivity - run if")
                fragmentManager.popBackStack()
            }

                // MAP
                if (isChecked) {
//                    Log.d(TAG, "BottomActivity - switch map")
                    Log.d(TAG, "NoLoginBottonNavActivity - onCreate() /isChecked /called")
//                    val intent = Intent(this, MapsMainActivity::class.java)
//                    startActivity(intent)

//                    mapFragment = MapFragment.newInstance()
//                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, mapFragment).commit()


                    //mapfragment 시도
//                    mapsMainFragment = MapsMainFragment.newInstance(this)
//                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, mapsMainFragment).commit()

                    noLoginCommFragment = NoLoginCommFragment.newInstance(this)
                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, noLoginCommFragment).commit()
                    fakeTrack.background = getDrawable(R.drawable.comm_toggle_backgroud)
                    nav.background= getDrawable(R.drawable.comm_bottom_nav_bg)
                } else {
                    Log.d(TAG, "NoLoginBottonNavActivity - onCreate() /else /called")
                    //mapfragment 시도 else : com
                    mapsMainNoLoginFragment = MapsMainNoLoginFragment.newInstance()
                    if(fragmentManager.backStackEntryCount !=0){
                        fragmentManager.popBackStack()
                    }
                    fragmentManager.beginTransaction()
                        .add(R.id.fragments_frame,mapsMainNoLoginFragment)
                        .addToBackStack(null)
                        .commit()

                    fakeTrack.background = getDrawable(R.drawable.map_toggle_backgroud)
                    nav.background = getDrawable(R.drawable.map_bottom_nav_bg)

                    // 원래 커뮤니티 바로노출
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
                noLoginFragment = NoLoginFragment.newInstance()

                if(fragmentManager.backStackEntryCount !=0){
                    fragmentManager.popBackStack()
                }
                fragmentManager.beginTransaction()
                        .add(R.id.fragments_frame,noLoginFragment)
                        .addToBackStack(null)
                        .commit()
            }

            R.id.mypage_btn -> {
                Log.d(TAG, "BottomActivity - 마페 클릭")
                noLoginFragment = NoLoginFragment.newInstance()
                if(fragmentManager.backStackEntryCount !=0){
                    fragmentManager.popBackStack()
                }
                supportFragmentManager.beginTransaction()
                        .add(R.id.fragments_frame,noLoginFragment)
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