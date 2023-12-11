package com.example.restapi.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restapi.DateBase.CatDB
import com.example.restapi.Interface.IWorkRepository
import com.example.restapi.Repository.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val repositoryBasic: IWorkRepository) : ViewModel() {
    var statusInternet = MutableStateFlow(Status.None)

    private var listCatMutableStateFlow : Flow<List<CatDB>> = repositoryBasic.getAllFlow()

    var flowCat by mutableStateOf(emptyList<CatDB>())

    init {
         viewModelScope.launch {
            listCatMutableStateFlow.collect {
            flowCat = it
         }
         }
    }

    suspend fun insert (vararg item: CatDB)
    {
        repositoryBasic.insert(*item)
    }

    suspend fun deleted()
    {
        repositoryBasic.deleted()
    }
}