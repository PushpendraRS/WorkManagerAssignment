package com.example.workmanagerassignment.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Module {

//    @Provides
//    @Singleton
//    fun retrofitInstance() : Retrofit {
//        return Retrofit.Builder()
//              .baseUrl("https://jsonplaceholder.typicode.com")
//              .addConverterFactory(GsonConverterFactory.create())
//              .build()
//    }
//
//    @Provides
//    fun retrofitApi(retrofit: Retrofit) : ApiInterface = retrofit.create(ApiInterface::class.java)
//
//
//
//    @Singleton
//    @Provides
//    fun dbInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
//        context,
//        Database::class.java,
//        "progressDb"
//    ).build()
//
//    @Singleton
//    @Provides
//    fun getDB(db : Database) = db.getDao()
}