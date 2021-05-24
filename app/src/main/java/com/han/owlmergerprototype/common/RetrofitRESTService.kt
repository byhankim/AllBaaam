package com.han.owlmergerprototype.common

import com.han.owlmergerprototype.data.OkFailResult
import com.han.owlmergerprototype.data.PopularPostModel
import com.han.owlmergerprototype.data.PostModel
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
}