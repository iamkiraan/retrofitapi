package com.example.retrifiapi

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("be8515")
    fun getProductData(): Call<MyData>
}
