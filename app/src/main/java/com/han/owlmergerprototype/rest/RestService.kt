package com.han.owlmergerprototype.rest

import com.han.owlmergerprototype.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RestService {
    @GET("/auth/kakao/")
    fun loginAndGetToken(
    ):Call<Login>

    @GET("/users/me/")
    fun getUserInfo(
        @Header("token")token:String
    ): Call<UserInfo>

}