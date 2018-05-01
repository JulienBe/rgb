package hacknslash.rgb.specific

import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.containers.GParticlesContainer
import hacknslash.rgb.general.datas.GDataObjectParticle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GHitter
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.gameobjects.GTtl
import hacknslash.rgb.general.particles.GObjectParticleEmitter
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

class Bullet private constructor(initPos: GVec2, initDir: GVec2, physic: GPhysic, assMan: GAssMan) :
        GActor(dim, initPos, physic, CollisionBits.bullet, CollisionBits.bulletCollisions),
        GMover,
        GTtl,
        GHitter,
        GObjectParticleEmitter {

    override val dataObjectPartEmitter: GDataObjectParticle = GDataObjectParticle(8, 1f, 1f, 1f)
    override val pPos = GVec2.get()
    override val maxSpeed = Const.bulletSpeed
    override var ttl: Float = 3f
    override val strength: Int = 5

    init {
        applyForce(initDir)
        setDamping(0f)
    }

    override fun ttl(): Int {
        return 4
    }

    override fun collide(other: GActor, particleContainer: GParticlesContainer) {
        if (other is Enemy)
            ttlExpired()
        super.collide(other, particleContainer)
    }

    companion object {
        val dim = GDim(1f, 1f)

        fun get(initPos: GVec2, initDir: GVec2, physic: GPhysic, assMan: GAssMan): Bullet {
            initDir.nor()
            return Bullet(
                    initPos,
                    initDir.scl(Const.bulletSpeed),
                    physic,
                    assMan)
        }
    }
}