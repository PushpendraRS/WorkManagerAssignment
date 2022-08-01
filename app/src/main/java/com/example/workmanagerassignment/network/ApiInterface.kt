package com.example.workmanagerassignment.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("/posts")
    suspend fun getPosts() : Response<List<UserDataItem>>

    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build()
                .create(ApiInterface::class.java)
        }
    }
}