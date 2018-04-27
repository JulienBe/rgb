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
        color = (width / initWidth) * 0.98f
        batch.setColor(r * color, g * color, b * color, 1f)
        batch.draw(texture, x, y, width, width)
        offset = (width - ((width * 0.94f))) / 2f
        x += offset + dirX
        y += offset + dirY
        dirX *= 0.97f
        dirY *= 0.97f
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
            p.dirX = GRand.gauss(0.1f)
            p.dirY = GRand.gauss(0.1f)
            p.r = r
            p.g = g
            p.b = b
            return p
        }

        fun get(a: GActor, assMan: GAssMan): GObjectParticle {
            a as GObjectParticleEmitter
            return get(
                    a.x + GRand.gauss(0.2f * a.dim.width),
                    a.y + GRand.gauss(0.2f * a.dim.width),
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