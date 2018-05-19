package hacknslash.rgb.general.map

import com.badlogic.gdx.utils.ObjectMap
import hacknslash.rgb.general.GClock
import hacknslash.rgb.general.GRand
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.particles.ColorBuilder
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.actors.Wall
import ktx.collections.GdxArray
import ktx.collections.GdxMap

class GMap(val walls: GdxArray<Wall>, val map: GMapHolder, val cellDim: GDim) {

    val rooms = map.rooms
    val energy = GdxMap<Int, FloatArray>()
    val energyToBeAdded = GdxArray<Pair<Int, Float>>()

    val floor = GBundle.bundle.assMan.getTexture("Floor")

    fun xInside(dim: GDim, lowXExclusion: Float = 0f, highXExclusion: Float = 0f): Float {
        return GRand.withinButNot(lowX, highX - dim.width, lowXExclusion, highXExclusion)
    }
    fun yInside(dim: GDim, lowYExclusion: Float = 0f, highYExclusion: Float = 0f): Float {
        return GRand.withinButNot(lowY, highY - dim.height, lowYExclusion, highYExclusion)
    }

    fun getPlayerStartup(): GVec2 {
        for (x in 0 until map.mapWidth)
            for (y in 0 until map.mapWidth)
                if (map.contains(x, y, GMapValue.ROOM))
                    return GVec2.get(((x + 1) * cellDim.width) + cellDim.hw, ((y + 1) * cellDim.height) + cellDim.hh)
        return GVec2.get()
    }

    fun addEnergy(x: Int, y: Int, amount: Float) {
        addEnergy(map.convert(x, y), amount)
    }
    fun addEnergy(coord: Int, amount: Float) {
        energyToBeAdded.add(Pair(coord, amount))

    }

    fun act() {
        energyToBeAdded.forEach {
            energy.put(it.first, floatArrayOf(it.second, GClock.time + GRand.float(0.5f, 1.5f)))
        }
        energyToBeAdded.clear()
        energy.forEach {
            if (it.value[1] < GClock.time) {
                it.value[0] /= 1.15f
                it.value[1] = GClock.time + 1f
                if (checkEnergyTransfer(it.key + 1, it.value[0])) {
                    addEnergy(it.key + 1, it.value[0])
                }
                if (checkEnergyTransfer(it.key - 1, it.value[0])) {
                    addEnergy(it.key - 1, it.value[0])
                }
                if (checkEnergyTransfer(it.key + map.mapWidth, it.value[0])) {
                    addEnergy(it.key + map.mapWidth, it.value[0])
                }
                if (checkEnergyTransfer(it.key - map.mapWidth, it.value[0])) {
                    addEnergy(it.key - map.mapWidth, it.value[0])
                }
            }
        }
    }

    private fun checkEnergyTransfer(coord: Int, value: Float) =
            map.exist(coord) && (!energy.containsKey(coord) || energy[coord][1] < value)

    fun display() {
        energy.forEach { entry ->
            GBundle.bundle.batch.setColor(getEnergyColor(entry.value[0]))
            GBundle.bundle.batch.draw(floor, (entry.key % map.mapWidth) * cellDim.width, (entry.key / map.mapWidth) * cellDim.height, cellDim.width, cellDim.height)
        }
    }

    private fun getEnergyColor(amount: Float) = ColorBuilder.convert(
                Math.min(1f, amount * 2),
                Math.min(1f, amount / 2f),
                Math.min(1f, amount / 20f),
                Math.min(1f, amount * 2))

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