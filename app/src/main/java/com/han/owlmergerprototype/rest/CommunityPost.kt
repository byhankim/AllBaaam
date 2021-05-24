package com.han.owlmergerprototype.rest

data class CommunityPost(
    var id:Int,
    var createdAt:String,
    var contents:String,
    var category:String,
    var images:ArrayList<Image>
) {
}