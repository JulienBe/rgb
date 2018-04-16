package hacknslash.rgb.general

import com.badlogic.gdx.math.Vector2

interface GMover {
    val pos get() = Vector2()
    val pPos get() = Vector2()
    val dir get() = Vector2()

    fun move(delta: Float) {
        pPos.set(pos)
        pos.add(dir.x * delta, dir.y * delta)
    }
}