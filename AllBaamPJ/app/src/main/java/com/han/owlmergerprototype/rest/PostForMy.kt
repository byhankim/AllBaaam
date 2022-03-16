package com.han.owlmergerprototype.rest

import com.han.owlmergerprototype.data.User

data class PostForMy(
    var id:Int = -1,
    var createdAt:String ="",
    var contents:String="",
    var category:String="",
    var images: ArrayList<Image>? =null,
    var userId: Int = -1,
    var user: User = User(-1,"","","")
)