package hacknslash.rgb.specific

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.GInput
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GControllable
import hacknslash.rgb.general.gameobjects.GDrawer
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.physics.GPhysic

class Player private constructor(assMan: GAssMan, physic: GPhysic, override val img: TextureRegion = assMan.square()) : GActor(dim, GVec2(), physic),
        GDrawer,
        GMover,
        GControllable {

    override val maxSpeed = 4f
    override val pPos = GVec2()

    override val input: GInput = GInput()

    init {
        keyPressed(Input.Keys.UP,    {addDir(0f, 8f)})
        keyPressed(Input.Keys.DOWN,  {addDir(0f, -8f)})
        keyPressed(Input.Keys.LEFT,  {addDir(-8f, 0f)})
        keyPressed(Input.Keys.RIGHT, {addDir(8f, 0f)})
    }

    companion object {
        val dim = GDim(10f, 10f)
        fun get(assMan: GAssMan, physic: GPhysic): Player {
            return Player(assMan, physic)
        }
    }
}