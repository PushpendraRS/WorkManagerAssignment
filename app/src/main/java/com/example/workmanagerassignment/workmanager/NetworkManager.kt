package com.example.workmanagerassignment.workmanager

import android.content.Context
import androidx.work.*
import com.example.workmanagerassignment.Constants
import com.example.workmanagerassignment.Utils
import com.example.workmanagerassignment.network.ApiInterface.Companion.instance
import com.example.workmanagerassignment.models.UserDataItem
import com.example.workmanagerassignment.showNotification

class NetworkManager(private val context : Context, private val params: WorkerParameters) : CoroutineWorker(context,params){
    override suspend fun doWork(): Result {

       val data = params.inputData.getString(Constants.WORKER_TYPE) ?: "Not Available"

       val response = instance.getPosts()

       response.body().let {
           showNotification(data,context)
           Utils.data.value = it as ArrayList<UserDataItem>
       }

        if (!response.isSuccessful) {
            if (response.code().toString().startsWith("5")) {
                return Result.retry()
            }
            return Result.failure(
                workDataOf(
                   "response" to " invalid server response"
                )
            )
        }


//       CoroutineScope(Dispatchers.IO).launch {
//           val response = instance.getPosts()
//           if (response.isSuccessful) {
//               response.body()
//               showNotification(data,context)
//               Utils.data.value = response.body() as ArrayList<UserDataItem>
//               Log.d("TAG", "doWork: ${Gson().toJson(response.body())}")
//           }else {
//               return@launch workDataOf("" to "")
//           }
//       }


       return Result.success()
    }
}