package hacknslash.rgb.specific

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.graphics.GAssMan
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.GInput
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.general.physics.GPhysic

class Player private constructor(assMan: GAssMan, physic: GPhysic, override val img: TextureRegion = assMan.square()) : GActor(dim, GVec2.get(), physic),
        GDrawable,
        GMover,
        GControllable,
        GShooter {

    override val shotPatterns: Array<GShotPattern> = arrayOf(ShotPatterns.basic)
    override val maxSpeed = Const.playerSpeed
    override val pPos = GVec2.get()
    override val input: GInput = GInput()
    override val shouldShoot: Boolean get() = Gdx.input.isTouched
    override val shotDir: GVec2
        get() = GVec2.get((GInput.x() - Bullet.dim.hw) - cx, (GInput.y() - Bullet.dim.hh) - cy)

    init {
        keyPressed(Input.Keys.UP,    {addDir(0f, 8f)})
        keyPressed(Input.Keys.DOWN,  {addDir(0f, -8f)})
        keyPressed(Input.Keys.LEFT,  {addDir(-8f, 0f)})
        keyPressed(Input.Keys.RIGHT, {addDir(8f, 0f)})
    }

    companion object {
        val dim = GDim(5f, 5f)
        fun get(assMan: GAssMan, physic: GPhysic): Player {
            return Player(assMan, physic)
        }
    }
}