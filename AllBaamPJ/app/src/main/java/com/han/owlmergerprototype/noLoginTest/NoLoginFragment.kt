package com.han.owlmergerprototype.noLoginTest

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.RetrofitRESTService
import com.han.owlmergerprototype.common.token
import com.han.owlmergerprototype.common.token2
import com.han.owlmergerprototype.data.TestUser
import com.han.owlmergerprototype.rest.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NoLoginFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var loginBTN:Button
    private lateinit var inte: Intent
    private lateinit var autoLogin : SharedPreferences

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : NoLoginFragment {
            return NoLoginFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"NoLoginFragment - onCreate() called")
        autoLogin = context!!.getSharedPreferences("autoLogin", Activity.MODE_PRIVATE)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"NoLoginFragment - onAttach() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"NoLoginFragment - onCreateView() called")
        val view = inflater.inflate(R.layout.nologin_layout,container,false)

        loginBTN = view.findViewById(R.id.nologin_layout_button)
        loginBTN.setOnClickListener {
            val dialog = Dialog(context!!)
            dialog.getWindow()!!.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            dialog.setContentView(R.layout.dialog_login)
            val cancelBTN: TextView = dialog.findViewById<TextView>(R.id.login_dialog_cancel_btn)
            cancelBTN.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
            })
            val kakaoLoginBTN: TextView = dialog.findViewById<TextView>(R.id.kakao_login_btn)
            kakaoLoginBTN.setOnClickListener(View.OnClickListener {
                dialog.dismiss()

                val retrofit = Retrofit.Builder()
                    .baseUrl(ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


                val loginService = retrofit.create(RetrofitRESTService::class.java)
                loginService.getUserInfo(token).enqueue(object : Callback<UserInfo> {
                    override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                        val dialog = AlertDialog.Builder(dialog.context)
                        dialog.setTitle("통신실패")
                        dialog.setMessage("실패")
                        dialog.show()
                    }

                    override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                        val userInfo = response.body()

                        if (userInfo?.ok == true) {

                            TestUser.token = token
                            TestUser.userName = userInfo.userName
                            TestUser.userID = userInfo.id
                            TestUser.verify = userInfo.verified

                            val editor = autoLogin.edit()
                            editor.putString("token", token)
                            editor.putString("userName", userInfo.userName)
                            editor.putInt("userId", userInfo.id)
                            editor.putBoolean("verified", userInfo.verified)
                            editor.apply()

                            inte = Intent(context, BottomNavActivity::class.java)
                            startActivity(inte)
                            activity!!.finish()


                        } else {
                            Toast.makeText(dialog.context, "틀리셨어용", Toast.LENGTH_SHORT).show()
                        }


                    }


                })
            })
                val naverLoginBTN:TextView = dialog.findViewById<TextView>(R.id.naver_login_btn)
                naverLoginBTN.setOnClickListener(View.OnClickListener {



                    val retrofit = Retrofit.Builder()
                        .baseUrl(ADDRESS)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()


                    val loginService = retrofit.create(RetrofitRESTService::class.java)
                    loginService.getUserInfo(token2).enqueue(object : Callback<UserInfo> {
                        override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                            val dialog = AlertDialog.Builder(dialog.context)
                            dialog.setTitle("통신실패")
                            dialog.setMessage("실패")
                            dialog.show()
                        }
                        override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                            val userInfo = response.body()

                            if(userInfo?.ok==true){

                                TestUser.token = token2
                                TestUser.userName = userInfo.userName
                                TestUser.userID =userInfo.id
                                TestUser.verify = userInfo.verified

                                val editor = autoLogin.edit()
                                editor.putString("token", token2)
                                editor.putString("userName",userInfo.userName)
                                editor.putInt("userId",userInfo.id)
                                editor.putBoolean("verified",userInfo.verified)
                                editor.apply()

                                inte = Intent(context, BottomNavActivity::class.java)
                                startActivity(inte)
                                activity!!.finish()


                            }else{
                                Toast.makeText(dialog.context,"틀리셨어용", Toast.LENGTH_SHORT).show()
                            }


                        }


                    })

                })
            dialog.show()
        }

        toolbar = view.findViewById(R.id.nologin_toolbar)

        toolbar.setNavigationIcon(R.drawable.ic_back) // need to set the icon here to have a navigation icon. You can simple create an vector image by "Vector Asset" and using here
        toolbar.setNavigationOnClickListener {
            val fragmentManager = getActivity()!!.getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(this).commit();
            fragmentManager.popBackStack()
        }


        return view
    }
}