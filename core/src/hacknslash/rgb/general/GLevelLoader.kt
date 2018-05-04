package hacknslash.rgb.general

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import hacknslash.rgb.specific.actors.Wall

object GLevelLoader {

    fun load(name: String): GMap {
        val map = TmxMapLoader().load("map/$name.tmx")
        val wallsObj = map.layers.get("Walls").objects
        return GMap(wallsObj.map {
            it as RectangleMapObject
            Wall.get(it.rectangle)
        })
    }
}