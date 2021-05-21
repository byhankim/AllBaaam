package com.han.owlmergerprototype.rest

data class VerifyCode(
    var ok:Boolean,
    var code:String,
    var error:String
) {
}