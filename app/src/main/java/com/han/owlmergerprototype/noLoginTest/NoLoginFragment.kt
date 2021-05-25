package com.han.owlmergerprototype.noLoginTest

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.han.owlmergerprototype.BottomNavActivity
import com.han.owlmergerprototype.R
import com.han.owlmergerprototype.data.TestUser

class NoLoginFragment : Fragment() {
    private lateinit var toolbar: Toolbar
    private lateinit var loginBTN:Button

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : NoLoginFragment {
            return NoLoginFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"NoLoginFragment - onCreate() called")
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

                val inte = Intent(context, BottomNavActivity::class.java)
                startActivity(inte)
                activity!!.finish()

//                        val retrofit = Retrofit.Builder()
//                            .baseUrl("https://91c1ad0a482f.ngrok.")
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build()
//
//                        val loginService = retrofit.create(RestService::class.java)
//                        loginService.loginAndGetToken().enqueue(object : Callback<Login> {
//                            override fun onFailure(call: Call<Login>, t: Throwable) {
//                                val dialog = AlertDialog.Builder(dialog.context)
//                                dialog.setTitle("통신실패")
//                                dialog.setMessage("실패")
//                                dialog.show()
//                            }
//                            override fun onResponse(call: Call<Login>, response: Response<Login>) {
//                                val login = response.body()
//
//                                if(login?.ok==true){
//                                    dialog.dismiss()
//                                    TestUser.token = login.token
//                                    inte = Intent(context, BottomNavActivity::class.java)
//                                    startActivity(inte)
//                                    activity!!.finish()
//
//                                }else{
//                                    Toast.makeText(dialog.context,"틀리셨어용", Toast.LENGTH_SHORT).show()
//                                }
//
//
//                                /*  val dialog = AlertDialog.Builder(this@LoginActivity)
//                                  dialog.setTitle("통신성공")
//                                  dialog.setMessage("ok: ${login?.ok.toString()} , token: ${login?.token}")
//                                  dialog.show()*/
//
//                            }
//
//
//                        })


//                        TestUser.userName ="떡볶이가 좋은 빙봉"
//                        TestUser.userID = 1

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