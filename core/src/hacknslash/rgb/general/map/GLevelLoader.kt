package hacknslash.rgb.general.map

import hacknslash.rgb.specific.actors.Wall
import ktx.collections.GdxArray
import java.util.function.Consumer

object GLevelLoader {

    const val wallWidth = 2f
    const val mapWidth = 100
    val mapHolder = GMapHolder()

    @JvmStatic
    fun main(args: Array<String>) {
        val map = GLevelLoader.proceduralGeneration()
        map.ascii()
    }

    fun proceduralGeneration(): GMap {
        placeRooms(10, 30)
        return processValues()
    }

    private fun placeRooms(minWidth: Int, maxWidth: Int) {
        for (i in 0 until 10) {
            val candidate = GArea.getRandom(minWidth, maxWidth)
            if (!mapHolder.contains(candidate, GMapValue.ROOM)) {
                mapHolder.markArea(candidate, GMapValue.ROOM)
                mapHolder.markAreaEdges(candidate, GMapValue.WALL)
                println("placed room $candidate")
            }
        }
    }

    private fun processValues(): GMap {
        val walls = GdxArray<Wall>()
        mapHolder.forEach(Consumer {
            entry ->
            if (entry.value.and(GMapValue.WALL.i) != 0)
                walls.add(Wall.get(entry.key % mapWidth * wallWidth, entry.key / mapWidth * wallWidth, wallWidth))
        })
        return GMap(walls, mapHolder)
    }

}

