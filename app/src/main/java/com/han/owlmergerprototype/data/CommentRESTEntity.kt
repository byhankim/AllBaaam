package com.han.owlmergerprototype.data

data class CommentRESTEntity(
    /*
    *       "comments": [
        {
          "id": 125,
          "createdAt": "2021-05-21T02:52:12.001Z",
          "updatedAt": "2021-05-21T02:52:12.002Z",
          "contents": "pigma 에 달린 댓글1",
          "userId": 26,
          "postId": 55,
          "isParent": true
        }
    * */
    var id: Int = -1,
    var createdAt: String = "1900-01-01T01:01:01.001Z",
    var updatedAt: String = "1900-01-01T01:01:01.001Z",
    var contents: String = "이건 댓글 불러오기 실패했을때 보이는 글입니다",
    var userId: Int = -1,
    var postId: Int = -1,
    var isParent: Boolean = true
)