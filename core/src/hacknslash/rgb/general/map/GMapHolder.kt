package hacknslash.rgb.general.map

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxMap
import java.util.function.Consumer

class GMapHolder() {
    private val mapArray: GdxMap<Int, Int> = GdxMap()

    internal fun setVal(x: Int, y: Int, value: GMapValue) {
        mapArray.put(convert(x, y), value.i)
    }
    internal fun addVal(x: Int, y: Int, value: GMapValue) {
        val current = if (mapArray.containsKey(convert(x, y))) mapArray[convert(x, y)] else 0
        mapArray.put(convert(x, y), value.i.or(current))
    }

    internal fun getVals(x: Int, y: Int): List<GMapValue> {
        val current =  mapArray.get(convert(x, y))
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
            GLevelLoader.mapHolder.addVal(a.x + i, a.y, value)
            GLevelLoader.mapHolder.addVal(a.x + i, a.y + a.height - 1, value)
        }
        for (i in 0 until a.height) {
            GLevelLoader.mapHolder.addVal(a.x, a.y + i, value)
            GLevelLoader.mapHolder.addVal(a.x + a.width - 1, a.y + i, value)
        }
    }

}