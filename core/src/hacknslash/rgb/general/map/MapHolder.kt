package hacknslash.rgb.general.map

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxMap
import java.util.function.Consumer

class MapHolder() {
    private val mapArray: GdxMap<Int, MapValues> = GdxMap()

    internal fun setVal(x: Int, y: Int, value: MapValues) {
        mapArray.put(x % GLevelLoader.mapWidth + y * GLevelLoader.mapWidth, value)
    }

    internal fun getVal(x: Int, y: Int): MapValues {
        return mapArray.get(x % GLevelLoader.mapWidth + y * GLevelLoader.mapWidth)
    }

    internal fun forEach(consumer: Consumer<ObjectMap.Entry<Int, MapValues>>) {
        mapArray.forEach(consumer)
    }
}