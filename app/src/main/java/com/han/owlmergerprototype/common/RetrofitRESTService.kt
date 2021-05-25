package com.han.owlmergerprototype.common

import com.google.gson.JsonObject
import com.han.owlmergerprototype.data.*
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
}