package com.han.owlmergerprototype.data

/*
Gson().toJson(CreatePostEntityFull(
                binding.commWriteArticleContentEt.text.toString(),
                "FOOD",
                imageId,
                latitude,
                longitude
            ))
* */

class CreateCommentEntity(var contents: String = "", var postId: Int = -1)

class CreateReCommentEntity(var contents: String = "", var postId: Int = -1, var parentId: Int = -1)