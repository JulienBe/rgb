package hacknslash.rgb.general.map

import hacknslash.rgb.specific.actors.Wall
import ktx.collections.GdxArray
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.function.Consumer
import javax.imageio.ImageIO

object GLevelLoader {

    const val wallWidth = 2f
    const val mapWidth = 100
    const val roomAttempts = 1200
    val map = GMapHolder()
    private val roomGenerator = GAreaPackage.MEDIUM_GUASS_ROOMS
    private val wall = GMapValue.WALL
    private val room = GMapValue.ROOM

    fun proceduralGeneration(): GMap {
        placeRooms()
        surround(room, wall)
        fixRoomEdges()
        return processValues()
    }

    private fun fixRoomEdges() {
        val toAdd = GdxArray<Pair<Int, Int>>()
        for (x in 0 until mapWidth)
            for (y in 0 until mapWidth) {
                if (!map.exist(x, y)) {
                    if (map.contains(x + 1, y, wall) && map.contains(x, y + 1, wall) && map.contains(x + 1, y + 1, room))
                        toAdd.add(Pair(x, y))
                    if (map.contains(x - 1, y, wall) && map.contains(x, y - 1, wall) && map.contains(x - 1, y - 1, room))
                        toAdd.add(Pair(x, y))
                    if (map.contains(x - 1, y, wall) && map.contains(x, y + 1, wall) && map.contains(x - 1, y + 1, room))
                        toAdd.add(Pair(x, y))
                    if (map.contains(x + 1, y, wall) && map.contains(x, y - 1, wall) && map.contains(x + 1, y - 1, room))
                        toAdd.add(Pair(x, y))
                }
            }
        toAdd.forEach {
            map.addVal(it.first, it.second, wall)
        }
    }

    private fun surround(room: GMapValue, wall: GMapValue) {
        for (x in 0 until mapWidth)
            for (y in 0 until mapWidth) {
                if (map.contains(x, y, room)) {
                    if (!map.contains(x - 1, y, wall) && !map.contains(x - 1, y, room)) map.addVal(x, y, wall)
                    if (!map.contains(x + 1, y, wall) && !map.contains(x + 1, y, room)) map.addVal(x, y, wall)
                    if (!map.contains(x, y - 1, wall) && !map.contains(x, y - 1, room)) map.addVal(x, y, wall)
                    if (!map.contains(x, y + 1, wall) && !map.contains(x, y + 1, room)) map.addVal(x, y, wall)
                }
            }
    }

    private fun antiAlias(value: GMapValue) {
        for (x in 0 until mapWidth step 2)
            for (y in 0 until mapWidth step 2) {
                val topLeft = map.contains(x, y, value)
                val topRight = map.contains(x + 1, y, value)
                val bottomLeft = map.contains(x, y + 1, value)
                val bottomRight = map.contains(x + 1, y + 1, value)
                if (topLeft && bottomRight && !topRight && !bottomLeft)
                    map.addVal(x + 1, y, value)
                if (!topLeft && !bottomRight && topRight && bottomLeft)
                    map.addVal(x + 1, y - 1, value)
            }
    }

    private fun placeRooms() {
        for (i in 0 until roomAttempts) {
            val candidate = roomGenerator.generate.invoke()
            if (!map.contains(candidate, room)) {
                map.markArea(candidate, room)
                map.addRoom(candidate)
                println("placed room $candidate")
            }
        }
    }

    private fun processValues(): GMap {
        val walls = GdxArray<Wall>()
        map.forEach(Consumer {
            entry ->
            if (entry.value.and(wall.i) != 0)
                walls.add(Wall.get(entry.key % mapWidth * wallWidth, entry.key / mapWidth * wallWidth, wallWidth))
        })

        val img = BufferedImage(mapWidth * 3, mapWidth * 3, BufferedImage.TYPE_INT_ARGB)
        for (x in mapWidth - 1 downTo 0)
            for (y in mapWidth - 1 downTo 0) {
                val values = map.getVals(x, (mapWidth - 1) - y)
                if (values.contains(GMapValue.EMPTY))
                    img.setRGB(x, y, Color.BLACK.rgb, 3)
                if (values.contains(room))
                    img.setRGB(x, y, Color.LIGHT_GRAY.rgb, 3)
                if (values.contains(wall))
                    img.setRGB(x, y, Color.DARK_GRAY.rgb, 3)
            }
        val f = File("/home/j/rgb/map${System.currentTimeMillis()}.png")
        ImageIO.write(img, "png", f)
        return GMap(walls, map)
    }

}

private fun BufferedImage.setRGB(x: Int, y: Int, intColor: Int, pixelSize: Int) {
    for (i in 0 until pixelSize) {
        for (j in 0 until pixelSize) {
            setRGB(x * pixelSize + i, y * pixelSize + j, intColor)
        }
    }
}

