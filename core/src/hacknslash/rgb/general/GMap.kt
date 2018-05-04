package hacknslash.rgb.general

import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.specific.Wall

class GMap(val walls: List<Wall>) {

    fun xInside(dim: GDim, lowXExclusion: Float = 0f, highXExclusion: Float = 0f): Float {
        return GRand.withinButNot(lowX, highX - dim.width, lowXExclusion, highXExclusion)
    }
    fun yInside(dim: GDim, lowYExclusion: Float = 0f, highYExclusion: Float = 0f): Float {
        return GRand.withinButNot(lowY, highY - dim.width, lowYExclusion, highYExclusion)
    }

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