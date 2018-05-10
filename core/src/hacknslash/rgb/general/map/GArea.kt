package hacknslash.rgb.general.map

import hacknslash.rgb.general.GRand

data class GArea(val x: Int, val y: Int, val width: Int, val height: Int) {
    companion object {
        fun getRandom(minDim: Int, maxDim: Int): GArea {
            val roomWidth = GRand.int(minDim, maxDim)
            val roomHeight = GRand.int(minDim, maxDim)
            val x = GRand.int(0, GLevelLoader.mapWidth - roomWidth)
            val y = GRand.int(0, GLevelLoader.mapWidth - roomHeight)
            return GArea(x, y, roomWidth, roomHeight)
        }
    }
}