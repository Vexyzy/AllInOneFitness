package com.example.all_in_one_fitness.data

import androidx.lifecycle.LiveData
import com.example.all_in_one_fitness.data.Steps
import com.example.all_in_one_fitness.data.StepsDao

class StepsRepository(private val stepsDao: StepsDao) {

    val readAllData: LiveData<List<Steps>> = stepsDao.readAllData()

    suspend fun addSteps(steps: Steps){
        stepsDao.addSteps(steps)
    }

    suspend fun updateSteps(date: String, steps: Float, id: Int){
        stepsDao.updateSteps(date, steps, id)
    }

}