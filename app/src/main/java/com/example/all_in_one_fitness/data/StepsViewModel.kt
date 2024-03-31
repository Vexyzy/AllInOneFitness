package com.example.roomapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.all_in_one_fitness.data.Steps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StepsViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Steps>>
    private val repository: StepsRepository

    init {
        val userDao = StepsDatabase.getDatabase(application).userDao()
        repository = StepsRepository(userDao)
        readAllData = repository.readAllData
    }

    fun addSteps(steps: Steps){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSteps(steps)
        }
    }

    fun updateSteps(steps: Steps){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSteps(steps)
        }
    }

}