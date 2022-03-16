package com.han.owlmergerprototype.data

data class PostEntity(

    /*
    * "id": 57,
      "createdAt": "2021-05-21T04:52:31.050Z",
      "updatedAt": "2021-05-21T04:52:31.050Z",
      "contents": "안뇽하세요 리버풀 챔스 가고싶어용",
      "category": "SPORTS",
      "userId": 25,
      "user": {
        "userName": "배아픈 고양이"
      },
      "images": [
        {
          "id": 24,
          "url": "https://owlservice.s3.ap-northeast-2.amazonaws.com/uploads/25-1621572629386-%EC%A0%9C%ED%8E%98%ED%86%A0%EC%88%98%ED%98%842.png"
        }
      ],
      "comments": [],
      "like": [],
      "bookmark":
    * */
    var id: Int = -1,
    var createdAt: String = "2020-05-05T09:09:09.050Z",
    var updatedAt: String = "2020-05-05T09:09:09.050Z",
    var contents: String = "지금 이 문장이 나온다면 post 가져오는데 실패한 것입니다..!",
    var category: String = "ERROR",
    var userId: Int = -1,
    var user: UserNameEntity = UserNameEntity(), // should allow nullable?
    var images: List<ImageEntity> = listOf(ImageEntity()), // id, url
    var comments: List<CommentRESTEntity> = listOf(CommentRESTEntity()),
    var like: List<BookmarkInPostEntity> = listOf(BookmarkInPostEntity()), // temporary
    var bookmark: List<BookmarkInPostEntity> = listOf(BookmarkInPostEntity()),
    var popular: Int? = -1 // 될랑가 모르겠다

)