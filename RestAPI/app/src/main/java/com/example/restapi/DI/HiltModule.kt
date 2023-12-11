package com.example.restapi.DI

import android.app.Application
import androidx.room.Room
import com.example.restapi.DateBase.CatDateBase
import com.example.restapi.Interface.IRepositoryCats
import com.example.restapi.Interface.IWorkRepository
import com.example.restapi.Repository.CatRepositoryAPI
import com.example.restapi.Repository.CatRepositoryRoom
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideDateBase(app : Application) : CatDateBase
    {
        return Room.databaseBuilder(app, CatDateBase::class.java, "cat1_db").build()
    }

    @Provides
    fun provideCatRepositoryApi() : IRepositoryCats
    {
        return CatRepositoryAPI()
    }

    @Provides
    fun provideCatRepositoryBasic(db : CatDateBase) : IWorkRepository
    {
        return CatRepositoryRoom(db)
    }
}