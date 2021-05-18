package com.han.owlmergerprototype

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.han.owlmergerprototype.data.TestUser
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException


class MainActivity : AppCompatActivity() {
    private lateinit var mSessionCallback: ISessionCallback
    //@RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSessionCallback = object : ISessionCallback {
            override fun onSessionOpened() {
                // 로그인 요청
                UserManagement.getInstance().me(object : MeV2ResponseCallback() {
                    override fun onFailure(errorResult: ErrorResult) {
                        // 로그인 실패
                        Toast.makeText(this@MainActivity, "로그인 도중에 오류가 발생했습니다.", Toast.LENGTH_SHORT)
                            .show()


                    }

                    override fun onSessionClosed(errorResult: ErrorResult) {
                        // 세션이 닫힘..
                        Toast.makeText(
                            this@MainActivity,
                            "세션이 닫혔습니다.. 다시 시도해주세요",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onSuccess(result: MeV2Response) {
                        // 로그인 성공
                        val intent = Intent(this@MainActivity, BottomNavActivity::class.java)
                        TestUser.userName =  "수현"
                        TestUser.userID = 10
                        startActivity(intent)


//                        Toast.makeText(MainActivity.this, "환영 합니다 !", Toast.LENGTH_SHORT).show();
                    }
                })
            }

            override fun onSessionOpenFailed(exception: KakaoException) {
                Toast.makeText(this@MainActivity, "onSessionOpenFailed", Toast.LENGTH_SHORT).show()
            }
        }
        Session.getCurrentSession().addCallback(mSessionCallback)
        Session.getCurrentSession().checkAndImplicitOpen()


//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
//            val signatures = info.signingInfo.apkContentsSigners
//            val md = MessageDigest.getInstance("SHA")
//            for (signature in signatures) {
//                val md: MessageDigest
//                md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val key = String(Base64.encode(md.digest(), 0))
//                Log.d("Hash key", "$key")
//            }
//        } catch (e: Exception) {
//            Log.e("name not found", e.toString())
//        }







    }


}


