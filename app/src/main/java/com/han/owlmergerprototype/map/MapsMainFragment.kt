package com.han.owlmergerprototype.map

import android.Manifest
import android.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.community.CreateArticleActivity
import com.han.owlmergerprototype.dataMapCmnt.Map
import com.han.owlmergerprototype.dataMapCmnt.MapCmnt
import com.han.owlmergerprototype.noLoginTest.NoLoginBottomNavActivity
import com.han.owlmergerprototype.noLoginTest.NoLoginFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MapsMainFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val TAG: String = "로그"
    val permission_list = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    var nowLongitude = ""
    var nowLatitude = ""

    private lateinit var mView: MapView
//    var noLoginBottomNavActivity = activity as NoLoginBottomNavActivity

    private lateinit var btm_nav:BottomNavigationView
    companion object{
        const val TAG : String = "로그 MapsMainFragment looooog"

        fun newInstance() : MapsMainFragment {
            return MapsMainFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"MapFragment - onCreate() called")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map_fragment) as SupportMapFragment
//        mapFragment.getMapAsync(this)


        requestPermissions(permission_list, 0)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"MapFragment - onAttach() called")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"MapFragment - onCreateView() called")

        // Inflate the layout for this fragment
        var rootView = inflater.inflate(R.layout.fragment_maps_main, container, false)
        mView = rootView.findViewById(R.id.mv_contactUs_gMap) as MapView
        mView.onCreate(savedInstanceState)
        mView.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
