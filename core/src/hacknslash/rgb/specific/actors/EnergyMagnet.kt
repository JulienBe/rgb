package hacknslash.rgb.specific.actors

import hacknslash.rgb.general.GRand
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GKinematic
import hacknslash.rgb.general.particles.GColorGradient
import hacknslash.rgb.general.particles.GStillParticleEmitter
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class EnergyMagnet(pos: GVec2): GActor(dim, pos, CollisionBits.magnet, CollisionBits.magnetCollision),
        GKinematic,
        GStillParticleEmitter {

    var energy = 0
    var percentage = 0f
    var currentWidth = 0f
    var hWidth = 0f
    var nextEmit = 0
    var emitPeriod = 8
    var amount = 1

    override fun collide(other: GActor) {
        updateEnergy()
        emit()
        if (percentage >= 1f) {
            Enemy.new(x, y)
            hp = 0
        }
        super.collide(other)
    }

    private fun updateEnergy() {
        energy++
        percentage = energy / popValue
        currentWidth = dim.width * percentage
        hWidth = currentWidth / 2f
        emitPeriod = ((1f - percentage) * 8).toInt()
        amount = (1 + (percentage * 3)).toInt()
    }

    override fun emitAmount(): Int {
        return amount
    }

    override fun GActor.emitX(): Float {
        return GRand.float(x, x + w - Energy.w)
    }

    override fun GActor.emitY(): Float {
        return GRand.float(y, y + w - Energy.w)
    }

    override fun shouldEmit(): Boolean {
        if (nextEmit-- == 0) {
            nextEmit = emitPeriod
            return true
        }
        return false
    }

    companion object {
        val dim = GDim(5f, 5f)
        val popValue = 50f
        val colors = GColorGradient.ENERGY.colors
    }
}