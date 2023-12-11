package com.example.myapplication.Class

import Interface.AuditionFactory
import java.time.LocalDateTime

class AuditionFactorySimple constructor() : AuditionFactory {
    override fun createAudition(startDate: LocalDateTime,
                                endDate: LocalDateTime,
                                numberAudtition: Int,
                                teacher: String)  = AuditionSimple(startDate, endDate, numberAudtition, teacher)
}