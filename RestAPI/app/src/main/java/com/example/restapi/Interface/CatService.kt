package com.example.restapi.Interface


import com.example.restapi.Repository.Fact
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.temporal.TemporalAmount

interface CatService {
    @GET("/facts/random/")
    fun getFacts(@Query("animal_type") animalType : String,
                 @Query("amount") amount: Int,
    ) : Call<List<Fact>>
}