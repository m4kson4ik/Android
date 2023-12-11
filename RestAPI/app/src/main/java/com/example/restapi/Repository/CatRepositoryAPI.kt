package com.example.restapi.Repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.restapi.DateBase.CatDB
import com.example.restapi.Interface.CatService
import com.example.restapi.Interface.IRepositoryCats
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


data class Fact(
    val text : String,
    val type : String,
    val updatedAt : String
)

//data class CatDB (
//    val text : String,
//    val type : String,
//    val updatedAt : LocalDate
//)

enum class Status
{
    Waiting,
    OK,
    Error,
    None,
}

class CatRepositoryAPI @Inject constructor() : IRepositoryCats {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    private val catService = retrofit.create(CatService::class.java)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFacts(animalType : String, amount : Int) : StateFlow<Pair<Status, List<CatDB>?>>
    {
        val result = catService.getFacts(animalType, amount)
        val stateFlow = MutableStateFlow<Pair<Status, List<CatDB>?>>(Status.Waiting to null)
        scope.launch {
            try {
                val res = result.execute()
                if (res.isSuccessful) {
                    val body = res.body()
                    if (body == null) {
                        stateFlow.emit(Status.Error to null)
                    } else {
                        Log.d("mgkit", body.toString())
                        stateFlow.emit(Status.OK to body.map {
                            val date = LocalDate.parse(it.updatedAt, DateTimeFormatter.ISO_DATE_TIME)
                            CatDB(
                                it.text,
                                it.type,
                                date.toString()
                            )
                        })
                    }
                } else {
                    stateFlow.emit(Status.Error to null)
                }
            } catch (ex : Exception)
            {
                stateFlow.emit(Status.Error to null)
            }
        }
        return stateFlow
    }

    companion object
    {
        const val BASE_URL = "https://cat-fact.herokuapp.com"
    }
}