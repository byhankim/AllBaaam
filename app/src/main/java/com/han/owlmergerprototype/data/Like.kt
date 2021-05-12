package com.han.owlmergerprototype.data

data class Like(
        var id: Int,
        var createdAt: String,
        var updatedAtval : String,
        var userID: Int,
        var postID: Int? = null,
        var cmtID: Int? = null
)