package hacknslash.rgb.general.particles

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Pool
import hacknslash.rgb.general.bundles.GBundle

class GStillParticle: GParticle() {

    var index = 0

    init {
        texture = GBundle.bundle.assMan.getTexture("Energy")
    }

    override fun draw(batch: SpriteBatch): Boolean {
        batch.setColor(colors.colors[index++])
        batch.draw(texture, x, y, width, width)
        return index >= size
    }

    override fun free() {
        pool.free(this)
    }

    override fun reset() {
        index = 0
    }

    companion object {
        val colors: GColorGradient = GColorGradient.ENERGY
        val size = colors.colors.size
        const val width: Float = 0.5f

        fun get(): GStillParticle {
            return pool.obtain()
        }

        val pool = object : Pool<GStillParticle>() {
            override fun newObject(): GStillParticle {
                return GStillParticle()
            }
        }
    }
}