package com.olympia.Interface

import com.olympia.Class.UserModel
import com.olympia.OlympiaRepository
import com.olympia.model.DateBase.Users
import kotlinx.coroutines.flow.Flow
interface IOlympiaRepository {
    //fun getListUsers() : List<Users>
    fun getListFlowUsers(): Flow<List<Users>>
    suspend fun editingUser(user: Users)
    suspend fun createUsers(vararg users: Users)
    suspend fun deletedUser(delUser: Users)
}