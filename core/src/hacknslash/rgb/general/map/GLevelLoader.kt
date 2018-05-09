package hacknslash.rgb.general.map

import hacknslash.rgb.specific.actors.Wall
import ktx.collections.GdxArray
import java.util.function.Consumer

object GLevelLoader {

    const val wallWidth = 1f
    const val mapWidth = 100
    val mapHolder = MapHolder()

    fun proceduralGeneration(): GMap {
        makeWalls()
        return processValues()
    }

    private fun makeWalls() {
        var x = mapWidth / 2
        var y = mapWidth / 2
        mapHolder.setVal(x, y, MapValues.WALL)
        for (i in 0 until 10) {
            mapHolder.setVal(x + i, y, MapValues.WALL)
            mapHolder.setVal(x - i, y, MapValues.WALL)
            mapHolder.setVal(x, y + i, MapValues.WALL)
            mapHolder.setVal(x, y - i, MapValues.WALL)
        }
    }

    private fun processValues(): GMap {
        val walls = GdxArray<Wall>()
        mapHolder.forEach(Consumer {
            entry ->
            if (entry.value == MapValues.WALL)
                walls.add(Wall.get(entry.key % mapWidth * wallWidth, entry.key / mapWidth * wallWidth, wallWidth))
        })
        return GMap(walls)
    }

}

