package com.han.owlmergerprototype.data

data class CommentEntity(
    var id: Int = -1,
    var attachedId: Int = -1,
    var isStandaloneComment: Boolean = true, // random??
    var uName: String = "test00",
    var timePassed: String = "5년 전",
    var content: String = "가나다라 마바사아 자차카타 파하 abcdefg hijklmnop"
)