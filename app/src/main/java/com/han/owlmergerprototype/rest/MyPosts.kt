package com.han.owlmergerprototype.rest



data class MyPosts(
    var ok :Boolean=false,
    var posts:ArrayList<CommunityPost>
) {
}