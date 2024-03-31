package com.example.roomapp.data

import androidx.lifecycle.LiveData
import com.example.all_in_one_fitness.data.Steps

class StepsRepository(private val stepsDao: StepsDao) {

    val readAllData: LiveData<List<Steps>> = stepsDao.readAllData()

    suspend fun addSteps(steps: Steps){
        stepsDao.addSteps(steps)
    }

    suspend fun updateSteps(steps: Steps){
        stepsDao.updateSteps(steps)
    }

}