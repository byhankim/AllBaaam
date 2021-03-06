package com.han.owlmergerprototype.data

data class ArticleEntity (
    val uID:Int=-1,
    var category: Int = -1,
    var uIcon: Int = -1,
    val datetime: String = "",
    var fixedDatetime: String = "",
    var uname: String = "",
    var content: String = "",
    var images: MutableList<Int>?
)