package hacknslash.rgb.general.particles

import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GParticleEmitter

interface GStillParticleEmitter: GParticleEmitter {

    override fun emit() {
        this as GActor
        if (shouldEmit())
            for (i in 1..emitAmount())
                GBundle.bundle.particles.add(GStillParticle.get(emitX(), emitY()))
    }

    fun GActor.emitX() = x
    fun GActor.emitY() = y
    fun emitAmount(): Int = 1
    fun shouldEmit(): Boolean = true
}