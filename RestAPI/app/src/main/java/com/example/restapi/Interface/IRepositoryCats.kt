package com.example.restapi.Interface

import com.example.restapi.DateBase.CatDB
import com.example.restapi.Repository.Status
import kotlinx.coroutines.flow.StateFlow

interface IRepositoryCats {
    fun getFacts(animalType : String = "cat", amount : Int = 1) : StateFlow<Pair<Status, List<CatDB>?>>
}