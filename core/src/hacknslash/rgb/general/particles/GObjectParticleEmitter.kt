package hacknslash.rgb.general.particles

import hacknslash.rgb.general.bundles.GActBundle
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

    override fun emit(bundle: GActBundle) {
        for (i in 0..dataObjectPartEmitter.particlesAmout)
            bundle.particles.add(this, getObjectParticle(bundle))
    }

    fun getObjectParticle(bundle: GActBundle) = GObjectParticle.get(this as GActor, bundle.assMan)
}