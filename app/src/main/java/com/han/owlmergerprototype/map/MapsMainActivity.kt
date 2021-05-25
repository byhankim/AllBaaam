package com.han.owlmergerprototype.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.ArticleActivity
import com.han.owlmergerprototype.community.CreateArticleActivity
import com.han.owlmergerprototype.dataMapCmnt.Map
import com.han.owlmergerprototype.dataMapCmnt.MapCmnt
import com.han.owlmergerprototype.dataMapCmnt.Post
import com.han.owlmergerprototype.dataMapLibrary.Library
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapsMainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val TAG: String = "로그"
    val permission_list = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    var nowLongitude = ""
    var nowLatitude = ""


    private var mLocationPermissionGranted = false
    private lateinit var locationRequest: LocationRequest

    // Construct a FusedLocationProviderClient.
    private lateinit var mFusedLocationProviderClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MapsMainActivity - onCreate() called")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        requestPermissions(permission_list, 0)
    }

    //check Permission

    @SuppressLint("ServiceCast")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.d(TAG, "MapsMainActivity - onRequestPermissionsResult() called")

        for(r1 in grantResults){
            if(r1 == PackageManager.PERMISSION_DENIED){
                return

                // permission_denied -> 프론트원 연결결
            }
       }

        // 위치 정보를 관리하는 매니저를 추출한다.
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager

        // 저장되어 있는 위치 정보값을 가져온다.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        val location1 = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val location2 = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        if(location1 != null){
            showInfo(location1)
        } else if(location2 != null){
            showInfo(location2)
        }

        val listener = LocationListener{
            showInfo(it)
        }

    }

    private fun showInfo(location: Location){
        if(location != null){
            /**
             * 카메라 렌즈 LATLNG에
             * 위경도 값 셋팅
             *
             * manager location에 담아서 셋팅,, ? -> 위경도 셋팅,,
             */
            nowLatitude = "${location.latitude}"
            nowLongitude = "${location.longitude}"

            Log.d(TAG, "MapsMainActivity - showInfo() called / 위도111 : ${location.latitude}, 경도 : ${location.longitude} ")
            Log.d(TAG, "MapsMainActivity - showInfo() called / 위도222 : $nowLatitude, 경도 : $nowLongitude ")
//
//            finish()
//            startActivity(intent)
//
            Toast.makeText(this,
                "Provider : ${location.provider}, 위도 : ${location.latitude}, 경도 : ${location.longitude}",
                Toast.LENGTH_LONG)
                .show()
//            textView.text = "Provider : ${location.provider}"
//            textView2.text = "위도 : ${location.latitude}"
//            textView3.text = "경도 : ${location.longitude}"
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMarkerClickListener { marker ->

            Log.d(TAG, "MapsMainActivity - onMapReady() called / marker.title = ${marker.title}")
            Log.d(TAG, "MapsMainActivity - onMapReady() called / marker.tag = ${marker.tag}")
            val mapCmntObject = marker.tag as Map

            //mapCmntObject.
//            val intent = Intent(this, CreateArticleActivity::class.java)
//            intent.putExtra("Map", "${mapCmntObject}")

            val intent = Intent(this, ArticleActivity::class.java).apply {
                val selectedPost = Gson().toJson(mapCmntObject.post)
                Log.d(TAG, "MapsMainActivity - onMapReady() called / selectedPost = ${selectedPost}")
                putExtra("selectedPost", selectedPost)
                Log.d(TAG, "MapsMainActivity - onMapReady() called / selectedPost2222 = ${selectedPost}")
            }
            this.startActivity(intent)


//            if (marker.isInfoWindowShown) {
//                marker.hideInfoWindow()
//            } else {
//                marker.showInfoWindow()
//            }
            true
        }



        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true

        Log.d(TAG, "MapsMainActivity - onMapReady() called ")
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        // 카메라의 위치 //== 기본값: 티아카데미 ==//
//        val TAcademy = LatLng(37.54547677189177, 126.95253576863207)
//        val cameraOption = CameraPosition.Builder()
//            .target(TAcademy)
//            .zoom(17f)
//            .build()
//        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)

        /**
         *
         *
         */
        //== 지도 카메라 위치 초기 셋팅 ==//
        // if not login : 티 아카데미
        // else if(logined||GPS not accepted) : 티 아카데미
        // else if(logined && GPS not accepted && 위치 activity에서 값 셋팅) : 넘어온 값 셋팅
        // else if(logined||GPS accepted) : 본인 위치 LATLNG 셋팅 // -> 본인위치 값 db에 저장되어 보류

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

            // 마커(핀) 추가
            googleMap.addMarker(mOptions)


            // 팝업 호출
            var name: String? = null
            // 값이 비어있으면 ""을 넣는다
            // unwrapping
            alertDialog(latitude, longitude)


            // fragment 같이 띄우는 코드 작성
            // 1fragment 띄우고 2카메라 위치 이동
        }


        /**
         * 위치 권한 설정 코드 입력
         *
         */


        //== 초기 위치 ==//
        //== 티 아카데미 PIN 찍어줌 ==//

        Log.d(TAG, "MapsMainActivity - onMapReady() called / else")
        Log.d(TAG, "MapsMainActivity - onMapReady() called / else 000 /nowLatitude ${nowLatitude}, nowLongitude${nowLongitude}")

        val LATLNG = LatLng(37.54547677189177, 126.95253576863207)
        val descriptor = getDescriptorFromDrawable(R.drawable.marker)

        val markerOptions = MarkerOptions()
            .position(LATLNG)
//            .title("티아카데미")
//            .snippet("위도 경도")
            .icon(descriptor)
        mMap.addMarker(markerOptions)


        // 카메라의 위치 //== 기본값: 티아카데미 ==//
        val TAcademy = LatLng(37.54547677189177, 126.95253576863207)
        val cameraOption = CameraPosition.Builder()
            .target(TAcademy)
            .zoom(17f)
            .build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        mMap.moveCamera(camera)
//

//        }

        // 카메라의 위치
//        val cameraOption = CameraPosition.Builder()
//            .target(LATLNG)
//            .zoom(17f)
//            .build()
//        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
//        mMap.moveCamera(camera)

//        loadLibrary()
        loadMapCmnt()

    }


    fun alertDialog(latitude: Double, longitude: Double) {
        AlertDialog.Builder(this)
//                .setTitle(title)
//                .setMessage("$title 와 함께하는 빡코딩! :)")
            .setMessage("이 장소에 글을 등록하시겠습니까?")
            .setPositiveButton("확인") { dialog, id ->
                Log.d(TAG, "MainActivity - 다이얼로그 확인 버튼 클릭했음")

                // 기존 Activity 새로고침
                finish()
                startActivity(intent)

                // 글쓰기 Activity 연결
                val intent = Intent(this, CreateArticleActivity::class.java)
                intent.putExtra("latitude", "$latitude")
                intent.putExtra("longitude", "$longitude")
                Log.d(TAG, "MapsMainActivity - onMapReady() 위도: $latitude 경도: $longitude  called")

                startActivity(intent)

            }
            .setNegativeButton("취소")  { dialog, id ->
                Log.d(TAG, "MainActivity - 다이얼로그 취소 버튼 클릭했음")

                // 새로고침
                finish()
                startActivity(intent)
            }
            .show()
    }



    private fun getDescriptorFromDrawable(drawableId: Int) : BitmapDescriptor {
        var bitMapDrawable: BitmapDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            bitMapDrawable = getDrawable(drawableId) as BitmapDrawable
        } else {
            bitMapDrawable = resources.getDrawable(drawableId) as BitmapDrawable
        }

        // bitmap 크기 변환
        var scaledBitmap = Bitmap.createScaledBitmap(bitMapDrawable.bitmap, 66, 172, false)
        if(drawableId == R.drawable.marker) {
            scaledBitmap = Bitmap.createScaledBitmap(bitMapDrawable.bitmap, 66, 112, false)
        }

        return BitmapDescriptorFactory.fromBitmap(scaledBitmap)

    }


    fun loadMapCmnt() {
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
    }

    fun showCmnt(cmnties: MapCmnt?) {

        var descriptor = getDescriptorFromDrawable(R.drawable.marker)
        val m_merged_blue = getDescriptorFromDrawable(R.drawable.m_merged_blue)
        val m_merged_green = getDescriptorFromDrawable(R.drawable.m_merged_green)
        val m_merged_orange = getDescriptorFromDrawable(R.drawable.m_merged_orange)
        val m_merged_pink = getDescriptorFromDrawable(R.drawable.m_merged_pink)
        val m_merged_purple = getDescriptorFromDrawable(R.drawable.m_merged_purple)
        val m_merged_red = getDescriptorFromDrawable(R.drawable.m_merged_red)
        val m_merged_yellow = getDescriptorFromDrawable(R.drawable.m_merged_yellow)

        val latLngBounds = LatLngBounds.Builder()

        for (cmnt in cmnties?.maps ?: listOf()) {

            Log.d(TAG, "MapsMainActivity - showCmnt() called / cmnt.latitude = ${cmnt.latitude}, cmnt.longitude = ${cmnt.longitude}")
            val position = LatLng(cmnt.latitude.toDouble(), cmnt.longitude.toDouble())

            val m_merged: String = cmnt.post.category.toString()
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

            val marker = MarkerOptions()
//                .title(lib.LBRRY_NAME)
                .snippet("${cmnt.post.toString()}")
                .position(position)
                .icon(descriptor)

            val markerObject = mMap.addMarker(marker)
            markerObject.title = cmnt.post.category
            markerObject.tag = cmnt

            var tag = Gson().toJson(markerObject.tag)
            Log.d(TAG, "MapsMainActivity - showCmnt() called / tag = ${cmnt}")
            Log.d(TAG, "MapsMainActivity - showCmnt() called / markerObject.title = ${markerObject.title}")

            mMap.addMarker((marker))
            latLngBounds.include(position)
        }


    }



    private fun loadLibrary() {
        Log.d(TAG, "MapsMainActivity - loadLibrary() called")
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SeoulOpenService::class.java)
        service.getLibrary(SeoulOpenApi.API_KEY).enqueue(object : Callback<Library> {
            override fun onResponse(call: Call<Library>, response: Response<Library>) {
                showLibraries(response.body())
            }

            override fun onFailure(call: Call<Library>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun showLibraries(libraries: Library?) {
        val latLngBounds = LatLngBounds.Builder()

        // 아이콘 변경
//        var bitMapDrawable: BitmapDrawable
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Log.d(TAG, "MapsMainActivity - onMapReady() bitMapDrawable called")
//
//            bitMapDrawable = getDrawable(R.drawable.marker) as BitmapDrawable
//        } else {
//            Log.d(TAG, "MapsMainActivity - onMapReady() bitMapDrawable /else/ called")
//            bitMapDrawable = resources.getDrawable(R.drawable.marker) as BitmapDrawable
//        }
//        // 마커 크기 변환
//        val scaledBitmap = Bitmap.createScaledBitmap(bitMapDrawable.bitmap, 100, 100, false)
//        val discriptor = BitmapDescriptorFactory.fromBitmap(scaledBitmap)

        val descriptor = getDescriptorFromDrawable(R.drawable.marker)

        for (lib in libraries?.SeoulPublicLibraryInfo?.row ?: listOf()) {
            val position = LatLng(lib.XCNTS.toDouble(), lib.YDNTS.toDouble())

            val marker = MarkerOptions()
                .position(position)
                .title(lib.LBRRY_NAME)
                .snippet("${lib.XCNTS.toString()}, ${lib.YDNTS.toString()}")
                .icon(descriptor)   // descriptor에 아이콘 다르게 주기

            //== 핀 찍기 ==//
            //MarkerOptions 값 셋팅
            // 위에서 onclickListener로 핀에 위경도값 넘겨주기


            mMap.addMarker(marker)

            // '마커들이 보이는 뷰'로 지도좌표를 이동시키기 위한 작업 //==데이터 설정 후 작업예정=//
            latLngBounds.include(position)
        }
//        val bounds = latLngBounds.build()
//        val padding = 0
//        val updatedCamera = CameraUpdateFactory.newLatLngBounds(bounds, padding)
//        mMap.moveCamera(updatedCamera)



//        Log.d(TAG, "MapsMainActivity - onMapReady() called / camera before /nowLatitude ${nowLatitude}, nowLongitude${nowLongitude}")
//
        if (nowLatitude.isNullOrBlank() || nowLongitude.isNullOrBlank()) {

            /***
             * 위에서 위치정보는 받아오는데
             * 여기가 더 늦게 호출되는 함수여서 콜 안됨~
             *
             */
            Log.d(TAG, "MapsMainActivity - onMapReady() called / camera now")
            Log.d(TAG, "MapsMainActivity - onMapReady() called / camera now /nowLatitude ${nowLatitude}, nowLongitude${nowLongitude}")
            Log.d(TAG, "MapsMainActivity - onMapReady() called / camera now /nowLatitude ${nowLatitude}, nowLongitude${nowLongitude}")

            // 카메라의 위치 //== 기본값: 티아카데미 ==//
            val TAcademy = LatLng(37.54547677189177, 126.95253576863207)
            val cameraOption = CameraPosition.Builder()
                .target(TAcademy)
                .zoom(17f)
                .build()
            val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
            mMap.moveCamera(camera)

            Log.d(TAG, "MapsMainActivity - onMapReady() called / camera now 222")

        } else {

            Log.d(TAG, "MapsMainActivity - onMapReady() called / camera else")
            Log.d(TAG, "MapsMainActivity - onMapReady() called / camera else /nowLatitude ${nowLatitude}, nowLongitude${nowLongitude}")

            var position = LatLng(nowLatitude.toDouble(), nowLongitude.toDouble())

            val descriptor = getDescriptorFromDrawable(R.drawable.m_merged_blue)

            val marker = MarkerOptions()
                .position(position)
//                .title(lib.LBRRY_NAME)
//                .snippet("${lib.XCNTS.toString()}, ${lib.YDNTS.toString()}")
                .icon(descriptor)   // descriptor에 아이콘 다르게 주기


            //== 핀 찍기 ==//
            //MarkerOptions 값 셋팅
            // 위에서 onclickListener로 핀에 위경도값 넘겨주기

            mMap.addMarker(marker)

            // 카메라의 위치
            val cameraOption = CameraPosition.Builder()
                .target(position)
                .zoom(17f)
                .build()
            val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
            mMap.moveCamera(camera)

        }
    }
}