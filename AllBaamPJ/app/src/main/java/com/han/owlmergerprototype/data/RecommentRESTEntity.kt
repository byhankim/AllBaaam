package com.han.owlmergerprototype.data

data class RecommentRESTEntity(
    /*"reComments": [
{
    "id": 159,
    "createdAt": "2021-05-26T02:59:09.930Z",
    "updatedAt": "2021-05-26T02:59:09.985Z",
    "contents": "대댓글1",
    "userId": 26,
    "user": {
    "userName": "앉아있는 사촌돼지"
}
}*/
    var id: Int = -1,
    var createdAt: String = "2000-05-26T02:59:09.930Z",
    var updatedAt: String = "2000-05-26T02:59:09.930Z",
    var contents: String = "",
    var userId: Int = -1,
    var user: UserNameEntity = UserNameEntity()
)