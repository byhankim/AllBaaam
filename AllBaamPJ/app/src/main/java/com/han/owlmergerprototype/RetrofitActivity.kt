package com.han.owlmergerprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.han.owlmergerprototype.databinding.ActivityRetrofitBinding
import com.han.owlmergerprototype.utils.SEARCH_TYPE

class RetrofitActivity : AppCompatActivity() {
    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_retrofit)
//    }
    val TAG: String = "로그"
    private var currentSearchType: SEARCH_TYPE = SEARCH_TYPE.PHOTO
    private var activityRetrofitBinding: ActivityRetrofitBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        val binding = ActivityRetrofitBinding.inflate(layoutInflater)
        activityRetrofitBinding = binding
        setContentView(binding.root)

        Log.d(TAG, "MainActivity - onCreate() called")

        // 라디오 그룹 가져오기
        var search_term_radio_group = findViewById<RadioGroup>(R.id.search_term_radio_group)
        search_term_radio_group.setOnCheckedChangeListener { _, checkedId ->

        var search_term_text_layout = findViewById<TextInputLayout>(R.id.search_term_text_layout)

            // switch 문
            when(checkedId) {
                R.id.photo_search_radio_btn -> {
                    Log.d(TAG, "사진검색 버튼 클릭!")
                    search_term_text_layout.hint = "사진검색"
                    search_term_text_layout.startIconDrawable = resources.getDrawable(R.drawable.ic_photo_library_black_24dp, resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.PHOTO
                }

                R.id.user_search_radio_btn -> {
                    Log.d(TAG, "사용자검색 버튼 클릭!")
                    search_term_text_layout.hint = "사용자검색"
                    search_term_text_layout.startIconDrawable = resources.getDrawable(R.drawable.ic_person_black_24dp, resources.newTheme())
                    this.currentSearchType = SEARCH_TYPE.USER
                }
            }
            Log.d(TAG, "MainActivity - OnCheckedChanged() called / currentSearchType : $currentSearchType")
        }


        var search_term_edit_text = findViewById<TextInputEditText>(R.id.search_term_edit_text)

        // 텍스트가 변경이 되었을때
//        search_term_edit_text.onMyTextChanged {
//
//            // 입력된 글자가 하나라도 있다면
//            if(it.toString().count() > 0){
//                // 검색 버튼을 보여준다.
//                frame_search_btn.visibility = View.VISIBLE
//                search_term_text_layout.helperText = " "
//
//
//
//                // 스크롤뷰를 올린다.
//                main_scrollview.scrollTo(0, 200)
//
//            } else {
//                frame_search_btn.visibility = View.INVISIBLE
//                search_term_text_layout.helperText = "검색어를 입력해주세요"
//            }
//
//            if (it.toString().count() == 12) {
//                Log.d(TAG, "MainActivity - 에러 띄우기 ")
//                Toast.makeText(this, "검색어는 12자 까지만 입력 가능합니다.", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//
//        // 버튼 클릭시
//        btn_search.setOnClickListener {
//            Log.d(TAG, "MainActivity - 검색 버튼이 클릭되었다. / currentSearchType : $currentSearchType")
//
//            this.handleSearchButtonUi()
//        }
//
//

    } // onCreate



//    private fun handleSearchButtonUi(){
//
//        btn_progress.visibility = View.VISIBLE
//
//        btn_search.text = ""
//
//        Handler().postDelayed({
//            btn_progress.visibility = View.INVISIBLE
//            btn_search.text = "검색"
//        }, 1500)
//
//    }



}