//        val seoul = LatLng(37.654601, 127.060530)
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul))
//        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f))
//
//        //마커 출력
//        val marker = MarkerOptions()
//            .position(seoul)
//            .title("Nowon")
//            .snippet("노원역입니다.")
//        mMap?.addMarker(marker)
        mMap = googleMap

        // 카메라의 위치 //== 기본값: 티아카데미 ==//
        val TAcademy = LatLng(37.54547677189177, 126.95253576863207)
        val cameraOption = CameraPosition.Builder()
            .target(TAcademy)
            .zoom(17f)
            .build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        mMap.moveCamera(camera)


        mMap.setOnMarkerClickListener { marker ->

            if (marker.title.isNullOrEmpty()) {
                Toast.makeText(activity as BottomNavActivity,
                    "커뮤니티 데이터가 없습니다. \n다른 올빼미 마커를 클릭해주세요",
                    Toast.LENGTH_LONG)
                    .show()
                // alertDialog로 넘기기


                return@setOnMarkerClickListener  true
            }
            Log.d(TAG, "MapsMainActivity - onMapReady() called / marker.title = ${marker.title}")
            Log.d(TAG, "MapsMainActivity - onMapReady() called / marker.tag = ${marker.tag}")

            val mapCmntObject = marker.tag as Map

            //mapCmntObject.
            val intent = Intent(activity as BottomNavActivity, ArticleActivity::class.java).apply {
                val selectedPost = Gson().toJson(mapCmntObject.post)
                Log.d(TAG, "MapsMainActivity - onMapReady() called / selectedPost = ${selectedPost}")
                putExtra("selectedPost", selectedPost)
                Log.d(TAG, "MapsMainActivity - onMapReady() called / selectedPost2222 = ${selectedPost}")
            }
//            this.startActivity(intent)

            // 새로고침 후 글쓰기 시작
            (activity as BottomNavActivity)?.let {
                                                        (activity as BottomNavActivity).finish()
                                                        (activity as BottomNavActivity).startActivity(Intent(activity as BottomNavActivity, BottomNavActivity::class.java))
                                                        (activity as BottomNavActivity).startActivity(intent)
                                                        }

            true
        }

        // 맵 터치 이벤트 구현 //
        mMap.setOnMapLongClickListener { point ->

            Log.d(TAG, "MapsMainActivity - onMapReady() called / setOnMapLongClickListener")
            val mOptions = MarkerOptions()
            // 마커 타이틀
            mOptions.title("마커 좌표")
            val latitude = point.latitude // 위도
            val longitude = point.longitude // 경도
            // 마커의 스니펫(간단한 텍스트) 설정
            mOptions.snippet("$latitude, $longitude")
            // LatLng: 위도 경도 쌍을 나타냄
            mOptions.position(LatLng(latitude, longitude))
                .draggable(true)

            // 팝업 호출
            var name: String? = null
            // 값이 비어있으면 ""을 넣는다
            // unwrapping
            //alertDialog(latitude, longitude)

            AlertDialog.Builder(activity as BottomNavActivity)
//                .setTitle(title)
//                .setMessage("$title 와 함께하는 빡코딩! :)")
                .setMessage("이 장소에 글을 등록하시겠습니까?")
                .setPositiveButton("확인") { dialog, id ->
                    Log.d(TAG, "MainActivity - 다이얼로그 확인 버튼 클릭했음")

                    // 마커(핀) 추가
                    mMap.addMarker(mOptions)

                    // 기존 Activity 새로고침
//                    finish()
//                    startActivity(intent)

                    // 글쓰기 Activity 연결
                    val intent = Intent(activity as BottomNavActivity, CreateArticleActivity::class.java)
                    intent.putExtra("latitude", "$latitude")
                    intent.putExtra("longitude", "$longitude")
                    Log.d(TAG, "MapsMainActivity - onMapReady() 위도: $latitude 경도: $longitude  called")

//                    startActivity(intent)
                    (activity as BottomNavActivity).finish()
                    (activity as BottomNavActivity).startActivity(Intent(activity as BottomNavActivity, BottomNavActivity::class.java))

                    (activity as BottomNavActivity).startActivity(intent)

                }
                .setNegativeButton("취소")  { dialog, id ->
                    Log.d(TAG, "MainActivity - 다이얼로그 취소 버튼 클릭했음")

                    // 새로고침 --> 불필요
//                    finish()
//                    startActivity(intent)

                }
                .show()

            // fragment 같이 띄우는 코드 작성
            // 1fragment 띄우고 2카메라 위치 이동
        }


        loadMapCmnt()

    }

    fun getDescriptorFromDrawable(drawableId: Int) : BitmapDescriptor {
        var bitMapDrawable: BitmapDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

//                bitMapDrawable = getDrawable(drawableId) as BitmapDrawable
            bitMapDrawable = resources.getDrawable(drawableId) as BitmapDrawable
        } else {
            bitMapDrawable = resources.getDrawable(drawableId) as BitmapDrawable
        }

        // bitmap 크기 변환
        var scaledBitmap = Bitmap.createScaledBitmap(bitMapDrawable.bitmap, 132, 222, false)
        if(drawableId == R.drawable.p_merged) {
            scaledBitmap = Bitmap.createScaledBitmap(bitMapDrawable.bitmap, 62, 108, false)
        }

        if(drawableId == R.drawable.p_merged_red ||drawableId == R.drawable.p_merged_purple ) {
            scaledBitmap = Bitmap.createScaledBitmap(bitMapDrawable.bitmap, 31, 84, false)
        }

        return BitmapDescriptorFactory.fromBitmap(scaledBitmap)

    }


    fun loadMapCmnt() {
        Log.d(TAG, "MapsMainFragment - loadMapCmnt() called")

        try {

            val retrofit = Retrofit.Builder()
                .baseUrl(MapCmntApi.ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(MapCmntService::class.java)
            service
                .getMapCmnt(MapCmntApi.token)
                .enqueue(object : Callback<MapCmnt> {
                    override fun onResponse(call: Call<MapCmnt>, response: Response<MapCmnt>) {
                        showCmnt(response.body())
                    }

                    override fun onFailure(call: Call<MapCmnt>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        } catch (i: Exception) {
            Log.e("[retrofit 불러오는중 문제발생]", i.toString(), i)
        }
    }

    fun showCmnt(cmnties: MapCmnt?) {

        Log.d(TAG, "MapsMainFragment - showCmnt() called")

//        var descriptor = getDescriptorFromDrawable(R.drawable.marker)
//        val m_merged_blue = getDescriptorFromDrawable(R.drawable.m_merged_blue)
//        val m_merged_green = getDescriptorFromDrawable(R.drawable.m_merged_green)
//        val m_merged_orange = getDescriptorFromDrawable(R.drawable.m_merged_orange)
//        val m_merged_pink = getDescriptorFromDrawable(R.drawable.m_merged_pink)
//        val m_merged_purple = getDescriptorFromDrawable(R.drawable.m_merged_purple)
//        val m_merged_red = getDescriptorFromDrawable(R.drawable.m_merged_red)
//        val m_merged_yellow = getDescriptorFromDrawable(R.drawable.m_merged_yellow)

        var descriptor = getDescriptorFromDrawable(R.drawable.p_merged)

        val m_merged_blue = getDescriptorFromDrawable(R.drawable.p_merged_blue)
        val m_merged_green = getDescriptorFromDrawable(R.drawable.p_merged_green)
        val m_merged_orange = getDescriptorFromDrawable(R.drawable.p_merged_orange)
        val m_merged_pink = getDescriptorFromDrawable(R.drawable.p_merged_pink)
        val m_merged_purple = getDescriptorFromDrawable(R.drawable.p_merged_purple)
        val m_merged_red = getDescriptorFromDrawable(R.drawable.p_merged_red)
        val m_merged_yellow = getDescriptorFromDrawable(R.drawable.p_merged_yellow)

        Log.d(TAG, "MapsMainFragment - showCmnt() called 2222")

        val latLngBounds = LatLngBounds.Builder()

        try {

            for (cmnt in cmnties?.maps ?: listOf()) {
                Log.d(TAG, "MapsMainFragment - showCmnt() called / cmnt.latitude = ${cmnt.latitude}, cmnt.longitude = ${cmnt.longitude}")
//            val position = LatLng(cmnt.latitude.toDouble(), cmnt.longitude.toDouble())
                val position = LatLng(cmnt.latitude, cmnt.longitude)
                Log.d(TAG, "MapsMainFragment - showCmnt() called2222 / cmnt.latitude = ${cmnt.latitude}, cmnt.longitude = ${cmnt.longitude}")
                Log.d(TAG, "MapsMainFragment - showCmnt() called2222 / cmnt.post.category = ${cmnt.post.category}")

                // NPE 에러 나는 지점
                val m_merged: String = cmnt.post.category.toString()

                Log.d(TAG, "MapsMainFragment - showCmnt() called2222 / m_merged = ${cmnt.post.category}")
                if (m_merged == "TIP") {
                    descriptor = m_merged_blue
                } else if (m_merged == "STOCK") {
                    descriptor = m_merged_green
                } else if (m_merged == "SPORTS") {
                    descriptor = m_merged_orange
                } else if (m_merged == "FOOD") {
                    descriptor = m_merged_pink
                } else if (m_merged == "STUDY") {
                    descriptor = m_merged_purple
                } else if (m_merged == "GAME") {
                    descriptor = m_merged_red
                }

                Log.d(TAG, "MapsMainFragment - showCmnt() called / tag = ${cmnt}")

                val marker = MarkerOptions()
//                .title(lib.LBRRY_NAME)
                    .snippet("${cmnt.post.toString()}")
                    .position(position)
                    .icon(descriptor)

                Log.d(TAG, "MapsMainFragment - showCmnt() called  222/ tag = ${cmnt}")

                val markerObject = mMap.addMarker(marker)
                markerObject.title = cmnt.post.category
                markerObject.tag = cmnt

                var tag = Gson().toJson(markerObject.tag)
                Log.d(TAG, "MapsMainActivity - showCmnt() called / tag = ${cmnt}")
                Log.d(TAG, "MapsMainActivity - showCmnt() called / markerObject.title = ${markerObject.title}")

                mMap.addMarker((marker))
                latLngBounds.include(position)
            }


        } catch (i: Exception) {
            Log.e("[list 불러오는중 문제발생]", i.toString(), i)
        }

    }


    override fun onStart() {
        super.onStart()
        mView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mView.onLowMemory()
    }
    override fun onDestroy() {
        mView.onDestroy()
        super.onDestroy()
    }


}