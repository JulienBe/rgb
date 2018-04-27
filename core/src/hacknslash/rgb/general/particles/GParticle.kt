package hacknslash.rgb.general.particles

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool

abstract class GParticle: Pool.Poolable {
    var x: Float = 0f
    var y: Float = 0f
    lateinit var texture: TextureRegion

    abstract fun draw(batch: SpriteBatch): Boolean
    abstract fun free()

}