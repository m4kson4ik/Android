package com.example.myrjdapi.Interface

import com.example.myrjdapi.Model.AllStation.Country
import com.example.myrjdapi.Model.AllStation.Region
import com.example.myrjdapi.Model.AllStation.Station
import com.example.myrjdapi.Model.Flights
import com.example.myrjdapi.Model.NearestСity.NearestCity
import com.example.myrjdapi.Model.Status
import com.example.myrjdapi.Repository.TrainRouteResponse
import kotlinx.coroutines.flow.StateFlow

interface ITrainRepositoryAPI {
    fun getFacts(from : String = "s9601122", to : String = "s9600741") : StateFlow<Pair<Status, Flights?>>

    fun getFrom(from : String = "s9601122", to : String = "s9600741", date : String) : StateFlow<Pair<Status, TrainRouteResponse?>>

    fun getNearestCity(lat : Double, lng : Double) : StateFlow<Pair<Status, NearestCity?>>
    fun getStation(): StateFlow<Pair<Status, Country?>>
}