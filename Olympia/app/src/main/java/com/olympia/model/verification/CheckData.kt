package com.olympia.model.verification

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CheckUserName : ICheckUserName {
    override fun check(inf: String): Boolean = inf.isNotEmpty() && inf.isNotBlank()
}
class CheckPassword : ICheckPassword {
    override fun check(inf: String): Boolean = inf.isNotEmpty() && inf.isNotBlank() && inf.length >= 8
}
class CheckGender : ICheckGender {
    override fun check(inf: String): Boolean = inf.isNotEmpty() && inf.isNotBlank()
}
class CheckBirthday : ICheckBirthday {
    override fun check(inf: String): Boolean {
        return if (inf.isNotEmpty() && inf.isNotBlank() && (inf.contains(Regex("""^\d\d\.\d\d\.\d\d\d\d$""")))) {
            val date = LocalDate.parse(inf, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            date < LocalDate.now()
        } else false
    }
}
class CheckEmail : ICheckEmail {
    override fun check(inf: String): Boolean = inf.isNotEmpty() && inf.isNotBlank() && (inf.contains("@gmail.com") || inf.contains("@mail.ru"))
}
class CheckReps : ICheckReps {
    override fun check(inf: Int): Boolean = (inf > 0) && (inf <= 400)
}
class CheckStaminaTest : ICheckStaminaTest {
    override fun check(inf: Int): Boolean = (inf > 0) && (inf <= 400)
}