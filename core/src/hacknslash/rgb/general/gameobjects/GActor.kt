package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import hacknslash.rgb.general.GActBundle
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

open class GActor(val dim: GDim, val initPos: GVec2, val physic: GPhysic) {
    private lateinit var box2DBody: Body
    val body: Body get() {
        if (!::box2DBody.isInitialized)
            box2DBody = physic.createBody(this)
        return box2DBody
    }
    var dead = false
    val center: Vector2 get() = body.position
    val cx: Float get() = center.x
    val cy: Float get() = center.y
    val x: Float get() = center.x - dim.hw
    val y: Float get() = center.y - dim.hh
    val w: Float get() = dim.width
    val h: Float get() = dim.height
    val hw: Float get() = dim.hw
    val hh: Float get() = dim.hh

    fun act(bundle: GActBundle): Boolean {
        if (this is GControllable)  control(bundle.input)
        if (this is GDrawable)      draw(bundle.batch)
        if (this is GShooter)       shoot(bundle.assMan, bundle.physic, bundle.actors)
        if (this is GMover)         move(bundle.delta)
        if (this is GTtl)           checkTtl(bundle.delta)
        return dead
    }

    fun collide(other: GActor) {
    }

    fun remove() {
        dead = true
    }
}