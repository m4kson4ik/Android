package com.example.myrjdapi.Repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myrjdapi.Interface.FlightsService
import com.example.myrjdapi.Interface.ITrainRepositoryAPI
import com.example.myrjdapi.Model.AllStation.Country
import com.example.myrjdapi.Model.AllStation.Region
import com.example.myrjdapi.Model.Flights
import com.example.myrjdapi.Model.NearestСity.NearestCity
import com.example.myrjdapi.Model.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Inject

data class TrainRouteResponse @Inject constructor(val search: TrainRouteData, val segments : List<SegmentResponse>)

data class SegmentResponse(
    val thread: Thread,
    val departure : String,
    val from : Station,
    val arrival : String,
    val to : Station
)

data class Thread(
    val number: String,
    val title: String,
    val short_title: String?,
    val express_type: String?,
    val transport_type: String
)

data class TrainRouteData(val from: Station, val to: Station)

data class Station(val type: String, val title: String, val code: String, val station_type: String, val station_type_name: String, val transport_type: String)

class TrainRepositoryAPI @Inject constructor() : ITrainRepositoryAPI {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URl).addConverterFactory(
        GsonConverterFactory.create()
    ).build()

    private val trainService = retrofit.create(FlightsService::class.java)

    override fun getFacts(
        from : String,
        to : String
    ): StateFlow<Pair<Status, Flights?>>
    {
        val result = trainService.getFacts(from, to)
        val stateFlow =MutableStateFlow<Pair<Status, Flights?>>(Status.Waiting to null)
        scope.launch {
                val res = result.execute()
                Log.d("mgkit", "res - $res.body()")
                if (res.isSuccessful)
                {
                    val body = res.body()
                    Log.d("mgkit", "body - $body")
                    if (body == null)
                    {
                        stateFlow.emit(Status.Error to null)
                    }
                    else
                    {
                        stateFlow.emit(Status.OK to body)
                    }
                }
                else
                {
                    stateFlow.emit(Status.Error to null)
                }
        }
        Log.d("mgkit", "stateFlowReturn - $stateFlow")
        return stateFlow
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFrom(from: String, to: String, date: String): StateFlow<Pair<Status, TrainRouteResponse?>> {
        val result = trainService.getFrom(from, to, date)
        val stateFlow = MutableStateFlow<Pair<Status, TrainRouteResponse?>>(Status.Waiting to null)
        scope.launch {
            try {
                val res = result.execute()
                Log.d("mgkit", "res - $res.body()")
                if (res.isSuccessful) {
                    val body = res.body()
                    Log.d("mgkit", "body - ${body}")
                    if (body == null) {
                        stateFlow.emit(Status.Error to null)
                    } else {

                        stateFlow.emit(Status.OK to body)
                    }

                } else {
                    stateFlow.emit(Status.Error to null)
                }
            }
            catch (ex : Exception)
            {
                stateFlow.emit(Status.Error to null)
            }
        }
        return stateFlow
    }

    override fun getNearestCity(lat: Double, lng: Double): StateFlow<Pair<Status, NearestCity?>> {
        val result = trainService.getNearestCity(lat, lng)
        val stateFlow =MutableStateFlow<Pair<Status, NearestCity?>>(Status.Waiting to null)
        scope.launch {
            try {
                val res = result.execute()
                Log.d("mgkit", "resCity - $res.body()")
                if (res.isSuccessful) {
                    val body = res.body()
                    Log.d("mgkit", "bodyCity - $body")
                    if (body == null) {
                        stateFlow.emit(Status.Error to null)
                    } else {
                        stateFlow.emit(Status.OK to body)
                    }
                } else {
                    stateFlow.emit(Status.Error to null)
                }
            }
            catch (ex : Exception)
            {
                stateFlow.emit(Status.Error to null)
            }
        }
        return stateFlow
    }

    override fun getStation(): StateFlow<Pair<Status,Country?>> {
        val result = trainService.getAllStation("Россия")
        val stateFlow =MutableStateFlow<Pair<Status,Country?>>(Status.Waiting to null)
        scope.launch {
            val res = result.execute()

            if (res.isSuccessful)
            {
                val body = res.body()
                if (body == null)
                {
                    stateFlow.emit(Status.Error to null)
                }
                else
                {
                    stateFlow.emit(Status.OK to body)
                }
            }
            else
            {
                stateFlow.emit(Status.Error to null)
            }
            Log.d("mgkit", "returnStation - $res")
        }
        return stateFlow
    }

    companion object {
        const val BASE_URl = "https://api.rasp.yandex.net"
    }
}