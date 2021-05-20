package com.han.owlmergerprototype.map

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.community.CreateArticleActivity
import com.han.owlmergerprototype.dataMapLibrary.Library
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MapsMainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MapsMainActivity - onCreate() called")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_main)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        Log.d(TAG, "MapsMainActivity - onMapReady() called ")
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        /**
         *
         *
         */
        //== 지도 카메라 위치 초기 셋팅 ==//
        // if not login : 티 아카데미
        // else if(logined||GPS not accepted) : 티 아카데미
        // else if(logined && GPS not accepted && 위치 activity에서 값 셋팅) : 넘어온 값 셋팅
        // else if(logined||GPS accepted) : 본인 위치 LATLNG 셋팅 // -> 본인위치 값 db에 저장되어 보류

        // (SOL1) 마커 설정 후 draggerable 설정?


        // (SOL2) longClickListener 로 마커 탭 놓기,,
//        mMap.setOnMapLongClickListener

        // 맵 터치 이벤트 구현 //
        mMap.setOnMapLongClickListener { point ->
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

//            val title: String = this.modelList[position].name ?: ""
            val title: String = "위 주소"

            alertDialog(latitude, longitude)

            AlertDialog.Builder(this)
//                .setTitle(title)
//                .setMessage("$title 와 함께하는 빡코딩! :)")
                .setMessage("이 장소에 글을 등록하시겠습니까?")
                .setPositiveButton("확인") { dialog, id ->
                    Log.d(TAG, "MainActivity - 다이얼로그 확인 버튼 클릭했음")
                    // 글쓰기 Activity 연결
                    val intent = Intent(this, CreateArticleActivity::class.java)
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    startActivity(intent)

                }
                .setNegativeButton("취소")  { dialog, id ->
                    Log.d(TAG, "MainActivity - 다이얼로그 취소 버튼 클릭했음")
                }
                .show()

            // 이슈
            // 맵 터치시 핀 여러개 찍힘
            // int 값이 1 초과이면, setOnMapLongClickListener 동작 안하고
            // draggable 동작하도록

            // fragment 같이 띄우는 코드 작성
            // 1fragment 띄우고 2카메라 위치 이동





            // 카메라의 위치 이동
//            val cameraOption = CameraPosition.Builder()
//                .target(LatLng(latitude, longitude))
//                .zoom(17f)
//                .build()
//            val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
//            mMap.moveCamera(camera)

        }


        //== 초기 위치 ==//
        //== 티 아카데미 PIN 찍어줌 ==//
        val LATLNG = LatLng(37.54547677189177, 126.95253576863207)
        val descriptor = getDescriptorFromDrawable(R.drawable.marker)

        val markerOptions = MarkerOptions()
            .position(LATLNG)
//            .title("티아카데미")
//            .snippet("위도 경도")
            .icon(descriptor)
        mMap.addMarker(markerOptions)

        // 카메라의 위치
//        val cameraOption = CameraPosition.Builder()
//            .target(LATLNG)
//            .zoom(17f)
//            .build()
//        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
//        mMap.moveCamera(camera)

        loadLibrary()
    }

    fun alertDialog(latitude: Double, longitude: Double) {

    }

    fun getDescriptorFromDrawable(drawableId: Int) : BitmapDescriptor {
        var bitMapDrawable: BitmapDrawable
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.d(TAG, "MapsMainActivity - onMapReady() bitMapDrawable called")

            bitMapDrawable = getDrawable(drawableId) as BitmapDrawable
        } else {
            Log.d(TAG, "MapsMainActivity - onMapReady() bitMapDrawable /else/ called")
            bitMapDrawable = resources.getDrawable(drawableId) as BitmapDrawable
        }
//        var discriptor = BitmapDescriptorFactory.fromBitmap(bitMapDrawable.bitmap)
        // 마커 크기 변환
        val scaledBitmap = Bitmap.createScaledBitmap(bitMapDrawable.bitmap, 66, 112, false)
//        val discriptor = BitmapDescriptorFactory.fromBitmap(scaledBitmap)
        return BitmapDescriptorFactory.fromBitmap(scaledBitmap)

    }

    fun loadLibrary() {
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

        // 카메라의 위치 //== 기본값: 티아카데미 ==//
        val TAcademy = LatLng(37.54547677189177, 126.95253576863207)
        val cameraOption = CameraPosition.Builder()
            .target(TAcademy)
            .zoom(17f)
            .build()
        val camera = CameraUpdateFactory.newCameraPosition(cameraOption)
        mMap.moveCamera(camera)

    }
}