package hacknslash.rgb.general

class GPeriodicCaller(val invoke: () -> Any, private val interval: Float) {

    var nextCall = GClock.time + interval

    fun act() {
        if (GClock.time < nextCall) {
            invoke.invoke()
            nextCall = GClock.time + interval
        }
    }

}