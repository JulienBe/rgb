package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import hacknslash.rgb.general.GActBundle
import hacknslash.rgb.general.behaviors.GAiBTree
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

open class GActor(val dim: GDim, val initPos: GVec2, val physic: GPhysic, val isA: Short, val collidesWith: Short) {
    private lateinit var box2DBody: Body
    private val body: Body get() {
        if (!::box2DBody.isInitialized)
            box2DBody = physic.createBody(this)
        return box2DBody
    }
    val bodyType: BodyDef.BodyType get() = body.type
    val dir: Vector2 get() = body.linearVelocity
    open var hp = 1
    var dead = false
    val speed2: Float get() = body.linearVelocity.len2()
    val center: Vector2 get() = body.position
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

    open fun act(bundle: GActBundle): Boolean {
        if (this is GControllable)  control(bundle.input)
        if (this is GShooter)       shoot(bundle.assMan, bundle.physic, bundle.actors)
        if (this is GMover)         move(bundle.delta)
        if (this is GTtl)           checkTtl(bundle.delta)
        if (this is GDrawable)      draw(bundle.batch)
        if (this is GAiBTree)       step()
        return dead
    }

    open fun dead() {
        remove()
    }

    open fun collide(other: GActor) {
        if (hp <= 0)
            dead()
    }

    open fun stopCollide(other: GActor) {
    }

    fun remove() {
        dead = true
    }

    fun applyForce(dir: Vector2) {
        body.applyForceToCenter(dir, true)
    }

    fun setDamping(d: Float) {
        body.linearDamping = d
    }

    fun destroyBody(world: World) {
        world.destroyBody(body)
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

}