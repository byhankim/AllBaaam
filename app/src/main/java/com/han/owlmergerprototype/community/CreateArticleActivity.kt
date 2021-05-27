package com.han.owlmergerprototype.community

import android.Manifest
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.SQLException
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.token
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.databinding.ActivityCreateArticleBinding
import com.han.owlmergerprototype.retrofit.OwlRetrofitManager
import java.util.*
import com.han.owlmergerprototype.noLoginTest.NoLoginBottomNavActivity
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// for Image upload
private const val PICK_FROM_GALLERY = 100
private const val PICK_FROM_CAMERA = 200

private const  val MY_PERMISSION_REQUEST_STORAGE = 500

class CreateArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateArticleBinding
    private lateinit var mAdapter: ThemeSelectorRecyclerAdapter

    // category number
    var selectedCategory: Int = -1
    var prevSelectedCategory: Int = -1

    // retrofit
    private var imageId: Int? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    // onStartActivity 분기용
    private var ACTIVITY_STATE = 100

    // Image rsc varibles
    private lateinit var currentSelectedUri: Uri
    private lateinit var myImageDir: File
    private var fileLocation = ""
    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        latitude = intent.getDoubleExtra("lat", 0.0)
        binding = ActivityCreateArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.commMainToolbar)

        /*if 지도에서 넘어온 경우*/
        // latlng toast
        latitude = intent.getStringExtra("latitude")?.toDouble()
        longitude = intent.getStringExtra("longitude")?.toDouble()
        Log.d(TAG, "CreateArticleActivity - onCreate() latitude: $latitude longitude: $longitude called")

        val latlng_toast = Toast.makeText(this, "위도 : $latitude, 경도 : $longitude", Toast.LENGTH_SHORT)
        latlng_toast.show()

        var latlng_text = findViewById<TextView>(R.id.comm_write_article_set_location_tip_tv)
        var latlng_img = findViewById<TextView>(R.id.comm_write_article_set_location_btn)

        if ( latitude != null || longitude != null ) {
            latlng_text.text = "위도 : $latitude, 경도 : $longitude"
        } else {
            latlng_img.setOnClickListener {
                val intent = Intent(this, NoLoginBottomNavActivity::class.java)
                startActivity(intent)
            }
            latlng_text.setOnClickListener {
                val intent = Intent(this, NoLoginBottomNavActivity::class.java)
                startActivity(intent)
            }

        }
        /*else 글쓰기 탭으로 바로 연결된 경우 : 위경도 text는 없음*/
        /**
         * 1위치 값 권한설정 - 권한설정 거부시
         * 2시/구 설정 화면 노출
         * 위경도 값 없으면 지도로 연결되도록 화면구성
         */



        // 동까지만 받아오는 코드 작성필요
        // db에는 위경도 지금대로 저장





        // set maxlines
        binding.commWriteArticleContentEt.maxLines = 5
        // android:maxLength="@dimen/create_article_et_text_maxlength"
//        binding.commWriteArticleContentEt.filters = arrayOf(InputFilter.LengthFilter(R.dimen.create_article_et_text_maxlength))


        // theme selector rv
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        with (binding.themeSelectorRecyclerview) {
            layoutManager = manager
            DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
/*
val category = when (selectedCategory) {
            0 -> "TIP"
            1 -> "STOCK"
            2 -> "STUDY"
            3 -> "SPORTS"
            4 -> "FOOD"
            5 -> "GAME"
            else -> "NOCATEGORY"
 */
            val testList = mutableListOf<ThemeEntity>()
            testList.add(ThemeEntity(getString(R.string.comm_honey_tip), R.drawable.ic_idea2, R.color.style1_5_20,R.color.style2_5,R.color.style1_5, 1, false))
            testList.add(ThemeEntity(getString(R.string.comm_stocks_overseas), R.drawable.ic_graph2, R.color.style1_4_20,R.color.style2_4,R.color.style1_4, 2, false))
            testList.add(ThemeEntity(getString(R.string.comm_study_hard), R.drawable.ic_book2, R.color.style1_6_20,R.color.style2_6, R.color.style1_6, 3, false))
            testList.add(ThemeEntity(getString(R.string.comm_sports_overseas), R.drawable.ic_sport2, R.color.style1_3_20,R.color.style2_3, R.color.style1_3, 4, false))
            testList.add(ThemeEntity(getString(R.string.comm_latenight_food), R.drawable.ic_chicken2, R.color.style1_2_20,R.color.style2_2, R.color.style1_2, 5, false))
            testList.add(ThemeEntity(getString(R.string.comm_games), R.drawable.ic_game2, R.color.style1_7_20,R.color.style2_7, R.color.style1_7, 6, false))
            mAdapter = ThemeSelectorRecyclerAdapter(testList, this@CreateArticleActivity,false) /*{
                setOnClickListener { Toast.makeText(context, "theme selected!", Toast.LENGTH_SHORT).show() }
            }*/


            mAdapter.setItemClickListener(object : ThemeSelectorRecyclerAdapter.ItemClickListener {
                override fun onClick(view: View, position: Int) {
                    Log.d(TAG, "onClick: ${position}")
                    selectedCategory = position
                }
            })

            adapter = mAdapter
//            selectedCategory



        }




        // test selectedCategory
