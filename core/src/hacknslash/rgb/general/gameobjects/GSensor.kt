package hacknslash.rgb.general.gameobjects

interface GSensor {
    val sensorRadius: Float

    fun senses(a: GActor) {
    }
    fun stopSenses(a: GActor) {
    }
}