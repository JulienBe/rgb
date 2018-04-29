package hacknslash.rgb.specific

import com.badlogic.gdx.ai.btree.BehaviorTree
import hacknslash.rgb.general.GArr
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.GDataHeartBeat
import hacknslash.rgb.general.behaviors.GAiBTree
import hacknslash.rgb.general.behaviors.GAvoider
import hacknslash.rgb.general.behaviors.GTracker
import hacknslash.rgb.general.behaviors.GWanderer
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.particles.GObjectParticleEmitter
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GSide
import hacknslash.rgb.general.physics.GVec2

class Enemy private constructor(x: Float, y: Float, assMan: GAssMan, physic: GPhysic) :
        GActor(Const.enemyDim, GVec2.get(x, y), physic, CollisionBits.enemy, CollisionBits.enemyCollisions),
        GMover,
        GSensor,
        GAiBTree,
        GTracker,
        GAvoider,
        GWanderer,
        GHeartBeat,
        GObjectParticleEmitter {

    override val dataHB: GDataHeartBeat = GDataHeartBeat()
    override var stuffToAvoid: GArr<GActor> = GArr()
    override var avoidImpulseStrenght: Float = 1f
    override var r: Float = 1f
    override var g: Float = 0f
    override val b: Float = 0f
    private val explosion = assMan.getEnemyExplosion()
    override val particlesAmout: Int get() = hp
    override val pPos = GVec2.get()
    override val maxSpeed = Const.enemySpeed
    override var trackImpulseStrength: Float = 0.2f
    override val wanderPush: Float = maxSpeed / 2f
    override val wanderPushDelay: Float = 0.2f
    override var hp = 10
    override val sensorRadius = Const.enemySenseRadius
    override var bTree: BehaviorTree<GAiBTree> = initTree("enemy")
    override var target: GActor? = null
    override var prevRotation: GSide = GSide.RIGHT

    override fun beat(delta: Float) {
        super.beat(delta)
        r = dataHB.currentHB
        g = r / 2f
    }

    override fun ttl(): Int {
        return hp * 10
    }

    override fun collide(other: GActor) {
        if (other is GHitter)
            hp -= other.strength
        super.collide(other)
    }

    override fun senses(a: GActor) {
        if (a is Player)
            target = a
        if (a is Bullet)
            stuffToAvoid.add(a)
        super.senses(a)
    }
    override fun stopSenses(a: GActor) {
        if (a is Player)
            target = null
        if (a is Bullet)
            stuffToAvoid.removeValue(a, true)
        super.senses(a)
    }

    override fun dead() {
        count--
        explosion.play()
        super.dead()
    }


    companion object {
        var count = 0
        val dim = Const.enemyDim

        fun get(x: Float, y: Float, assMan: GAssMan, physic: GPhysic): Enemy {
            count++
            return Enemy(x, y, assMan, physic)
        }
    }
}