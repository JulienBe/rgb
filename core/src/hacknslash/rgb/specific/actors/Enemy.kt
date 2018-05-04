package hacknslash.rgb.specific.actors

import com.badlogic.gdx.ai.btree.BehaviorTree
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.behaviors.GAiBTree
import hacknslash.rgb.general.behaviors.GAvoider
import hacknslash.rgb.general.behaviors.GTracker
import hacknslash.rgb.general.behaviors.GWanderer
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.datas.*
import hacknslash.rgb.general.gameobjects.*
import hacknslash.rgb.general.particles.GObjectParticle
import hacknslash.rgb.general.particles.GObjectParticleEmitter
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits
import hacknslash.rgb.specific.Const
import ktx.collections.gdxArrayOf

class Enemy private constructor(x: Float, y: Float) :
        GActor(Const.enemyDim, GVec2.get(x, y), CollisionBits.enemy, CollisionBits.enemyCollisions),
        GMover,
        GSensor,
        GAiBTree,
        GTracker,
        GAvoider,
        GWanderer,
        GAnimated,
        GHeartBeat,
        GObjectParticleEmitter {

    private val explosion = GBundle.bundle.assMan.getSound("Enemy")
    override val dataMover: GDataMover = GDataMover(Const.enemySpeed)
    override val anim: Animation<TextureRegion> = GBundle.bundle.assMan.getAnimation("Enemy")
    override val dataHB: GDataHeartBeat = GDataHeartBeat(0.7f, 0.98f, GHeartBeatSpeed.MEDIUM)
    override val dataObjectPartEmitter: GDataObjectParticle = GDataObjectParticle(3, 1f, 0f, 0f)
    override var stuffToAvoid = gdxArrayOf<GActor>()
    override var avoidImpulseStrenght: Float = 1f
    override val wandererData: GDataWanderer = GDataWanderer(maxSpeed / 2f, 0.2f)
    override val trackerData: GDataTracker = GDataTracker(0.2f)
    override var hp = 10
    override val sensorRadius = Const.enemySenseRadius
    override var bTree: BehaviorTree<GAiBTree> = initTree("enemy")

    override fun beat() {
        super.beat()
        r = dataHB.currentHB
        g = r / 4f
        offsetAmplitude = ((dataHB.currentHB - dataHB.minHB) * 2f)
    }

    override fun animate() {
        GBundle.bundle.batch.setColor(1f, g, b, 1f)
        super.animate()
        GBundle.bundle.batch.color = Color.WHITE
    }

    override fun ttl(): Int {
        return (10 * dataHB.currentHB).toInt()
    }

    override fun collide(other: GActor) {
        if (other is GHitter) {
            hp -= other.strength
        }
        super.collide(other)
    }

    override fun senses(a: GActor) {
        if (a is Player)
            targets.add(a)
        if (a is Bullet)
            stuffToAvoid.add(a)
        super.senses(a)
    }

    override fun stopSenses(a: GActor) {
        if (a is Player)
            targets.removeValue(a, true)
        if (a is Bullet)
            stuffToAvoid.removeValue(a, true)
        super.senses(a)
    }

    override fun dead() {
        count--
        explosion.play()
        val particles = GBundle.bundle.particles.getMine(this)
        particles?.forEach {p ->
            p as GObjectParticle
            p.dirX *= 5f
            p.dirY *= 5f
            p.ttl *= 2
        }
        GBundle.bundle.actors.add(PowerUp.get(GVec2.get(cx, cy)))
        super.dead()
    }

    companion object {
        var count = 0
        val dim = Const.enemyDim

        fun get(x: Float, y: Float): Enemy {
            count++
            return Enemy(x, y)
        }
    }
}