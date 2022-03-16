package com.han.owlmergerprototype.data

data class BookmarkInPostEntity(
    /*
    *           "id": 155,
          "createdAt": "2021-05-21T04:44:02.115Z",
          "updatedAt": "2021-05-21T04:44:02.115Z",
          "userId": 25,
          "postId": 54
    * */
    var id: Int = -1,
    var createdAt: String = "1900-05-21T04:44:02.115Z",
    var updatedAt: String = "1900-05-21T04:44:02.115Z",
    var userId: Int = -1,
    var postId: Int = -1
)