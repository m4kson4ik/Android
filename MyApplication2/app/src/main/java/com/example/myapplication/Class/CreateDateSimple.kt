package com.example.myapplication.Class

import Interface.CreateDate
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CreateDateSimple @Inject constructor() : CreateDate
{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun checkDate(string : String): LocalDateTime?
    {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return if(string.trim().contains(Regex("""^\d\d\.\d\d\.\d\d\d\d\s\d\d:\d\d:\d\d$""")))
        {
            LocalDateTime.parse(string,formatter)
        }
        else null
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun parseLocalTimeInString(dateTime : LocalDateTime) : String
    {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val reg = Regex("""^\d\d\.\d\d\.\d\d\d\d\s\d\d:\d\d:\d\d$""")
        val date = dateTime.format(formatter)
        return date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLocalDateTime(string: String) : LocalDateTime
    {
        return LocalDateTime.parse(string)
    }
}