package hacknslash.rgb.general

import hacknslash.rgb.specific.Wall

class GMap(val walls: List<Wall>) {
    val width = walls.maxBy {
        it.x + it.w
    }!!.rightX
    val heigth = walls.maxBy {
        it.y + it.h
    }!!.upY
    val highX = walls.maxBy {
        it.x
    }!!.x
    val highY = walls.maxBy {
        it.y
    }!!.y
    val lowX = walls.minBy {
        it.x + it.w
    }!!.rightX
    val lowY = walls.minBy {
        it.y + it.h
    }!!.upY
}