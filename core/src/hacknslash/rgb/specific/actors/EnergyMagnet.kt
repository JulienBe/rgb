package hacknslash.rgb.specific.actors

import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GDrawable
import hacknslash.rgb.general.gameobjects.GKinematic
import hacknslash.rgb.general.particles.GColorGradient
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class EnergyMagnet(pos: GVec2): GActor(dim, pos, CollisionBits.magnet, CollisionBits.magnetCollision),
        GKinematic,
        GDrawable {

    var energy = 0
    var percentage = 0f
    var currentWidth = 0f
    var hWidth = 0f
    override val img: TextureRegion = GBundle.bundle.assMan.getTexture("Magnet")

    override fun draw() {
        GBundle.bundle.batch.setColor(colors.get(colors.size - (colors.size-1 * percentage).toInt()))
        GBundle.bundle.batch.draw(img, cx - hWidth, cy - hWidth, currentWidth, currentWidth)
    }

    override fun collide(other: GActor) {
        energy++
        percentage = energy / popValue
        currentWidth = dim.width * percentage
        hWidth = currentWidth / 2f
        if (percentage >= 1f) {
            Enemy.new(x, y)
            hp = 0
//            println("\tENEMY from $this")
        }
        super.collide(other)
    }

    companion object {
        val dim = GDim(5f, 5f)
        val popValue = 50f
        val colors = GColorGradient.ENERGY.colors
    }
}