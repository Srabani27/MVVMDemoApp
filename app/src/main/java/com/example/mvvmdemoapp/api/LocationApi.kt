package com.example.mvvmdemoapp.api

import com.example.mvvmdemoapp.response.LocationResponse
import retrofit2.Call
import retrofit2.http.GET

interface LocationApi {

    @GET("tracker.json")
    fun getLocationPoints(): Call<LocationResponse>
}
