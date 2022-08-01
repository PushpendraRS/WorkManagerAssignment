package com.example.workmanagerassignment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun add(progress : UserEntity)

  @Query("Select * from users")
  fun getUsersFromDB() : Flow<List<UserEntity>>
}