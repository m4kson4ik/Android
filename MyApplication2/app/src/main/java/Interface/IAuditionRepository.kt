package Interface

import com.example.myapplication.Model.AuditionRepositoryBase
import com.example.myapplication.Room.AuditionDB
import kotlinx.coroutines.flow.Flow

interface IAuditionRepository
{
    fun getListAudition(): List<AuditionDB>
    suspend fun deletedAudtiton(audition: AuditionDB)
    suspend fun createAudition(vararg audition: AuditionDB)
    suspend fun editingAudition(audition: AuditionDB)
    fun searchAudition(str : String)
    fun<T : Comparable<T>> sortedAudition(field : (AuditionDB) -> T)
    fun registerListener(listener: (AuditionRepositoryBase) -> Unit): Boolean
    fun unregisterListener(listener: (AuditionRepositoryBase) -> Unit): Boolean
    fun getAllFlow(): Flow<List<AuditionDB>>
}