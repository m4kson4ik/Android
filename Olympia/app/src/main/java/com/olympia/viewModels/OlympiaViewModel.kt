package com.olympia.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.olympia.Class.UserModel
import com.olympia.Interface.IOlympiaRepository
import com.olympia.OlympiaRepository
import com.olympia.model.DateBase.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OlympiaViewModel @Inject constructor(private val repository : IOlympiaRepository) : ViewModel() {

    val stateFlowUsers : Flow<List<Users>> = repository.getListFlowUsers()

    suspend fun create(user : Users)
    {
        Log.d("mgkit", "Create")
        repository.createUsers(user)
    }

    suspend fun editing(user : Users)
    {
        repository.editingUser(user)
    }
}