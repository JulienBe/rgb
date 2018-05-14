package hacknslash.rgb.general.map

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxArray
import ktx.collections.GdxMap
import ktx.collections.GdxSet
import ktx.collections.set
import java.util.function.Consumer

class GMapHolder(val mapWidth: Int) {
    private val mapArray: GdxMap<Int, Int> = GdxMap()
    val rooms: GdxArray<GArea> = GdxArray()
    val hubs: GdxArray<GdxSet<Int>> = GdxArray()

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

    internal fun contains(x: Int, y: Int, value: GMapValue) = contains(convert(x, y), value)
    internal fun contains(area: GArea, value: GMapValue) = contains(area.x, area.y, area.width, area.height, value)
    internal fun contains(coord: Int, value: GMapValue) = mapArray.containsKey(coord) && mapArray.get(coord).and(value.i) != 0

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
    internal fun containsOnlyHorizontal(x: Int, width: Int, y: Int, value: GMapValue): Boolean {
        for (i in x until x + width)
            if (!contains(i, y, value))
                return false
        return true
    }
    internal fun containsOnlyVertical(x: Int, y: Int, height: Int, value: GMapValue): Boolean {
        for (i in y until y + height)
            if (!contains(x, i, value))
                return false
        return true
    }


    internal fun markArea(area: GArea, value: GMapValue) {
        for (i in area.x until area.x + area.width)
            for (j in area.y until area.y + area.height)
                addVal(i, j, value)
    }

    internal fun convert(x: Int, y: Int): Int = (x % mapWidth + y * mapWidth)
    internal fun unconvert(i: Int): Pair<Int, Int> = Pair(i % mapWidth, i / mapWidth)

    internal fun forEach(consumer: Consumer<ObjectMap.Entry<Int, Int>>) {
        mapArray.forEach(consumer)
    }

    fun markAreaEdges(a: GArea, value: GMapValue) {
        for (i in 0 until a.width) {
            addVal(a.x + i, a.y, value)
            addVal(a.x + i, a.y + a.height - 1, value)
        }
        for (i in 0 until a.height) {
            addVal(a.x, a.y + i, value)
            addVal(a.x + a.width - 1, a.y + i, value)
        }
    }

    fun addRoom(room: GArea) {
        rooms.add(room)
    }

    fun removeVal(x: Int, y: Int, value: GMapValue) {
        mapArray[convert(x, y)] = mapArray[convert(x, y)].xor(value.i)
    }

    fun exist(x: Int, y: Int): Boolean {
        return mapArray.containsKey(convert(x, y))
    }

    fun containsEdges(x: Int, y: Int, width: Int, height: Int, value: GMapValue): Boolean {
        return (containsHorizontal(x, y, width, value) && containsHorizontal(x, y + height - 1, width, value) &&
                containsVertical(x, y, height, value) && containsVertical(x + width - 1, y, height, value))
    }

    fun placeIfNot(x: Int, y: Int, place: GMapValue, not: GMapValue) {
        if (!contains(x, y, not))
            addVal(x, y, place)
    }

    fun addHub(squares: GdxSet<Int>) {
        hubs.add(squares)
    }

    fun hubsContainers(x: Int, y: Int): Boolean {
        val coord = convert(x, y)
        hubs.forEach {
            if (it.contains(coord))
                return true
        }
        return false
    }

}