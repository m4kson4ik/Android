package com.olympia

import android.util.Log
import com.olympia.Class.UserModel
import com.olympia.Interface.IOlympiaRepository
import com.olympia.model.DateBase.Users
import com.olympia.model.DateBase.UsersDateBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class OlympiaRepository @Inject constructor(private val dbUser : UsersDateBase) : IOlympiaRepository {
    private val usersDAO = dbUser.userDAO()
    //override fun getListUsers(): List<Users> = usersDAO.getListUsers()

    override fun getListFlowUsers() : Flow<List<Users>> = usersDAO.getListFLowUser()

    override suspend fun editingUser(user: Users) {
        usersDAO.insertUser(user)
    }

    override suspend fun createUsers(vararg users: Users){
        Log.d("mgkit", "Create")
        return usersDAO.insertUser(*users)
    }

    override suspend fun deletedUser(delUser: Users) {

    }
}