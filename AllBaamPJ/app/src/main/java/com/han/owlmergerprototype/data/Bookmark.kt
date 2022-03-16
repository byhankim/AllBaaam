package com.han.owlmergerprototype.data

data class Bookmark(
        var id: Int,
        var createdAt: String,
        var updatedAt: String?,
        var bookmark: Boolean,
        var userID: Int,
        var postID: Int,
        var cmdID: Int? = null
)