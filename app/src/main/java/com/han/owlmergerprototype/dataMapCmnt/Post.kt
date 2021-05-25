package com.han.owlmergerprototype.dataMapCmnt

data class Post(
    val bookmark: List<Any>,
    val category: String,
    val comments: List<Any>,
    val contents: String,
    val createdAt: String,
    val id: Int,
    val images: List<Any>,
    val like: List<Any>,
    val updatedAt: String,
    val user: User,
    val userId: Int
)