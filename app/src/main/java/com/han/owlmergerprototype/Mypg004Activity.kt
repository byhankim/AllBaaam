package com.han.owlmergerprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.han.owlmergerprototype.MyRecyclerAdapter
import com.han.owlmergerprototype.MyRecyclerviewInterface
import com.han.owlmergerprototype.databinding.ActivityMainBinding
import com.han.owlmergerprototype.databinding.ActivityMypg004Binding

class Mypg004Activity : AppCompatActivity(), MyRecyclerviewInterface {

    //  private lateinit var binding: LayoutOkhttpRestActivityBinding
//    private var activityMainBinding: ActivityMainBinding? = null

    // 뷰가 사라질 때, 즉 메모리에서 날아갈 때 같이 날리기 위해 따로 빼두기
    private lateinit var binding: ActivityMypg004Binding
    val TAG: String = "로그"

    //데이터를 담을 그릇, 즉 배열
    var modelList = ArrayList<MyModel>()

    private lateinit var myRecyclerAdapter: MyRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        try {

            Log.e(TAG, "Mypg004Activity - onCreate() called")
            super.onCreate(savedInstanceState)
            binding = ActivityMypg004Binding.inflate(layoutInflater)
            //binding = binding
            setContentView(binding.root)

            binding.mysavedToolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
            binding.mysavedToolbar.setNavigationOnClickListener {
                finish()
            }

            Log.d(TAG, "Mypg004Activity - 반복문 돌리기 전 this.modelList.size : ${this.modelList.size}")

            for (i in 1..10) {
                val myModel = MyModel(name = "쩡대리 $i")
                this.modelList.add(myModel)
            }
            Log.d(TAG, "Mypg004Activity - 반복문 돌린 후 this.modelList.size : ${this.modelList.size}")

            // 어답터 인스턴스 생성
            myRecyclerAdapter = MyRecyclerAdapter(this)
            myRecyclerAdapter.submitList(this.modelList)

            // 리사이클러뷰 설정
            // my_recycler_view.apply {
            binding.myRecyclerView.apply {

                // 리사이클러뷰 방향 등 설정
                layoutManager = LinearLayoutManager(this@Mypg004Activity, LinearLayoutManager.VERTICAL,false)

                // 어답터 장착
                adapter = myRecyclerAdapter
            }
        } catch (e : Exception) {
            Log.d(TAG, "onCreate: Exception / exception : ${e}")
            e.printStackTrace()
        }


    }


    fun onBackButtonClicked(view: View){
        Log.d(TAG, "SecondActivity - onBackButtonClicked() called")
        finish()
    }

    override fun onItemClicked(position: Int) {
        Log.d(TAG, "Mypg004Activity - onItemClicked() called/ position: $position")

        var name: String? = null

        // 값이 비어있으면 ""을 넣는다
        // unwrapping

        val title: String = this.modelList[position].name ?: ""

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage("$title 와 함께하는 빡코딩! :)")
            .setPositiveButton("오케이") { dialog, id ->
                Log.d(TAG, "MainActivity - 다이얼로그 확인 버튼 클릭했음")
            }
            .show()
    }


}