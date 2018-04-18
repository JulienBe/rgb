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

class Player private constructor(assMan: GAssMan, physic: GPhysic, override val img: TextureRegion = assMan.square()) : GActor(dim, physic),
        GDrawer,
        GMover,
        GControllable {

    override val maxSpeed = 1000f
    override val pPos = GVec2()
    override val dir = GVec2()

    override val input: GInput = GInput()

    init {
        keyPressed(Input.Keys.UP,    {dir.y += 1f})
        keyPressed(Input.Keys.DOWN,  {dir.y -= 1f})
        keyPressed(Input.Keys.LEFT,  {dir.x -= 1f})
        keyPressed(Input.Keys.RIGHT, {dir.x += 1f})
    }

    companion object {
        val dim = GDim(10f, 10f)
        fun get(assMan: GAssMan, physic: GPhysic): Player {
            return Player(assMan, physic)
        }
    }
}