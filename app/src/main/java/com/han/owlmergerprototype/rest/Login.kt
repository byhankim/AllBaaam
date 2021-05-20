package com.han.owlmergerprototype.rest

data class Login(
    var ok:Boolean = false,
    var token:String="") {
    //token은 shared preference에 저장
}