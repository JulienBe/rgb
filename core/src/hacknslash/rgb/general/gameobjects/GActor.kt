package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import hacknslash.rgb.general.behaviors.GAiBTree
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2

open class GActor(val dim: GDim, val initPos: GVec2, val isA: Short, val collidesWith: Short) {

    internal lateinit var body: Body
    val bodyType: BodyDef.BodyType get() = body.type
    val dir: Vector2 get() = body.linearVelocity
    val speed2: Float get() = body.linearVelocity.len2()
    val center: Vector2 get() = body.position
    val speedX: Float get() = body.linearVelocity.x
    val speedY: Float get() = body.linearVelocity.y
    val cx: Float get() = center.x
    val cy: Float get() = center.y
    val x: Float get() = center.x - dim.hw
    val y: Float get() = center.y - dim.hh
    val w: Float get() = dim.width
    val h: Float get() = dim.height
    val hw: Float get() = dim.hw
    val hh: Float get() = dim.hh
    val rightX: Float get() = x + w
    val upY: Float get() = y + h
    open var hp = 1
    var dead = false

    open fun setup(): GActor {
        body = GBundle.bundle.physic.createBody(this)
        dead = false
        hp = 1
        return this
    }

    open fun act(): Boolean {
        if (this is GControllable)          control()
        if (this is GShooter)               shoot()
        if (this is GMover)                 move()
        if (this is GTtl)                   checkTtl()
        if (this is GDrawable)              draw()
        if (this is GAiBTree)               step()
        if (this is GParticleEmitter)       emit()
        if (this is GHeartBeat)             beat()
        if (this is GAnimated)              animate()
        return dead
    }

    open fun dead() {
        dead = true
    }

    open fun collide(other: GActor) {
        if (hp <= 0 && !dead)
            dead()
    }

    open fun stopCollide(other: GActor) {
    }

    fun applyForce(dir: Vector2) {
        body.applyForceToCenter(dir, true)
    }

    fun setDamping(d: Float) {
        body.linearDamping = d
    }

    fun setSpeed2(velocity: Float) {
        body.linearVelocity = body.linearVelocity.setLength2(velocity)
    }

    fun impulse(x: Float, y: Float) {
        body.applyLinearImpulse(x, y, cx, cy, true)
    }

    fun addVelocity(x: Float, y: Float) {
        body.linearVelocity = body.linearVelocity.add(x, y)
    }

    companion object {
        fun initBody(actor: GActor): Body {
            return GBundle.bundle.physic.createBody(actor)
        }
    }

}