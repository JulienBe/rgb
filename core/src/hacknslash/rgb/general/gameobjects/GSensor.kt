package hacknslash.rgb.general.gameobjects

interface GSensor {
    fun senses(a: GActor) {
        println("senses : " + a)
    }

    val sensorRadius: Float
}