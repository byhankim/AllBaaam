package com.han.owlmergerprototype.data

class CreatePostEntityMinimal(
    var contents: String = "x",
    var category: String = "x"
)

class CreatePostEntityImage(
    var contents: String = "x",
    var category: String = "x",
    var imageId: String? = ""
)

class CreatePostEntityLocation(
    var contents: String = "x",
    var category: String = "x",
    var latitude: String? = "",
    var longitude: String? = ""
)

class CreatePostEntityFull(
    var contents: String = "x",
    var category: String = "x",
    var imageId: String? = "",
    var latitude: String? = "",
    var longitude: String? = ""
)