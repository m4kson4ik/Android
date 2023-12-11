package com.olympia.model.di

import android.app.Application
import androidx.room.Room
import com.olympia.Interface.IOlympiaRepository
import com.olympia.OlympiaRepository
import com.olympia.model.DateBase.UserDAO
import com.olympia.model.DateBase.UsersDateBase
import com.olympia.model.database.DB
import com.olympia.model.repositories.*
import com.olympia.viewModels.useCases.AddUserData
import com.olympia.viewModels.useCases.DeleteUserData
import com.olympia.viewModels.useCases.GetUserData
import com.olympia.viewModels.useCases.GetUsers
import com.olympia.viewModels.useCases.UsersUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(app: Application): UsersDateBase {
        return Room.databaseBuilder(app, UsersDateBase::class.java, "olympiaTEST4.db").build()
    }


    @Provides
    fun provideOlympiaRepository(db: UsersDateBase): IOlympiaRepository {
        return OlympiaRepository(db)
    }

//    @Provides
//    @Singleton
//    fun providePushUpsRepository(db: DB): IPushUpsRepository {
//        return PushUpsRepository(db.pushUpsDao)
//    }
//    @Provides
//    @Singleton
//    fun provideSquatsRepository(db: DB): ISquatsRepository {
//        return SquatsRepository(db.squatsDao)
//    }
//    @Provides
//    @Singleton
//    fun provideAbsRepository(db: DB): IAbsRepository {
//        return AbsRepository(db.absDao)
//    }
//    @Provides
//    @Singleton
//    fun provideDatabaseUseCases(repository: IUserRepository): UsersUseCases {
//        return UsersUseCases(AddUserData(repository), DeleteUserData(repository), GetUsers(repository), GetUserData(repository))
//    }
}