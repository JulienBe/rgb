package hacknslash.rgb.general.gameobjects

interface GSensor {
    fun senses(a: GActor) {
    }

    val sensorRadius: Float
}