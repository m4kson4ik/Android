package com.example.myrjdapi.DI

import android.app.Application
import androidx.room.PrimaryKey
import androidx.room.Room
import com.example.myrjdapi.Interface.ITrainRepositoryAPI
import com.example.myrjdapi.Interface.ITrainRepositoryBasic
import com.example.myrjdapi.Repository.TrainRepositoryAPI
import com.example.myrjdapi.Repository.TrainRepositoryBasic
import com.example.myrjdapi.Room.TrainDateBase
import com.example.myrjdapi.ViewModel.TrainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideDateBase(app : Application) : TrainDateBase
    {
        return Room.databaseBuilder(app, TrainDateBase::class.java, "train1_db").build()
    }

    @Provides
    fun provideRepositoryAPI() : ITrainRepositoryAPI
    {
        return TrainRepositoryAPI()
    }

    @Provides
    fun provideRepositoryBasic(db : TrainDateBase) : ITrainRepositoryBasic
    {
        return TrainRepositoryBasic(db)
    }
    @Provides
    fun provideViewModel(rep1 : TrainRepositoryBasic, rep2 : TrainRepositoryAPI): TrainViewModel {
        return TrainViewModel(rep1, rep2)
    }
}