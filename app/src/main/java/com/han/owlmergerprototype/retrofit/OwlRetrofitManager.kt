package com.han.owlmergerprototype.retrofit

import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.RetrofitRESTService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OwlRetrofitManager {
    object OwlRestService {
        var owlRestService: RetrofitRESTService = Retrofit.Builder()
            .baseUrl(ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitRESTService::class.java)
    }
}