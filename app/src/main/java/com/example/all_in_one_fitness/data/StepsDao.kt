package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.all_in_one_fitness.data.Steps

@Dao
interface StepsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSteps(steps: Steps)

    @Update
    suspend fun updateSteps(steps: Steps)

    @Query("SELECT * FROM steps_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Steps>>

}