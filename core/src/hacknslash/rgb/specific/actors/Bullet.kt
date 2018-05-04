package hacknslash.rgb.specific.actors

import com.badlogic.gdx.utils.Pool
import hacknslash.rgb.general.datas.GDataMover
import hacknslash.rgb.general.datas.GDataObjectParticle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GHitter
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.gameobjects.GTtl
import hacknslash.rgb.general.particles.GObjectParticleEmitter
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits
import hacknslash.rgb.specific.Const

class Bullet private constructor(initPos: GVec2, initDir: GVec2) :
        GActor(dim, initPos, CollisionBits.bullet, CollisionBits.bulletCollisions),
        GMover,
        GTtl,
        GHitter,
        GObjectParticleEmitter {

    constructor() : this(GVec2.get(), GVec2.get())

    override val dataObjectPartEmitter: GDataObjectParticle = GDataObjectParticle(8, 1f, 0.8f, 0.8f)
    override val dataMover: GDataMover = GDataMover(Const.bulletSpeed)
    override var ttl: Float = 3f
    override val strength: Int = 5

    init {
        applyForce(initDir)
        setDamping(0f)
    }

    override fun ttl(): Int {
        return 4
    }

    override fun collide(other: GActor) {
        if (other is Enemy)
            ttlExpired()
        super.collide(other)
    }

    companion object {
        val dim = GDim(1f, 1f)

        fun get(initPos: GVec2, initDir: GVec2): Bullet {
            initDir.nor()
            return Bullet(
                    initPos,
                    initDir.scl(Const.bulletSpeed))
        }
    }
}

private object BulletPool : Pool<Bullet>() {
    override fun newObject(): Bullet {
        return Bullet()
    }
}