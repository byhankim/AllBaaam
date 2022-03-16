package com.han.owlmergerprototype.data

class CreatePostEntityMinimal(
    var contents: String = "x",
    var category: String = "x"
)

class CreatePostEntityImage(
    var contents: String = "x",
    var category: String = "x",
    var imageId: Int? = -1
)

class CreatePostEntityLocation(
    var contents: String = "x",
    var category: String = "x",
    var latitude: Double? = -1.0,
    var longitude: Double? = -1.0
)

class CreatePostEntityFull(
    var contents: String = "x",
    var category: String = "x",
    var imageId: Int? = -1,
    var latitude: Double? = -1.0,
    var longitude: Double? = -1.0
)