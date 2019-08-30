package com.aware.app.testx.model

import java.io.Serializable
import java.util.*

class User(var username: String, var age: Int, var diagnosed_pd: Boolean) : Serializable {
    var diagnosed_time: Int? = null
    var medications: ArrayList<Medication>? = null
    var symptoms: Symptoms? = null

    inner class Medication() {
        var name: String? = null
        var dosage: String? = null
        var intakeTime: ArrayList<IntakeTime>? = null
        var booster: Boolean? = null
        var notes: String? = null

        inner class IntakeTime (var hour: Int, var minute: Int)
    }

    inner class Symptoms constructor(var swallowing: Int, var dressing: Int, var falling: Int,
                                     var turning_in_bed: Int, var walking: Int, var handwriting: Int,
                                     var freezing_when_walking: Int, var tremor: Int, var sensory_complaints: Int,
                                     var speech: Int, var salivation: Int, var cutting_food: Int, var hygiene: Int)

}


