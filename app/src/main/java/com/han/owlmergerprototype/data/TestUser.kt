package com.han.owlmergerprototype.data

object TestUser {
    var userName:String =""
    var userID:Int=1
    var phoneCheck = false
    var token:String = ""

    fun initialTestUser(){
        this.userID = -1
        this.userName = ""
        this.token = ""
        this.phoneCheck = false
    }
}