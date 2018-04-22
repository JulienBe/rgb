package hacknslash.rgb.general.gameobjects

interface GSensor {
    fun senses(a: GActor) {
        println("GSensor.senses")
    }

    val sensorRadius: Float
}