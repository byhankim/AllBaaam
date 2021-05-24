package com.han.owlmergerprototype.data

data class SimplePostEntity(
    var id: Int = -1,
    var createdAt: String = "1500-05-05T0109:09:09:050Z",
    var updatedAt: String = "1500-05-05T0109:09:09:050Z",
    var contents: String = "지금 이 문장이 나온다면 post 가져오는데 실패한 것입니다..!",
    var category: String = "ERROR"
)