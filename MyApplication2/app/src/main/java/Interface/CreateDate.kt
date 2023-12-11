package Interface

import java.time.LocalDateTime

interface CreateDate
{
    fun checkDate(string : String) : LocalDateTime?

    fun parseLocalTimeInString(dateTime : LocalDateTime) : String?

    fun getLocalDateTime(string: String) : LocalDateTime
}