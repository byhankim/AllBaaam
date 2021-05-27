package com.han.owlmergerprototype.dataMapCmnt

data class mLike(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val userId: Int,
    val postId: Int,
    val commentId: Int
)