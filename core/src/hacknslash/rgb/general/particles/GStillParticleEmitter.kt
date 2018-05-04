package hacknslash.rgb.general.particles

import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GParticleEmitter

interface GStillParticleEmitter: GParticleEmitter {

    override fun emit() {
        this as GActor
        val p = GStillParticle.get()
        p.x = x
        p.y = y
        GBundle.bundle.particles.add(p)
    }
}