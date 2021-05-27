package com.han.owlmergerprototype

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.han.owlmergerprototype.community.CommFragment
import com.han.owlmergerprototype.map.MapsMainActivity
import com.han.owlmergerprototype.map.MapsMainFragment
import com.han.owlmergerprototype.map.MapsMainNoLoginFragment
import com.han.owlmergerprototype.mypage.MypageFragment

class BottomNavActivity : AppCompatActivity() {

    private lateinit var mapsMainFragment: MapsMainFragment
    private lateinit var mapFragment: MapFragment
    private lateinit var alarmFragment: AlarmFragment
    private lateinit var mypageFragment: MypageFragment
    private lateinit var commFragment: CommFragment
    private lateinit var nav:BottomNavigationView
    private lateinit var switch:SwitchCompat
    private lateinit var fragmentManager : FragmentManager
    private lateinit var inte: Intent
    private lateinit var fakeTrack:TextView


    companion object{
        const val TAG:String = "BottomNavActivity"
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.bottom_nav_layout)
        nav = findViewById(R.id.bottom_nav)
        switch = findViewById(R.id.bottom_switch)
        fakeTrack = findViewById(R.id.fake_track)
       

        nav.menu.getItem(0).isCheckable = false


        Log.d(TAG, "BottomActivity - onCreate() called")

        //nav.setOnNavigationItemSelectedListener(this)
        nav.setOnNavigationItemSelectedListener(onBottomNavigationSelectedListener)
        fragmentManager = supportFragmentManager

        commFragment = CommFragment.newInstance(this)
//        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, commFragment).commit()

        mapsMainFragment = MapsMainFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, mapsMainFragment).commit()



        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            Log.d(TAG, "onCreate: ${fragmentManager.backStackEntryCount}")

            if(fragmentManager.backStackEntryCount !=0) {
                fragmentManager.popBackStack()
            }

            // MAP
            if (isChecked) {
                Log.d(TAG, "BottomNavActivity - onCreate() MAP isChecked called")


                commFragment = CommFragment.newInstance(this)
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, commFragment).commit()
                fakeTrack.background = getDrawable(R.drawable.comm_toggle_backgroud)


//
//                val intent = Intent(this@BottomNavActivity, MapsMainActivity::class.java)
//                startActivity(intent)

//                    mapFragment = MapFragment.newInstance()
//                    supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, mapFragment).commit()

            } else {
               Log.d(TAG, "BottomNavActivity - onCreate() MAP !isChecked! called")


                mapsMainFragment = MapsMainFragment.newInstance()
                
                fakeTrack.background = getDrawable(R.drawable.map_toggle_backgroud)
                fragmentManager.beginTransaction()
                    .add(R.id.fragments_frame,mapsMainFragment)
                    .addToBackStack(null)
                    .commit()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private val onBottomNavigationSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener{

        fragmentManager = supportFragmentManager
       

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
                mypageFragment = MypageFragment.newInstance()
                if(fragmentManager.backStackEntryCount !=0){
                    fragmentManager.popBackStack()
                }
                supportFragmentManager.beginTransaction()
                        .add(R.id.fragments_frame,mypageFragment)
                        .addToBackStack(null)
                        .commit()
            }
        }
        true
    }
}