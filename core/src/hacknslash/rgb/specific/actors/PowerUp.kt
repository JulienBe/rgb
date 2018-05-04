package hacknslash.rgb.specific.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import hacknslash.rgb.general.bundles.GBundle
import hacknslash.rgb.general.gameobjects.GActor
import hacknslash.rgb.general.gameobjects.GAnimated
import hacknslash.rgb.general.gameobjects.GTtl
import hacknslash.rgb.general.physics.GDim
import hacknslash.rgb.general.physics.GVec2
import hacknslash.rgb.specific.CollisionBits

class PowerUp private constructor(initPos: GVec2) :
        GActor(dim, initPos, CollisionBits.powerUp, CollisionBits.powerUpCollision),
        GTtl,
        GAnimated {

    override var ttl: Float = 6f
    override val anim: Animation<TextureRegion> = GBundle.bundle.assMan.getAnimation("PowerUp")

    override fun collide(other: GActor) {
        if (other is Player) {
            ttlExpired()
            other.powerup()
        }
        super.collide(other)
    }

    override fun animate() {
        GBundle.bundle.batch.setColor(1f, 0.8f, 8f, 1f)
        super.animate()
        GBundle.bundle.batch.color = Color.WHITE
    }

    companion object {
        val dim = GDim(4f, 4f)

        fun get(initPos: GVec2): PowerUp {
            return PowerUp(initPos)
        }
    }
}