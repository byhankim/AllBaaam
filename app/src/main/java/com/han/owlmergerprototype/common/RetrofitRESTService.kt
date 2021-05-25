package com.han.owlmergerprototype.common

import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.rest.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitRESTService {
    @FormUrlEncoded
    @POST("/posts")
    fun createPost( // without image
        @Field("contents") contents: String?,
        @Field("category") category: String?,
        @Field("imageId") imageId: String?
    ): Call<OkFailResult>

    // full posts (default)
    @GET("/posts")
    fun getPosts(
        @Query("cursor") cursor_key: Int?,
        @Header("token") token: String?
    ): Call<PostModel>

    // posts by category
    @GET("/posts")
    fun getPostsByCategory(
        @Query("sort") sort_key: Int?,
        @Query("cursor") cursor_key: Int?
    ): Call<PostModel>

    // sort by popularity
    @GET("/posts/popular")
    fun getPopularPosts(@Query("cursor_id") key: Int?): Call<PopularPostModel>

    @GET("/auth/kakao/")
    fun kakaoLogin(
    ):Call<Login>

    @GET("/auth/naver/")
    fun naverLogin(
    ):Call<Login>

    @GET("/users/me/")
    fun getUserInfo(
        @Header("token")token:String
    ): Call<UserInfo>

    @FormUrlEncoded
    @POST("/auth/verify/")
    fun getVerifyCode(
        @Header("token")token:String,
        @Field("phone") phone:String
    ):Call<Ok>

    @FormUrlEncoded
    @POST("/auth/verify-check/")
    fun verifyPhoneNumber(
        @Header("token")token:String,
        @Field("verifyCode") verifyCode:Int
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

    @GET("/posts/notifications/")
    fun getMyNotifictions(
        @Header("token")token: String
    ):Call<Notis>

    @FormUrlEncoded
    @POST("/posts/is-like/")
    fun getIsLike(
        @Header("token")token: String,
        @Field("postId")postId:Int
    ):Call<IsLike>

    @FormUrlEncoded
    @POST("/posts/is-bookmark/")
    fun getIsBookmark(
        @Header("token")token: String,
        @Field("postId")postId:Int
    ):Call<IsBookmark>

    @FormUrlEncoded
    @POST("/posts/bookmark/")
    fun postBookmark(
        @Header("token")token: String,
        @Field("postId")postId:Int
    ):Call<IsBookmark>

    @FormUrlEncoded
    @POST("/posts/like/")
    fun postLike(
        @Header("token")token: String,
        @Field("postId")postId:Int
    ):Call<IsLike>
}