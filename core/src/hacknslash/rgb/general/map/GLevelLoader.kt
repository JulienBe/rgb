package hacknslash.rgb.general.map

import hacknslash.rgb.general.GRand
import hacknslash.rgb.specific.actors.Wall
import ktx.collections.GdxArray
import ktx.collections.GdxSet
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.util.function.Consumer
import javax.imageio.ImageIO

class GLevelLoader {

    private val squareWidth = 8f
    private val drawSize = 4
    val mapWidth = 200
    val mapHalfWidth = mapWidth / 2
    val map = GMapHolder(mapWidth)
    private val wall = GMapValue.WALL
    private val room = GMapValue.ROOM

    fun proceduralGeneration(): GMap {
        val seed = System.currentTimeMillis()
        GRand.setSeed(seed)
        println("SEED $seed")
        timed({
            timed({ placeRooms(GAreaPackage.HUGE_GUASS_ROOMS, 10) }, "room gen 1")
            timed({ placeRooms(GAreaPackage.BIG_GUASS_ROOMS, 500) }, "room gen 2")
            timed({ placeRooms(GAreaPackage.SMALL_NORMAL_ROOMS, 10000) }, "room gen 3")
            timed({ surround(room, wall) }, "surround  ")
            timed({ placeHallways() }, "hallways  ")
            timed({ surround(room, wall) }, "surround  ")
            timed({ fixRoomEdges() }, "fix edges ")
            timed({ detectHubs() }, "hub detect")
            timed({ keepBiggestHubs() }, "hub delete")
        }, "==============\nTOTAL     ")
        println("")
        return processValues()
    }

    private fun keepBiggestHubs() {
        map.hubs.sort { one, two -> two.size - one.size }
        for (i in 1 until map.hubs.size) {
            val hub = map.hubs[i]
            hub.forEach {
                map.remove(it)
            }
        }
        map.hubs.removeRange(1, map.hubs.size - 1)
        map.forEach(Consumer { entry ->
            val i = entry.key
            if (map.contains(i, wall) && !map.containsOnly(i - 1, room) && !map.containsOnly(i + 1, room) &&
                    !map.containsOnly(i + mapWidth, room) && !map.containsOnly(i - mapWidth, room) &&
                    !map.containsOnly(i + mapWidth + 1, room) && !map.containsOnly(i + mapWidth - 1, room) &&
                    !map.containsOnly(i - mapWidth + 1, room) && !map.containsOnly(i - mapWidth - 1, room))
                map.remove(entry.key)
        })
    }

    private fun timed(function: () -> Unit, output: String) {
        val start = System.currentTimeMillis()
        function.invoke()
        println("$output ${System.currentTimeMillis() - start}")
    }

    private fun detectHubs() {
        map.rooms.forEach {
            if (!map.hubsContainers(it.centerX, it.centerY))
                map.addHub(detectArea(it.centerX, it.centerY, GdxSet()))
        }
    }

    private fun detectArea(x: Int, y: Int, set: GdxSet<Int>): GdxSet<Int> {
        if (!isRoom(x, y) || isWall(x, y))
            return set
        val toProcess = GdxSet<Int>()
        toProcess.add(map.convert(x, y))
        while (toProcess.size > 0) {
            val current = toProcess.first()
            if (isRoom(current) && !isWall(current) && !set.contains(current)) {
                toProcess.add(current - 1)
                toProcess.add(current + 1)
                toProcess.add(current + mapWidth)
                toProcess.add(current - mapWidth)
                set.add(current)
            }
            toProcess.remove(current)
        }
        return set
    }

    private fun placeHallways() {
        map.rooms.forEach {
            if (roomNotConnected(it)) {
                val dirX = it.centerX >= mapHalfWidth
                val dirY = it.centerY >= mapHalfWidth
                for (i in 0 until (it.width + it.height) * 2) {
                    if (dirX && dirY)   hallwayHorizontal(it, -1, it.x - i)
                    if (!dirX && !dirY) hallwayHorizontal(it,  1, (it.x - 1) + it.width + i)
                    if (dirY && !dirX)  hallwayVertical(it, -1, it.y - i)
                    if (!dirY && dirX)  hallwayVertical(it, 1, (it.y - 1) + it.width + i)
                }
            }
        }
    }

    private fun hallwayVertical(it: GArea, mod: Int, baseY: Int) {
        // sides
        map.placeIfNot(it.centerX - 2, baseY, wall, room)
        map.placeIfNot(it.centerX + 1, baseY, wall, room)
        // terminator
        placeTunnel(it.centerX + 1, baseY + mod)
        placeTunnel(it.centerX, baseY + mod)
        placeTunnel(it.centerX - 1, baseY + mod)
        placeTunnel(it.centerX - 2, baseY + mod)

        // center
        map.setVal(it.centerX, baseY, room)
        map.setVal(it.centerX - 1, baseY, room)
    }

    private fun placeTunnel(x: Int, y: Int) {
        if (map.contains(x, y, wall)) {
            map.setVal(x, y, room)
        } else {
            map.placeIfNot(x, y, wall, room)
        }
    }

