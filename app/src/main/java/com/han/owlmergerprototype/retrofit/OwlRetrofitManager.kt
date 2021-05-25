package com.han.owlmergerprototype.retrofit

import com.han.owlmergerprototype.common.ADDRESS
import com.han.owlmergerprototype.common.RetrofitRESTService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class OwlRetrofitManager {
    object OwlRestService {
        var owlRestService: RetrofitRESTService = Retrofit.Builder()
            .baseUrl(ADDRESS)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitRESTService::class.java)
    }
}