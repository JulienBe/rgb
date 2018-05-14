package hacknslash.rgb.general.map

import hacknslash.rgb.general.GRand

data class GArea(val x: Int, val y: Int, val width: Int, val height: Int) {
    val centerX = x + (width / 2)
    val centerY = y + (height / 2)
    val xEnd = x + width
    val yEnd = y + height

    companion object {
        fun getRandom(minDim: Int, maxDim: Int, mapWidth: Int): GArea {
            val roomWidth = GRand.int(minDim, maxDim)
            val roomHeight = GRand.int(minDim, maxDim)
            val x = GRand.int(0, (mapWidth - roomWidth))
            val y = GRand.int(0, (mapWidth - roomHeight))
            return GArea(x, y, roomWidth, roomHeight)
        }
        fun getGauss(min: Int, mod: Float, mapWidth: Int): GArea {
            var roomWidth = min + Math.abs(GRand.gauss(mod).toInt())
            while (roomWidth >= mapWidth) {
                roomWidth = min + Math.abs(GRand.gauss(mod).toInt())
            }
            var roomHeight = min + Math.abs(GRand.gauss(mod).toInt())
            while (roomHeight >= mapWidth) {
                roomHeight = min + Math.abs(GRand.gauss(mod).toInt())
            }

            val x = GRand.int(0, (mapWidth - roomWidth))
            val y = GRand.int(0, (mapWidth - roomHeight))
            return GArea(x, y, roomWidth, roomHeight)
        }
    }
}