    private fun hallwayHorizontal(it: GArea, mod: Int, baseX: Int) {
        // sides
        map.placeIfNot(baseX, it.centerY - 2, wall, room)
        map.placeIfNot(baseX, it.centerY + 1, wall, room)
        // terminator
        placeTunnel(baseX + mod, it.centerY + 1)
        placeTunnel(baseX + mod, it.centerY)
        placeTunnel(baseX + mod, it.centerY - 1)
        placeTunnel(baseX + mod, it.centerY - 2)

        map.setVal(baseX, it.centerY, room)
        map.setVal(baseX, it.centerY - 1, room)
    }

    private fun fixRoomEdges() {
        val toAdd = GdxArray<Pair<Int, Int>>()
        for (x in 0 until mapWidth)
            for (y in 0 until mapWidth) {
                if (!map.exist(x, y)) {
                    if (isWall(x + 1, y) && isWall(x, y + 1) && isRoom(x + 1, y + 1))
                        toAdd.add(Pair(x, y))
                    if (isWall(x - 1, y) && isWall(x, y - 1) && isRoom(x - 1, y - 1))
                        toAdd.add(Pair(x, y))
                    if (isWall(x - 1, y) && isWall(x, y + 1) && isRoom(x - 1, y + 1))
                        toAdd.add(Pair(x, y))
                    if (isWall(x + 1, y) && isWall(x, y - 1) && isRoom(x + 1, y - 1))
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
    private fun roomNotConnected(area: GArea): Boolean {
        for (x in area.x until area.x + area.width) {
            if (!isWall(x, area.y))
                return false
            if (!isWall(x, (area.y - 1) + area.height))
                return false
        }
        for (y in area.y until area.y + area.height) {
            if (!isWall(area.x, y))
                return false
            if (!isWall((area.x - 1) + area.width, y))
                return false
        }
        return true
    }

    private fun isRoom(x: Int, y: Int) = map.contains(x, y, room)
    private fun isRoom(coord: Int) = map.contains(coord, room)
    private fun isWall(x: Int, y: Int) = map.contains(x, y, wall)
    private fun isWall(coord: Int) = map.contains(coord, wall)

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

    private fun placeRooms(roomGenerator: GAreaPackage, attempts: Int) {
        for (i in 0 until attempts) {
            val candidate = roomGenerator.generate.invoke(mapWidth)
            if (!map.contains(candidate, room) && candidate.x > 0 && candidate.y > 0) {
                map.markArea(candidate, room)
                map.addRoom(candidate)
            }
        }
    }

    private fun processValues(): GMap {
        val walls = GdxArray<Wall>()
        map.forEach(Consumer {
            entry ->
            if (entry.value.and(wall.i) != 0)
                walls.add(Wall.get(entry.key % mapWidth * squareWidth, entry.key / mapWidth * squareWidth, squareWidth))
        })

        val img = BufferedImage(mapWidth * drawSize, mapWidth * drawSize, BufferedImage.TYPE_INT_ARGB)
//        drawRooms(img)
        for (x in mapWidth - 1 downTo 0)
            for (y in mapWidth - 1 downTo 0) {
                val values = map.getVals(x, (mapWidth - 1) - y)
                if (values.contains(GMapValue.EMPTY))
                    img.setRGB(x, y, Color.PINK.rgb, drawSize)
                if (values.contains(room))
                    img.setRGB(x, y, Color.LIGHT_GRAY.rgb, drawSize)
                if (values.contains(wall))
                    img.setRGB(x, y, Color.DARK_GRAY.rgb, drawSize)
            }
        drawHubs(img)
        val f = File("/home/j/rgb/map${System.currentTimeMillis()}.png")
        ImageIO.write(img, "png", f)
        return GMap(walls, map)
    }

    private fun drawHubs(img: BufferedImage) {
        map.hubs.forEach {
            val color = Color.getHSBColor(GRand.nextFloat(), GRand.nextFloat(), GRand.nextFloat()).rgb
            it.forEach { square ->
                val coord = map.unconvert(square)
                img.setRGB(coord.first, (mapWidth - 1) - coord.second, color, drawSize)
                if (map.contains(coord.first, coord.second, wall))
                    img.setRGB(coord.first, (mapWidth - 1) - coord.second, Color.DARK_GRAY.rgb, drawSize)
            }
        }
    }

    private fun drawRooms(img: BufferedImage) {
        map.rooms.forEach {
            val roomColor =
                    if (roomNotConnected(it))
                        Color.RED.rgb
                    else
                        Color.LIGHT_GRAY.rgb
            for (x in it.x until it.x + it.width)
                for (y in it.y until it.y + it.height)
                    img.setRGB(x, (mapWidth - 1) - y, roomColor, drawSize)
        }
    }


}

private fun BufferedImage.setRGB(x: Int, y: Int, intColor: Int, pixelSize: Int) {
    for (i in 0 until pixelSize) {
        for (j in 0 until pixelSize) {
            setRGB(x * pixelSize + i, y * pixelSize + j, intColor)
        }
    }
}

