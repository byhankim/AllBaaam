package com.han.owlmergerprototype.dataMapCmnt

data class Map(
    val createdAt: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val post: Post,
    val postId: Int
)