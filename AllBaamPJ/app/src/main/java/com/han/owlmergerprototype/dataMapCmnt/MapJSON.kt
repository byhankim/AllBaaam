package com.han.owlmergerprototype.dataMapCmnt
data class MapCmnt(
    val maps: List<Map>,
    val ok: Boolean
)
data class Map(
    val id: Int,
    val createdAt: String,
    val latitude: Double,
    val longitude: Double,
    val post: Post?=null,
    val postId: Int
)
data class Post(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val contents: String,
    val category: String = "EMPTY",
    val userId: Int,
    val user: User,
    val images: MutableList<Any>?=null,
    val comments: MutableList<Comment>,
    val like: MutableList<Like>,
    val bookmark: MutableList<Bookmark>
)
data class User(
    val userName: String
)
data class Comment(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val contents: String,
    val userId: Int,
    val postId: Int,
    val isParent: Boolean
)
data class Like(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val userId: Int,
    val postId: Int,
    val commentId: Int
)
data class Bookmark(
    val id: Int,
    val createdAt: String,
    val postId: Int,
    val updatedAt: String,
    val userId: Int
)