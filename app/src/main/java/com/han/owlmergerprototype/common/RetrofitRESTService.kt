package com.han.owlmergerprototype.common

import com.google.gson.JsonObject
import com.han.owlmergerprototype.data.*
import com.han.owlmergerprototype.rest.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitRESTService {
//    @FormUrlEncoded
    @POST("/posts")
    @Headers("Content-Type: application/json")
    fun createPost( // without image
        @Header("token") token: String,
        @Body body: String
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

    // map
    @GET("/posts/map")
    fun getMapCommunity(): Call<MapCommunityModel>

    // get comments
    @GET("/posts/comment/{postId}")
    fun getPostComments(@Path("postId") postId: Int): Call<CommentRestModel>

    // post add a comment
    @POST("/posts/comment")
    @Headers("Content-Type: application/json")
    fun createComment(
        @Header("token") token: String,
        @Body body: String
    ): Call<OkFailResult>


    // AUTH KAKAO
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

    @GET("/posts/like/count/{postId}")
    fun getLikeCount(
        @Path("postId")postId:Int
    ): Call<CountLike>

    @GET("/posts/comment/count/{postId}")
    fun getCommentCount(
        @Path("postId")postId:Int
    ):Call<CountComment>
}