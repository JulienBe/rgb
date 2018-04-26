package hacknslash.rgb.general.particles

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Pool
import hacknslash.rgb.general.GAssMan

class GStaticParticle internal constructor() : GParticle() {

    var width: Float = 0f
    var ttl: Int = 0

    override fun reset() {
    }


    override fun draw(batch: SpriteBatch): Boolean {
        batch.draw(texture, x, y, width, width)
        val offset = (width - ((width * 0.97f))) / 2f
        x += offset
        y += offset
        width *= 0.97f
        return ttl-- < 0
    }

    companion object {
        fun get(x: Float, y: Float, width: Float, ttl: Int, assMan: GAssMan): GStaticParticle {
            val p = GStaticParticlePool.obtain()
            p.x = x
            p.y = y
            p.ttl = ttl
            p.width = width
            p.texture = assMan.square()
            return p
        }
    }

}

private object GStaticParticlePool : Pool<GStaticParticle>() {

    override fun newObject(): GStaticParticle {
        return GStaticParticle()
    }
}