package com.han.owlmergerprototype.data

class BookmarkEntity(
    /*
    *       "post": {
        "id": 53,
        "createdAt": "2021-05-21T02:30:27.078Z",
        "contents": "안뇽하세요 리버풀 챔스 가고싶어용",
        "category": "SPORTS",
        "images": []
    * */
    var id: Int = -1,
    var createdAt: String = "1500-05-21T02:30:27.078Z",
    var contents: String = "북마크 불러오기 실패했을때 보이는 글",
    var category: String = "EMPTY",
    var images : MutableList<ImageEntity>
)