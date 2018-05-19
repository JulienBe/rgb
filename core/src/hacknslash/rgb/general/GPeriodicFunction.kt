package hacknslash.rgb.general

class GPeriodicFunction(val timer: Float, val function: () -> Boolean) {

    private var next = GClock.time + timer

    fun tick() {
        if (next < GClock.time) {
            function.invoke()
            next = GClock.time + timer
        }
    }

}