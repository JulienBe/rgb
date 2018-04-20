package hacknslash.rgb.specific

import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GDrawable
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

class Bullet private constructor(initPos: GVec2, initDir: GVec2, physic: GPhysic, assMan: GAssMan) : GActor(dim, initPos, physic),
        GDrawable,
        GMover {

    override val img = assMan.square()
    override val pPos = GVec2.get()
    override val maxSpeed = Bullet.maxSpeed

    init {
        // works but probably flimsy
        body.applyForceToCenter(initDir, true)
        body.linearDamping = 0f
    }

    companion object {
        val dim = GDim(1f, 1f)
        const val maxSpeed = Player.maxSpeed * 40f

        fun get(initPos: GVec2, initDir: GVec2, physic: GPhysic, assMan: GAssMan): Bullet {
            initDir.nor()
            return Bullet(
                    initPos,
                    initDir.scl(maxSpeed),
                    physic,
                    assMan)
        }
    }
}