package com.example.myrjdapi.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrjdapi.Model.AllStation.Country
import com.example.myrjdapi.Model.AllStation.Station
import com.example.myrjdapi.Repository.TrainRepositoryAPI
import com.example.myrjdapi.Repository.TrainRepositoryBasic
import com.example.myrjdapi.Room.LikedThread
import com.example.myrjdapi.Room.StationDB
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

enum class SelectedStation
{
    START, END
}

enum class ShowingResult
{
    Wait, Start, NoShow
}
@HiltViewModel
class TrainViewModel @Inject constructor(private val repository : TrainRepositoryBasic, private val repositoryAPI: TrainRepositoryAPI) : ViewModel() {
    val listLikedThreadMutableStateFlow = repository.getAllFlowLikedThread()

    val listStationMutableStateFlow = repository.getAllFlow()
    var flowStation by mutableStateOf(listOf<StationDB>())
    val listSelectedStation = MutableStateFlow(SelectedStation.START)

    var startStation = MutableStateFlow(StationDB(null, null, "", null,null, null))
    var endStation = MutableStateFlow(StationDB(null, null, "", null,null, null))

    val showResult = MutableStateFlow(ShowingResult.NoShow)

        init {
        viewModelScope.launch {
            listStationMutableStateFlow.collect{
                flowStation = it
            }
        }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getStation()
    {
        val thisDate = LocalDateTime.now().plusDays(7)
        viewModelScope.launch {
        Log.d("mgkit","date$thisDate")
        val correctDate = flowStation.filter {
             LocalDateTime.parse(it.dateCreate) > thisDate
        }
        Log.d("mgkit", "flowCount = ${flowStation.count()}")
        Log.d("mgkit", "date${correctDate}")
        if (correctDate.count() > 1 && flowStation.isEmpty()) {
                val item = repositoryAPI.getStation()
                Log.d("mgkit", "viewModelCountries - ${item.value.second}")
                item.collect {
                    download(it.second)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun download(country: Country?)
    {
        if (country != null) {
            for (countryItem in country.countries) {
                if (countryItem.title == "Россия") {
                    for (region in countryItem.regions) {
                        if (region.title == "Москва и Московская область") {
                            for (settlement in region.settlements) {
                                for (station in settlement.stations) {
                                    if ((station.station_type == "station" || station.station_type == "platform") && (station.direction == "МЦД-2" || station.direction == "МЦД-3" || station.direction == "МЦД-4" || station.direction == "Рижское" || station.direction == "Курское") && station.transport_type == "train") {
                                        Log.d("mgkit", "station$station")
                                        repository.insert(
                                            StationDB(
                                                station.direction,
                                                station.title,
                                                station.codes.yandex_code,
                                                station.station_type,
                                                station.transport_type,
                                                LocalDateTime.now().toString()
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun findStationByTitle(country: Country?, title: String): List<Station> {
        if (country != null) {
            for (countryItem in country.countries) {
                //Log.d("mgkit", countryItem.title)
                for (region in countryItem.regions) {
                    //Log.d("mgkit", region.title)
                    for (settlement in region.settlements) {
                       // Log.d("mgkit", "gorod" + settlement.title)
                        if (settlement.title == title) {
                            //Log.d("mgkit","return" + settlement.stations.toString())
                            return settlement.stations.filter { it.transport_type == "train" }
                        }
                    }
                }
            }
        }
        return emptyList()
    }
    suspend fun insert(item : StationDB)
    {
        repository.insert(item)
    }

    suspend fun deleted(item : StationDB)
    {
        repository.deleted(item)
    }

    suspend fun insertLikedThread(item: LikedThread)
    {
        repository.insertLikedThread(item)
        Log.d("mgkit", "itemCreate$item")
        Log.d("mgkit", "Liked${listLikedThreadMutableStateFlow.toString()}")
    }

    suspend fun deletedLikedThread(item : LikedThread)
    {
        repository.deletedLikedThread(item)
    }
}