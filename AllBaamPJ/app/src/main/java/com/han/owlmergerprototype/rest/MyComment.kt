package com.han.owlmergerprototype.rest

import com.han.owlmergerprototype.data.Comment

data class MyComment(
    var ok :Boolean,
    var comments:ArrayList<Comment>
) {
}