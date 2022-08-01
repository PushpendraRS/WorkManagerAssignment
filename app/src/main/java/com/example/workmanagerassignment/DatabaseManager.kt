package com.example.workmanagerassignment

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerassignment.db.AppDatabase
import com.example.workmanagerassignment.db.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch

class DatabaseManager(private val context: Context,params: WorkerParameters) : Worker(context,params) {
    override fun doWork(): Result {

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(context)
            Utils.data.collect {
             it.forEach {
                 Log.d("TAG", "doWorkDB: $it")
                 db.getDao().add(UserEntity(it.id,it.body,it.title,it.userId))
              }
            }
        }

        return Result.success()
    }
}