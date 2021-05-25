package com.han.owlmergerprototype.data

data class CommentRestModel(
    var ok: String = "fail",
    var comments: MutableList<CommentRESTEntity> = mutableListOf()
)