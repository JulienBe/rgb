package hacknslash.rgb.general.containers

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import hacknslash.rgb.general.gameobjects.GParticleEmitter
import hacknslash.rgb.general.particles.GParticle
import ktx.collections.*

class GParticlesContainer {

    private val tracked = gdxMapOf<GParticleEmitter, GdxArray<GParticle>>()
    private val free = gdxSetOf<GParticle>()

    fun draw(batch: SpriteBatch) {
        tracked.forEach { map ->
            map.value.removeAll(
                    map.value.filter {
                        it.draw(batch)
                    })
        }
        free.forEach {
            if (it.draw(batch))
                free.remove(it)
        }
    }

    fun add(actor: GParticleEmitter, p: GParticle) {
        if (!tracked.containsKey(actor))
            tracked.put(actor, gdxArrayOf(p))
        else
            tracked[actor].add(p)
    }

    fun getMine(emitter: GParticleEmitter): GdxArray<GParticle>? {
        return tracked[emitter]
    }

    fun add(p: GParticle) {
        free.add(p)
    }
}