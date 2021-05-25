package com.han.owlmergerprototype.map

import com.han.owlmergerprototype.dataMapCmnt.MapCmnt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

object MapCmntApi {
    const val ADDRESS = "https://d4f88a051f32.ngrok.io/"
    const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjYsImlhdCI6MTYyMTU2Mjc0N30._kpcxNJa6zDk40EfKKwlAaGf0kJs6vNdeFHAd4qdIXc"
}

interface MapCmntService  {

    @GET("/posts/map")
    fun getMapCmnt(
//        @Path("key") key: String?,
        @Header("token") token: String?
    ) : Call<MapCmnt>
}