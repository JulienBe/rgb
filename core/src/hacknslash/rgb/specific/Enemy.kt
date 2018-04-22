package hacknslash.rgb.specific

import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.behaviors.GAiBTree
import hacknslash.rgb.general.behaviors.GTracker
import hacknslash.rgb.general.behaviors.GWanderer
import hacknslash.rgb.general.graphics.GAssMan
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GSide

class Enemy private constructor(x: Float, y: Float, assMan: GAssMan, physic: GPhysic, override val img: TextureRegion = assMan.square()) :
        GActor(dim, GVec2.get(x, y), physic),
        GDrawable,
        GMover,
        GSensor,
        GAiBTree,
        GTracker,
        GWanderer {

    override val pPos = GVec2.get()
    override val maxSpeed = 20f
    override var hp = 10
    override val sensorRadius = dim.width * 3
    override var bTree: BehaviorTree<GAiBTree> = initTree("enemy")
    override var target: GActor? = null
    override var trackImpulseStrength: Float = 10f
    override var prevRotation: GSide = GSide.RIGHT

    override fun collide(other: GActor) {
        if (other is GHitter)
            hp -= other.strength
        super.collide(other)
    }

    override fun senses(a: GActor) {
        if (a is Player)
            target = a
        super.senses(a)
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