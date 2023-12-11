package com.example.myapplication.Model

import java.time.LocalDateTime

class AuditionModel constructor (startDateTime: LocalDateTime, endDate : LocalDateTime, numberAudition : Int, name : String, id: Int = 0){

    lateinit var auditoriums: List<AuditionModel>
    var id = id
    var startDateTime = startDateTime
        private set
    var endDate = endDate
        private set
    var numberAudition = numberAudition
        private set
    var name = name
        private set

    companion object {
        private var nextId = 0
        private fun generateId(): Int {
            return nextId++
        }
    }

    init {
        if (id == 0) {
            this.id = generateId()
        } else {
            this.id = id
            nextId = maxOf(nextId, id + 1)
        }
    }
        fun change(
            startDateTime: LocalDateTime,
            endDate: LocalDateTime,
            numberAudition: Int,
            name: String
        ) {
            this.startDateTime = startDateTime
            this.endDate = endDate
            this.numberAudition = numberAudition
            this.name = name
            sendEvent()
        }

        private val listeners = mutableListOf<(AuditionModel) -> Unit>()

        private fun sendEvent() = listeners.map { it(this) }

        fun registerListener(listener: (AuditionModel) -> Unit) = listeners.add(listener)

        fun unregisterListener(listener: (AuditionModel) -> Unit) =
            listeners.removeIf { listener == it }
}
