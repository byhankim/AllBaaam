package com.han.owlmergerprototype.map

import com.han.owlmergerprototype.dataMapLibrary.Library
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


object SeoulOpenApi {
    val DOMAIN = "http://openapi.seoul.go.kr:8088/"
    val API_KEY = "597346537a6666663739667470686f"

}

interface SeoulOpenService {

    @GET("/{key}/JSON/SeoulPublicLibraryInfo/1/5/")
    fun getLibrary(@Path("key") key: String) : Call<Library>
}