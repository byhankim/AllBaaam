package com.han.owlmergerprototype.data

data class User(
        var id: Int,
        var createdAt: String,
        var updatedAt: String,
        var userName: String,
        var role: String = "USER",
        var verified: Boolean = false,
        var profider: String? = null,
        var kakaoID: Int? = null,
        var naverID: Int? = null,
        var phone: String? = null,
        var act: Int = 0
)