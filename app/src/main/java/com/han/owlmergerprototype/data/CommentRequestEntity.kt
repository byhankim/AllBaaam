package com.han.owlmergerprototype.data

data class CommentRequestEntity(var contents: String, var postId: Int)

data class ReCommentRequestEntity(var contents: String, var postId: Int, var parentId: Int)