package hacknslash.rgb.specific

import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GDrawer
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.physics.GPhysic

class Enemy private constructor(assMan: GAssMan, physic: GPhysic, override val img: TextureRegion = assMan.square()) : GActor(dim, GVec2(), physic),
        GDrawer,
        GMover {
    override val pPos = GVec2()
    override val maxSpeed = 20f

    companion object {
        val dim = GDim(30f, 30f)
        var count = 0

        fun get(assMan: GAssMan, physic: GPhysic): Enemy {
            count++
            return Enemy(assMan, physic)
        }
    }
}