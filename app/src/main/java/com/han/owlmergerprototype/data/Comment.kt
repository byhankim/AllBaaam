package com.han.owlmergerprototype.data

import com.han.owlmergerprototype.rest.PostForComm

data class Comment(
        var id: Int,
        var createdAt: String,
        var updatedAt: String?,
        var contents: String,
        var postId: Int,
        var isParent: Boolean,
        var recomment: Int?,
        var userId: Int?,
        var post:PostForComm?=null
)