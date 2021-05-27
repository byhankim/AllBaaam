package com.han.owlmergerprototype.data

import com.han.owlmergerprototype.rest.Image

data class PostSaebaeEvent(
        var id: Int = -1,
        var createdAt: String = "",
        var updatedAt: String = "",
        var contents: String = "",
        var category: Int=-1,
        var images:ArrayList<Image>?=null,
        var cmntID: Int = -1,
        var user: User,
        var comments:ArrayList<Comment>,
        var like:ArrayList<Like>,
        var bookmark:ArrayList<Bookmark>
)