package hacknslash.rgb.specific.actors

import com.badlogic.gdx.ai.btree.BehaviorTree
import hacknslash.rgb.general.GMap
import hacknslash.rgb.general.behaviors.GAiBTree
import hacknslash.rgb.general.behaviors.GTracker
import hacknslash.rgb.general.behaviors.GWanderer
import hacknslash.rgb.general.datas.GDataMover
import hacknslash.rgb.general.datas.GDataObjectParticle
import hacknslash.rgb.general.datas.GDataTracker
import hacknslash.rgb.general.datas.GDataWanderer
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.gameobjects.GSensor
import hacknslash.rgb.general.particles.GObjectParticle
import hacknslash.rgb.general.particles.GObjectParticleEmitter
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class Energy(initPos: GVec2): GActor(dim, initPos, CollisionBits.energy, CollisionBits.energyCollision),
    GMover,
    GObjectParticleEmitter,
    GSensor,
    GWanderer,
    GTracker {

    override val wandererData: GDataWanderer = GDataWanderer(10f, 1f)
    override val trackerData: GDataTracker = GDataTracker(0.1f)
    override var bTree: BehaviorTree<GAiBTree> = initTree("energy")
    override val sensorRadius: Float = 50f
    override val dataMover: GDataMover = GDataMover(1000f)
    override val dataObjectPartEmitter: GDataObjectParticle = GDataObjectParticle(1, 0.5f, 1f, 0.5f, 0f)

    override fun senses(a: GActor) {
        if (a is EnergyMagnet)
            targets.add(a)
    }

    override fun collide(other: GActor) {
        if (other is EnergyMagnet)
            dead = true
        super.collide(other)
    }

    override fun stopSenses(a: GActor) {
        if (a is EnergyMagnet)
            targets.removeValue(a, true)
    }

    override fun getObjectParticle(): GObjectParticle {
        val p = super.getObjectParticle()
        p.dirY = 0f
        p.dirX = 0f
        return p
    }

    companion object {
        fun get(map: GMap): GActor {
            return Energy(GVec2.get(map.xInside(dim), map.xInside(dim)))
        }

        val dim = GDim(1f, 1f)
    }
}