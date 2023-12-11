package com.example.myapplication.Class

import Interface.CreateDate
import Interface.IAuditionRepository
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.myapplication.Model.AuditionRepositoryBase
import com.example.myapplication.Room.AuditionDateBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.O)
@Module
@InstallIn(SingletonComponent::class)
object AuditionModule {

    @Provides
    @Singleton
    fun provideDateBase(app : Application) : AuditionDateBase
    {
        return Room.databaseBuilder(app, AuditionDateBase::class.java, "audition_db").build()
    }

    @Provides
    fun provideAuditionRepository(db : AuditionDateBase): IAuditionRepository
    {
        return AuditionRepositoryBase(db)
    }

    @Provides
    fun provideCreateDate(createDateSimple : CreateDateSimple) : CreateDate
    {
        return CreateDateSimple()
    }
}