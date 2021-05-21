package com.han.owlmergerprototype.rest

import retrofit2.Call
import retrofit2.http.*

interface RestService {
    @GET("/auth/kakao/")
    fun loginAndGetToken(
    ):Call<Login>

    @GET("/users/me/")
    fun getUserInfo(
        @Header("token")token:String
    ): Call<UserInfo>

    @POST("/auth/verify/")
    fun getVerifyCode(
        @Header("token")token:String,
        @Field("phone") phone:String
    ):Call<VerifyCode>

    @POST("/auth/verify/")
    fun verifyPhoneNumber(
        @Header("token")token:String,
        @Field("verifyCode") phone:String
    ):Call<Ok>

    @GET("/posts/bookmark/")
    fun getMyBookMark(
        @Header("token")token:String
    ): Call<MyPosts>

    @GET("/posts/my/")
    fun getMyPost(
        @Header("token")token:String
    ): Call<MyPosts>

    @GET("/posts/comment/my/")
    fun getMyComment(
        @Header("token")token:String
    ): Call<MyComment>


}