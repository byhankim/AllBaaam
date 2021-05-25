package com.han.owlmergerprototype.data

data class Noti (
    var id: Int,
    var createdAt: String,
    var userId: Int,
    var postId : Int,
    var post:Post,
    var commentId : Int,
    var likeId : Int,
    var isRead : Boolean,
    var type:String
)