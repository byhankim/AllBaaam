package com.han.owlmergerprototype.dataMapCmnt

data class PostDoobaeEvent(
    val bookmark: MutableList<Any>,
    val category: String = "",
    val comments: MutableList<Any>,
    val contents: String,
    val createdAt: String,
    val id: Int,
    val images: MutableList<Any>,
    val like: MutableList<Any>,
    val updatedAt: String,
    val user: User,
    val userId: Int
)