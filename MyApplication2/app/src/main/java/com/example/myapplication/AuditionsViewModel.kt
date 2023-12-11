package com.example.myapplication

import Interface.IAuditionRepository
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Room.AuditionDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

//class AuditionViewModel constructor(private var auditionModel: AuditionModel, private var coroutineScope: CoroutineScope)
//{
//    fun change(name : String, numberAudition : Int, startDate : LocalDateTime, endDate : LocalDateTime)
//    {
//        auditionModel.change(startDate, endDate, numberAudition, name)
//    }
//
//    val id = auditionModel.id
//    private val auditionMutableStateFlow = MutableStateFlow<AuditionModel>(auditionModel)
//
//    var auditionStateFlow = auditionMutableStateFlow.asStateFlow()
//
//    init
//    {
//        coroutineScope.launch {
//            auditionModel.registerListener {
//                coroutineScope.launch {
//                    auditionMutableStateFlow.emit(auditionModel)
//                }
//            }
//            auditionModel.unregisterListener {
//                coroutineScope.launch {
//                    auditionMutableStateFlow.emit(auditionModel)
//                }
//            }
//        }
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AuditionsViewModel @Inject constructor(private val repository: IAuditionRepository) : ViewModel()
{
    var listAuditionMutableStateFlow  : Flow<List<AuditionDB>> = repository.getAllFlow()
    var auditions by mutableStateOf(emptyList<AuditionDB>())
    init {
        viewModelScope.launch {
            listAuditionMutableStateFlow.collect {
                auditions = it
            }
        }
    }

    suspend fun create(auditionModel: AuditionDB)
    {
        repository.createAudition(auditionModel)
    }

    suspend fun deleted(auditionModel: AuditionDB)
    {
        repository.deletedAudtiton(auditionModel)
    }

    fun search(string: String)
    {
        auditions.filter { it.numberAudition == string.toIntOrNull() || it.name == string}.toMutableList()
    }

    suspend fun update(
        auditionModel: AuditionDB,
        name: String,
        numberAudition: Int,
        startDate: String,
        endDate: String,
        uri: String? = null,
        numberImage: Int
    )
    {
        auditionModel.numberAudition = numberAudition
        auditionModel.name = name
        auditionModel.startDate = startDate
        auditionModel.endDate = endDate
        if (uri != null)
        {
            auditionModel.uri = uri
            auditionModel.numberImage = numberImage
        }
        repository.editingAudition(auditionModel)
    }

    fun <T : Comparable<T>> sortedAudition(field : (AuditionDB) -> T) {
        auditions.sortedBy(field)
    }
}
