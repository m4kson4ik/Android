package com.example.myappusesensors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myappusesensors.DateBase.Plank
import kotlinx.coroutines.launch

class PlankViewModels(private val repository: PlankRepository) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
    val mutableStateFlowPlank = repository.getAllFlow()
    fun createPlank(vararg plankResult : Plank)
    {
        viewModelScope.launch {
            repository.create(*plankResult)
        }
    }

    suspend fun deletedPlank(plank: Plank)
    {
        repository.deleted(plank)
    }
}