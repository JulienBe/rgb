package hacknslash.rgb.general.gameobjects

import hacknslash.rgb.general.bundles.GActBundle
import hacknslash.rgb.general.particles.GParticle

interface GParticleEmitter {
    fun emit(bundle: GActBundle)
    fun particleEnded(particle: GParticle) {}
}