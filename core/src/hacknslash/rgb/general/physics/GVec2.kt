package hacknslash.rgb.general.physics

import com.badlogic.gdx.math.Vector2

class GVec2 private constructor(x: Float, y: Float) : Vector2(x, y) {

    override fun rotate(degrees: Float): GVec2 {
        return super.rotate(degrees) as GVec2
    }

    override fun scl(scalar: Float): GVec2 {
        return super.scl(scalar) as GVec2
    }

    override fun add(v: Vector2?): GVec2 {
        return super.add(v) as GVec2
    }

    companion object {
        fun get() = GVec2(0f, 0f)
        fun get(x: Float, y: Float) = GVec2(x, y)
        fun get(vec2: Vector2): GVec2 = GVec2(vec2.x, vec2.y)
    }
}