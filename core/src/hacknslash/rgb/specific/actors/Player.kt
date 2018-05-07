package hacknslash.rgb.specific.actors

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import hacknslash.rgb.general.GInput
import hacknslash.rgb.general.datas.GDataMover
import hacknslash.rgb.general.datas.GDataObjectParticle
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.particles.GObjectParticleEmitter
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits
import hacknslash.rgb.specific.Const
import hacknslash.rgb.specific.ShotPatterns
import ktx.collections.gdxArrayOf

class Player private constructor() :
        GActor(Const.playerDim, GVec2.get(5f, 5f), CollisionBits.player, CollisionBits.playerCollisions),
        GMover,
        GControllable,
//        GKinematic,
        GShooter,
        GObjectParticleEmitter {

    override val dataObjectPartEmitter: GDataObjectParticle = GDataObjectParticle(4, 1f, 1f, 1f)
    override val shotPatterns = gdxArrayOf(ShotPatterns.basic)
    override val dataMover: GDataMover = GDataMover(Const.playerSpeed)
    override val input: GInput = GInput()
    override val shouldShoot: Boolean get() = Gdx.input.isTouched
    override val shotDir: GVec2
        get() = GVec2.get((GInput.x() - Bullet.dim.hw) - GInput.centerW(), (GInput.y() - Bullet.dim.hh) - GInput.centerH())

    var level = 1

    init {
        keyPressed(Input.Keys.UP,    {addVelocity(0f, Const.playerImpulse)})
        keyPressed(Input.Keys.DOWN,  {addVelocity(0f, -Const.playerImpulse)})
        keyPressed(Input.Keys.LEFT,  {addVelocity(-Const.playerImpulse, 0f)})
        keyPressed(Input.Keys.RIGHT, {addVelocity(Const.playerImpulse, 0f)})
    }

    override fun setup(): GActor {
        super.setup()
        body.fixtureList.first().friction = 1f
        body.fixtureList.first().density = 1f
        return this
    }

    override fun move() {
        clampSpeed()
        setSpeed2(speed2 * 0.4f)
        super.move()
    }

    fun powerup() {
        level++
        val basePattern = shotPatterns.get(0)
        shotPatterns.add(
                GShotPattern(
                        basePattern.cd * level,
                        basePattern.offsetAngle + (if (level % 2 == 0) -level * 4 else level * 4),
                        basePattern.weapon)
        )
    }

    companion object {
        fun get(): Player {
            return Player()
        }
    }
}