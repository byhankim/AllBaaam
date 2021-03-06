package com.han.owlmergerprototype

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.RetrofitRESTService
import com.han.owlmergerprototype.data.Noti
import com.han.owlmergerprototype.data.Notis
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.utils.SpaceDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AlarmFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar

    companion object{
        const val TAG : String = "looooog"

        fun newInstance() : AlarmFragment {
            return AlarmFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"AlarmFragment - onCreate() called")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"AlarmFragment - onAttach() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view1 = inflater.inflate(R.layout.fragment_alarm,container,false)
        recyclerView = view1.findViewById(R.id.alarm_rcyView)
        toolbar = view1.findViewById(R.id.alarm_toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        toolbar.setNavigationOnClickListener {
            val fragmentManager = getActivity()!!.getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(this).commit();
            fragmentManager.popBackStack()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val loginService = retrofit.create(RetrofitRESTService::class.java)

        loginService.getMyNotifictions(TestUser.token).enqueue(object : Callback<Notis> {
            override fun onFailure(call: Call<Notis>, t: Throwable) {
                val dialog = AlertDialog.Builder(context)
                dialog.setTitle("????????????")
                dialog.setMessage("??????")
                dialog.show()
            }
            override fun onResponse(call: Call<Notis>, response: Response<Notis>) {
                val notis = response.body()
                Log.e(TAG, "onResponse: ${notis!!.notis}" )

                if(response.isSuccessful){


                    activity?.runOnUiThread {
                        val size = resources.getDimensionPixelSize(R.dimen.comm_theme_padding_vertical) * 2
                        val deco = SpaceDecoration(size)
                        recyclerView.addItemDecoration(deco)
                        val adap1 = RecyclerAdapter(notis!!.notis)
                        recyclerView.adapter = adap1
                        recyclerView.layoutManager = LinearLayoutManager(context)
                    }


                }else{
                    Toast.makeText(context,"???????????????", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return view1

    }


    inner class RecyclerAdapter(val articleList :ArrayList<Noti>): RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>() {
        //?????? ????????? ?????? ????????? viewholder ????????? ???????????? ???????????? ?????????
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView = layoutInflater.inflate(R.layout.alarm_box_layout, null)
            val holder = ViewHolderClass(itemView)
            return holder
        }

        //????????? ??????
        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.alarmTimeTV.text = articleList[position].createdAt
            holder.alarmCategoryTV.text =when(articleList[position].type){
                "LIKE" -> "[????????????]"
                "COMMENT" -> "[????????????]"
                else -> "[????????????]"
            }
            holder.alarmContextTV.text = when(articleList[position].type){
                "LIKE" -> context!!.getString(R.string.someone_give_me_heart)
                "COMMENT" -> context!!.getString(R.string.someone_give_me_comment)
                else -> "[????????????]"
            }
            if(articleList[position].post?.contents==null){
                holder.alarmTitleTV.text = null
            }else{
                holder.alarmTitleTV.text =articleList[position].post?.contents
            }

        }

        //????????????????????? ???????????? ??????
        override fun getItemCount(): Int {
            return articleList.size
        }


        inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
            //??????View ????????? View ??????
            val alarmTimeTV: TextView = itemView.findViewById(R.id.alarm_time_textView)
            val alarmCategoryTV: TextView = itemView.findViewById(R.id.alarm_category_tv)
            val alarmTitleTV: TextView = itemView.findViewById(R.id.alarm_title_textView)
            val alarmContextTV:TextView = itemView.findViewById(R.id.alarm_context_textView)
        }

    }

}