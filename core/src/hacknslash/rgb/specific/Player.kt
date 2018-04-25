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

class Player private constructor(assMan: GAssMan, physic: GPhysic) : GActor(Const.playerDim, GVec2.get(), physic),
        GDrawable,
        GMover,
        GControllable,
        GShooter {

    override val img: TextureRegion = assMan.square()
    override val shotPatterns: Array<GShotPattern> = arrayOf(ShotPatterns.basic)
    override val maxSpeed = Const.playerSpeed
    override val pPos = GVec2.get()
    override val input: GInput = GInput()
    override val shouldShoot: Boolean get() = Gdx.input.isTouched
    override val shotDir: GVec2
        get() = GVec2.get((GInput.x() - Bullet.dim.hw) - GInput.centerW(), (GInput.y() - Bullet.dim.hh) - GInput.centerH())

    init {
        keyPressed(Input.Keys.SPACE, { println(GInput.x())})
        keyPressed(Input.Keys.UP,    {addVelocity(0f, Const.playerImpulse)})
        keyPressed(Input.Keys.DOWN,  {addVelocity(0f, -Const.playerImpulse)})
        keyPressed(Input.Keys.LEFT,  {addVelocity(-Const.playerImpulse, 0f)})
        keyPressed(Input.Keys.RIGHT, {addVelocity(Const.playerImpulse, 0f)})
    }

    override fun move(delta: Float) {
        clampSpeed()
        super.move(delta)
    }

    companion object {
        fun get(assMan: GAssMan, physic: GPhysic): Player {
            return Player(assMan, physic)
        }
    }
}