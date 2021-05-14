package com.han.owlmergerprototype.data

data class Comment(
        var id: Int,
        var createdAt: String,
        var updatedAt: String?,
        var contents: String,
        var postID: Int,
        var isParent: Boolean,
        var recomment: Int?,
        var userID: Int
)