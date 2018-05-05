package hacknslash.rgb.general.containers

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.specific.actors.EnergyMagnet
import ktx.collections.GdxArray

class GActorsContainer {

    private val newBorns = GdxArray<GActor>()
    private val actors = GdxArray<GActor>()
    private val deadActors = GdxArray<GActor>()

    fun act() {
        GBundle.bundle.physic.removeAll(deadActors)
        actors.removeAll(deadActors, true)
        deadActors.clear()
        newBorns.forEach {
            it.setup()
            actors.add(it)
        }
        actors.forEach {
            if (it.act()) {
                deadActors.add(it)
            }
        }
//        println(
//                "${Gdx.graphics.framesPerSecond} -" +
//                " Magnets : " + actors.filter { it is EnergyMagnet }.size +
//                " Enemies : " + actors.filter { it is Enemy }.size +
//                " Energy : " + actors.filter { it is Energy }.size)
        newBorns.clear()
    }

    fun toSetup(actor: GActor) {
        newBorns.add(actor)
    }

    fun debug(shapeRenderer: ShapeRenderer) {
        actors.forEach {
            if (it is EnergyMagnet)
                shapeRenderer.line(it.cx, it.cy, it.cx + it.speedX, it.cy + it.speedY)
        }
    }

}