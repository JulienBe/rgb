package hacknslash.rgb.general.particles

import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.GRand
import hacknslash.rgb.general.datas.GDataObjectParticle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GParticleEmitter

interface GObjectParticleEmitter: GParticleEmitter {

    val dataObjectPartEmitter: GDataObjectParticle
    var r: Float get() = dataObjectPartEmitter.r
        set(r) { dataObjectPartEmitter.r = r }
    var g: Float get() = dataObjectPartEmitter.g
        set(g) { dataObjectPartEmitter.g = g }
    var b: Float get() = dataObjectPartEmitter.b
        set(b) { dataObjectPartEmitter.b = b }
    var offsetAmplitude: Float get() = dataObjectPartEmitter.offsetAmplitude
        set(offsetAmplitude) { dataObjectPartEmitter.offsetAmplitude = offsetAmplitude }


    fun ttl(): Int {
        return 20 + GRand.gauss(3)
    }

    override fun emit() {
        for (i in 0..dataObjectPartEmitter.particlesAmout)
            GBundle.bundle.particles.add(this, getObjectParticle())
    }

    fun getObjectParticle() = GObjectParticle.get(this as GActor)
}