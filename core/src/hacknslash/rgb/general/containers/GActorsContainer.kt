package hacknslash.rgb.general.containers

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.specific.actors.EnergyMagnet
import ktx.collections.gdxArrayOf

class GActorsContainer {

    private val actors = gdxArrayOf<GActor>()
    private val deadActors = gdxArrayOf<GActor>()

    fun act() {
        GBundle.bundle.physic.removeAll(deadActors)
        actors.removeAll(deadActors, true)
        deadActors.clear()
        actors.forEach {
            if (it.act())
                deadActors.add(it)
        }
    }

    fun add(actor: GActor) {
        actors.add(actor)
    }

    fun debug(shapeRenderer: ShapeRenderer) {
        actors.forEach {
            if (it is EnergyMagnet)
                shapeRenderer.line(it.cx, it.cy, it.cx + it.speedX, it.cy + it.speedY)
        }
    }

}