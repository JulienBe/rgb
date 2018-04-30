package hacknslash.rgb.general.datas

class GDataHeartBeat(var minHB: Float, var maxHB: Float, var speed: GHeartBeatSpeed) {

    var middle: Float = 0f
    var currentHB: Float = 0.0f
    var upHB: Boolean = true
    var currentAdd: Float = 0.0f

    init {
        setValues()
    }

    private fun setValues() {
        middle = minHB + ((maxHB - minHB) / 2f)
        currentHB = minHB
        currentAdd = 0f
        upHB = true
    }
}

enum class GHeartBeatSpeed(val f: Float) {
    SLOW(0.001f),
    MEDIUM(0.01f)
}