package com.aware.app.testx.model

import java.io.Serializable
import java.util.*

class User(var username: String, var age: Int, var diagnosed_pd: Boolean) : Serializable {
    var diagnosed_time: Int? = null
    var dbs: Boolean? = null
    var medications: ArrayList<Medication>? = null
    var symptoms: ArrayList<Symptom>? = null

    class Medication {
        var name: String? = null
        var dosage: String? = null
        var intakeTime: ArrayList<IntakeTime>? = null
        var booster: Boolean? = null
        var notes: String? = null

        class IntakeTime (var hour: Int, var minute: Int)
    }

    class Symptom (var name: String, var rate: Int)

}
