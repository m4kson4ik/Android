package com.olympia.Class

import java.time.LocalDateTime

class UserModel(id: Int,
                firstname : String,
                secondName : String,
                lastName : String,
                birthday : LocalDateTime,
                numberPhone : String,
                email : String,
                password : String,
    ) {
    val id = id
    var firstname = firstname
    var secondName = secondName
    var lastName = lastName
    var birthday = birthday
    var numberPhone = numberPhone
    var email = email
    var password = password

    fun change(firstname : String,
               secondName : String,
               lastName : String,
               birthday : LocalDateTime,
               numberPhone : String,
               email : String,
               password : String,
    )
    {
        this.firstname = firstname
        this.secondName = secondName
        this.lastName = lastName
        this.birthday = birthday
        this.password = password
        this.numberPhone = numberPhone
        this.email = email
    }

    private val listeners = mutableListOf<(UserModel) -> Unit>()

    private fun sendEvent() = listeners.map { it(this) }

    fun registerListener(listener:(UserModel) -> Unit) = listeners.add(listener)

    fun unregisterListener(listener: (UserModel) -> Unit) = listeners.removeIf {listener == it}
}