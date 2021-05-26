package com.han.owlmergerprototype.data

data class CommentRESTEntity(
    /*
    * {
      "id": 136,
      "createdAt": "2021-05-24T08:26:56.871Z",
      "updatedAt": "2021-05-24T08:26:56.872Z",
      "contents": "댓글사냥꾼1",
      "userId": 26,
      "user": {
        "userName": "앉아있는 사촌돼지"
      },
      "postId": 78,
      "isParent": true,
      "reComments": []
    }
    * */
    var id: Int = -1,
    var createdAt: String = "1900-01-01T01:01:01.001Z",
    var updatedAt: String = "1900-01-01T01:01:01.001Z",
    var contents: String = "이건 댓글 불러오기 실패했을때 보이는 글입니다",
    var userId: Int = -1,
    var user: UserNameEntity = UserNameEntity(),
    var postId: Int = -1,
    var isParent: Boolean = true,
    var reComments: MutableList<RecommentRESTEntity> = mutableListOf()
)