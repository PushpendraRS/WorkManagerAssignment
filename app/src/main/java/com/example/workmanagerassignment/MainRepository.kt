package com.example.workmanagerassignment

import com.example.workmanagerassignment.db.Dao
import com.example.workmanagerassignment.network.ApiInterface
import com.example.workmanagerassignment.network.UserDataItem
import retrofit2.Response


class MainRepository(private val apiInterface : ApiInterface, private val dao : Dao){

    suspend fun getPosts() : Response<List<UserDataItem>>{
        return apiInterface.getPosts()
    }
}
