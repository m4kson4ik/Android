package com.olympia.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface CheckUserName {
    fun check(inf: String): Boolean = inf.isNotEmpty() && inf.isNotBlank()
}
interface CheckPassword {
    fun check(inf: String): Boolean = inf.isNotEmpty() && inf.isNotBlank()
}
interface CheckGender {
    fun check(inf: String): Boolean = inf.isNotEmpty() && inf.isNotBlank() && inf.length > 1
}
interface CheckBirthday {
    fun check(inf: String): Boolean {
        return if (inf.isNotEmpty() && inf.isNotBlank() && (inf.contains(Regex("""^\d\d\.\d\d\.\d\d\d\d$""")))) {
            val date = LocalDate.parse(inf, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            date < LocalDate.now()
        } else false
    }
}
interface CheckEmail {
    fun check(inf: String): Boolean = inf.isNotEmpty() && inf.isNotBlank() && inf.contains("@gmail.com") && inf.contains("@mail.ru")
}