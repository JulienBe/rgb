package hacknslash.rgb.specific

import hacknslash.rgb.general.graphics.GAssMan
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

class Bullet private constructor(initPos: GVec2, initDir: GVec2, physic: GPhysic, assMan: GAssMan) :
        GActor(dim, initPos, physic),
        GDrawable,
        GMover,
        GTtl,
        GHitter {

    override val img = assMan.square()
    override val pPos = GVec2.get()
    override val maxSpeed = Const.bulletSpeed
    override var ttl: Float = 3f
    override val strength: Int = 5

    init {
        // works but probably flimsy
        applyForce(initDir)
        setDamping(0f)
    }

    override fun collide(other: GActor) {
        if (other is Enemy)
            ttlExpired()
        println("Bullet.collide")
        super.collide(other)
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