//        binding.commWriteArticleSetLocationTipTv.setOnClickListener {
//        }

        binding.commWriteArticleAddImgBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK // 뭔가 가져오겠다는 뜻
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, PICK_FROM_GALLERY)


        }

        // count text
        binding.commWriteArticleContentEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.createArticleCurrentTextCountTv.text = if (s != null) {
                    s.length.toString()
                } else {
                    "0"
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.commMainToolbar.inflateMenu(R.menu.comm_create_article_menu)

        // buttons
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back) // set drawable icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.action_write_article -> {


                Log.d(TAG, "selectedCategory: ${selectedCategory} ")
                val cate: String = when (selectedCategory) {
                    0 -> "TIP"
                    1 -> "STOCK"
                    2 -> "STUDY"
                    3 -> "SPORTS"
                    4 -> "FOOD"
                    5 -> "GAME"
                    else -> "error"
                }
                Log.d("nonono", "selectedCategory: ${selectedCategory} ")
                if (cate != "error") {
                    Log.d(TAG, "cate: ${cate} ")
                    if (checkValidate()) {
                        createPost(cate)
                    }

                    // Activity back stack flush
                    val intent = Intent(this, BottomNavActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                } else {

                    //Toast.makeText(this, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show()
                   val dialog = OkDialog(this@CreateArticleActivity)
                   dialog.show()
                }


            }
        }
        return true
    }

    private fun checkValidate(): Boolean {
        var flag = false
        with (binding.commWriteArticleContentEt) {
            if (text.toString().isNotEmpty()) {
                flag = true
            } else {
                requestFocus()
            }
        }
//        with (mAdapter.ThemeHolder(binding.themeSelectorRecyclerview).)
        return flag
    }

    private fun fildUploadAsync(queryString: String) {
        Thread {
            val uploadFile = File(queryString)
            /*var response: Response? = null
            try {
                Log.e("[entered]", "fileUploadAsync")

                val toServer: OkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .build()

                val fileUploadBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", uploadFile.name,
                        RequestBody.create("image/ *".toMediaTypeOrNull(), uploadFile))
                    .build()

                // request setting
                val request: Request = Request.Builder()
                    .url(ADDRESS_IMAGE)
                    .post(fileUploadBody)
                    .addHeader("token", token)
                    .build()
                response = toServer.newCall(request).execute()
//                val imgId = Gson().toJson(response.toString()) as
                if (response.isSuccessful) {
                    Log.e("[ImageIdSuccess]", response.toString() + "---" + response.message)
                }


            } catch (e: Exception) {
                Log.e("[ImageUpException]", "image upload error!: $e")
            } finally {
                response?.close()
            }
             */
            imageId = uploadImage(queryString)
            Log.e("[myImageId]", "$imageId")
        }.start()
    }

    override fun onResume() {
        super.onResume()
        Log.e("[onResume]", "hi!")

//        if (ACTIVITY_STATE != 100) { // if onstartactivity run b4
            val currentAppPackage = packageName
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.e("[onResumeVersion>=M]", "")
                myImageDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    File(Environment.getExternalStorageDirectory().absolutePath, currentAppPackage)
                } else {
                    File(Environment.getExternalStorageDirectory().absolutePath, "uploadImage")
                }
                checkSDCardPermission()
                Log.e("[AHHHH]", myImageDir.absolutePath + myImageDir.name)
            } else {
                myImageDir = File(
                        Environment.getExternalStorageDirectory().absolutePath, "uploadIamge"
                )
            }
            if (myImageDir.mkdirs()) {
                Toast.makeText(application, "저장할 디렉토리가 생성됨", Toast.LENGTH_SHORT).show()
            }
