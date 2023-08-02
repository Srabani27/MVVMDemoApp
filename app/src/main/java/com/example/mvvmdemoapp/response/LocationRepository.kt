package com.example.mvvmdemoapp.response

import com.example.mvvmdemoapp.api.LocationApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocationRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://43.204.34.133/tempfiles/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(LocationApi::class.java)

    fun getLocationPoints(callback: (List<LocationPoint>) -> Unit) {
        api.getLocationPoints().enqueue(object : Callback<LocationResponse> {
            override fun onResponse(
                call: Call<LocationResponse>,
                response: Response<LocationResponse>
            ) {
                if (response.isSuccessful) {
                    val locationResponse = response.body()
                    locationResponse?.points?.let {
                        callback(it)
                    }
                }
            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}