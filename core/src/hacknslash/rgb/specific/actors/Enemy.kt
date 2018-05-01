package hacknslash.rgb.specific.actors

import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.behaviors.GAiBTree
import hacknslash.rgb.general.behaviors.GAvoider
import hacknslash.rgb.general.behaviors.GTracker
import hacknslash.rgb.general.behaviors.GWanderer
import hacknslash.rgb.general.bundles.GActBundle
import hacknslash.rgb.general.datas.GDataHeartBeat
import hacknslash.rgb.general.datas.GDataObjectParticle
import hacknslash.rgb.general.datas.GHeartBeatSpeed
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.particles.GObjectParticle
import hacknslash.rgb.general.particles.GObjectParticleEmitter
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GSide
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits
import hacknslash.rgb.specific.Const
import ktx.collections.gdxArrayOf

class Enemy private constructor(x: Float, y: Float, assMan: GAssMan, physic: GPhysic) :
        GActor(Const.enemyDim, GVec2.get(x, y), physic, CollisionBits.enemy, CollisionBits.enemyCollisions),
        GMover,
        GSensor,
        GAiBTree,
        GTracker,
        GAvoider,
        GWanderer,
        GAnimated,
        GHeartBeat,
        GObjectParticleEmitter {

    private val explosion = assMan.getSound("Enemy")
    override val anim: Animation<TextureRegion> = assMan.getAnimation("Enemy")
    override val dataHB: GDataHeartBeat = GDataHeartBeat(0.7f, 0.98f, GHeartBeatSpeed.MEDIUM)
    override val dataObjectPartEmitter: GDataObjectParticle = GDataObjectParticle(3, 1f, 0f, 0f)
    override var stuffToAvoid = gdxArrayOf<GActor>()
    override var avoidImpulseStrenght: Float = 1f
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
        g = r / 4f
        offsetAmplitude = ((dataHB.currentHB - dataHB.minHB) * 2f)
    }

    override fun animate(batch: SpriteBatch) {
        batch.setColor(1f, g, b, 1f)
        super.animate(batch)
        batch.color = Color.WHITE
    }

    override fun ttl(): Int {
        return (10 * dataHB.currentHB).toInt()
    }

    override fun collide(other: GActor, bundle: GActBundle) {
        if (other is GHitter) {
            hp -= other.strength
        }
        super.collide(other, bundle)
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

    override fun dead(bundle: GActBundle) {
        count--
        explosion.play()
        val particles = bundle.particles.getMine(this)
        particles?.forEach {p ->
            p as GObjectParticle
            p.dirX *= 5f
            p.dirY *= 5f
            p.ttl *= 2
        }
        bundle.actors.add(PowerUp.get(GVec2.get(cx, cy), bundle))
        super.dead(bundle)
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