package com.han.owlmergerprototype.data

import com.han.owlmergerprototype.rest.Image

data class Post(
        var id: Int = -1,
        var createdAt: String = "",
        var updatedAt: String = "",
        var contents: String = "",
        var category: Int = -1,
        var images:ArrayList<Image>?=null,
        var cmntID: Int = -1,
        var userID: Int = -1
)