package com.example.myapplication.Class

import Interface.Audition
import java.time.LocalDateTime

class AuditionSimple constructor (
    override var startDate: LocalDateTime,
    override var endDate: LocalDateTime,
    override var numberAudtition: Int,
    override var teacher: String,
) : Audition