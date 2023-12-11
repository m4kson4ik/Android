package com.example.myapplication.Model


import Interface.IAuditionRepository
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myapplication.Room.AuditionDB
import com.example.myapplication.Room.AuditionDateBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class AuditionRepositoryBase @Inject constructor (private val db : AuditionDateBase) : IAuditionRepository {
    private var list : MutableList<AuditionDB> = mutableListOf()
    var searchList : MutableList<AuditionDB> = list
    private val dao = db.auditionDAO()
    private val listeners = mutableListOf<(AuditionRepositoryBase)->Unit>()

    override fun getAllFlow(): Flow<List<AuditionDB>> {
        Log.d("mgkit","db object in repository $db")
        return dao.getAllFlowAudition()
    }

    private fun sendEvent() = listeners.map { it(this) }

    override fun registerListener(listener : (AuditionRepositoryBase)->Unit) = listeners.add(listener)

    override fun unregisterListener(listener : (AuditionRepositoryBase)->Unit) = listeners.removeIf { listener == it }

    override fun getListAudition(): List<AuditionDB> {
        return list.toList()
    }

    override suspend fun deletedAudtiton(audition: AuditionDB) {
        dao.deleted(audition)
    }

    override suspend fun createAudition(vararg audition: AuditionDB) {
        dao.insert(*audition)
    }

    override suspend fun editingAudition(audition: AuditionDB) {
        dao.update(audition)
    }

    override fun searchAudition(str: String) {
        val search = list.filter { it.numberAudition == str.toIntOrNull() || it.name == str}.toMutableList()
    }

    override fun <T : Comparable<T>> sortedAudition(field : (AuditionDB) -> T) {
        list.sortBy(field)
        sendEvent()
    }
}