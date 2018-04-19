package hacknslash.rgb.general.gameobjects

import com.badlogic.gdx.math.Vector2
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GPhysic
import hacknslash.rgb.general.physics.GVec2

open class GActor(val dim: GDim, initPos: GVec2, physic: GPhysic) {
    val body = physic.createBody(initPos, dim)
    val center: Vector2 get() = body.position
    val x: Float get() = center.x - dim.hw
    val y: Float get() = center.y - dim.hh
    val w: Float get() = dim.width
    val h: Float get() = dim.height
}