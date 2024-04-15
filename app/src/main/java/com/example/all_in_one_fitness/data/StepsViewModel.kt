package com.example.all_in_one_fitness.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepsViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Steps>>
    private val repository: StepsRepository

    init {
        val stepsDao = StepsDatabase.getDatabase(application).stepsDao()
        repository = StepsRepository(stepsDao)
        readAllData = repository.readAllData
    }

    fun addSteps(steps: Steps){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSteps(steps)
        }
    }

    fun updateSteps(date: String, steps: Float, id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSteps(date, steps, id)
        }
    }

}