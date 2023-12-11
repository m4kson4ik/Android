package com.olympia.model.verification

interface ICheckUserName {
    fun check(inf: String): Boolean
}
interface ICheckPassword {
    fun check(inf: String): Boolean
}
interface ICheckGender {
    fun check(inf: String): Boolean
}
interface ICheckBirthday {
    fun check(inf: String): Boolean
}
interface ICheckEmail {
    fun check(inf: String): Boolean
}
interface ICheckReps {
    fun check(inf: Int): Boolean
}
interface ICheckStaminaTest {
    fun check(inf: Int): Boolean
}
//interface ICheckFirstRep {
//    fun check(inf: Int): Boolean
//}
//interface ICheckSecondRep {
//    fun check(inf: Int): Boolean
//}
//interface ICheckThirdRep {
//    fun check(inf: Int): Boolean
//}
//interface ICheckFourthRep {
//    fun check(inf: Int): Boolean
//}
//interface ICheckFifthRep {
//    fun check(inf: Int): Boolean
//}
