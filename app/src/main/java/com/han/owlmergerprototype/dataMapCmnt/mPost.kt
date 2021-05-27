package com.han.owlmergerprototype.dataMapCmnt

data class mPost(
    val id: Int,
    val createdAt: String,
    val updatedAt: String,
    val contents: String,
    val category: String = "EMPTY",
    val userId: Int,
    val user: mUser,
    val images: List<Any>?=null,
    val comments: List<mComment>,
    val like: List<mLike>,
    val bookmark: List<mBookmark>
)