//        }
    }

    private fun checkSDCardPermission() {
        Log.e("[SDCardPermission]", "boom!")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {}
                requestPermissions(
                        arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ),
                        MY_PERMISSION_REQUEST_STORAGE
                )
            } else {
                // permissions granted!
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("[onActivityResult]", requestCode.toString())
        var returnedImgURI: Uri?
        if (resultCode != RESULT_OK || resultCode == RESULT_CANCELED) {
            Log.e("[onActivityResult2]", "requestCode fail....I'm leaving :(")
            return
        }
        ACTIVITY_STATE = 200
        Log.e("[onActivityResult2]", ACTIVITY_STATE.toString())
        when (requestCode) {
            PICK_FROM_GALLERY -> {
                Log.e("[onActivityResult3]", "hi!")
                returnedImgURI = data?.data
                if (returnedImgURI != null) {
                    Log.e("[PICK_FROM_GALLERY]", returnedImgURI.toString())

                    binding.createArcileUploadedImg.visibility = View.VISIBLE  // ??
                    binding.createArcileUploadedImg.setImageURI(returnedImgURI)
                    // hint text refresh if edittext is empty
                    if (binding.commWriteArticleContentEt.text.isEmpty()) {
                        binding.commWriteArticleContentEt.hint =
                                getString(R.string.create_article_photo_upload_hint_text)
                    }

                    // 업로드 가능하게 절대 주소 알아내기
                    if (findImageFileNameFromUri(returnedImgURI)) {
                        Log.e("[PICK_FROM_GALLERY]", "갤러리에서 절대주소 획득 성공: $fileLocation")

                        // file upload
                        fildUploadAsync(fileLocation)
                    } else {
                        Log.e("[PICK_FROM_GALLERY]", "갤러리에서 절대주소 획득 실패")
                    }

                } else {
                    val extras = data?.extras
                    val returnedBitmap = extras!!["data"] as Bitmap?
                    if (tempSavedBitmapFile(returnedBitmap)) {
                        Log.e("[PICK_FROM_GALLERY]", "갤러리 URI 값이 없어 실제 파일로 저장함")
                    } else {
                        Log.e("[PICK_FROM_GALLERY]", "갤러리 URI 값이 없어 실제 파일로 저장 실패")
                    }
                }
            }
            else -> {}
        }
    }

    private fun tempSavedBitmapFile(tempBitmap: Bitmap?): Boolean {
        var flag = false
        try {
            val tempName = "upload_" + System.currentTimeMillis() / 1000
            val fileSuffix = ".jpg"
            // run temp file (auto removal when app closes)
            val tempFile = File.createTempFile(
                    tempName,
                    fileSuffix,
                    myImageDir
            )
            val bitmapStream = FileOutputStream(tempFile)
            // compress quality
            tempBitmap!!.compress(Bitmap.CompressFormat.JPEG, R.dimen.image_upload_compress_quality, bitmapStream)
            bitmapStream.close()
            fileLocation = tempFile.absolutePath

            // ?????
            flag = true
        } catch (i: IOException) {
            Log.e("[저장중 문제발생]", i.toString(), i)
        }
        return flag
    }

    private fun findImageFileNameFromUri(tempUri: Uri): Boolean {
        var flag = false
        // actual image uri's absolute path
        val IMAGE_DB_COLUMN = arrayOf(MediaStore.Images.ImageColumns.DATA)
        var cursor: Cursor? = null

        try {
            // pk extraction
            val imagePK = ContentUris.parseId(tempUri).toString()
            // image db에 쿼리 날리기
            cursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    IMAGE_DB_COLUMN,
                    MediaStore.Images.Media._ID + "=?", arrayOf(imagePK), null, null
            )
            if (cursor!!.count > 0) {
                cursor.moveToFirst()
                fileLocation = cursor.getString(
                        cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                )
                flag = true
            }
        } catch (sqle: SQLException) {
            Log.e("[Error_findImage]", sqle.toString(), sqle)
        } finally {
            cursor?.close()
        }
        return flag
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // RETROFIT upload image
    private fun uploadImage(path: String = ""): Int? {
        var imgId: Int? = null
        if (path.isEmpty())
            return null
        val uploadImageService = OwlRetrofitManager.OwlRestService.owlRestService

        val file = File(path)
//        val fileUploadBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        /*
        MultipartBody.Part.createFormData(
            name = key,
            filename = file.name,
            body = file.asRequestBody("image/ *".toMediaType())
        )
        */
        val multipartBody = MultipartBody.Part.createFormData(
            "file", file.name, file.asRequestBody("image/*".toMediaTypeOrNull())
        )


        val call: Call<ImageRESTEntity> = uploadImageService.uploadImage(  multipartBody, token )
//        Log.e("[imgCall]", call.execute().toString())
        call.enqueue(object: Callback<ImageRESTEntity> {
            override fun onResponse(
                call: Call<ImageRESTEntity>,
                response: retrofit2.Response<ImageRESTEntity>
            ) {
                if (response.isSuccessful) {
                    val imgRESTEntity = response.body() as ImageRESTEntity
                    Toast.makeText(this@CreateArticleActivity, "이미지를 서버에 저장했습니다", Toast.LENGTH_SHORT).show()
                    Log.e("[imgUpSuccess]", "이미지 업로드 성공! res body: ${response.body()}, entity: ${imgRESTEntity.toString()}")
                    imageId = imgRESTEntity.imageId
                    imgId = imgRESTEntity.imageId
                    Log.e("[TRUEimgID]", "$imageId")
                }
            }

            override fun onFailure(call: Call<ImageRESTEntity>, t: Throwable) {
                imgId = null
                Log.e("[imgUpFail]", "이미지 업로드 실패! $t")
            }
        })
        return imgId
    }

    private fun createPost(category:String) {
        //getMapCommunity
        val createPostService = OwlRetrofitManager.OwlRestService.owlRestService
        val category = when (selectedCategory) {
            0 -> "TIP"
            1 -> "STOCK"
            2 -> "STUDY"
            3 -> "SPORTS"
            4 -> "FOOD"
            5 -> "GAME"
            else -> "NOCATEGORY"
        }

        val myJson = if (imageId != null && latitude != null) {
            // full
            Log.e("[죄다보냅니다!]", "a")
            Gson().toJson(CreatePostEntityFull(
                binding.commWriteArticleContentEt.text.toString(),
                category,
                imageId,
                latitude,
                longitude
            ))
        } else if (imageId == null && latitude != null) {
            Log.e("[위경도만보냅니다!]", "a")
            Gson().toJson(CreatePostEntityLocation(
                binding.commWriteArticleContentEt.text.toString(),
                category,
                latitude,
                longitude
            ))
        } else if (imageId != null && latitude == null) {
            Log.e("[이미지만보냅니다!]", "a")
            Gson().toJson(CreatePostEntityImage(
                binding.commWriteArticleContentEt.text.toString(),
                category,
                imageId
            ))
        } else {
            Log.e("[글만보냅니다!]", "a")
            Gson().toJson(CreatePostEntityMinimal(
                binding.commWriteArticleContentEt.text.toString(),
                category
            ))
        }

        Log.e("postjson:", myJson)
        val result: Call<OkFailResult> = createPostService.createPost(
            token,
            myJson
        )

        // 로그찍기
//        Log.e("[createPstRESULT]",
//            Gson().toJson(CreatePostEntityMinimal(binding.commWriteArticleContentEt.text.toString(),
//            "FOOD")))



        result.enqueue(object: Callback<OkFailResult> {
            override fun onResponse(
                call: Call<OkFailResult>,
                response: retrofit2.Response<OkFailResult>
            ) {
                Log.e("[CreatePostCALL]", call.toString())
                if (response.isSuccessful) {
                    val okFail = response.body()
                    okFail?.let {
                        Toast.makeText(this@CreateArticleActivity, okFail.ok/* + okFail.error*/, Toast.LENGTH_SHORT).show()
                        Log.e("[CreatePostSuccess]", "YEY")
                    }
                }
            }

            override fun onFailure(call: Call<OkFailResult>, t: Throwable) {
                Toast.makeText(this@CreateArticleActivity, t.toString(), Toast.LENGTH_SHORT).show()
                Log.e("[CreatePostFail]", "$t")
            }
        })
    }

}