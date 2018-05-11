package hacknslash.rgb.general.map

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxArray
import ktx.collections.GdxMap
import ktx.collections.set
import java.util.function.Consumer

class GMapHolder {
    private val mapArray: GdxMap<Int, Int> = GdxMap()
    val rooms: GdxArray<GArea> = GdxArray()

    internal fun setVal(x: Int, y: Int, value: GMapValue) {
        mapArray.put(convert(x, y), value.i)
    }
    internal fun addVal(x: Int, y: Int, value: GMapValue) {
        val current = if (mapArray.containsKey(convert(x, y))) mapArray[convert(x, y)] else 0
        mapArray.put(convert(x, y), value.i.or(current))
    }

    internal fun getVals(x: Int, y: Int): List<GMapValue> {
        val current: Int =  if (mapArray.containsKey(convert(x, y))) mapArray.get(convert(x, y)) else 0
        return GMapValue.values().filter {
            it.i.and(current) != 0
        }
    }

    internal fun contains(x: Int, y: Int, value: GMapValue): Boolean {
        return mapArray.containsKey(convert(x, y)) && mapArray.get(convert(x, y)).and(value.i) != 0
    }

    internal fun contains(x: Int, y: Int, width: Int, height: Int, value: GMapValue): Boolean {
        for (i in x until x + width)
            for (j in y until y + height)
                if (contains(i, j, value))
                    return true
        return false
    }
    internal fun containsHorizontal(x: Int, width: Int, y: Int, value: GMapValue): Boolean {
        for (i in x until x + width)
            if (contains(i, y, value))
                return true
        return false
    }
    internal fun containsVertical(x: Int, y: Int, height: Int, value: GMapValue): Boolean {
        for (i in y until y + height)
            if (contains(x, i, value))
                return true
        return false
    }

    internal fun contains(area: GArea, value: GMapValue): Boolean = contains(area.x, area.y, area.width, area.height, value)

    internal fun markArea(area: GArea, value: GMapValue) {
        for (i in area.x until area.x + area.width)
            for (j in area.y until area.y + area.height)
                addVal(i, j, value)
    }

    private fun convert(x: Int, y: Int) = x % GLevelLoader.mapWidth + y * GLevelLoader.mapWidth

    internal fun forEach(consumer: Consumer<ObjectMap.Entry<Int, Int>>) {
        mapArray.forEach(consumer)
    }

    fun markAreaEdges(a: GArea, value: GMapValue) {
        for (i in 0 until a.width) {
            GLevelLoader.map.addVal(a.x + i, a.y, value)
            GLevelLoader.map.addVal(a.x + i, a.y + a.height - 1, value)
        }
        for (i in 0 until a.height) {
            GLevelLoader.map.addVal(a.x, a.y + i, value)
            GLevelLoader.map.addVal(a.x + a.width - 1, a.y + i, value)
        }
    }

    fun addRoom(room: GArea) {
        rooms.add(room)
    }

    fun removeVal(x: Int, y: Int, value: GMapValue) {
        println("remove $x $y " + mapArray[convert(x, y)])
        mapArray[convert(x, y)] = mapArray[convert(x, y)].xor(value.i)
        println("?????? $x $y " + mapArray[convert(x, y)])
    }

    fun exist(x: Int, y: Int): Boolean {
        return mapArray.containsKey(convert(x, y))
    }

    fun containsEdges(x: Int, y: Int, width: Int, height: Int, value: GMapValue): Boolean {
        return (containsHorizontal(x, y, width, value) && containsHorizontal(x, y + height - 1, width, value) &&
                containsVertical(x, y, height, value) && containsVertical(x + width - 1, y, height, value))
    }

}