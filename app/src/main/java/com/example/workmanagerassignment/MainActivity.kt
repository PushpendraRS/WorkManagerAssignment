package com.example.workmanagerassignment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.workmanagerassignment.databinding.ActivityMainBinding
import com.example.workmanagerassignment.db.AppDatabase
import com.example.workmanagerassignment.db.Dao
import com.example.workmanagerassignment.workmanager.DatabaseManager
import com.example.workmanagerassignment.workmanager.NetworkManager
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    /*  Task 1: Create an app which will fetch the data from api and save it locally
        (You can use Room or Sharedpref). It will have two separate worker.
        One for fetching data from api and second one is of saving data --> One Time Request */

    /*  Task 2: Create an example in which you will fetch the data from api and it's
       going to repeat itself after 15 mins. You can use any UI or foreground
       notification to display the changes---> Periodic Request  */

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : Adapter
    private val db: Dao
        get() = AppDatabase.getDatabase(this).getDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setAdapter()
        setObserver()
        setClickListener()
    }

    private fun setClickListener() {

        binding.oneTime.setOnClickListener {
            setOneTimeWorker()
        }

        binding.periodic.setOnClickListener {
            setPeriodicWorker()
        }
    }

    private fun getDataFromDB() {
        lifecycleScope.launch {
            db.getUsersFromDB().collect {
                // setAdapter(it as ArrayList)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setOneTimeWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val data        = Data.Builder().putString(Constants.WORKER_TYPE, "One Time Request")

        val networkRequest = OneTimeWorkRequestBuilder<NetworkManager>()
            .addTag("oneTimeWork")
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        val dbWork = OneTimeWorkRequestBuilder<DatabaseManager>()
            .addTag("oneTimeDbWork")
            .build()

        val worker = WorkManager.getInstance(this)

        worker.beginUniqueWork("oneTimeWork", ExistingWorkPolicy.KEEP, networkRequest)
            .then(dbWork)
            .enqueue()

        worker.getWorkInfoByIdLiveData(networkRequest.id).observe(this) {
            when (it?.state) {
                WorkInfo.State.RUNNING -> {
                    binding.txtStatus.text = "Network Worker Running"
                }

                WorkInfo.State.ENQUEUED -> {
                    binding.txtStatus.text = "Network Worker Enqueued"
                }

                WorkInfo.State.CANCELLED -> {
                    binding.txtStatus.text = "Network Worker Cancelled"
                }

                WorkInfo.State.SUCCEEDED -> {
                    binding.txtStatus.text = "Network Worker Succeeded"
                }

                else -> {}
            }

            worker.getWorkInfoByIdLiveData(dbWork.id).observe(this) {
                when (it?.state) {
                    WorkInfo.State.RUNNING -> {
                        binding.txtStatus.text = "Database Worker Running"
                    }

                    WorkInfo.State.ENQUEUED -> {
                        binding.txtStatus.text = "Database Worker Enqueued"
                    }

                    WorkInfo.State.CANCELLED -> {
                        binding.txtStatus.text = "Database Worker Cancelled"
                    }

                    WorkInfo.State.SUCCEEDED -> {
                        binding.txtStatus.text = "Database Worker Succeeded"
                    }
                    else -> {}
                }
            }

        }
    }


    @SuppressLint("SetTextI18n")
    private fun setPeriodicWorker() {
        val constraints     = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val data            = Data.Builder().putString(Constants.WORKER_TYPE, "Periodic Request")

        val periodicRequest = PeriodicWorkRequestBuilder<NetworkManager>(10, TimeUnit.MINUTES)
                .addTag("periodicWork")
                .setInputData(data.build())
                .setConstraints(constraints)
                .build()

        val worker = WorkManager.getInstance(this)
            worker.enqueue(periodicRequest)

        worker.getWorkInfoByIdLiveData(periodicRequest.id).observe(this){
            when (it?.state) {
                WorkInfo.State.RUNNING -> {
                    binding.txtStatus.text = "Periodic Worker Running"
                }

                WorkInfo.State.ENQUEUED -> {
                    binding.txtStatus.text = "Periodic Worker Enqueued"
                }

                WorkInfo.State.CANCELLED -> {
                    binding.txtStatus.text = "Periodic Worker Cancelled"
                }

                WorkInfo.State.SUCCEEDED -> {
                    binding.txtStatus.text = "Periodic Worker Succeeded"
                }
                else -> {}
            }
        }
    }

    private fun setObserver(){
        lifecycleScope.launch {
            Utils.data.collect {
                Log.d("TAG", "setAdapter: updated")
                adapter.updateList(it)
            }
        }
    }


    private fun setAdapter() {
        adapter = Adapter(arrayListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}