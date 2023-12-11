package com.olympia.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AccountInf: ViewModel() {
    private val userName = MutableStateFlow("")
    val unState = userName.asStateFlow()
    private val password = MutableStateFlow("")
    val passState = password.asStateFlow()
    private val gender = MutableStateFlow("")
    val genderState = gender.asStateFlow()
    private val dataOfBirth = MutableStateFlow("")
    val dataOfBirthState = dataOfBirth.asStateFlow()
    private val email = MutableStateFlow("")
    val emailState = email.asStateFlow()
    fun changeData(un: String, pass: String, g: String, d: String, e: String) {
        Constants.USERNAME = un
        Constants.PASSWORD = pass
        Constants.SEX = g
        Constants.BIRTHDAY = d
        Constants.EMAIL = e
        viewModelScope.launch {
            password.emit(un)
            password.emit(pass)
            gender.emit(g)
            dataOfBirth.emit(d)
            email.emit(e)
        }
    }
    fun clearData() {
        Constants.USERNAME = ""
        Constants.PASSWORD = ""
        Constants.SEX = ""
        Constants.BIRTHDAY = ""
        Constants.EMAIL = ""
    }
}
object Constants {
    var USERNAME = ""
    var PASSWORD = ""
    var SEX = ""
    var BIRTHDAY = ""
    var EMAIL = ""
}