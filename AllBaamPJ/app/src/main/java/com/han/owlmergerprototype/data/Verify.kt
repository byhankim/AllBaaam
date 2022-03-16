package com.han.owlmergerprototype.data

data class Verify(
        var verifyCode: String,
        var phone: String,
        var userID: Int,
        var id: Int,
        var createdAt: String,
        var updatedAt: String,
        var expireTime: String
)