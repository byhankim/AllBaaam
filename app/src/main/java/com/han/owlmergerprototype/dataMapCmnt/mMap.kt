package com.han.owlmergerprototype.dataMapCmnt

data class mMap(
    val id: Int,
    val createdAt: String,
    val latitude: Double,
    val longitude: Double,
    val post: mPost,
    val postId: Int
)