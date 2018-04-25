package hacknslash.rgb.general

import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import hacknslash.rgb.general.graphics.GAssMan
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.specific.Wall

object GLevelLoader {

    fun load(name: String, physics: GPhysic, assMan: GAssMan): GMap {
        val map = TmxMapLoader().load("map/$name.tmx")
        val wallsObj = map.layers.get("Walls").objects
        return GMap(wallsObj.map {
            it as RectangleMapObject
            Wall.get(it.rectangle, physics, assMan)
        })
    }
}