package hacknslash.rgb.specific.actors

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GAnimated
import hacknslash.rgb.general.gameobjects.GKinematic
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class EnergyMagnet(pos: GVec2): GActor(dim, pos, CollisionBits.magnet, CollisionBits.magnetCollision),
        GKinematic,
        GAnimated {

    override val anim: Animation<TextureRegion> = GBundle.bundle.assMan.getAnimation("Magnet")

    override fun animate() {
        GBundle.bundle.batch.setColor(0.5f, 1f, 0.5f, 1f)
        super.animate()
    }

    companion object {
        val dim = GDim(5f, 5f)
    }
}