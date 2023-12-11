package Interface

import java.time.LocalDateTime

interface AuditionFactory {
    fun createAudition(startDate: LocalDateTime,
                       endDate: LocalDateTime,
                       numberAudtition: Int,
                       teacher: String) : Audition
}