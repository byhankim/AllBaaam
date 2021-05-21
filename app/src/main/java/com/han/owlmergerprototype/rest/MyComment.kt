package com.han.owlmergerprototype.rest

import com.han.owlmergerprototype.data.Comment
import com.han.owlmergerprototype.data.Post

data class MyComment(
    var ok :Boolean,
    var comments:ArrayList<Comment>
) {
}