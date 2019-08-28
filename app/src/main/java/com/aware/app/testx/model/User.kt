package com.aware.app.testx.model

import java.io.Serializable
import java.util.*

class User(var username: String, var age: Int, var diagnosed_pd: Boolean) : Serializable {
    var diagnosed_time: Int? = null
    var medications: ArrayList<Medication>? = null
    var symptoms: Symptoms? = null

    inner class Medication public constructor(var name: String, var intakeTime: String,
                                               var dosage: String, var notes: String?)

    inner class Symptoms public constructor(var swallowing: Int, var dressing: Int, var falling: Int,
                                             var turning_in_bed: Int, var walking: Int, var handwriting: Int,
                                             var freezing_when_walking: Int, var tremor: Int, var sensory_complaints: Int,
                                             var speech: Int, var salivation: Int, var cutting_food: Int, var hygiene: Int)

}
