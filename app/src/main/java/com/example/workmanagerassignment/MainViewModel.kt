package com.example.workmanagerassignment

import androidx.lifecycle.ViewModel
import com.example.workmanagerassignment.models.UserDataItem
import retrofit2.Response

class MainViewModel(private val repo : MainRepository) : ViewModel() {

    suspend fun getPosts() : Response<List<UserDataItem>>{
        return repo.getPosts()
    }
}