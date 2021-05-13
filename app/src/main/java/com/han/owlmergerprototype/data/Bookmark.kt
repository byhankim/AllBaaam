package com.han.owlmergerprototype.data

data class Bookmark(
        var id: Int,
        var createdAt: String,
        var updatedAt: String,
        var bookmark: Boolean,
        var userID: String,
        var postID: String? = null,
        var cmdID: String? = null
)