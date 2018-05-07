package hacknslash.rgb.specific.actors

import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.utils.Pool
import hacknslash.rgb.general.map.GMap
import hacknslash.rgb.general.behaviors.GAiBTree
import hacknslash.rgb.general.behaviors.GTracker
import hacknslash.rgb.general.behaviors.GWanderer
import hacknslash.rgb.general.datas.GDataMover
import hacknslash.rgb.general.datas.GDataTracker
import hacknslash.rgb.general.datas.GDataWanderer
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GMover
import hacknslash.rgb.general.gameobjects.GSensor
import hacknslash.rgb.general.particles.GStillParticle
import hacknslash.rgb.general.particles.GStillParticleEmitter
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class Energy private constructor(initPos: GVec2): GActor(dim, initPos, CollisionBits.energy, CollisionBits.energyCollision),
    GMover,
    GSensor,
    GStillParticleEmitter,
    GWanderer,
    GTracker {

    override val wandererData: GDataWanderer = GDataWanderer(100f, 1f)
    override val trackerData: GDataTracker = GDataTracker(0.3f)
    override val dataMover: GDataMover = GDataMover(1000f)
    override var bTree: BehaviorTree<GAiBTree> = initTree("energy")
    override val sensorRadius: Float = 100f
    var alternate = false

    override fun senses(a: GActor) {
        if (a is EnergyMagnet) {
            targets.add(a)
            targets.sort { one, two ->
                if (one.center.dst(cx, cy) > two.center.dst(cx, cy)) 1 else -1
            }
        }
    }

    override fun shouldEmit(): Boolean {
        alternate = !alternate
        return alternate
    }

    override fun collide(other: GActor) {
        if (other is EnergyMagnet)
            hp = 0
        super.collide(other)
    }

    override fun stopSenses(a: GActor) {
        if (a is EnergyMagnet)
            targets.removeValue(a, true)
    }

    override fun dead() {
        super.dead()
        pool.free(this)
    }

    companion object {
        val dim = GDim(GStillParticle.width, GStillParticle.width)
        val w = dim.width
        val hw = w / 2f

        val pool = object : Pool<Energy>() {
            override fun newObject(): Energy {
                return Energy(GVec2.get())
            }
        }

        fun get(map: GMap): GActor {
            val e = pool.obtain()
            e.initPos.x = map.xInside(dim)
            e.initPos.y = map.yInside(dim)
            return e
        }
    }
}