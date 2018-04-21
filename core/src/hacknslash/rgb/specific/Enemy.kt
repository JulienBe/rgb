package hacknslash.rgb.specific

import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.general.physics.GPhysic

class Enemy private constructor(x: Float, y: Float, assMan: GAssMan, physic: GPhysic, override val img: TextureRegion = assMan.square()) :
        GActor(dim, GVec2.get(x, y), physic),
        GDrawable,
        GMover,
        GSensor {
    override val pPos = GVec2.get()
    override val maxSpeed = 20f
    override var hp = 10
    override val sensorRadius = dim.width * 3

    override fun collide(other: GActor) {
        if (other is GHitter)
            hp -= other.strength
        super.collide(other)
    }

    companion object {
        val dim = GDim(10f, 10f)
        var count = 0

        fun get(assMan: GAssMan, physic: GPhysic): Enemy {
            count++
            return Enemy(40f, 40f, assMan, physic)
        }
    }
}