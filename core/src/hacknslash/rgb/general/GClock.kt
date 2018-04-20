package hacknslash.rgb.general

object GClock {
    var time = 0f

    fun act(delta: Float) {
        time += delta
    }
}