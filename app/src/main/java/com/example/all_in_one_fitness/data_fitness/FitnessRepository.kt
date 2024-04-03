package com.example.all_in_one_fitness.data_fitness

import androidx.lifecycle.LiveData

class FitnessRepository(private val fitnessDao: FitnessDao) {

    val readAllData: LiveData<List<Fitness>> = fitnessDao.readAllData()

    suspend fun addFitness(fitness: Fitness){
        fitnessDao.addFitness(fitness)
    }

    suspend fun updateFitness(fitness: Fitness){
        fitnessDao.updateFitness(fitness)
    }
}