package com.example.all_in_one_fitness.data_fitness

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FitnessViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Fitness>>
    private val repository: FitnessRepository

    init{
        val fitnessDao = FitnessDatabase.getDatabase(application).fitnessDao()
        repository = FitnessRepository(fitnessDao)
        readAllData = repository.readAllData
    }

    fun addFitness(fitness: Fitness){
        viewModelScope.launch(Dispatchers.IO){
            repository.addFitness(fitness)
        }
    }

    fun updateSteps(fitness: Fitness){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFitness(fitness)
        }
    }
}