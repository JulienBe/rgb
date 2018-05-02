package hacknslash.rgb.general.containers

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import hacknslash.rgb.general.bundles.GActBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.physics.GPhysic
import ktx.collections.gdxArrayOf

class GActorsContainer {

    private val actors = gdxArrayOf<GActor>()
    private val deadActors = gdxArrayOf<GActor>()

    fun act() {
        GActBundle.bundle.physic.removeAll(deadActors)
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
            shapeRenderer.line(it.cx, it.cy, it.cx + it.speedX, it.cy + it.speedY)
        }
    }

}