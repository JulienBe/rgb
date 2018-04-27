package hacknslash.rgb.general.particles

import hacknslash.rgb.general.GActBundle
import hacknslash.rgb.general.GRand
import hacknslash.rgb.general.gameobjects.GActor

interface GObjectParticleEmitter {

    val particlesAmout: Int
    val r: Float
    val g: Float
    val b: Float

    fun ttl(): Int {
        return 20 + GRand.gauss(3)
    }

    fun emit(bundle: GActBundle) {
        this as GActor
        bundle.particles.add(GObjectParticle.get(this, bundle.assMan))
    }
}