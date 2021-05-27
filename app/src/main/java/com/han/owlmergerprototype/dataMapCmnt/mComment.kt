package com.han.owlmergerprototype.dataMapCmnt

data class mComment(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val contents: String,
    val userId: Int,
    val postId: Int,
    val isParent: Boolean
)