package com.han.owlmergerprototype.community

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.SQLException
import android.graphics.Bitmap
import android.icu.text.MessageFormat.format
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.format.DateFormat.format
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityManagerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.internal.bind.util.ISO8601Utils.format
import com.google.gson.reflect.TypeToken
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.ArticleEntity
import com.han.owlmergerprototype.data.ThemeEntity
import com.han.owlmergerprototype.databinding.ActivityCreateArticleBinding
import com.han.owlmergerprototype.sharedTest.SharedPrefManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.han.owlmergerprototype.data.Post
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.utils.DateTimeFormatManager
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// for Image upload
private const val PICK_FROM_GALLERY = 100
private const val PICK_FROM_CAMERA = 200

private const  val MY_PERMISSION_REQUEST_STORAGE = 500

class CreateArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateArticleBinding
    private lateinit var newArticleList : ArrayList<ArticleEntity>

    // onStartActivity 분기용
    private var ACTIVITY_STATE = 100

    // Image rsc varibles
    private lateinit var currentSelectedUri: Uri
    private lateinit var myImageDir: File
    private var fileLocation = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.commMainToolbar)

        // set maxlines
        binding.commWriteArticleContentEt.maxLines = 5
        // android:maxLength="@dimen/create_article_et_text_maxlength"
//        binding.commWriteArticleContentEt.filters = arrayOf(InputFilter.LengthFilter(R.dimen.create_article_et_text_maxlength))

        // theme selector rv
        val manager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        with (binding.themeSelectorRecyclerview) {
            layoutManager = manager
            DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)

            val testList = mutableListOf<ThemeEntity>()
            testList.add(ThemeEntity(getString(R.string.comm_honey_tip), R.drawable.ic_idea2, R.color.style1_5_20,R.color.style2_5,R.color.style1_5, 1, false))
            testList.add(ThemeEntity(getString(R.string.comm_stocks_overseas), R.drawable.ic_graph2, R.color.style1_4_20,R.color.style2_4,R.color.style1_4, 2, false))
            testList.add(ThemeEntity(getString(R.string.comm_sports_overseas), R.drawable.ic_sport2, R.color.style1_3_20,R.color.style2_3, R.color.style1_3, 3, false))
            testList.add(ThemeEntity(getString(R.string.comm_latenight_food), R.drawable.ic_chicken2, R.color.style1_2_20,R.color.style2_2, R.color.style1_2, 4, false))
            testList.add(ThemeEntity(getString(R.string.comm_study_hard), R.drawable.ic_book2, R.color.style1_6_20,R.color.style2_6, R.color.style1_6, 5, false))
            testList.add(ThemeEntity(getString(R.string.comm_games), R.drawable.ic_game2, R.color.style1_7_20,R.color.style2_7, R.color.style1_7, 5, false))
            adapter = ThemeSelectorRecyclerAdapter(testList, this@CreateArticleActivity,false) /*{
                setOnClickListener { Toast.makeText(context, "theme selected!", Toast.LENGTH_SHORT).show() }
            }*/
        }

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
                // TO DO("Store them in SharedPreferences")
                val sharedPrefName = getString(R.string.owl_shared_preferences_name)
                val myShared = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)

                val sharedKey = getString(R.string.owl_shared_preferences_dummy_comm_posts)

                val dummyCommPostsType = object: TypeToken<MutableList<Post>>() {}.type
                val dummyDataSetFromSharedPreferences: MutableList<Post> = Gson().fromJson(myShared.getString(sharedKey, ""), dummyCommPostsType)
                dummyDataSetFromSharedPreferences.add(Post(
                    id = dummyDataSetFromSharedPreferences.size + 1,
                    createdAt = DateTimeFormatManager.getCurrentDatetime(),
                    contents = binding.commWriteArticleContentEt.text.toString(),
                    category = R.string.comm_latenight_food,
                    userID = TestUser.userID
                ))

                with (myShared.edit()) {
                    putString(sharedKey, Gson().toJson(dummyDataSetFromSharedPreferences))
                    commit()
                }

                // Activity back stack flush
                val intent = Intent(this, BottomNavActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    private fun fildUploadAsync(queryString: String) {
        Thread {
            val uploadFile = File(fileLocation)
            var response: Response? = null
        }
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
                        Log.e("[PICK_FROM_GALLERY]", "갤러리에서 절대주소 획득 성공: $returnedImgURI")
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
}