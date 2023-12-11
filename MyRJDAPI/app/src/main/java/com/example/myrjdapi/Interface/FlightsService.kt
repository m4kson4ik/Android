package com.example.myrjdapi.Interface

import com.example.myrjdapi.Model.AllStation.Country
import com.example.myrjdapi.Model.Flights
import com.example.myrjdapi.Model.NearestСity.NearestCity
import com.example.myrjdapi.Repository.TrainRouteResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightsService {
    @GET("/v3.0/search/?apikey=14c313a0-652b-45b9-a0f2-7fb1e77e002e")

    fun getFacts(
        @Query("from") from : String,
        @Query("to") to: String,
    ) : Call<Flights>


    @GET("/v3.0/search/?apikey=14c313a0-652b-45b9-a0f2-7fb1e77e002e")
    fun getFrom(
        @Query("from") from : String,
        @Query("to") to: String,
        @Query("date") date: String,
    ) : Call<TrainRouteResponse>



    @GET("/v3.0/nearest_settlement/?apikey=14c313a0-652b-45b9-a0f2-7fb1e77e002e")
    fun getNearestCity(
        @Query("lat") lat : Double,
        @Query("lng") lng : Double,
        ) : Call<NearestCity>

    @GET("/v3.0/stations_list/?apikey=14c313a0-652b-45b9-a0f2-7fb1e77e002e")
    fun getAllStation(@Query("countryTitle") countryTitle: String) : Call<Country>
}