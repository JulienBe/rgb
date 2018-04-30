package hacknslash.rgb.general.particles

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Pool
import hacknslash.rgb.general.GAssMan
import hacknslash.rgb.general.GRand
import hacknslash.rgb.general.gameobjects.GActor

class GObjectParticle internal constructor() : GParticle() {

    var initWidth = 0f
    var width: Float = 0f
    var ttl: Int = 0
    var offset = 0f
    var color = 1f
    var dirX = 0f
    var dirY = 0f
    var r = 1f
    var g = 1f
    var b = 1f

    override fun reset() {
    }

    override fun draw(batch: SpriteBatch): Boolean {
        color *= 0.95f
        batch.setColor(r * color, g * color, b * color, 1f)
        batch.draw(texture, x, y, width, width)
        offset = (width - ((width * 0.94f))) / 2f
        dirX *= 0.8f
        dirY *= 0.8f
        x += offset + dirX
        y += offset + dirY
        width *= 0.94f
        return ttl-- < 0
    }

    override fun free() {
        GStaticParticlePool.free(this)
    }

    companion object {
        fun get(x: Float, y: Float, width: Float, ttl: Int, r: Float, g: Float, b: Float, assMan: GAssMan): GObjectParticle {
            val p = GStaticParticlePool.obtain()
            p.x = x
            p.y = y
            p.ttl = ttl
            p.width = width
            p.initWidth = width
            p.texture = assMan.square()
            p.dirX = GRand.gauss(0.2f)
            p.dirY = GRand.gauss(0.2f)
            p.r = r
            p.g = g
            p.b = b
            p.color = 1f
            return p
        }

        fun get(x: Float, y: Float, other: GObjectParticle): GObjectParticle {
            val p = GStaticParticlePool.obtain()
            p.x = x
            p.y = y
            p.ttl = other.ttl
            p.width = other.width
            p.initWidth = other.initWidth
            p.texture = other.texture
            p.dirX = GRand.gauss(0.2f)
            p.dirY = GRand.gauss(0.2f)
            p.r = other.r
            p.g = other.g
            p.b = other.b
            p.color = other.color
            return p
        }

        fun get(a: GActor, assMan: GAssMan): GObjectParticle {
            a as GObjectParticleEmitter
            return get(
                    a.x + GRand.gauss(a.offsetAmplitude),
                    a.y + GRand.gauss(a.offsetAmplitude),
                    a.dim.width,
                    a.ttl(),
                    a.r,
                    a.g,
                    a.b,
                    assMan)
        }
    }

}

private object GStaticParticlePool : Pool<GObjectParticle>() {

    override fun newObject(): GObjectParticle {
        return GObjectParticle()
    }
}