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
import hacknslash.rgb.general.gameobjects.GParticleEmitter
import hacknslash.rgb.general.gameobjects.GSensor
import hacknslash.rgb.general.particles.GObjectParticle
import hacknslash.rgb.general.particles.GObjectParticleEmitter
import hacknslash.rgb.general.particles.GStillParticle
import hacknslash.rgb.general.particles.GStillParticleEmitter
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class Energy(initPos: GVec2): GActor(dim, initPos, CollisionBits.energy, CollisionBits.energyCollision),
    GMover,
    GSensor,
    GStillParticleEmitter,
    GWanderer,
    GTracker {

    override val wandererData: GDataWanderer = GDataWanderer(100f, 1f)
    override val trackerData: GDataTracker = GDataTracker(0.3f)
    override var bTree: BehaviorTree<GAiBTree> = initTree("energy")
    override val sensorRadius: Float = 100f
    override val dataMover: GDataMover = GDataMover(1000f)
    var alternate = false

    override fun senses(a: GActor) {
        if (a is EnergyMagnet) {
            targets.add(a)
            targets.sort { one, two ->
                if (one.center.dst(cx, cy) > two.center.dst(cx, cy))
                    1
                else
                    -1
            }
        }
    }

    override fun emit() {
        if (alternate)
            super.emit()
        alternate = !alternate
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

    companion object {
        fun get(map: GMap): GActor {
            return Energy(GVec2.get(map.xInside(dim), map.xInside(dim)))
        }

        val dim = GDim(GStillParticle.width, GStillParticle.width)
    }
}