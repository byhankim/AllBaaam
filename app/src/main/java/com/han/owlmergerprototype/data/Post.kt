package com.han.owlmergerprototype.data

data class Post(
        var id: Int,
        var createdAt: String,
        var updatedAt: String,
        var contents: String,
        var category: Int,
        var cmntID: Int,
        var userID: Int
)