package com.han.owlmergerprototype.data

data class PopularPostEntity(
    var id: Int = -1,
    var createdAt: String = "1500-05-05T0109:09:09:050Z",
    var updatedAt: String = "1500-05-05T0109:09:09:050Z",
    var contents: String = "지금 이 문장이 나온다면 post 가져오는데 실패한 것입니다..!",
    var category: String = "ERROR",
    var userId: List<String>, // 유저 객체로 변할 수도 있음!
    var images: List<ImageEntity>, // id, url
    var comments: List<CommentRESTEntity>,
    var like: List<BookmarkInPostEntity>, // temporary
    var bookmark: List<BookmarkInPostEntity>,
    var popular: Int = -1
)