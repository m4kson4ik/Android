package com.olympia.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olympia.extensions.Info
import com.olympia.model.entities.DUsers
import com.olympia.model.verification.CheckBirthday
import com.olympia.model.verification.CheckEmail
import com.olympia.model.verification.CheckGender
import com.olympia.model.verification.CheckPassword
import com.olympia.model.verification.CheckUserName
import com.olympia.viewModels.useCases.UsersUseCases
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

//@OptIn(ExperimentalCoroutinesApi::class)
//@HiltViewModel
//class UsersViewModel @Inject constructor(private val useCases: UsersUseCases) : ViewModel() {
//    private val userList = mutableListOf<DUsers>()
//    private val userStateFlow = MutableStateFlow<DUsers?>(null)
//    val userState = userStateFlow.asStateFlow()
//    private val checkUserName = CheckUserName()
//    private val checkPassword = CheckPassword()
//    private val checkGender = CheckGender()
//    private val checkBirthday = CheckBirthday()
//    private val checkEmail = CheckEmail()
//
//
//
//    suspend fun addUserData(userName: String, password: String, gender: String, dataOfBirth: String, email: String) {
//        useCases.addUserData(DUsers(userName, password, gender, dataOfBirth, email))
//    }
//
//    fun getUserData() {
//        var currentId = userList[0].id
//        var currentAccount = userList[0].registerDate
//        if (userList.size > 1) {
//            for (i in userList) {
//                if (currentAccount > i.registerDate) {
//                    currentId = i.id
//                    currentAccount = i.registerDate
//                }
//            }
//        }
//        viewModelScope.launch {
//            userStateFlow.emit(useCases.getUserData(currentId))
//        }
//    }
//    fun deleteUserData(user: DUsers?) {
//        if (user != null) {
//            viewModelScope.launch {
//                useCases.deleteUserData(user)
//            }
//        }
//    }
//    fun getList(): List<DUsers> {
//        userList.clear()
//        viewModelScope.launch {
//            val all = useCases.getUsers().flatMapConcat { it.asFlow() }.toList()
//            for (i in all) userList.add(i)
//        }
//        return userList.toList()
//    }
//}

//class AccountInf : ViewModel() {
//    fun changeData(un: String, pass: String, g: String, d: String, e: String) {
//        Info.USERNAME = un
//        Info.PASSWORD = pass
//        Info.SEX = g
//        Info.BIRTHDAY = d
//        Info.EMAIL = e
//    }
//    fun clearData() {
//        Info.USERNAME = ""
//        Info.PASSWORD = ""
//        Info.SEX = ""
//        Info.BIRTHDAY = ""
//        Info.EMAIL = ""
//    }
//}