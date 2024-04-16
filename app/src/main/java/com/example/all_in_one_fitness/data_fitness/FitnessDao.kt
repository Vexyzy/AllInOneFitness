package com.example.all_in_one_fitness.data_fitness

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface FitnessDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFitness(fitness: Fitness)

    @Update
    suspend fun updateFitness(fitness: Fitness)

    @Delete
    suspend fun deleteFitness(fitness: Fitness)

    @Query("DELETE FROM FITNESS_TABLE")
    suspend fun deleteAll()

    @Query("SELECT * FROM fitness_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Fitness>>

}